package in.zero.link;

import in.zero.IllegalOperationException;

import java.util.Arrays;

public class Stack<T> {

    private StackNode<T> top;
    private int count;

    public void push(T value) {
        top = new StackNode<>(value, top);
        count++;
    }

    public void pushAll(T... values) {
        if (values != null) {
            Arrays.stream(values).forEach(this::push);
        }
    }

    public T pop() {
        if (top != null) {
            T data = top.data;
            top = top.prev;
            count--;
            return data;
        } else {
            throw new IllegalOperationException("`pop` operation is not allowed on empty stack");
        }
    }

    public T peek() {
        if (top != null) {
            return top.data;
        } else {
            throw new IllegalOperationException("`peek` operation is not allowed on empty stack");
        }
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int getCount() {
        return count;
    }
}

class StackNode<T> {

    StackNode<T> prev;
    T data;

    StackNode(T data, StackNode<T> prev) {
        this.data = data;
        this.prev = prev;
    }
}
