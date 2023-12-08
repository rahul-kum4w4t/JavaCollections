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
            throw new IllegalArgumentException("Order for " + this.getClass().getSimpleName() + " must be >= 3");
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

    MwayNode<T> createNode(T value, MwayNode<T> parent) {
        return new MwayNode<>(this.TYPE, this.ORDER, value, parent);
    }

    void insertAndSplit(final MwayNode<T> node, final int insertIndex, final T value, final MwayNode<T> right) {
        MwayNode<T> newNode = createNode(value, node);
        node.children[insertIndex] = newNode;
    }

    @Override
    public Collection<T> add(T value) {
        if (value != null) {
            if (root == null) {
                this.root = createNode(value, null);
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
            throw new IllegalArgumentException(this.getClass().getSimpleName() + " can't store null values");
        }
        return this;
    }

    FoundNodeAtIndex<T> findEligibleNode(T value) {
        MwayNode<T> node = this.root;
        MwayNode<T> prev = node;
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
                    throw new IllegalArgumentException(this.getClass().getSimpleName() + " can't store duplicate values");
                }
            }
            if (i == till) {
                prev = node;
                childIndex = foundIndex;
                node = node.children[i];
                foundIndex = i;
            }
        }
        return new FoundNodeAtIndex<>(prev, foundIndex, childIndex);
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
        FoundNodeAtIndex<T> fNode = searchNode(val);
        if (fNode != null) {
            rm(fNode);
            return val;
        } else {
            return null;
        }
    }

    public void rm(FoundNodeAtIndex<T> fNode) {

        MwayNode<T> node = fNode.node;
        int index = fNode.index;
        MwayNode<T> lNode = node.children[index];
        MwayNode<T> rNode = node.children[index + 1];
        if (lNode != null) {
            node.values[index] = lNode.values[lNode.counter - 1];
            fNode.node = lNode;
            fNode.index = lNode.counter - 1;
            fNode.childIndex = index;
            rm(fNode);
        } else if (rNode != null) {
            node.values[index] = rNode.values[0];
            fNode.node = rNode;
            fNode.index = 0;
            fNode.childIndex = index + 1;
            rm(fNode);
        } else {
            ArrayUtils.shift(node.values, index);
            ArrayUtils.shift(node.children, index);
            node.counter--;
            if (node.counter == 0) {
                if (node == root) {
                    root = null;
                } else {
                    node.parent.children[fNode.childIndex] = null;
                }
            }
        }
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
        if (val != null) {
            MwayNode<T> node = this.root;
            int i, comp, till, childIndex = -1;
            while (node != null) {
                for (i = 0, till = node.counter; i < till; i++) {
                    comp = this.ORDER_MUL * node.values[i].compareTo(val);
                    if (comp > 0) {
                        if (node.children[i] != null) {
                            childIndex = i;
                            node = node.children[i];
                            break;
                        } else {
                            return null;
                        }
                    } else if (comp == 0) {
                        return new FoundNodeAtIndex<>(node, i, childIndex);
                    }
                }
                if (i == till) {
                    if (node.children[i] != null) {
                        childIndex = i;
                        node = node.children[i];
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;
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
        MwayNode<T>[] stack = new MwayNode[arrCount];
        int stackCounter = 0, count = 0, stackStart = 0;
        MwayNode<T> node;
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

    private int preOrder(MwayNode<T> node, T[] pre, int count) {
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

    private int inOrder(MwayNode<T> node, T[] in, int count) {
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

    private int postOrder(MwayNode<T> node, T[] post, int count) {
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

    private int reverseOrder(MwayNode<T> node, T[] reverse, int count) {
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

    public int getSize() {
        return valuesCount;
    }

    @SuppressWarnings("unchecked")
    public int getHeight() {
        int count = -1;
        MwayNode<T>[] stack = new MwayNode[1];
        stack[0] = this.root;
        while (stack.length > 0 && stack[0] != null) {
            count++;
            stack = Arrays.stream(stack).flatMap(n -> Arrays.stream(n.children)).filter(Objects::nonNull).toArray(MwayNode[]::new);
        }
        return count;
    }

    @Override
    public String toString() {
        if (this.root != null) {
            return "{\"order\":" + ORDER +
                    ",\"total values\":" + valuesCount +
                    ",\"sorting order\":\"" + (ORDER_MUL == -1 ? "inverse sorting order" : "natural sorting order") + "\"" +
                    ",\"data\":" + this.root +
                    "}";
        } else return "<empty_tree>";
    }
}

class MwayNode<T extends Comparable<T>> {
    T[] values;
    MwayNode<T>[] children;

    MwayNode<T> parent;
    int counter;

    MwayNode() {
    }

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
        StringBuilder childrenStr = new StringBuilder();
        int index = 0;
        for (MwayNode<T> child : children) {
            if (child != null) {
                childrenStr.append("\"").append(index).append("\":").append(child).append(",");
            }
            index++;
        }
        if (childrenStr.length() > 0) {
            childrenStr.deleteCharAt(childrenStr.length() - 1);
            childrenStr.insert(0, "{");
            childrenStr.append("}");
            build.append(",\"children\":").append(childrenStr);
        }
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