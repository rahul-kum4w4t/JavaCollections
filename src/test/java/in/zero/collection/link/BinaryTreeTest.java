package in.zero.collection.link;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import in.zero.collection.Collection;

public class BinaryTreeTest {

    private static BinaryTree<Integer> bt;

    final static Integer[] testData = {10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35};
    final static Integer[] inOrderData = {21, 15, 5, 1, 33, 25, 73, 10, 71, 17, 35, 20, 7};
    final static Integer[] levelOrderData = {10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35};
    final static Integer[] preOrderData = {10, 1, 15, 21, 5, 25, 33, 73, 20, 17, 71, 35, 7};
    final static Integer[] postOrderData = {21, 5, 15, 33, 73, 25, 1, 71, 35, 17, 7, 20, 10};
    final static Integer[] reverseOrderData = {7, 20, 35, 17, 71, 10, 73, 25, 33, 1, 5, 15, 21};

    @BeforeAll
    public static void beforeAll() {
        bt = new BinaryTree<>();
        bt.addAll(testData);
    }

    @Test
    public void metadata() {

        assertEquals(bt.nodesCount, testData.length, "Node count for Binary Tree does not matches");
        assertEquals(bt.size(), testData.length, "Node count for Binary Tree does not matches");
        assertEquals(bt.getHeight(), 3, "Binary Tree height doesn't match the expected");

        assertTrue(bt instanceof BinaryTree, "Binary Tree is not an instance of BinaryTree");
        assertTrue(bt instanceof Collection, "Binary Tree is not an instance of Collection");
        assertTrue(bt instanceof Iterable, "Binary Tree is not an instance of Iterable");
        assertTrue(bt instanceof LinkBinaryTreeIterable, "Binary Tree is not an instance of LinkBinaryTreeIterable");
    }

    @Test
    public void addElements() {
        BinaryTree<Integer> bt = new BinaryTree<>();
        bt.add(0);
        bt.addAll(testData);
        bt.add(19);

        assertTrue(bt.addNode(299) instanceof BinaryTreeNode, "Binary Tree addNode does not returns a BinaryTreeNode");
        assertSame(bt.add(94), bt, "Add method not returning tree object in Binary Tree");
        assertSame(bt.addAll(97), bt, "Add All method not returning tree object in Binary Tree");

        BinaryTreeNode<Integer> node = bt.createNewNode(103, null);
        assertTrue(node instanceof BinaryTreeNode, "Created Node doesn't return an instance of BinaryTreeNode in BinaryTree");

    }

    @Test
    public void searchElements() {
        assertTrue(Arrays.stream(testData).allMatch(bt::search), "Data missing in Binary Tree");
        BinaryTreeNode<Integer> node = bt.searchNode(21);
        assertTrue(node instanceof BinaryTreeNode, "Binary Tree search node not a BinaryTreeNode");
        assertNotNull(node, "Binary Tree search node is null");
        assertEquals(21, node.data, "Binary Tree search node value not found");
    }

    @Test
    public void removeElements() {
        BinaryTree<Integer> bt = new BinaryTree<>();
        bt.addAll(testData);

        assertEquals(bt.remove(-20), -20, "Removing non existing data from Binary Tree");
        assertTrue(Arrays.stream(testData).allMatch(elem -> {
            Integer data = bt.remove(elem);
            assertFalse(bt.search(elem), "Searching deleted data in Binary Tree");
            return data.equals(elem);
        }), "Deleting all the data from Binary Tree");

        assertEquals(0, bt.nodesCount, "Binary Tree node count must be 0 after deleting all the elements");
        assertNull(bt.root, "Binary Tree root node must be null after deleting all the elements");

        BinaryTree<Integer> bt1 = new BinaryTree<>();
        bt1.addAll(testData);
        bt1.removeAll(21, 5, 71, 25);
        assertEquals(bt1.nodesCount, testData.length - 4,
                "Nodes count not matching after deletion of few values in Binary Tree");

    }

    @Test
    public void levelOrderTest() {
        Iterator<Integer> levelOrder = bt.levelOrderIterator();

        for (int count = 0; levelOrder.hasNext(); count++) {
            assertEquals(levelOrderData[count], levelOrder.next(), "Binary Tree level order not matching");
        }
    }

    @Test
    public void preOrderTest() {
        Iterator<Integer> preOrder = bt.preOrderIterator();

        for (int count = 0; preOrder.hasNext(); count++) {
            assertEquals(preOrderData[count], preOrder.next(), "Binary Tree Pre order not matching");
        }
    }

    @Test
    public void postOrderTest() {
        Iterator<Integer> postOrder = bt.postOrderIterator();

        for (int count = 0; postOrder.hasNext(); count++) {
            assertEquals(postOrderData[count], postOrder.next(), "Binary Tree Post order not matching");
        }
    }

    @Test
    public void inOrderTest() {
        Iterator<Integer> inOrder = bt.inOrderIterator();

        for (int count = 0; inOrder.hasNext(); count++) {
            assertEquals(inOrderData[count], inOrder.next(), "Binary Tree In order not matching");
        }
    }

    @Test
    public void reverseOrderTest() {
        Iterator<Integer> reverseOrder = bt.reverseOrderIterator();

        for (int count = 0; reverseOrder.hasNext(); count++) {
            assertEquals(reverseOrderData[count], reverseOrder.next(), "Binary Tree reverse order not matching");
        }
    }

    @Test
    public void iterator() {
        Iterator<Integer> itr = bt.iterator();

        for (int count = 0; itr.hasNext(); count++) {
            assertEquals(inOrderData[count], itr.next(), "Binary Tree iterator data not matching");
        }
    }

    @Test
    public void stream() {
        assertArrayEquals(bt.stream().toArray(), inOrderData,
                "Stream output doesn't matches with expected in Binary Tree");
    }
}
