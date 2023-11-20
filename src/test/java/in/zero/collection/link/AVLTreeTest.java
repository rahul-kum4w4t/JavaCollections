package in.zero.collection.link;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import in.zero.collection.Collection;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class AVLTreeTest extends BSTBaseTest {

    AVLTreeTest() {
        super(
                new Integer[]{17, 10, 33, 5, 15, 21, 71, 1, 7, 20, 25, 35, 73},
                new Integer[]{17, 33, 10, 71, 21, 15, 5, 73, 35, 25, 20, 7, 1},
                new Integer[]{17, 10, 5, 1, 7, 15, 33, 21, 20, 25, 71, 35, 73},
                new Integer[]{17, 33, 71, 73, 35, 21, 25, 20, 10, 15, 5, 7, 1},
                new Integer[]{1, 7, 5, 15, 10, 20, 25, 21, 35, 73, 71, 33, 17},
                new Integer[]{73, 35, 71, 25, 20, 21, 33, 15, 7, 1, 5, 10, 17}
        );
    }

    @BeforeAll
    public static void beforeAll() {
        asc = new AVLTree<>();
        asc.addAll(testData);

        desc = new AVLTree<>(true);
        desc.addAll(testData);
    }

    @Test
    public void metadata() {
        super.metadata();

        final double minHeight = Math.floor(Math.log(testData.length) / Math.log(2));
        final double maxHeight = 1.44 * Math.log(testData.length) / Math.log(2);

        final int ascAvlTreeHeight = asc.getHeight();
        final int descAvlTreeHeight = desc.getHeight();

        assertEquals(3, ascAvlTreeHeight, "ASC AVL tree height doesn't match the expected");
        assertTrue(ascAvlTreeHeight >= minHeight, "Min height for ASC AVL tree doesn't matches");
        assertTrue(ascAvlTreeHeight <= maxHeight, "Max height for ASC AVL tree doesn't matches");

        assertEquals(3, descAvlTreeHeight, "DESC AVL tree height doesn't match the expected");
        assertTrue(descAvlTreeHeight >= minHeight, "Min height for DESC AVL tree doesn't matches");
        assertTrue(descAvlTreeHeight <= maxHeight, "Max height for DESC AVL tree doesn't matches");

        assertTrue(asc instanceof AVLTree, "ASC AVL Tree is not instance of AVLTree");
        assertTrue(asc.root instanceof AVLTreeNode, this.getClass().getSimpleName() + "'s node is not instance of AVLTreeNode");
    }

    @Test
    public void addElements() {
        AVLTree<Integer> avl = new AVLTree<>();
        avl.add(0);
        avl.addAll(testData);
        avl.add(19);
        assertTrue(avl.addNode(299) instanceof BinaryTreeNode, "ASC AVL Tree addNode not returns a BinaryTreeNode");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> avl.add(10),
                "Duplicate insertion in avl tree must throw exception");
        assertEquals("BST can't accept duplicate values", ex.getMessage(),
                "Addition throws exception message doesn't matches");

        assertSame(avl.add(94), avl, "Add method not returning tree object in AVL Tree");
        assertSame(avl.addAll(97), avl, "Add All method not returning tree object in AVL Tree");

        BinaryTreeNode<Integer> node = avl.createNewNode(103, null);
        assertTrue(node instanceof AVLTreeNode, "Created Node doesn't return an instance of AVLTreeNode in AVL Tree");
        assertTrue(node instanceof BinaryTreeNode,
                "Created Node doesn't return an instance of BinaryTreeNode in AVL Tree");
    }

    @Test
    public void searchElements() {
        assertTrue(Arrays.stream(testData).allMatch(asc::search), "Data missing in ASC AVL tree");
        assertTrue(Arrays.stream(testData).allMatch(desc::search), "Data missing in DESC AVL tree");
        BinaryTreeNode<Integer> node = asc.searchNode(21);
        assertTrue(node instanceof AVLTreeNode, "ASC AVL Tree search node search not a AVLTreeNode");
        assertNotNull(node, "ASC AVL Tree search node search results null");
        assertEquals(21, node.data, "ASC AVL Tree search node not searches the value");
    }

    @Test
    public void removeElements() {
        AVLTree<Integer> avl = new AVLTree<>();
        avl.addAll(testData);
        avl.add(299);

        assertTrue(avl.removeNode(299) instanceof AVLTreeNode, "ASC AVL Tree removeNode not returns a AVLTreeNode");
        assertEquals(avl.remove(-20), -20, "Removing non existing data from AVL tree");
        assertTrue(Arrays.stream(testData).allMatch(elem -> {
            Integer data = avl.remove(elem);
            assertFalse(avl.search(elem), "Searching deleted data in AVL tree");
            return data.equals(elem);
        }), "Deleting all the data from AVL tree");

        assertEquals(0, avl.nodesCount, "AVL tree node count must be 0 after deleting all the elements");
        assertNull(avl.root, "AVL Tree root node must be null after deleting all the elements");

        AVLTree<Integer> avl1 = new AVLTree<>();
        avl1.addAll(testData);
        avl1.removeAll(21, 5, 71, 25);
        assertEquals(avl1.nodesCount, testData.length - 4,
                "Nodes count not matching after deletion of few values in AVL Tree");
    }
}
