package in.zero.link.tree;

import in.zero.Collection;
import in.zero.link.tree.BinarySearchTree;
import in.zero.link.tree.BinaryTree;
import in.zero.link.tree.BinaryTreeNode;
import in.zero.link.tree.LinkBinaryTreeIterable;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class BSTBaseTest {

    static BinarySearchTree<Integer> asc;
    static BinarySearchTree<Integer> desc;

    final static Integer[] testData = {10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35};
    final Integer[] ascSortedOrderData;
    final Integer[] descSortedOrderData;
    final Integer[] ascLevelOrderData;
    final Integer[] descLevelOrderData;
    final Integer[] ascPreOrderData;
    final Integer[] descPreOrderData;
    final Integer[] ascPostOrderData;
    final Integer[] descPostOrderData;


    BSTBaseTest(Integer[] ascLevelOrderData, Integer[] descLevelOrderData,
                Integer[] ascPreOrderData, Integer[] descPreOrderData,
                Integer[] ascPostOrderData, Integer[] descPostOrderData) {
        this.ascPostOrderData = ascPostOrderData;
        this.ascLevelOrderData = ascLevelOrderData;
        this.ascPreOrderData = ascPreOrderData;
        this.descLevelOrderData = descLevelOrderData;
        this.descPreOrderData = descPreOrderData;
        this.descPostOrderData = descPostOrderData;
        this.ascSortedOrderData = new Integer[]{1, 5, 7, 10, 15, 17, 20, 21, 25, 33, 35, 71, 73};
        this.descSortedOrderData = new Integer[]{73, 71, 35, 33, 25, 21, 20, 17, 15, 10, 7, 5, 1};
    }

    @SuppressWarnings("all")
    public void metadata() {

        assertEquals(asc.nodesCount, testData.length, "Node count for ASC " + this.getClass().getSimpleName() + " not matches");
        assertEquals(asc.size(), testData.length, "Node count for ASC " + this.getClass().getSimpleName() + " does not matches");
        //assertEquals(ascBinarySearchTreeHeight, 6, "ASC BST height doesn't match the expected");
        assertEquals("NORMAL", asc.getSortingOrder(), "Sorting order doesn't match for ASC BST");

        assertEquals(desc.nodesCount, testData.length, "Node count for DESC " + this.getClass().getSimpleName() + " not matches");
        assertEquals(desc.size(), testData.length, "Node count for DESC " + this.getClass().getSimpleName() + " does not matches");
        //assertEquals(descBinarySearchTreeHeight, 6, "DESC BST height doesn't match the expected");
        assertEquals("REVERSE", desc.getSortingOrder(), "Sorting order doesn't match for DESC BST");

        assertTrue(asc instanceof BinarySearchTree, this.getClass().getSimpleName() + " is not instance of BinarySearchTree");
        assertTrue(desc instanceof BinaryTree, this.getClass().getSimpleName() + " is not instance of BinaryTree");
        assertTrue(asc instanceof Collection, this.getClass().getSimpleName() + " is not instance of Collection");
        assertTrue(asc instanceof Iterable, this.getClass().getSimpleName() + " is not instance of Iterable");
        assertTrue(asc instanceof LinkBinaryTreeIterable, this.getClass().getSimpleName() + " is not instance of LinkBinaryTreeIterable");

        assertTrue(asc.root instanceof BinaryTreeNode, this.getClass().getSimpleName() + "'s node is not instance of BinaryTreeNode");
    }

    @Test
    public void levelOrderTest() {
        Iterator<Integer> ascLevelOrder = asc.levelOrderIterator();
        Iterator<Integer> descLevelOrder = desc.levelOrderIterator();

        for (int count = 0; ascLevelOrder.hasNext(); count++) {
            assertEquals(ascLevelOrderData[count], ascLevelOrder.next(), "ASC " + this.getClass().getSimpleName() + " level order not matching");
        }

        for (int count = 0; descLevelOrder.hasNext(); count++) {
            assertEquals(descLevelOrderData[count], descLevelOrder.next(), "DESC " + this.getClass().getSimpleName() + " level order not matching");
        }
    }

    @Test
    public void preOrderTest() {
        Iterator<Integer> ascPreOrderOrder = asc.preOrderIterator();
        Iterator<Integer> descPreOrderOrder = desc.preOrderIterator();

        for (int count = 0; ascPreOrderOrder.hasNext(); count++) {
            assertEquals(ascPreOrderData[count], ascPreOrderOrder.next(), "ASC " + this.getClass().getSimpleName() + " pre order not matching");
        }

        for (int count = 0; descPreOrderOrder.hasNext(); count++) {
            assertEquals(descPreOrderData[count], descPreOrderOrder.next(), "DESC " + this.getClass().getSimpleName() + " pre order not matching");
        }
    }

    @Test
    public void postOrderTest() {
        Iterator<Integer> ascPostOrder = asc.postOrderIterator();
        Iterator<Integer> descPostOrder = desc.postOrderIterator();

        for (int count = 0; ascPostOrder.hasNext(); count++) {
            assertEquals(ascPostOrderData[count], ascPostOrder.next(), "ASC " + this.getClass().getSimpleName() + " post order not matching");
        }

        for (int count = 0; descPostOrder.hasNext(); count++) {
            assertEquals(descPostOrderData[count], descPostOrder.next(), "DESC " + this.getClass().getSimpleName() + " post order not matching");
        }
    }

    @Test
    public void inOrderTest() {
        Iterator<Integer> ascInOrder = asc.inOrderIterator();
        Iterator<Integer> descInOrder = desc.inOrderIterator();

        for (int count = 0; ascInOrder.hasNext(); count++) {
            assertEquals(ascSortedOrderData[count], ascInOrder.next(), "ASC " + this.getClass().getSimpleName() + " in order not matching");
        }

        for (int count = 0; descInOrder.hasNext(); count++) {
            assertEquals(descSortedOrderData[count], descInOrder.next(), "DESC " + this.getClass().getSimpleName() + " in order not matching");
        }
    }

    @Test
    public void reverseOrderTest() {
        Iterator<Integer> ascReverseOrder = asc.reverseOrderIterator();
        Iterator<Integer> descReverseOrder = desc.reverseOrderIterator();

        for (int count = 0; ascReverseOrder.hasNext(); count++) {
            assertEquals(descSortedOrderData[count], ascReverseOrder.next(), "ASC " + this.getClass().getSimpleName() + " reverse order not matching");
        }

        for (int count = 0; descReverseOrder.hasNext(); count++) {
            assertEquals(ascSortedOrderData[count], descReverseOrder.next(), "DESC " + this.getClass().getSimpleName() + " reverse order not matching");
        }
    }

    @Test
    public void iterator() {
        Iterator<Integer> ascItr = asc.iterator();
        Iterator<Integer> descItr = desc.iterator();

        for (int count = 0; ascItr.hasNext(); count++) {
            assertEquals(ascSortedOrderData[count], ascItr.next(), "ASC " + this.getClass().getSimpleName() + " iterator data not matching");
        }

        for (int count = 0; descItr.hasNext(); count++) {
            assertEquals(descSortedOrderData[count], descItr.next(), "DESC " + this.getClass().getSimpleName() + " iterator data not matching");
        }
    }

    @Test
    public void stream() {
        assertArrayEquals(asc.stream().toArray(), ascSortedOrderData,
                "Stream output doesn't matches with expected in ASC " + this.getClass().getSimpleName());

        assertArrayEquals(desc.stream().toArray(), descSortedOrderData,
                "Stream output doesn't matches with expected in DESC " + this.getClass().getSimpleName());
    }
}
