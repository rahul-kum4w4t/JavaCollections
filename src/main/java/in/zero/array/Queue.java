package in.zero.array;

import in.zero.IllegalOperationException;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class Queue<T> {

    private static final int DEFAULT_STORAGE_CAPACITY = 16;
    private final int storCap;
    private int start;
    private int end;

    Object[] data;

    public Queue() {
        this(DEFAULT_STORAGE_CAPACITY);
    }

    public Queue(int init) {
        if (init > 0) {
            storCap = init;
            data = new Object[storCap];
            start = -1;
            end = 0;
        } else {
            throw new IllegalArgumentException("Queue needs to have positive integer value as storage capacity");
        }
    }

    public void enqueue(T d) {
        if (start != end) {
            data[end] = d;
            end = (end + 1) % storCap;
            if (start == -1) {
                start = 0;
            }
        } else {
            throw new IllegalOperationException("Queue is full");
        }
    }

    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (start != -1) {
            T d = (T) data[start];
            data[start] = null;
            start = (start + 1) % storCap;
            if (start == end) {
                start = -1;
                end = 0;
            }
            return d;
        } else {
            throw new IllegalOperationException("Queue is empty");
        }
    }

    public boolean isEmpty() {
        return start == -1;
    }

    public boolean isFull() {
        return ((end + 1) % storCap) == start;
    }

    void printTrace() {
        System.out.println(Arrays.stream(data).map(String::valueOf).collect(Collectors.joining(", ")));
    }

    public static void main(String[] args) {
        Queue<Integer> que = new Queue(5);

        que.enqueue(1);
        que.printTrace();
        que.enqueue(2);
        que.printTrace();
        que.enqueue(3);
        que.printTrace();
        que.enqueue(4);
        que.printTrace();
        que.enqueue(5);
        que.printTrace();
        que.dequeue();
        que.printTrace();
        que.dequeue();
        que.dequeue();
        que.dequeue();
        que.printTrace();
        que.enqueue(6);
        que.printTrace();
        que.enqueue(7);
        que.printTrace();
        que.dequeue();
        que.printTrace();
        que.dequeue();
        que.printTrace();
        System.out.printf(que.start + ", " + que.end);
    }
}
