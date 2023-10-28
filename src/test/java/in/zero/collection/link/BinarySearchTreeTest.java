package in.zero.collection.link;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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

public class BinarySearchTreeTest {

	private static BinarySearchTree<Integer> asc;
	private static BinarySearchTree<Integer> desc;

	final static Integer[] testData = { 10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35 };
	final static Integer[] ascLevelOrderData = { 10, 1, 20, 7, 15, 25, 5, 17, 21, 33, 73, 71, 35 };
	final static Integer[] descLevelOrderData = { 10, 20, 1, 25, 15, 7, 33, 21, 17, 5, 73, 71, 35 };
	final static Integer[] ascPreOrderData = { 10, 1, 7, 5, 20, 15, 17, 25, 21, 33, 73, 71, 35 };
	final static Integer[] descPreOrderData = { 10, 20, 25, 33, 73, 71, 35, 21, 15, 17, 1, 7, 5 };
	final static Integer[] ascPostOrderData = { 5, 7, 1, 17, 15, 21, 35, 71, 73, 33, 25, 20, 10 };
	final static Integer[] descPostOrderData = { 35, 71, 73, 33, 21, 25, 17, 15, 20, 5, 7, 1, 10 };
	final static Integer[] ascInOrderData = { 1, 5, 7, 10, 15, 17, 20, 21, 25, 33, 35, 71, 73 };
	final static Integer[] descInOrderData = { 73, 71, 35, 33, 25, 21, 20, 17, 15, 10, 7, 5, 1 };

	@BeforeAll
	public static void beforeAll() {
		asc = new BinarySearchTree<>();
		asc.addAll(testData);

		desc = new BinarySearchTree<>(true);
		desc.addAll(testData);
	}

	@Test
	public void metadata() {

		final int ascBinarySearchTreeHeight = asc.getHeight();
		final int descBinarySearchTreeHeight = asc.getHeight();

		assertEquals(asc.nodesCount, testData.length, "Node count for ASC BST not matches");
		assertEquals(asc.size(), testData.length, "Node count for ASC BST does not matches");
		assertEquals(ascBinarySearchTreeHeight, 6, "ASC BST height doesn't match the expected");
		assertEquals("NORMAL", asc.getSortingOrder(), "Sorting order doesn't match for ASC BST");

		assertEquals(desc.nodesCount, testData.length, "Node count for DESC BST not matches");
		assertEquals(desc.size(), testData.length, "Node count for DESC BST does not matches");
		assertEquals(descBinarySearchTreeHeight, 6, "DESC BST height doesn't match the expected");
		assertEquals("REVERSE", desc.getSortingOrder(), "Sorting order doesn't match for DESC BST");

		assertTrue(asc instanceof BinarySearchTree, "BST is not instance of BinarySearchTree");
		assertTrue(desc instanceof BinaryTree, "BST is not instance of BinaryTree");
		assertTrue(asc instanceof Collection, "BST is not instance of Collection");
		assertTrue(asc instanceof Iterable, "BST is not instance of Iterable");
		assertTrue(asc instanceof LinkBinaryTreeIterable, "BST is not instance of LinkBinaryTreeIterable");

		Comparable<Integer> comp = desc.root.data;

		assertTrue(comp instanceof Comparable, "Stored data is not a instance of Comparable in BST");
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

	@Test
	public void levelOrderTest() {
		Iterator<Integer> ascLevelOrder = asc.levelOrderIterator();
		Iterator<Integer> descLevelOrder = desc.levelOrderIterator();

		for (int count = 0; ascLevelOrder.hasNext(); count++) {
			assertEquals(ascLevelOrderData[count], ascLevelOrder.next(), "ASC BST level order not matching");
		}

		for (int count = 0; descLevelOrder.hasNext(); count++) {
			assertEquals(descLevelOrderData[count], descLevelOrder.next(), "DESC BST level order not matching");
		}
	}

	@Test
	public void preOrderTest() {
		Iterator<Integer> ascPreOrderOrder = asc.preOrderIterator();
		Iterator<Integer> descPreOrderOrder = desc.preOrderIterator();

		for (int count = 0; ascPreOrderOrder.hasNext(); count++) {
			assertEquals(ascPreOrderData[count], ascPreOrderOrder.next(), "ASC BST Pre order not matching");
		}

		for (int count = 0; descPreOrderOrder.hasNext(); count++) {
			assertEquals(descPreOrderData[count], descPreOrderOrder.next(), "DESC BST Pre order not matching");
		}
	}

	@Test
	public void postOrderTest() {
		Iterator<Integer> ascPostOrder = asc.postOrderIterator();
		Iterator<Integer> descPostOrder = desc.postOrderIterator();

		for (int count = 0; ascPostOrder.hasNext(); count++) {
			assertEquals(ascPostOrderData[count], ascPostOrder.next(), "ASC BST Post order not matching");
		}

		for (int count = 0; descPostOrder.hasNext(); count++) {
			assertEquals(descPostOrderData[count], descPostOrder.next(), "DESC BST Post order not matching");
		}
	}

	@Test
	public void inOrderTest() {
		Iterator<Integer> ascInOrder = asc.inOrderIterator();
		Iterator<Integer> descInOrder = desc.inOrderIterator();

		for (int count = 0; ascInOrder.hasNext(); count++) {
			assertEquals(ascInOrderData[count], ascInOrder.next(), "ASC BST In order not matching");
		}

		for (int count = 0; descInOrder.hasNext(); count++) {
			assertEquals(descInOrderData[count], descInOrder.next(), "DESC BST In order not matching");
		}
	}

	@Test
	public void iterator() {
		Iterator<Integer> ascItr = asc.iterator();
		Iterator<Integer> descItr = desc.iterator();

		for (int count = 0; ascItr.hasNext(); count++) {
			assertEquals(ascInOrderData[count], ascItr.next(), "ASC BST iterator data not matching");
		}

		for (int count = 0; descItr.hasNext(); count++) {
			assertEquals(descInOrderData[count], descItr.next(), "DESC BST iterator data not matching");
		}
	}

	@Test
	public void stream() {
		assertArrayEquals(asc.stream().toArray(), ascInOrderData,
				"Stream output doesn't matches with expected in ASC BST");

		assertArrayEquals(desc.stream().toArray(), descInOrderData,
				"Stream output doesn't matches with expected in DESC BST");
	}

}
