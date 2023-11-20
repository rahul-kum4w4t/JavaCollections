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

public class RedBlackTreeTest extends BSTBaseTest {

    RedBlackTreeTest() {
        super(
                new Integer[]{20, 10, 25, 5, 15, 21, 71, 1, 7, 17, 33, 73, 35},
                new Integer[]{20, 25, 10, 71, 21, 15, 5, 73, 33, 17, 7, 1, 35},
                new Integer[]{20, 10, 5, 1, 7, 15, 17, 25, 21, 71, 33, 35, 73},
                new Integer[]{20, 25, 71, 73, 33, 35, 21, 10, 15, 17, 5, 7, 1},
                new Integer[]{1, 7, 5, 17, 15, 10, 21, 35, 33, 73, 71, 25, 20},
                new Integer[]{73, 35, 33, 71, 21, 25, 17, 15, 7, 1, 5, 10, 20}
        );
    }

    @BeforeAll
    public static void beforeAll() {
        asc = new RedBlackTree<>();
        asc.addAll(testData);

        desc = new RedBlackTree<>(true);
        desc.addAll(testData);
    }

    @Test
    public void metadata() {

        super.metadata();

        assertEquals(4, asc.getHeight(), "ASC RB Tree height doesn't match the expected");
        assertEquals(4, desc.getHeight(), "DESC RB Tree height doesn't match the expected");

        assertTrue(asc instanceof RedBlackTree, "ASC RB Tree is not instance of RedBlackTree");
        assertTrue(asc.root instanceof RedBlackTreeNode, this.getClass().getSimpleName() + "'s node is not instance of RedBlackTreeNode");
    }

    @Test
    public void addElements() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.add(0);
        rbt.addAll(testData);
        rbt.add(19);
        assertTrue(rbt.addNode(299) instanceof BinaryTreeNode, "ASC RB Tree addNode not returns a BinaryTreeNode");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> rbt.add(10),
                "Duplicate insertion in RB Tree must throw exception");
        assertEquals("BST can't accept duplicate values", ex.getMessage(),
                "Addition throws exception message doesn't matches");

        assertSame(rbt.add(94), rbt, "Add method not returning tree object in RB Tree");
        assertSame(rbt.addAll(97), rbt, "Add All method not returning tree object in RB Tree");

        BinaryTreeNode<Integer> node = rbt.createNewNode(103, null);
        assertTrue(node instanceof RedBlackTreeNode,
                "Created Node doesn't return an instance of RedBlackTreeNode in RB Tree");
        assertTrue(node instanceof BinaryTreeNode,
                "Created Node doesn't return an instance of BinaryTreeNode in RB Tree");
    }

    @Test
    public void searchElements() {
        assertTrue(Arrays.stream(testData).allMatch(asc::search), "Data missing in ASC RB Tree");
        assertTrue(Arrays.stream(testData).allMatch(desc::search), "Data missing in DESC RB Tree");
        BinaryTreeNode<Integer> node = asc.searchNode(21);
        assertTrue(node instanceof RedBlackTreeNode, "ASC RB Tree search node search not a BinaryTreeNode");
        assertNotNull(node, "ASC RB Tree search node search results null");
        assertEquals(21, node.data, "ASC RB Tree search node not searches the value");
    }

    @Test
    public void removeElements() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.addAll(testData);
        rbt.add(299);

        assertTrue(rbt.removeNode(299) instanceof RedBlackTreeNode,
                "ASC RB Tree removeNode not returns a RedBlackTreeNode");
        assertEquals(rbt.remove(-20), -20, "Removing non existing data from RB Tree");
        assertTrue(Arrays.stream(testData).allMatch(elem -> {
            Integer data = rbt.remove(elem);
            assertFalse(rbt.search(elem), "Searching deleted data in RB Tree");
            return data.equals(elem);
        }), "Deleting all the data from RB Tree");

        assertEquals(0, rbt.nodesCount, "RB Tree node count must be 0 after deleting all the elements");
        assertNull(rbt.root, "RB Tree root node must be null after deleting all the elements");

        RedBlackTree<Integer> avl1 = new RedBlackTree<>();
        avl1.addAll(testData);
        avl1.removeAll(21, 5, 71, 25);
        assertEquals(avl1.nodesCount, testData.length - 4,
                "Nodes count not matching after deletion of few values in RB Tree");
    }
}
