package in.zero.link;

import in.zero.IllegalOperationException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class QueueTest {

    final static Integer[] testData = {10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35};

    @Test
    void testQueue() {
        Queue<Integer> queue = new Queue<>();

        assertTrue(queue.isEmpty(), "Queue should be empty at Queue creation");

        Arrays.stream(testData).forEach(queue::enqueue);

        assertFalse(queue.isEmpty(), "Queue should not be empty after Queue population");

        assertEquals(testData.length, queue.getCount(), "Queue data count not matching");

        assertEquals(10, queue.peek(), "Peek method does not works for queue");
        Arrays.stream(testData).forEach(elem -> {
            assertEquals(elem, queue.dequeue(), "Queue dequeue not working");
        });

        assertTrue(queue.isEmpty(), "Queue should be empty after queue data removal");

        assertEquals(0, queue.getCount(), "Queue data count must be zero after data removal");

        queue.enqueueAll(1, 2, 3, 4, 5);

        assertEquals(5, queue.getCount(), "Enqueue all should add all the 5 elements to the queue");
    }

    @Test
    void negativeTests() {
        Queue<Integer> queue = new Queue<>();

        assertThrows(IllegalOperationException.class, queue::dequeue, "Empty queue must throw exception");
        assertThrows(IllegalOperationException.class, queue::peek, "Empty queue must throw exception");
    }
}
