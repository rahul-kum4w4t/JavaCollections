package in.zero.collection.link;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import in.zero.collection.Collection;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class AVLTreeTest {

	private static AVLTree<Integer> asc;
	private static AVLTree<Integer> desc;

	final static Integer[] testData = { 10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35 };
	final static Integer[] ascLevelOrderData = { 17, 10, 33, 5, 15, 21, 71, 1, 7, 20, 25, 35, 73 };
	final static Integer[] descLevelOrderData = { 17, 33, 10, 71, 21, 15, 5, 73, 35, 25, 20, 7, 1 };
	final static Integer[] ascPreOrderData = { 17, 10, 5, 1, 7, 15, 33, 21, 20, 25, 71, 35, 73 };
	final static Integer[] descPreOrderData = { 17, 33, 71, 73, 35, 21, 25, 20, 10, 15, 5, 7, 1 };
	final static Integer[] ascPostOrderData = { 1, 7, 5, 15, 10, 20, 25, 21, 35, 73, 71, 33, 17 };
	final static Integer[] descPostOrderData = { 73, 35, 71, 25, 20, 21, 33, 15, 7, 1, 5, 10, 17 };
	final static Integer[] ascInOrderData = { 1, 5, 7, 10, 15, 17, 20, 21, 25, 33, 35, 71, 73 };
	final static Integer[] descInOrderData = { 73, 71, 35, 33, 25, 21, 20, 17, 15, 10, 7, 5, 1 };

	@BeforeAll
	public static void beforeAll() {
		asc = new AVLTree<>();
		asc.addAll(testData);

		desc = new AVLTree<>(true);
		desc.addAll(testData);
	}

	@Test
	public void metadata() {
		final double minHeight = Math.floor(Math.log(testData.length) / Math.log(2));
		final double maxHeight = 1.44 * Math.log(testData.length) / Math.log(2);

		final int ascAvlTreeHeight = asc.getHeight();
		final int descAvlTreeHeight = asc.getHeight();

		assertEquals(asc.nodesCount, testData.length, "Node count for ASC AVL Tree not matches");
		assertEquals(asc.size(), testData.length, "Node count for ASC AVL Tree does not matches");
		assertEquals(3, ascAvlTreeHeight, "ASC AVL tree height doesn't match the expected");
		assertTrue(ascAvlTreeHeight >= minHeight, "Min height for ASC AVL tree doesn't matches");
		assertTrue(ascAvlTreeHeight <= maxHeight, "Max height for ASC AVL tree doesn't matches");
		assertEquals("NORMAL", asc.getSortingOrder(), "Sorting order doesn't match for ASC AVL tree");

		assertEquals(desc.nodesCount, testData.length, "Node count for DESC AVL Tree not matches");
		assertEquals(asc.size(), testData.length, "Node count for DESC AVL Tree does not matches");
		assertEquals(3, descAvlTreeHeight, "DESC AVL tree height doesn't match the expected");
		assertTrue(descAvlTreeHeight >= minHeight, "Min height for DESC AVL tree doesn't matches");
		assertTrue(descAvlTreeHeight <= maxHeight, "Max height for DESC AVL tree doesn't matches");
		assertEquals("REVERSE", desc.getSortingOrder(), "Sorting order doesn't match for DESC AVL tree");

		assertTrue(asc instanceof AVLTree, "ASC AVL Tree is not instance of BinarySearchTree");
		assertTrue(asc instanceof BinarySearchTree, "ASC AVL Tree is not instance of BinarySearchTree");
		assertTrue(asc instanceof BinaryTree, "ASC AVL Tree is not instance of BinaryTree");
		assertTrue(asc instanceof Collection, "ASC AVL Tree is not instance of Collection");
		assertTrue(asc instanceof Iterable, "ASC AVL Tree is not instance of Iterable");
		assertTrue(asc instanceof LinkBinaryTreeIterable, "ASC AVL Tree is not instance of LinkBinaryTreeIterable");

		Comparable<Integer> comp = desc.root.data;

		assertTrue(comp instanceof Comparable, "Stored data is not a instance of Comparable in ASC AVL Tree");
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
