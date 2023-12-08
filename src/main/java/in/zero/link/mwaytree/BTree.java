package in.zero.link.mwaytree;

import in.zero.array.ArrayUtils;

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
public class BTree<T extends Comparable<T>> extends MwaySearchTree<T> {

    private final int MIDDLE;

    /**
     * BTree constructor
     *
     * @param type type of the data which needs to be stored
     */
    public BTree(Class<T> type) {
        this(type, 5, false, false);
    }

    /**
     * BTree constructor
     *
     * @param type    type of the data which needs to be stored
     * @param inverse inverse the order of sorting?, Default - natural sorting order
     */
    public BTree(Class<T> type, boolean inverse) {
        this(type, 5, inverse, false);
    }

    /**
     * BTree constructor
     *
     * @param type type of the data which needs to be stored
     * @param bias left/right bias based on which even ordered BTree's get constructed, Default - right bias
     */
    public BTree(boolean bias, Class<T> type) {
        this(type, 5, false, bias);
    }

    /**
     * BTree constructor
     *
     * @param type  type of the data which needs to be stored
     * @param order order of BTree, Default - 5
     */
    public BTree(Class<T> type, int order) {
        this(type, order, false, false);
    }

    /**
     * BTree constructor
     *
     * @param type    type of the data which needs to be stored
     * @param order   order of BTree, Default - 5
     * @param inverse inverse the order of sorting?, Default - natural sorting order
     */
    public BTree(Class<T> type, int order, boolean inverse) {
        this(type, order, inverse, false);
    }

    /**
     * BTree constructor
     *
     * @param type  type of the data which needs to be stored
     * @param order order of BTree, Default - 5
     * @param bias  left/right bias based on which even ordered BTree's get constructed, Default - right bias
     */
    public BTree(Class<T> type, boolean bias, int order) {
        this(type, order, false, bias);
    }

    /**
     * BTree constructor
     *
     * @param type    type of the data which needs to be stored
     * @param bias    left/right bias based on which even ordered BTree's get constructed, Default - right bias
     * @param inverse inverse the order of sorting?, Default - natural sorting order
     */
    public BTree(Class<T> type, boolean bias, boolean inverse) {
        this(type, 5, inverse, bias);
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
        super(type, order, inverse);

        int middle = (int) Math.floor((double) order / 2);
        if (order % 2 == 0 && !bias) {
            middle--;
        }
        MIDDLE = middle;
    }

    @Override
    BTreeNode<T> createNode(T value, MwayNode<T> parent) {
        return new BTreeNode<>(this.TYPE, this.ORDER, value, (BTreeNode<T>) parent);
    }

    @Override
    void insertAndSplit(final MwayNode<T> node, final int insertIndex, final T value, final MwayNode<T> right) {
        MwayNode<T> parent = node.parent;
        MwayNode<T> newNode = createNode(null, parent);

        T[] temp = Arrays.copyOf(node.values, ORDER);
        ArrayUtils.unshift(temp, insertIndex, value);
        Arrays.fill(node.values, MIDDLE, ORDER - 1, null);
        ArrayUtils.copyRangeToAnotherArray(temp, 0, MIDDLE, node.values);
        ArrayUtils.copyRangeToAnotherArray(temp, MIDDLE + 1, temp.length, newNode.values);
        node.counter = MIDDLE;
        newNode.counter = ORDER - MIDDLE - 1;

        if (right != null) {
            MwayNode<T>[] tempChildren = Arrays.copyOf(node.children, ORDER + 1);
            ArrayUtils.unshift(tempChildren, insertIndex + 1, right);
            Arrays.fill(node.children, MIDDLE, ORDER, null);
            ArrayUtils.copyRangeToAnotherArray(tempChildren, 0, MIDDLE + 1, node.children);
            ArrayUtils.copyRangeToAnotherArray(tempChildren, MIDDLE + 1, ORDER + 1, newNode.children);
            newNode.updateChildren();
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
            MwayNode<T> newRoot = createNode(splitOnValue, null);
            newRoot.children[0] = node;
            newRoot.children[1] = newNode;
            this.root = newRoot;
            node.parent = newRoot;
            newNode.parent = newRoot;
        }
    }

    /**
     * remove a value from the tree
     *
     * @param val value to be removed
     * @return Value which gets removed
     */
    @Override
    public T remove(T val) {

        FoundNodeAtIndex<T> found = searchNode(val);
        if (found != null) {
            final int MIN_KEYS = (int) Math.ceil((double) ORDER / 2) - 1;
            MwayNode<T> node = found.node;
            int foundIndex = found.index;
            int childIndex = found.childIndex;

            if (node.children[0] == null) {
                ArrayUtils.shift(node.values, foundIndex);
            } else {
                FoundNodeAtIndex<T> replacer = inOrderPredecessor(node, foundIndex);
                MwayNode<T> replaceWithNode = replacer.node;
                if (replaceWithNode.counter > MIN_KEYS) {
                    node.values[foundIndex] = replaceWithNode.values[replacer.index];
                    replaceWithNode.values[replacer.index] = null;
                } else {
                    replacer = inOrderSuccessor(node, foundIndex);
                    replaceWithNode = replacer.node;
                    node.values[foundIndex] = replaceWithNode.values[replacer.index];
                    ArrayUtils.shift(replaceWithNode.values, replacer.index);
                }
                childIndex = replacer.childIndex;
                node = replaceWithNode;
            }
            node.counter--;

            MwayNode<T> leftSibling = null, rightSibling = null, parent;
            while (node.parent != null && node.counter < MIN_KEYS && childIndex >= 0) {
                parent = node.parent;
                if (childIndex > 0) {
                    leftSibling = parent.children[childIndex - 1];
                } else {
                    leftSibling = null;
                }
                if (childIndex < parent.counter) {
                    rightSibling = parent.children[childIndex + 1];
                } else {
                    rightSibling = null;
                }

                if (leftSibling != null) {
                    if (leftSibling.counter > MIN_KEYS) {
                        borrowFromLeftSibling(node, leftSibling, childIndex);
                    } else if (rightSibling != null) {
                        if (rightSibling.counter > MIN_KEYS) {
                            borrowFromRightSibling(node, rightSibling, childIndex);
                        } else {
                            // merge right child with node
                            mergeSibling(node, rightSibling, childIndex);
                            node = parent;
                            childIndex = getChildIndex(node);
                        }
                    } else {
                        // merge node with left child
                        mergeSibling(leftSibling, node, childIndex - 1);
                        node = parent;
                        childIndex = getChildIndex(node);
                    }
                } else if (rightSibling.counter > MIN_KEYS) {
                    borrowFromRightSibling(node, rightSibling, childIndex);
                } else {
                    // merge right child with node
                    mergeSibling(node, rightSibling, childIndex);
                    node = parent;
                    childIndex = getChildIndex(node);
                }
            }
            if (node == root && node.counter == 0) {
                this.root = node.children[0];
                if (this.root != null) this.root.parent = null;
            }
            valuesCount--;
            return val;
        }
        return null;
    }

    private int getChildIndex(MwayNode<T> node) {
        if (node.parent != null) {
            for (int place = 0; place <= node.parent.counter; place++) {
                if (node.parent.children[place] == node) {
                    return place;
                }
            }
        }
        return -1;
    }

    private FoundNodeAtIndex<T> inOrderPredecessor(MwayNode<T> node, int index) {
        node = node.children[index];
        while (node != null && node.children[node.counter] != null) {
            index = node.counter;
            node = node.children[node.counter];
        }
        return new FoundNodeAtIndex<>(node, node.counter - 1, index);
    }

    private FoundNodeAtIndex<T> inOrderSuccessor(MwayNode<T> node, int index) {
        node = node.children[index + 1];
        index = index + 1;
        while (node != null && node.children[0] != null) {
            index = 0;
            node = node.children[0];
        }
        return new FoundNodeAtIndex<>(node, 0, index);
    }

    private void borrowFromLeftSibling(MwayNode<T> node, MwayNode<T> leftSibling, int index) {
        MwayNode<T> parent = node.parent;
        ArrayUtils.unshift(node.values, 0, parent.values[index - 1]);
        node.counter++;
        parent.values[index - 1] = leftSibling.values[leftSibling.counter - 1];
        leftSibling.values[leftSibling.counter - 1] = null;
        if (leftSibling.children[leftSibling.counter] != null) {
            ArrayUtils.unshift(node.children, 0, leftSibling.children[leftSibling.counter]);
            leftSibling.children[leftSibling.counter].parent = node;
            leftSibling.children[leftSibling.counter] = null;
        }
        leftSibling.counter--;
    }

    private void borrowFromRightSibling(MwayNode<T> node, MwayNode<T> rightSibling, int index) {
        MwayNode<T> parent = node.parent;
        node.values[node.counter] = parent.values[index];
        node.counter++;
        parent.values[index] = rightSibling.values[0];
        ArrayUtils.shift(rightSibling.values, 0);
        if (rightSibling.children[0] != null) {
            node.children[node.counter] = rightSibling.children[0];
            rightSibling.children[0].parent = node;
            ArrayUtils.shift(rightSibling.children, 0);
        }
        rightSibling.counter--;
    }

    private void mergeSibling(MwayNode<T> node, MwayNode<T> rightSibling, int index) {
        MwayNode<T> parent = node.parent;
        node.values[node.counter] = parent.values[index];
        node.counter++;
        ArrayUtils.copyRangeToAnotherArray(rightSibling.values, 0, rightSibling.counter, node.values, node.counter, ORDER - 1);
        ArrayUtils.copyRangeToAnotherArray(rightSibling.children, 0, rightSibling.counter + 1, node.children, node.counter, ORDER);
        node.updateChildren();
        node.counter += rightSibling.counter;
        ArrayUtils.shift(parent.values, index);
        ArrayUtils.shift(parent.children, index + 1);
        rightSibling.parent = null;
        parent.counter--;
    }

    public int getHeight() {
        int count = -1;
        MwayNode<T> node = this.root;
        while (node != null) {
            count++;
            node = node.children[0];
        }
        return count;
    }

    public String toString() {
        String str = super.toString();
        if (ORDER % 2 == 0) {
            str = str.substring(0, str.length() - 1);
            str += ",\"bias\":";
            if (Math.floor((double) ORDER / 2) != MIDDLE) {
                str += "\"right biased\"}";
            } else {
                str += "\"left biased\"}";
            }
        }
        return str;
    }
}


class BTreeNode<T extends Comparable<T>> extends MwayNode<T> {

    BTreeNode(Class<T> type, int order, T value, BTreeNode<T> parent) {
        super(type, order, value, parent);
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("{\"values\":").append(Arrays.toString(Arrays.stream(values).filter(Objects::nonNull).map(Objects::toString).toArray()));
        if (children[0] != null)
            build.append(",\"children\":").append(Arrays.toString(Arrays.stream(children).filter(Objects::nonNull).map(Objects::toString).toArray()));

        return build.append("}").toString();
    }
}