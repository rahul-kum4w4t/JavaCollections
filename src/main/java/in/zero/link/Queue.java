package in.zero.link;

import in.zero.IllegalOperationException;

import java.util.Arrays;

public class Queue<T> {

    private QueueNode<T> start;
    private QueueNode<T> end;
    private int count;

    public void enqueue(T data) {
        QueueNode<T> node = new QueueNode<>(data);
        if (end == null) {
            start = end = node;
        } else {
            end.next = node;
            end = node;
        }
        count++;
    }

    public void enqueueAll(T... values) {
        if (values != null) {
            Arrays.stream(values).forEach(this::enqueue);
        }
    }

    public T dequeue() {
        if (start != null) {
            T data = start.data;
            start = start.next;
            if (start == null) end = null;
            count--;
            return data;
        } else {
            throw new IllegalOperationException("`dequeue` operation is not allowed on empty queue");
        }
    }

    public T peek() {
        if (start != null) {
            return start.data;
        } else {
            throw new IllegalOperationException("`peek` operation is not allowed on empty queue");
        }
    }

    public boolean isEmpty() {
        return end == null;
    }

    public int getCount() {
        return count;
    }
}


class QueueNode<T> {
    T data;
    QueueNode<T> next;

    QueueNode(T data) {
        this.data = data;
    }
}