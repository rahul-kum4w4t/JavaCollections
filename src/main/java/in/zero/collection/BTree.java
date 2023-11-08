package in.zero.collection;

import in.zero.collection.link.BinaryTree;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

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
 * 2. Right Bias - consider middle right element for splitting
 */
public class BTree<T extends Comparable<T>> implements Collection<T> {

    private int order;
    private final Class<T> type;
    private boolean leftBias;
    private BTreeNode<T> root;
    private int orderMul;

    private int valuesCount;

    /**
     * BTree constructor
     *
     * @param type type of the data which needs to be stored
     */
    public BTree(Class<T> type) {
        if (type != null) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("Class Type for the data is required.");
        }
        this.order = 5;
        this.orderMul = 1;
    }

    /**
     * BTree constructor
     *
     * @param type    type of the data which needs to be stored
     * @param inverse inverse the order of sorting?, Default - natural sorting order
     */
    public BTree(Class<T> type, boolean inverse) {
        this(type);
        this.orderMul = inverse ? -1 : 1;
    }

    /**
     * BTree constructor
     *
     * @param type type of the data which needs to be stored
     * @param bias left/right bias based on which even ordered BTree's get constructed, Default - right bias
     */
    public BTree(boolean bias, Class<T> type) {
        this(type);
        this.leftBias = bias;
    }

    /**
     * BTree constructor
     *
     * @param type  type of the data which needs to be stored
     * @param order order of BTree, Default - 5
     */
    public BTree(Class<T> type, int order) {
        this(type);
        if (order >= 3) this.order = order;
        else throw new IllegalArgumentException("Order for BTree must be >= 3");
    }

    /**
     * BTree constructor
     *
     * @param type    type of the data which needs to be stored
     * @param order   order of BTree, Default - 5
     * @param inverse inverse the order of sorting?, Default - natural sorting order
     */
    public BTree(Class<T> type, int order, boolean inverse) {
        this(type, order);
        this.orderMul = inverse ? -1 : 1;
    }

    /**
     * BTree constructor
     *
     * @param type  type of the data which needs to be stored
     * @param order order of BTree, Default - 5
     * @param bias  left/right bias based on which even ordered BTree's get constructed, Default - right bias
     */
    public BTree(Class<T> type, boolean bias, int order) {
        this(type, order);
        this.leftBias = bias;
    }

    /**
     * BTree constructor
     *
     * @param type    type of the data which needs to be stored
     * @param bias    left/right bias based on which even ordered BTree's get constructed, Default - right bias
     * @param inverse inverse the order of sorting?, Default - natural sorting order
     */
    public BTree(Class<T> type, boolean bias, boolean inverse) {
        this(type, inverse);
        this.leftBias = bias;
    }

    /**
     * BTree constructor
     *
     * @param type    type of the data which needs to be stored
     * @param order   order of BTree, Default - 5
     * @param bias    left/right bias based on which even ordered BTree's get constructed, Default - right bias
     * @param inverse inverse the order of sorting?, Default - natural sorting order
     */
    public BTree(Class<T> type, int order, boolean inverse, boolean bias) {
        this(type, order, inverse);
        this.leftBias = bias;
    }

    /**
     * Add value to the tree
     *
     * @param value Value which needs to be added
     * @return BTree reference
     */
    @Override
    public BTree<T> add(T value) {
        if (value != null) {
            if (root == null) {
                this.root = new BTreeNode<>(type, order, value);
            } else {
                BTreeNode<T> node = findEligibleNode(value);
                int comp;
                for (int i = 0; i < node.counter; i++) {
                    comp = this.orderMul * node.values[i].compareTo(value);
                    if (comp > 0) {
                        if ((node.counter + 1) < this.order) {
                            shiftAndInsertAt(node, i, value, null);
                        } else {
                            insertAndSplit(node, i, value, null);
                        }
                        valuesCount++;
                        return this;
                    } else if (comp == 0) {
                        throw new IllegalArgumentException("BTree doesn't accept duplicate values");
                    }
                }
                if ((node.counter + 1) < this.order) {
                    shiftAndInsertAt(node, node.counter, value, null);
                } else {
                    insertAndSplit(node, node.counter, value, null);
                }
            }
            valuesCount++;
        } else {
            throw new IllegalArgumentException("BTree can't store null values");
        }
        return this;
    }

    private void shiftAndInsertAt(final BTreeNode<T> node, final int insertIndex, final T value, final BTreeNode<T> right) {
        for (int i = node.counter - 1; i >= insertIndex; i--) {
            node.values[i + 1] = node.values[i];
            node.children[i + 2] = node.children[i + 1];
        }
        node.values[insertIndex] = value;
        node.children[insertIndex + 1] = right;
        if (right != null) right.parent = node;
        node.counter++;
    }

    private void insertAndSplit(final BTreeNode<T> node, final int insertIndex, final T value, final BTreeNode<T> right) {
        int middle = (int) Math.floor((double) this.order / 2);
        if (this.order % 2 == 0 && !this.leftBias) {
            middle--;
        }
        BTreeNode<T> newNode = new BTreeNode<>(this.type, this.order, null, node.parent);
        int copyFromIndex = insertIndex <= middle ? middle : middle + 1;
        int till = node.counter, j = 0, i;
        for (i = copyFromIndex; i < till; i++, j++) {
            newNode.values[j] = node.values[i];
            newNode.children[j] = node.children[i];
            node.values[i] = null;
            node.children[i] = null;
            newNode.counter++;
            node.counter--;
        }
        newNode.children[j] = node.children[i];
        node.children[i] = null;
        if (copyFromIndex != middle) {
            shiftAndInsertAt(newNode, insertIndex - copyFromIndex, value, right);
        } else {
            shiftAndInsertAt(node, insertIndex, value, right);
        }
        T splitOnValue = node.values[node.counter - 1];
        node.values[node.counter - 1] = null;
        node.counter--;

        if (node.parent != null) {
            for (i = 0; i <= node.parent.counter; i++) {
                if (node.parent.children[i] == node) {
                    if ((node.parent.counter + 1) < this.order) {
                        shiftAndInsertAt(node.parent, i, splitOnValue, newNode);
                    } else {
                        insertAndSplit(node.parent, i, splitOnValue, newNode);
                    }
                }
            }
        } else {
            BTreeNode<T> newRoot = new BTreeNode<T>(this.type, this.order, splitOnValue);
            newRoot.children[0] = node;
            newRoot.children[1] = newNode;
            this.root = newRoot;
            node.parent = newRoot;
            newNode.parent = newRoot;
        }
        newNode.updateChildren();
    }


    @Override
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public T[] removeAll(T... vals) {
        T[] deleted = (T[]) Array.newInstance(type, vals.length);
        for (int i = 0; i < vals.length; i++) {
            deleted[i] = this.remove(vals[i]);
        }
        return deleted;
    }

    @Override
    public boolean search(T val) {
        if (val != null) {
            BTreeNode<T> node = this.root;
            int i, comp, till;
            while (node != null) {
                for (i = 0, till = node.counter; i < node.counter; i++) {
                    comp = this.orderMul * node.values[i].compareTo(val);
                    if (comp > 0) {
                        node = node.children[i];
                        break;
                    } else if (comp == 0) {
                        return true;
                    }
                }
                if (i == till) {
                    node = node.children[node.counter];
                }
            }
        }
        return false;
    }

    private BTreeNode<T> findEligibleNode(T value) {
        BTreeNode<T> node = this.root;
        BTreeNode<T> prev = node;
        int i, till;
        while (node != null) {
            for (i = 0, till = node.counter; i < till; i++) {
                if ((this.orderMul * node.values[i].compareTo(value)) > 0) {
                    prev = node;
                    node = node.children[i];
                    break;
                }
            }
            if (i == till) {
                prev = node;
                node = node.children[node.counter];
            }
        }
        return prev;
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("{\"order\":").append(order)
                .append(",\"total values\":").append(valuesCount)
                .append(",\"bias\":\"").append(leftBias ? "left biased" : "right biased").append("\"")
                .append(",\"sorting order\":\"").append(orderMul == -1 ? "inverse sorting order" : "natural sorting order").append("\"")
                .append(",\"data\":").append(this.root.toString())
                .append("}");
        return build.toString();
    }

//    BTreeNode<T>[] inOrder() {
//
//    }
//
//    BTreeNode<T>[] preOrder() {
//
//    }
//
//    BTreeNode<T>[] postOrder() {
//
//    }
//
//    BTreeNode<T>[] levelOrder() {
//
//    }
}

class BTreeNode<T> {

    T[] values;
    BTreeNode<T>[] children;

    BTreeNode<T> parent;
    int counter;

    @SuppressWarnings("unchecked")
    BTreeNode(Class<T> type, int order, T value) {
        values = (T[]) Array.newInstance(type, order - 1);
        children = new BTreeNode[order];
        if (value != null) values[counter++] = value;
    }

    BTreeNode(Class<T> type, int order, T value, BTreeNode<T> parent) {
        this(type, order, value);
        this.parent = parent;
    }

    void updateChildren() {
        Arrays.stream(children).forEach(child -> {
            if (child != null) child.parent = this;
        });
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("{\"values\":").append(Arrays.toString(Arrays.stream(values).filter(Objects::nonNull).map(Objects::toString).toArray()));
        if (children[0] != null)
            build.append(",\"children\":").append(Arrays.toString(Arrays.stream(children).filter(Objects::nonNull).map(Objects::toString).toArray()));
        build.append("}");
        return build.toString();
    }
}
