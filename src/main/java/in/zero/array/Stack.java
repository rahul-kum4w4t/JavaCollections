package in.zero.array;

import in.zero.IllegalOperationException;

public final class Stack<T> {

    private static final int DEFAULT_STORAGE_CAPACITY = 16;
    private Object[] data;
    private int top = 0;

    public Stack() {
        this(DEFAULT_STORAGE_CAPACITY);
    }

    public Stack(int init) {
        if (init > 0) {
            data = new Object[init];
        } else {
            throw new IllegalArgumentException("Stack needs to have positive integer value as storage capacity");
        }
    }

    public boolean isEmpty() {
        return top < 1;
    }

    public boolean isFull() {
        return top == data.length;
    }

    public void push(T elem) {
        if (!isFull()) {
            data[top++] = elem;
        } else {
            throw new IllegalOperationException("Stack is full. Can't push more.");
        }
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (!isEmpty()) {
            T d = (T) data[--top];
            data[top] = null;
            return d;
        } else {
            throw new IllegalOperationException("Stack is empty. Can't pop more.");
        }
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (!isEmpty()) {
            return (T) data[top];
        } else {
            throw new IllegalOperationException("Stack is empty.");
        }
    }
}
