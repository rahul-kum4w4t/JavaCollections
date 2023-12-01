package in.zero.link.tree;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BinarySearchTreeTest extends BSTBaseTest {

    BinarySearchTreeTest() {
        super(
                new Integer[]{10, 1, 20, 7, 15, 25, 5, 17, 21, 33, 73, 71, 35},
                new Integer[]{10, 20, 1, 25, 15, 7, 33, 21, 17, 5, 73, 71, 35},
                new Integer[]{10, 1, 7, 5, 20, 15, 17, 25, 21, 33, 73, 71, 35},
                new Integer[]{10, 20, 25, 33, 73, 71, 35, 21, 15, 17, 1, 7, 5},
                new Integer[]{5, 7, 1, 17, 15, 21, 35, 71, 73, 33, 25, 20, 10},
                new Integer[]{35, 71, 73, 33, 21, 25, 17, 15, 20, 5, 7, 1, 10}
        );
    }

    @BeforeAll
    public static void beforeAll() {
        asc = new BinarySearchTree<>();
        asc.addAll(testData);

        desc = new BinarySearchTree<>(true);
        desc.addAll(testData);
    }

    @Test
    @Override
    public void metadata() {

        super.metadata();

        assertEquals(asc.getHeight(), 6, "ASC BST height doesn't match the expected");
        assertEquals(desc.getHeight(), 6, "DESC BST height doesn't match the expected");
    }

    @Test
    public void addElements() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(0);
        bst.addAll(testData);
        bst.add(19);

        assertTrue(bst.addNode(299) instanceof BinaryTreeNode, "ASC BST addNode does not returns a BinaryTreeNode");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> bst.add(10),
                "Duplicate insertion in BST must throw exception");
        assertEquals("BST can't accept duplicate values", ex.getMessage(),
                "Addition throws exception message doesn't matches");

        assertSame(bst.add(94), bst, "Add method not returning tree object in BST");
        assertSame(bst.addAll(97), bst, "Add All method not returning tree object in BST");

        BinaryTreeNode<Integer> node = bst.createNewNode(103, null);
        assertTrue(node instanceof BinaryTreeNode, "Created Node doesn't return an instance of BinaryTreeNode in BST");
    }

    @Test
    public void searchElements() {
        assertTrue(Arrays.stream(testData).allMatch(asc::search), "Data missing in ASC BST");
        assertTrue(Arrays.stream(testData).allMatch(desc::search), "Data missing in DESC BST");
        BinaryTreeNode<Integer> node = asc.searchNode(21);
        assertTrue(node instanceof BinaryTreeNode, "ASC BST search node search not a BinaryTreeNode");
        assertNotNull(node, "ASC BST search node search results null");
        assertEquals(21, node.data, "ASC BST search node not searches the value");
    }

    @Test
    public void removeElements() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.addAll(testData);
        bst.add(299);

        assertTrue(bst.removeNode(299) instanceof BinaryTreeNode, "ASC BST removeNode not returns a BinaryTreeNode");
        assertEquals(bst.remove(-20), -20, "Removing non existing data from BST");
        assertTrue(Arrays.stream(testData).allMatch(elem -> {
            Integer data = bst.remove(elem);
            assertFalse(bst.search(elem), "Searching deleted data in BST");
            return data.equals(elem);
        }), "Deleting all the data from BST");

        assertEquals(0, bst.nodesCount, "BST node count must be 0 after deleting all the elements");
        assertNull(bst.root, "BST root node must be null after deleting all the elements");

        BinarySearchTree<Integer> bst1 = new BinarySearchTree<>();
        bst1.addAll(testData);
        bst1.removeAll(21, 5, 71, 25);
        assertEquals(bst1.nodesCount, testData.length - 4,
                "Nodes count not matching after deletion of few values in BST");

    }
}
