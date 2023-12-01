package in.zero.link.mwaytree;

import in.zero.array.ArrayUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class BPlusTree<T extends Comparable<T>> extends MwaySearchTree<T> {

    private final int MIDDLE;

    public static void main(String[] args) {
        BPlusTree<Integer> tree = new BPlusTree<>(Integer.class, true, 4);
        Stream.of(10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35, 28, 50, 56).forEach(elem -> {
            System.out.println(elem);
            tree.add(elem);
        });
        System.out.println(tree);

    }


    /**
     * BPlusTree constructor
     *
     * @param type type of the data which needs to be stored
     */
    public BPlusTree(Class<T> type) {
        this(type, 5, false, false);
    }

    /**
     * BPlusTree constructor
     *
     * @param type    type of the data which needs to be stored
     * @param inverse inverse the order of sorting?, Default - natural sorting order
     */
    public BPlusTree(Class<T> type, boolean inverse) {
        this(type, 5, inverse, false);
    }

    /**
     * BPlusTree constructor
     *
     * @param type type of the data which needs to be stored
     * @param bias left/right bias based on which even ordered BPlusTree's get constructed, Default - right bias
     */
    public BPlusTree(boolean bias, Class<T> type) {
        this(type, 5, false, bias);
    }

    /**
     * BPlusTree constructor
     *
     * @param type  type of the data which needs to be stored
     * @param order order of BPlusTree, Default - 5
     */
    public BPlusTree(Class<T> type, int order) {
        this(type, order, false, false);
    }

    /**
     * BPlusTree constructor
     *
     * @param type    type of the data which needs to be stored
     * @param order   order of BPlusTree, Default - 5
     * @param inverse inverse the order of sorting?, Default - natural sorting order
     */
    public BPlusTree(Class<T> type, int order, boolean inverse) {
        this(type, order, inverse, false);
    }

    /**
     * BPlusTree constructor
     *
     * @param type  type of the data which needs to be stored
     * @param order order of BPlusTree, Default - 5
     * @param bias  left/right bias based on which even ordered BPlusTree's get constructed, Default - right bias
     */
    public BPlusTree(Class<T> type, boolean bias, int order) {
        this(type, order, false, bias);
    }

    /**
     * BPlusTree constructor
     *
     * @param type    type of the data which needs to be stored
     * @param bias    left/right bias based on which even ordered BPlusTree's get constructed, Default - right bias
     * @param inverse inverse the order of sorting?, Default - natural sorting order
     */
    public BPlusTree(Class<T> type, boolean bias, boolean inverse) {
        this(type, 5, inverse, bias);
    }

    /**
     * BPlusTree constructor
     *
     * @param type    type of the data which needs to be stored
     * @param order   order of BPlusTree, Default - 5
     * @param bias    left/right bias based on which even ordered BPlusTree's get constructed, Default - right bias
     * @param inverse inverse the order of sorting?, Default - natural sorting order
     */
    public BPlusTree(Class<T> type, int order, boolean inverse, boolean bias) {
        super(type, order, inverse);

        int middle = (int) Math.floor((double) order / 2);
        if (order % 2 == 0 && !bias) {
            middle--;
        }
        MIDDLE = middle;
    }

    /**
     * Add value to the tree
     *
     * @param value Value which needs to be added
     * @return BPlusTree reference
     */
    @Override
    public BPlusTree<T> add(T value) {
        if (value != null) {
            if (root == null) {
                this.root = new BPlusTreeNode<>(TYPE, ORDER, value);
            } else {
                FoundNodeAtIndex<T> found = findEligibleNode(value);
                if ((found.node.counter + 1) < ORDER) {
                    shiftAndInsertAt(found.node, found.index, value, null);
                } else {
                    insertAndSplit((BPlusTreeNode<T>) found.node, found.index, value, null);
                }
            }
            valuesCount++;
        } else {
            throw new IllegalArgumentException("BPlusTree can't store null values");
        }
        return this;
    }

    private FoundNodeAtIndex<T> findEligibleNode(T value) {
        BPlusTreeNode<T> node = (BPlusTreeNode<T>) this.root;
        BPlusTreeNode<T> prev = node;
        int i, till, foundIndex = 0, comp, childIndex = -1;
        while (node != null) {
            for (i = 0, till = node.counter; i < till; i++) {
                comp = this.ORDER_MUL * node.values[i].compareTo(value);
                if (comp > 0) {
                    prev = node;
                    childIndex = foundIndex;
                    node = node.children[i];
                    foundIndex = i;
                    break;
                } else if (comp == 0) {
                    throw new IllegalArgumentException("BPlusTree can't store duplicate values");
                }
            }
            if (i == till) {
                prev = node;
                childIndex = foundIndex;
                node = node.children[node.counter];
                foundIndex = till;
            }
        }
        return new FoundNodeAtIndex<>(prev, foundIndex, childIndex);
    }

    void shiftAndInsertAt(final BPlusTreeNode<T> node, final int insertIndex, final T value, final BPlusTreeNode<T> right) {
        ArrayUtils.unshift(node.values, insertIndex, value, node.counter);
        node.counter++;
        if (right != null) {
            right.parent = node;
            ArrayUtils.unshift(node.children, insertIndex + 1, right);
        }
    }

    void insertAndSplit(final BPlusTreeNode<T> node, final int insertIndex, final T value, final BPlusTreeNode<T> right) {
        BPlusTreeNode<T> parent = (BPlusTreeNode<T>) node.parent;
        BPlusTreeNode<T> newNode = new BPlusTreeNode<>(this.TYPE, ORDER, null, parent);

        T[] temp = Arrays.copyOf(node.values, ORDER);
        ArrayUtils.unshift(temp, insertIndex, value);
        Arrays.fill(node.values, MIDDLE, ORDER - 1, null);
        ArrayUtils.copyRangeToAnotherArray(temp, 0, MIDDLE, node.values);
        node.counter = MIDDLE;

        if (right != null) {
            ArrayUtils.copyRangeToAnotherArray(temp, MIDDLE + 1, ORDER, newNode.values);
            newNode.counter = ORDER - MIDDLE - 1;

            BPlusTreeNode<T>[] tempChildren = Arrays.copyOf((BPlusTreeNode<T>[]) node.children, ORDER + 1);
            ArrayUtils.unshift(tempChildren, insertIndex + 1, right);
            Arrays.fill(node.children, MIDDLE, ORDER, null);
            ArrayUtils.copyRangeToAnotherArray(tempChildren, 0, MIDDLE + 1, node.children);
            ArrayUtils.copyRangeToAnotherArray(tempChildren, MIDDLE + 1, ORDER + 1, newNode.children);
            newNode.updateChildren();
        } else {
            ArrayUtils.copyRangeToAnotherArray(temp, MIDDLE, ORDER, newNode.values);
            newNode.counter = ORDER - MIDDLE;
            node.next = newNode;
        }

        T splitOnValue = temp[MIDDLE];
        if (parent != null) {
            for (int i = 0; i <= parent.counter; i++) {
                if (parent.children[i] == node) {
                    if ((parent.counter + 1) < ORDER) {
                        shiftAndInsertAt(parent, i, splitOnValue, newNode);
                    } else {
                        insertAndSplit(parent, i, splitOnValue, newNode);
                    }
                    break;
                }
            }
        } else {
            BPlusTreeNode<T> newRoot = new BPlusTreeNode<T>(this.TYPE, ORDER, splitOnValue);
            newRoot.children[0] = node;
            newRoot.children[1] = newNode;
            this.root = newRoot;
            node.parent = newRoot;
            newNode.parent = newRoot;
        }
    }


    @Override
    public T remove(T val) {
        return null;
    }

    FoundNodeAtIndex<T> searchNode(T val) {
        if (val != null) {
            BPlusTreeNode<T> node = (BPlusTreeNode<T>) this.root;
            int i, comp, till, childIndex = -1;
            while (node != null) {
                for (i = 0, till = node.counter; i < till; i++) {
                    comp = this.ORDER_MUL * node.values[i].compareTo(val);
                    if (comp > 0) {
                        childIndex = i;
                        node = (BPlusTreeNode<T>) node.children[i];
                        break;
                    } else if (comp == 0) {
                        return new FoundNodeAtIndex<>(node, i, childIndex);
                    }
                }
                if (i == till) {
                    childIndex = till;
                    node = (BPlusTreeNode<T>) node.children[node.counter];
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String str = super.toString();
        if (!str.equalsIgnoreCase("<empty tree>") && ORDER % 2 == 0) {
            str = str.substring(0, str.length() - 1);
            str += ",\"bias\":\"" + ((ORDER / 2) == MIDDLE ? "left biased" : "right biased") + "\"}";
        }
        return str;
    }
}

class BPlusTreeNode<T extends Comparable<T>> extends MwayNode<T> {

    BPlusTreeNode<T> next;
    BPlusTreeNode<T>[] children;

    @SuppressWarnings("unchecked")
    BPlusTreeNode(Class<T> type, int order, T value) {
        values = (T[]) Array.newInstance(type, order - 1);
        children = new BPlusTreeNode[order];
        if (value != null) values[counter++] = value;
    }

    BPlusTreeNode(Class<T> type, int order, T value, MwayNode<T> parent) {
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