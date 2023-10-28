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

public class RedBlackTreeTest {

	private static RedBlackTree<Integer> asc;
	private static RedBlackTree<Integer> desc;

	final static Integer[] testData = { 10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35 };
	final static Integer[] ascLevelOrderData = { 20, 10, 25, 5, 15, 21, 71, 1, 7, 17, 33, 73, 35 };
	final static Integer[] descLevelOrderData = { 20, 25, 10, 71, 21, 15, 5, 73, 33, 17, 7, 1, 35 };
	final static Integer[] ascPreOrderData = { 20, 10, 5, 1, 7, 15, 17, 25, 21, 71, 33, 35, 73 };
	final static Integer[] descPreOrderData = { 20, 25, 71, 73, 33, 35, 21, 10, 15, 17, 5, 7, 1 };
	final static Integer[] ascPostOrderData = { 1, 7, 5, 17, 15, 10, 21, 35, 33, 73, 71, 25, 20 };
	final static Integer[] descPostOrderData = { 73, 35, 33, 71, 21, 25, 17, 15, 7, 1, 5, 10, 20 };
	final static Integer[] ascInOrderData = { 1, 5, 7, 10, 15, 17, 20, 21, 25, 33, 35, 71, 73 };
	final static Integer[] descInOrderData = { 73, 71, 35, 33, 25, 21, 20, 17, 15, 10, 7, 5, 1 };

	@BeforeAll
	public static void beforeAll() {
		asc = new RedBlackTree<>();
		asc.addAll(testData);

		desc = new RedBlackTree<>(true);
		desc.addAll(testData);
	}

	@Test
	public void metadata() {

		final int ascAvlTreeHeight = asc.getHeight();
		final int descAvlTreeHeight = asc.getHeight();

		assertEquals(asc.nodesCount, testData.length, "Node count for ASC RB Tree not matches");
		assertEquals(asc.size(), testData.length, "Node count for ASC RB Tree does not matches");
		assertEquals(4, ascAvlTreeHeight, "ASC RB Tree height doesn't match the expected");
		assertEquals("NORMAL", asc.getSortingOrder(), "Sorting order doesn't match for ASC RB Tree");

		assertEquals(desc.nodesCount, testData.length, "Node count for DESC RB Tree not matches");
		assertEquals(asc.size(), testData.length, "Node count for DESC RB Tree does not matches");
		assertEquals(4, descAvlTreeHeight, "DESC RB Tree height doesn't match the expected");
		assertEquals("REVERSE", desc.getSortingOrder(), "Sorting order doesn't match for DESC RB Tree");

		assertTrue(asc instanceof RedBlackTree, "ASC RB Tree is not instance of BinarySearchTree");
		assertTrue(asc instanceof BinarySearchTree, "ASC RB Tree is not instance of BinarySearchTree");
		assertTrue(asc instanceof BinaryTree, "ASC RB Tree is not instance of BinaryTree");
		assertTrue(asc instanceof Collection, "ASC RB Tree is not instance of Collection");
		assertTrue(asc instanceof Iterable, "ASC RB Tree is not instance of Iterable");
		assertTrue(asc instanceof LinkBinaryTreeIterable, "ASC RB Tree is not instance of LinkBinaryTreeIterable");

		Comparable<Integer> comp = desc.root.data;

		assertTrue(comp instanceof Comparable, "Stored data is not a instance of Comparable in ASC RB Tree");
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

	@Test
	public void levelOrderTest() {
		Iterator<Integer> ascLevelOrder = asc.levelOrderIterator();
		Iterator<Integer> descLevelOrder = desc.levelOrderIterator();

		for (int count = 0; ascLevelOrder.hasNext(); count++) {
			assertEquals(ascLevelOrderData[count], ascLevelOrder.next(), "ASC RB Tree level order not matching");
		}

		for (int count = 0; descLevelOrder.hasNext(); count++) {
			assertEquals(descLevelOrderData[count], descLevelOrder.next(), "DESC RB Tree level order not matching");
		}
	}

	@Test
	public void preOrderTest() {
		Iterator<Integer> ascPreOrderOrder = asc.preOrderIterator();
		Iterator<Integer> descPreOrderOrder = desc.preOrderIterator();

		for (int count = 0; ascPreOrderOrder.hasNext(); count++) {
			assertEquals(ascPreOrderData[count], ascPreOrderOrder.next(), "ASC RB Tree Pre order not matching");
		}

		for (int count = 0; descPreOrderOrder.hasNext(); count++) {
			assertEquals(descPreOrderData[count], descPreOrderOrder.next(), "DESC RB Tree Pre order not matching");
		}
	}

	@Test
	public void postOrderTest() {
		Iterator<Integer> ascPostOrder = asc.postOrderIterator();
		Iterator<Integer> descPostOrder = desc.postOrderIterator();

		for (int count = 0; ascPostOrder.hasNext(); count++) {
			assertEquals(ascPostOrderData[count], ascPostOrder.next(), "ASC RB Tree Post order not matching");
		}

		for (int count = 0; descPostOrder.hasNext(); count++) {
			assertEquals(descPostOrderData[count], descPostOrder.next(), "DESC RB Tree Post order not matching");
		}
	}

	@Test
	public void inOrderTest() {
		Iterator<Integer> ascInOrder = asc.inOrderIterator();
		Iterator<Integer> descInOrder = desc.inOrderIterator();

		for (int count = 0; ascInOrder.hasNext(); count++) {
			assertEquals(ascInOrderData[count], ascInOrder.next(), "ASC RB Tree In order not matching");
		}

		for (int count = 0; descInOrder.hasNext(); count++) {
			assertEquals(descInOrderData[count], descInOrder.next(), "DESC RB Tree In order not matching");
		}
	}

}
