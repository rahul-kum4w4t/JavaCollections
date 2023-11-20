package in.zero.collection.link;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import in.zero.collection.Collection;

public class SplayTreeTest extends BSTBaseTest {

    SplayTreeTest() {
        super(
                new Integer[]{35, 33, 71, 5, 73, 1, 21, 7, 25, 17, 15, 20, 10},
                new Integer[]{35, 71, 33, 73, 5, 21, 1, 25, 7, 17, 20, 15, 10},
                new Integer[]{35, 33, 5, 1, 21, 7, 17, 15, 10, 20, 25, 71, 73},
                new Integer[]{35, 71, 73, 33, 5, 21, 25, 7, 17, 20, 15, 10, 1},
                new Integer[]{1, 10, 15, 20, 17, 7, 25, 21, 5, 33, 73, 71, 35},
                new Integer[]{73, 71, 25, 20, 10, 15, 17, 7, 21, 1, 5, 33, 35}
        );
    }

    @BeforeAll
    public static void beforeAll() {
        asc = new SplayTree<>();
        asc.addAll(testData);

        desc = new SplayTree<>(true);
        desc.addAll(testData);
    }

    @Test
    public void metadata() {

        super.metadata();
        assertEquals(7, asc.getHeight(), "ASC Splay Tree height doesn't match the expected");
        assertEquals(7, desc.getHeight(), "DESC Splay Tree height doesn't match the expected");

        assertTrue(asc instanceof SplayTree, "ASC Splay Tree is not instance of BinarySearchTree");
    }

    @Test
    public void addElements() {
        SplayTree<Integer> splay = new SplayTree<>();
        splay.add(0);
        splay.addAll(testData);
        splay.add(19);
        assertTrue(splay.addNode(299) instanceof BinaryTreeNode, "ASC Splay Tree addNode not returns a BinaryTreeNode");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> splay.add(10),
                "Duplicate insertion in Splay Tree must throw exception");
        assertEquals("BST can't accept duplicate values", ex.getMessage(),
                "Addition throws exception message doesn't matches");

        assertSame(splay.add(94), splay, "Add method not returning tree object in Splay Tree");
        assertSame(splay.addAll(97), splay, "Add All method not returning tree object in Splay Tree");

        assertEquals(splay.root.data, 97, "After addition splay tree root must have inserted value");
    }

    @Test
    public void searchElements() {
        SplayTree<Integer> ascSplay = new SplayTree<>();
        ascSplay.addAll(testData);
        SplayTree<Integer> descSplay = new SplayTree<>();
        descSplay.addAll(testData);

        assertTrue(Arrays.stream(testData).allMatch(ascSplay::search), "Data missing in ASC Splay Tree");
        assertTrue(Arrays.stream(testData).allMatch(descSplay::search), "Data missing in DESC Splay Tree");

        BinaryTreeNode<Integer> node = asc.searchNode(21);
        assertNotNull(node, "ASC Splay Tree search node search results null");
        assertEquals(21, node.data, "ASC Splay Tree search node not searches the value");

        descSplay.search(10);
        assertEquals(descSplay.root.data, 10, "After searching desc splay tree root must have searched value");
    }

    @Test
    public void removeElements() {
        SplayTree<Integer> splay = new SplayTree<>();
        splay.addAll(testData);

        assertEquals(splay.remove(-20), -20, "Removing non existing data from Splay Tree");
        assertTrue(Arrays.stream(testData).allMatch(elem -> {
            Integer data = splay.remove(elem);
            assertFalse(splay.search(elem), "Searching deleted data in Splay Tree");
            return data.equals(elem);
        }), "Deleting all the data from Splay Tree");

        assertEquals(0, splay.nodesCount, "Splay Tree node count must be 0 after deleting all the elements");
        assertNull(splay.root, "Splay tree root node must be null after deleting all the elements");

        SplayTree<Integer> avl1 = new SplayTree<>();
        avl1.addAll(testData);
        avl1.removeAll(21, 5, 71, 25);
        assertEquals(avl1.nodesCount, testData.length - 4,
                "Nodes count not matching after deletion of few values in Splay Tree");
    }
}
