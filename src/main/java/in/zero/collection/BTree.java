package in.zero.collection;

import in.zero.collection.array.ArrayUtils;

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

    private final int ORDER;

    private final Class<T> TYPE;

    private final boolean LEFT_BIAS;

    BTreeNode<T> root;

    private final int ORDER_MUL;

    int valuesCount;

    private final int MIN_KEYS;

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
        LEFT_BIAS = bias;
        ORDER_MUL = inverse ? -1 : 1;
        if (type != null) {
            TYPE = type;
        } else {
            throw new IllegalArgumentException("Class type for the data is required.");
        }
        if (order >= 3) {
            ORDER = order;
            int middle = (int) Math.floor((double) order / 2);
            if (order % 2 == 0 && !LEFT_BIAS) {
                middle--;
            }
            MIDDLE = middle;
            MIN_KEYS = (int) Math.ceil((double) order / 2) - 1;
        } else {
            throw new IllegalArgumentException("Order for BTree must be >= 3");
        }
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
                this.root = new BTreeNode<>(TYPE, ORDER, value);
            } else {
                FoundNodeAtIndex<T> found = findEligibleNode(value);
                if ((found.node.counter + 1) < ORDER) {
                    shiftAndInsertAt(found.node, found.index, value, null);
                } else {
                    insertAndSplit(found.node, found.index, value, null);
                }
            }
            valuesCount++;
        } else {
            throw new IllegalArgumentException("BTree can't store null values");
        }
        return this;
    }

    private void shiftAndInsertAt(final BTreeNode<T> node, final int insertIndex, final T value, final BTreeNode<T> right) {
        ArrayUtils.unshift(node.values, insertIndex, value, node.counter);
        node.counter++;
        if (right != null) {
            right.parent = node;
            ArrayUtils.unshift(node.children, insertIndex + 1, right);
        }
    }

    private void insertAndSplit(final BTreeNode<T> node, final int insertIndex, final T value, final BTreeNode<T> right) {
        BTreeNode<T> parent = node.parent;
        BTreeNode<T> newNode = new BTreeNode<>(this.TYPE, ORDER, null, parent);

        T[] temp = Arrays.copyOf(node.values, ORDER);
        ArrayUtils.unshift(temp, insertIndex, value);
        Arrays.fill(node.values, MIDDLE, ORDER - 1, null);
        ArrayUtils.copyRangeToAnotherArray(temp, 0, MIDDLE, node.values);
        ArrayUtils.copyRangeToAnotherArray(temp, MIDDLE + 1, temp.length, newNode.values);
        node.counter = MIDDLE;
        newNode.counter = ORDER - MIDDLE - 1;

        if (right != null) {
            BTreeNode<T>[] tempChildren = Arrays.copyOf(node.children, ORDER + 1);
            ArrayUtils.unshift(tempChildren, insertIndex + 1, right);
            Arrays.fill(node.children, MIDDLE, node.children.length, null);
            ArrayUtils.copyRangeToAnotherArray(tempChildren, 0, MIDDLE + 1, node.children);
            ArrayUtils.copyRangeToAnotherArray(tempChildren, MIDDLE + 1, tempChildren.length, newNode.children);
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
            BTreeNode<T> newRoot = new BTreeNode<T>(this.TYPE, ORDER, splitOnValue);
            newRoot.children[0] = node;
            newRoot.children[1] = newNode;
            this.root = newRoot;
            node.parent = newRoot;
            newNode.parent = newRoot;
        }
    }

    /**
     * Add multiple values to the tree
     *
     * @param vals values which needs to be added in the tree
     * @return reference to tree object
     */
    @Override
    @SuppressWarnings("unchecked")
    public Collection<T> addAll(T... vals) {
        for (T val : vals) {
            this.add(val);
        }
        return this;
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
            BTreeNode<T> node = found.node;
            int foundIndex = found.index;
            int childIndex = found.childIndex;

            if (node.children[0] == null) {
                ArrayUtils.shift(node.values, foundIndex);
            } else {
                FoundNodeAtIndex<T> replacer = inOrderPredecessor(node, foundIndex);
                BTreeNode<T> replaceWithNode = replacer.node;
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

            BTreeNode<T> leftSibling = null, rightSibling = null, parent;
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

    private int getChildIndex(BTreeNode<T> node) {
        if (node.parent != null) {
            for (int place = 0; place <= node.parent.counter; place++) {
                if (node.parent.children[place] == node) {
                    return place;
                }
            }
        }
        return -1;
    }

    private FoundNodeAtIndex<T> inOrderPredecessor(BTreeNode<T> node, int index) {
        node = node.children[index];
        while (node != null && node.children[node.counter] != null) {
            index = node.counter;
            node = node.children[node.counter];
        }
        return new FoundNodeAtIndex<>(node, node.counter - 1, index);
    }

    private FoundNodeAtIndex<T> inOrderSuccessor(BTreeNode<T> node, int index) {
        node = node.children[index + 1];
        index = index + 1;
        while (node != null && node.children[0] != null) {
            index = 0;
            node = node.children[0];
        }
        return new FoundNodeAtIndex<>(node, 0, index);
    }

    private void borrowFromLeftSibling(BTreeNode<T> node, BTreeNode<T> leftSibling, int index) {
        BTreeNode<T> parent = node.parent;
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

    private void borrowFromRightSibling(BTreeNode<T> node, BTreeNode<T> rightSibling, int index) {
        BTreeNode<T> parent = node.parent;
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

    private void mergeSibling(BTreeNode<T> node, BTreeNode<T> rightSibling, int index) {
        BTreeNode<T> parent = node.parent;
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

    private FoundNodeAtIndex<T> searchNode(T val) {
        if (val != null) {
            BTreeNode<T> node = this.root;
            int i, comp, till, childIndex = -1;
            while (node != null) {
                for (i = 0, till = node.counter; i < till; i++) {
                    comp = this.ORDER_MUL * node.values[i].compareTo(val);
                    if (comp > 0) {
                        childIndex = i;
                        node = node.children[i];
                        break;
                    } else if (comp == 0) {
                        return new FoundNodeAtIndex<>(node, i, childIndex);
                    }
                }
                if (i == till) {
                    childIndex = till;
                    node = node.children[node.counter];
                }
            }
        }
        return null;
    }

    private FoundNodeAtIndex<T> findEligibleNode(T value) {
        BTreeNode<T> node = this.root;
        BTreeNode<T> prev = node;
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
                    throw new IllegalArgumentException("BTree can't store duplicate values");
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

    @Override
    public String toString() {
        if (this.root != null) {
            StringBuilder build = new StringBuilder();
            build.append("{\"order\":").append(ORDER)
                    .append(",\"total values\":").append(valuesCount)
                    .append(",\"bias\":\"").append(LEFT_BIAS ? "left biased" : "right biased").append("\"")
                    .append(",\"sorting order\":\"").append(ORDER_MUL == -1 ? "inverse sorting order" : "natural sorting order").append("\"")
                    .append(",\"data\":").append(this.root.toString())
                    .append("}");
            return build.toString();
        } else return "<empty BTree>";
    }

    @SuppressWarnings("unchecked")
    public T[] preOrder() {
        T[] pre = (T[]) Array.newInstance(TYPE, valuesCount);
        if (this.root != null) preOrder(this.root, pre, 0);
        return pre;
    }

    @SuppressWarnings("unchecked")
    public T[] postOrder() {
        T[] post = (T[]) Array.newInstance(TYPE, valuesCount);
        if (this.root != null) postOrder(this.root, post, 0);
        return post;
    }

    @SuppressWarnings("unchecked")
    public T[] inOrder() {
        T[] in = (T[]) Array.newInstance(TYPE, valuesCount);
        if (this.root != null) inOrder(this.root, in, 0);
        return in;
    }

    @SuppressWarnings("unchecked")
    public T[] reverseOrder() {
        T[] reverse = (T[]) Array.newInstance(TYPE, valuesCount);
        if (this.root != null) reverseOrder(this.root, reverse, 0);
        return reverse;
    }

    @SuppressWarnings("unchecked")
    public T[] levelOrder() {
        T[] level = (T[]) Array.newInstance(TYPE, valuesCount);
        final int arrCount = valuesCount / ((int) Math.ceil((double) ORDER / 2) - 1);
        BTreeNode<T>[] stack = new BTreeNode[arrCount];
        int stackCounter = 0, count = 0, stackStart = 0;
        BTreeNode<T> node;
        stack[stackCounter++] = this.root;
        while (stackStart < stackCounter) {
            node = stack[stackStart++];
            for (int i = 0; i < node.counter; i++) {
                level[count++] = node.values[i];
                if (node.children[i] != null) stack[stackCounter++] = node.children[i];
            }
            if (node.children[node.counter] != null) stack[stackCounter++] = node.children[node.counter];
        }
        return level;
    }

    private int preOrder(BTreeNode<T> node, T[] pre, int count) {
        for (int i = 0; i < node.counter; i++) {
            pre[count++] = node.values[i];
            if (node.children[i] != null) {
                count = preOrder(node.children[i], pre, count);
            }
        }
        if (node.children[node.counter] != null) {
            count = preOrder(node.children[node.counter], pre, count);
        }
        return count;
    }

    private int inOrder(BTreeNode<T> node, T[] in, int count) {
        for (int i = 0; i < node.counter; i++) {
            if (node.children[i] != null) {
                count = inOrder(node.children[i], in, count);
            }
            in[count++] = node.values[i];
        }
        if (node.children[node.counter] != null) {
            count = inOrder(node.children[node.counter], in, count);
        }
        return count;
    }

    private int postOrder(BTreeNode<T> node, T[] post, int count) {
        if (node.children[0] != null) {
            count = postOrder(node.children[0], post, count);
        }
        for (int i = 0; i < node.counter; i++) {
            if (node.children[i + 1] != null) {
                count = postOrder(node.children[i + 1], post, count);
            }
            post[count++] = node.values[i];
        }
        return count;
    }

    private int reverseOrder(BTreeNode<T> node, T[] reverse, int count) {
        for (int i = node.counter; i > 0; i--) {
            if (node.children[i] != null) {
                count = reverseOrder(node.children[i], reverse, count);
            }
            reverse[count++] = node.values[i - 1];
        }
        if (node.children[0] != null) {
            count = reverseOrder(node.children[0], reverse, count);
        }
        return count;
    }

    public int getHeight() {
        int count = -1;
        BTreeNode<T> node = this.root;
        while (node != null) {
            count++;
            node = node.children[0];
        }
        return count;
    }
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


class FoundNodeAtIndex<T> {
    BTreeNode<T> node;
    int index;

    int childIndex;

    FoundNodeAtIndex(BTreeNode<T> node, int index, int childIndex) {
        this.node = node;
        this.index = index;
        this.childIndex = childIndex;
    }
}