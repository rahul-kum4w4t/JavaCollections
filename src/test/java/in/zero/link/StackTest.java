package in.zero.link;

import in.zero.IllegalOperationException;
import in.zero.array.ArrayUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class StackTest {

    final static Integer[] testData = {10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35};

    @Test
    void testStackOperations() {
        Stack<Integer> stack = new Stack<Integer>();

        assertTrue(stack.isEmpty(), "Stack should be empty at stack creation");

        Arrays.stream(testData).forEach(stack::push);

        assertFalse(stack.isEmpty(), "Stack should not be empty after stack population");

        assertEquals(testData.length, stack.getCount(), "Stack data count not matching");

        assertEquals(35, stack.peek(), "Peek method does not works for stack");
        Arrays.stream(ArrayUtils.reverseCopy(testData, Integer.class)).forEach(elem -> {
            assertEquals(elem, stack.pop(), "Stack pop not working");
        });

        assertTrue(stack.isEmpty(), "Stack should be empty after stack data removal");

        assertEquals(0, stack.getCount(), "Stack data count must be zero after data removal");

        stack.pushAll(1, 2, 3, 4, 5);

        assertEquals(5, stack.getCount(), "Push all should add all the 5 elements to the stack");
    }

    @Test
    void negativeTests() {
        Stack<Integer> stack = new Stack<>();

        assertThrows(IllegalOperationException.class, stack::pop, "Empty stack must throw exception");
        assertThrows(IllegalOperationException.class, stack::peek, "Empty stack must throw exception");
    }
}
