package in.zero.collection.link;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class AVLTreeTest extends BasicTest {

    private static AVLTree<Integer> asc;
    private static AVLTree<Integer> desc;

    final static Integer[] ascLevelOrderData = {17, 10, 33, 5, 15, 21, 71, 1, 7, 20, 25, 35, 73};
    final static Integer[] descLevelOrderData = {17, 33, 10, 71, 21, 15, 5, 73, 35, 25, 20, 7, 1};
    final static Integer[] ascPreOrderData = {17, 10, 5, 1, 7, 15, 33, 21, 20, 25, 71, 35, 73};
    final static Integer[] descPreOrderData = {17, 33, 71, 73, 35, 21, 25, 20, 10, 15, 5, 7, 1};
    final static Integer[] ascPostOrderData = {1, 7, 5, 15, 10, 20, 25, 21, 35, 73, 71, 33, 17};
    final static Integer[] descPostOrderData = {73, 35, 71, 25, 20, 21, 33, 15, 7, 1, 5, 10, 17};

    @BeforeAll
    public static void beforeAll() {
        asc = new AVLTree<>();
        asc.addAll(testData);

        desc = new AVLTree<>(BinarySearchTree.Sort.DESC);
        desc.addAll(testData);
    }

    @Test
    public void metadata() {
        final double minHeight = Math.floor(Math.log(testData.length) / Math.log(2));
        final double maxHeight = 1.44 * Math.log(testData.length) / Math.log(2);

        final int ascAvlTreeHeight = asc.getHeight();
        final int descAvlTreeHeight = asc.getHeight();

        assertEquals(asc.nodesCount, testData.length, "Node count for ASC AVL Tree not matches");
        assertEquals(ascAvlTreeHeight, 3, "ASC AVL tree height doesn't match the expected");
        assertTrue(ascAvlTreeHeight >= minHeight, "Min height for ASC AVL tree doesn't matches");
        assertTrue(ascAvlTreeHeight <= maxHeight, "Max height for ASC AVL tree doesn't matches");
        assertEquals("ASC", asc.getSortingOrder(), "Sorting order doesn't match for ASC AVL tree");

        assertEquals(desc.nodesCount, testData.length, "Node count for DESC AVL Tree not matches");
        assertEquals(descAvlTreeHeight, 3, "DESC AVL tree height doesn't match the expected");
        assertTrue(descAvlTreeHeight >= minHeight, "Min height for DESC AVL tree doesn't matches");
        assertTrue(descAvlTreeHeight <= maxHeight, "Max height for DESC AVL tree doesn't matches");
        assertEquals("DESC", desc.getSortingOrder(), "Sorting order doesn't match for DESC AVL tree");
    }

    @Test
    public void addElements() {
        AVLTree<Integer> avl = new AVLTree<>(AVLTree.Sort.ASC);
        avl.add(0);
        avl.addAll(testData);
        avl.add(19);
        assertTrue(avl.addNode(299) instanceof BinaryTreeNode, "ASC AVL Tree addNode not returns a BinaryTreeNode");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> avl.add(10), "Duplicate insertion in avl tree must throw exception");
        assertEquals("BST can't accept duplicate values", ex.getMessage(), "Addition throws exception message doesn't matches");
    }

    @Test
    public void searchElements() {
        assertTrue(Arrays.stream(testData).allMatch(asc::search), "Data missing in ASC AVL tree");
        assertTrue(Arrays.stream(testData).allMatch(desc::search), "Data missing in DESC AVL tree");
        BinaryTreeNode<Integer> node = asc.searchNode(21);
        assertTrue(node instanceof BinaryTreeNode, "ASC AVL Tree search node search not a BinaryTreeNode");
        assertNotNull(node, "ASC AVL Tree search node search results null");
        assertEquals(21, node.data, "ASC AVL Tree search node not searches the value");
    }

    @Test
    public void removeElements() {
        AVLTree<Integer> avl = new AVLTree<>(AVLTree.Sort.ASC);
        avl.addAll(testData);
        avl.add(299);

        assertTrue(avl.removeNode(299) instanceof BinaryTreeNode, "ASC AVL Tree removeNode not returns a BinaryTreeNode");
        assertEquals(avl.remove(-20), -20, "Removing non existing data from AVL tree");
        assertTrue(Arrays.stream(testData).allMatch(elem -> {
            Integer data = avl.remove(elem);
            assertFalse(avl.search(elem), "Searching deleted data in AVL tree");
            return data.equals(elem);
        }), "Deleting all the data from AVL tree");

        assertEquals(avl.nodesCount, 0, "AVL tree node count must be 0 after deleting all the elements");
        assertNull(avl.root, "AVL root node must be true after deleting all the elements");

    }

    @Test
    public void levelOrderTest() {
        Iterator<Integer> ascLevelOrder = asc.levelOrderIterator();
        Iterator<Integer> descLevelOrder = desc.levelOrderIterator();

        for (int count = 0; ascLevelOrder.hasNext(); count++) {
            assertEquals(ascLevelOrderData[count], ascLevelOrder.next(), "ASC AVL tree level order not matching");
        }

        for (int count = 0; descLevelOrder.hasNext(); count++) {
            assertEquals(descLevelOrderData[count], descLevelOrder.next(), "DESC AVL tree level order not matching");
        }
    }

    @Test
    public void preOrderTest() {
        Iterator<Integer> ascPreOrderOrder = asc.preOrderIterator();
        Iterator<Integer> descPreOrderOrder = desc.preOrderIterator();

        for (int count = 0; ascPreOrderOrder.hasNext(); count++) {
            assertEquals(ascPreOrderData[count], ascPreOrderOrder.next(), "ASC AVL tree Pre order not matching");
        }

        for (int count = 0; descPreOrderOrder.hasNext(); count++) {
            assertEquals(descPreOrderData[count], descPreOrderOrder.next(), "DESC AVL tree Pre order not matching");
        }
    }

    @Test
    public void postOrderTest() {
        Iterator<Integer> ascPostOrder = asc.postOrderIterator();
        Iterator<Integer> descPostOrder = desc.postOrderIterator();

        for (int count = 0; ascPostOrder.hasNext(); count++) {
            assertEquals(ascPostOrderData[count], ascPostOrder.next(), "ASC AVL tree Post order not matching");
        }

        for (int count = 0; descPostOrder.hasNext(); count++) {
            assertEquals(descPostOrderData[count], descPostOrder.next(), "DESC AVL tree Post order not matching");
        }
    }

    @Test
    public void inOrderTest() {
        Iterator<Integer> ascInOrder = asc.inOrderIterator();
        Iterator<Integer> descInOrder = desc.inOrderIterator();

        for (int count = 0; ascInOrder.hasNext(); count++) {
            assertEquals(ascInOrderData[count], ascInOrder.next(), "ASC AVL tree In order not matching");
        }

        for (int count = 0; descInOrder.hasNext(); count++) {
            assertEquals(descInOrderData[count], descInOrder.next(), "DESC AVL tree In order not matching");
        }
    }
}
