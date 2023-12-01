package in.zero.link.mwaytree;

import in.zero.Collection;
import in.zero.array.ArrayUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

class MwaySearchTree<T extends Comparable<T>> implements Collection<T> {
    final int ORDER;

    final Class<T> TYPE;

    MwayNode<T> root;

    final int ORDER_MUL;

    int valuesCount;

    MwaySearchTree(Class<T> type) {
        this(type, 5, false);
    }

    MwaySearchTree(Class<T> type, int order) {
        this(type, order, false);
    }

    MwaySearchTree(Class<T> type, boolean inverse) {
        this(type, 5, inverse);
    }

    MwaySearchTree(Class<T> type, int order, boolean inverse) {

        if (type != null) {
            TYPE = type;
        } else {
            throw new IllegalArgumentException("Class type for the data is required.");
        }

        if (order >= 3) {
            ORDER = order;
        } else {
            throw new IllegalArgumentException("Order for BTree must be >= 3");
        }

        ORDER_MUL = inverse ? -1 : 1;
    }

    void shiftAndInsertAt(final MwayNode<T> node, final int insertIndex, final T value, final MwayNode<T> right) {
        ArrayUtils.unshift(node.values, insertIndex, value, node.counter);
        node.counter++;
        if (right != null) {
            right.parent = node;
            ArrayUtils.unshift(node.children, insertIndex + 1, right);
        }
    }

    @Override
    public Collection<T> add(T val) {
        return null;
    }

    /**
     * Add multiple values to the tree
     *
     * @param vals values which needs to be added in the tree
     * @return reference to tree object
     */
    @Override
    @SuppressWarnings("unchecked")
    public MwaySearchTree<T> addAll(T... vals) {
        for (T val : vals) {
            this.add(val);
        }
        return this;
    }

    @Override
    public T remove(T val) {
        return null;
    }

    /**
     * remove multiple elements from the tree
     *
     * @param vals values which needs to be removed
     * @return array of all removed values
     */
    @Override
    @SuppressWarnings("unchecked")
    public T[] removeAll(T... vals) {
        T[] deleted = (T[]) Array.newInstance(TYPE, vals.length);
        for (int i = 0; i < vals.length; i++) {
            deleted[i] = this.remove(vals[i]);
        }
        return deleted;
    }

    /**
     * Search an element in the tree
     *
     * @param val value to be searched
     * @return true/false
     */
    @Override
    public boolean search(T val) {
        return searchNode(val) != null;
    }

    FoundNodeAtIndex<T> searchNode(T val) {
        return null;
    }

    public int getSize() {
        return valuesCount;
    }

    @Override
    public String toString() {
        if (this.root != null) {
            return new StringBuilder("{\"order\":").append(ORDER)
                    .append(",\"total values\":").append(valuesCount)
                    .append(",\"sorting order\":\"").append(ORDER_MUL == -1 ? "inverse sorting order" : "natural sorting order").append("\"")
                    .append(",\"data\":").append(this.root.toString())
                    .append("}").toString();
        } else return "<empty tree>";
    }
}

class MwayNode<T extends Comparable<T>> {
    T[] values;
    MwayNode<T>[] children;

    MwayNode<T> parent;
    int counter;
    MwayNode(){}

    @SuppressWarnings("unchecked")
    MwayNode(Class<T> type, int order, T value) {
        values = (T[]) Array.newInstance(type, order - 1);
        children = new MwayNode[order];
        if (value != null) values[counter++] = value;
    }

    MwayNode(Class<T> type, int order, T value, MwayNode<T> parent) {
        this(type, order, value);
        this.parent = parent;
    }

    void updateChildren() {
        Arrays.stream(this.children).forEach(child -> {
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


class FoundNodeAtIndex<T extends Comparable<T>> {
    MwayNode<T> node;
    int index;

    int childIndex;

    FoundNodeAtIndex(MwayNode<T> node, int index, int childIndex) {
        this.node = node;
        this.index = index;
        this.childIndex = childIndex;
    }
}