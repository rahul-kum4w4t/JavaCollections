package in.zero.collection;

import java.lang.reflect.Array;

/**
 * BTree:
 * - Also called Balanced M-way tree (Here M is the order of tree)
 * - generalized version of BST
 * - New value can only be inserted in leaf node
 * - All leaf nodes must be at same level
 * - can have more than two children per node
 * - Can have max M children for any node and Max M-1 keys
 * - Must have min 0 child for leaf node, 2 children for root node, and ceil(M/2) children for internal nodes
 * - Must have min 1 key for root node, ceil(M/2) - 1 for all other nodes
 * If the given order is even then there are two trees possible
 * 1. Left bias - consider middle left element for splitting
 * 2. Right Bias - conside middle right element for splitting
 */
public class BTree<T extends Comparable<T>> implements Collection<T> {

    private final int order;
    private final Class<T> type;
    private boolean leftBias;
    private BTreeNode<T> root;
    private int nodesCount = 0;

    public BTree(Class<T> type) {
        this.order = 4;
        this.type = type;
    }

    public BTree(Class<T> type, int order) {
        if (order < 3) throw new IllegalArgumentException("Order for BTree must be > 2");
        else this.order = order - 1;
        this.type = type;
    }

    public BTree(Class<T> type, boolean bias) {
        this.leftBias = bias;
        this.order = 4;
        this.type = type;
    }

    public BTree(Class<T> type, int order, boolean bias) {
        if (order < 4) throw new IllegalArgumentException("Order for BTree must be > 2");
        else this.order = order;
        this.leftBias = bias;
        this.type = type;
    }

    @Override
    public BTree<T> add(T value) {
        if (value != null) {
            if (root == null) {
                this.root = new BTreeNode<>(type, order, value);
                nodesCount++;
            } else {
                BTreeNode<T> node = findEligibleNode(value);
                if (node != null) {
                    int comp;
                    for (int i = 0; i < node.counter; i++) {
                        comp = node.values[i].compareTo(value);
                        if (comp > 0) {

                        }
                    }
                }
            }
        }
        return this;
    }

    @Override
    public Collection<T> addAll(T... vals) {
        for (T val : vals) {
            this.add(val);
        }
        return this;
    }

    @Override
    public T remove(T val) {
        return null;
    }

    @Override
    public T[] removeAll(T... vals) {
        T[] deleted = (T[]) Array.newInstance(type, vals.length);
        for (int i = 0; i < vals.length; i++) {
            deleted[i] = this.remove(vals[i]);
        }
        return deleted;
    }

    @Override
    public boolean search(T val) {
        return false;
    }

    private BTreeNode<T> findEligibleNode(T value) {
        if (value != null) {
            BTreeNode<T> node = this.root;
            BTreeNode<T> prev = node;
            int comp;
            while (node != null) {
                for (int i = 0; i < node.counter; i++) {
                    comp = node.values[i].compareTo(value);
                    if (comp > 0) {
                        return node;
                    }
                }
                if (node.counter == this.order) {
                    prev = node;
                    node = node.nodes[node.counter + 1];
                } else {
                    return node;
                }
            }
            return prev;
        }
        return null;
    }
}

class BTreeNode<T> {

    T[] values;
    BTreeNode<T>[] nodes;
    int counter;

    @SuppressWarnings("unchecked")
    BTreeNode(Class<T> type, int order, T value) {
        values = (T[]) Array.newInstance(type, order - 1);
        nodes = new BTreeNode[order];
        values[counter++] = value;
    }
}
