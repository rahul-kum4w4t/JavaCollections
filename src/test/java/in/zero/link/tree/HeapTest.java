package in.zero.link.tree;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;

import in.zero.Collection;
import in.zero.link.tree.BinaryTree;
import in.zero.link.tree.Heap;
import in.zero.link.tree.LinkBinaryTreeIterable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HeapTest {

    private static Heap<Integer> max;
    private static Heap<Integer> min;

    final static Integer[] testData = {10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35};
    final static Integer[] maxLevelOrderData = {73, 33, 71, 20, 25, 35, 7, 1, 5, 15, 21, 10, 17};
    final static Integer[] minLevelOrderData = {1, 5, 7, 10, 25, 20, 17, 21, 15, 33, 73, 71, 35};
    final static Integer[] maxPreOrderData = {73, 33, 20, 1, 5, 25, 15, 21, 71, 35, 10, 17, 7};
    final static Integer[] minPreOrderData = {1, 5, 10, 21, 15, 25, 33, 73, 7, 20, 71, 35, 17};
    final static Integer[] maxPostOrderData = {1, 5, 20, 15, 21, 25, 33, 10, 17, 35, 7, 71, 73};
    final static Integer[] minPostOrderData = {21, 15, 10, 33, 73, 25, 5, 71, 35, 20, 17, 7, 1};
    final static Integer[] maxInOrderData = {1, 20, 5, 33, 15, 25, 21, 73, 10, 35, 17, 71, 7};
    final static Integer[] minInOrderData = {21, 10, 15, 5, 33, 25, 73, 1, 71, 20, 35, 7, 17};
    final static Integer[] maxReverseOrderData = {7, 71, 17, 35, 10, 73, 21, 25, 15, 33, 5, 20, 1};
    final static Integer[] minReverseOrderData = {17, 7, 35, 20, 71, 1, 73, 25, 33, 5, 15, 10, 21};

    @BeforeAll
    public static void beforeAll() {
        max = new Heap<>(Heap.Type.MAX);
        max.addAll(testData);

        min = new Heap<>();
        min.addAll(testData);
    }

    @Test
    public void metadata() {

        final int maxHeapHeight = max.getHeight();
        final int minHeapHeight = max.getHeight();

        assertEquals(max.nodesCount, testData.length, "Node count for max Heap not matches");
        assertEquals(max.size(), testData.length, "Node count for max Heap does not matches");
        assertEquals(maxHeapHeight, 3, "max Heap height doesn't match the expected");
        assertEquals("MAX", max.heapType(), "Sorting order doesn't match for max Heap");

        assertEquals(min.nodesCount, testData.length, "Node count for min Heap not matches");
        assertEquals(min.size(), testData.length, "Node count for min Heap does not matches");
        assertEquals(minHeapHeight, 3, "min Heap height doesn't match the expected");
        assertEquals("MIN", min.heapType(), "Sorting order doesn't match for min Heap");

        assertTrue(max instanceof Heap, "Heap is not instance of Heap");
        assertTrue(min instanceof BinaryTree, "Heap is not instance of BinaryTree");
        assertTrue(max instanceof Collection, "Heap is not instance of Collection");
        assertTrue(max instanceof Iterable, "Heap is not instance of Iterable");
        assertTrue(max instanceof LinkBinaryTreeIterable, "Heap is not instance of LinkBinaryTreeIterable");

        Comparable<Integer> comp = min.root.data;

        assertTrue(comp instanceof Comparable, "Stored data is not a instance of Comparable in Heap");
    }

    @Test
    public void addElements() {
        Heap<Integer> heap = new Heap<>();
        heap.add(0);
        heap.addAll(testData);
        heap.add(19);

        assertSame(heap.add(19), heap, "Heap not accepting duplicate values");

        assertSame(heap.add(94), heap, "Add method not returning tree object in Heap");
        assertSame(heap.addAll(97), heap, "Add All method not returning tree object in Heap");
    }

    @Test
    public void searchElements() {
        assertTrue(Arrays.stream(testData).allMatch(max::search), "Data missing in max Heap");
        assertTrue(Arrays.stream(testData).allMatch(min::search), "Data missing in min Heap");
    }

    @Test
    public void removeElements() {
        Heap<Integer> heap = new Heap<>();
        heap.addAll(testData);

        assertThrows(RuntimeException.class, () -> heap.remove(10), "Heap allowing to remove specific item");

        assertTrue(Arrays.stream(testData).allMatch(elem -> heap.remove() != null), "Deleting all the data from Heap");

        assertEquals(0, heap.nodesCount, "Heap node count must be 0 after deleting all the elements");
        assertNull(heap.root, "Heap root node must be null after deleting all the elements");
    }

    @Test
    public void levelOrderTest() {
        Iterator<Integer> maxLevelOrder = max.levelOrderIterator();
        Iterator<Integer> minLevelOrder = min.levelOrderIterator();

        for (int count = 0; maxLevelOrder.hasNext(); count++) {
            assertEquals(maxLevelOrderData[count], maxLevelOrder.next(), "max Heap level order not matching");
        }

        for (int count = 0; minLevelOrder.hasNext(); count++) {
            assertEquals(minLevelOrderData[count], minLevelOrder.next(), "min Heap level order not matching");
        }
    }

    @Test
    public void preOrderTest() {
        Iterator<Integer> maxPreOrderOrder = max.preOrderIterator();
        Iterator<Integer> minPreOrderOrder = min.preOrderIterator();

        for (int count = 0; maxPreOrderOrder.hasNext(); count++) {
            assertEquals(maxPreOrderData[count], maxPreOrderOrder.next(), "max Heap Pre order not matching");
        }

        for (int count = 0; minPreOrderOrder.hasNext(); count++) {
            assertEquals(minPreOrderData[count], minPreOrderOrder.next(), "min Heap Pre order not matching");
        }
    }

    @Test
    public void postOrderTest() {
        Iterator<Integer> maxPostOrder = max.postOrderIterator();
        Iterator<Integer> minPostOrder = min.postOrderIterator();

        for (int count = 0; maxPostOrder.hasNext(); count++) {
            assertEquals(maxPostOrderData[count], maxPostOrder.next(), "max Heap Post order not matching");
        }

        for (int count = 0; minPostOrder.hasNext(); count++) {
            assertEquals(minPostOrderData[count], minPostOrder.next(), "min Heap Post order not matching");
        }
    }

    @Test
    public void inOrderTest() {
        Iterator<Integer> maxInOrder = max.inOrderIterator();
        Iterator<Integer> minInOrder = min.inOrderIterator();

        for (int count = 0; maxInOrder.hasNext(); count++) {
            assertEquals(maxInOrderData[count], maxInOrder.next(), "max Heap In order not matching");
        }

        for (int count = 0; minInOrder.hasNext(); count++) {
            assertEquals(minInOrderData[count], minInOrder.next(), "min Heap In order not matching");
        }
    }

    @Test
    public void reverseOrderTest() {
        Iterator<Integer> maxReverseOrder = max.reverseOrderIterator();
        Iterator<Integer> minReverseOrder = min.reverseOrderIterator();

        for (int count = 0; maxReverseOrder.hasNext(); count++) {
            assertEquals(maxReverseOrderData[count], maxReverseOrder.next(), "max " + this.getClass().getSimpleName() + " reverse order not matching");
        }

        for (int count = 0; minReverseOrder.hasNext(); count++) {
            assertEquals(minReverseOrderData[count], minReverseOrder.next(), "min " + this.getClass().getSimpleName() + " reverse order not matching");
        }
    }

    @Test
    public void iterator() {
        Iterator<Integer> maxItr = max.iterator();
        Iterator<Integer> minItr = min.iterator();

        for (int count = 0; maxItr.hasNext(); count++) {
            assertEquals(maxInOrderData[count], maxItr.next(), "max Heap iterator data not matching");
        }

        for (int count = 0; minItr.hasNext(); count++) {
            assertEquals(minInOrderData[count], minItr.next(), "min Heap iterator data not matching");
        }
    }

    @Test
    public void stream() {
        assertArrayEquals(max.stream().toArray(), maxInOrderData,
                "Stream output doesn't matches with expected in max Heap");

        assertArrayEquals(min.stream().toArray(), minInOrderData,
                "Stream output doesn't matches with expected in min Heap");
    }

}
