package in.zero.collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A tree where each node holds two child nodes
 * Provide ways to iterate and traverse the value inside a binary tree
 *
 * @param <T> Any object which we want to store inside a tree
 */
public class LinkBinaryTree<T> implements LinkBinaryTreeIterable<T>, Collection<T> {
    BinaryTreeNode<T> root;
    int nodesCount = 0;

    /**
     * Custom iterator to iterate values of any Binary Tree
     *
     * @param <T> Any object which we want to store inside a tree
     */
    private static class BinaryTreeIterator<T> implements Iterator<T> {

        private final BinaryTreeNode<T>[] nodes;
        private int index = 0;

        BinaryTreeIterator(BinaryTreeNode<T>[] nodes) {
            this.nodes = nodes;
        }

        @Override
        public boolean hasNext() {
            return index < nodes.length;
        }

        @Override
        public T next() {
            if (hasNext()) return nodes[index++].data;
            else throw new NoSuchElementException("Iterator exhausted");
        }
    }

    /**
     * Add all the values provided
     *
     * @param values Values to be added
     * @return reference to the tree
     */
    public LinkBinaryTree<T> addAll(T... values) {
        Arrays.stream(values).forEach(this::add);
        return this;
    }

    /**
     * Add a value to the tree
     *
     * @param value Value to be added
     * @return Reference to the tree
     */
    public LinkBinaryTree<T> add(T value) {
        BinaryTreeNode<T> lastNode = getLastVacantNode();
        if (lastNode != null) {
            BinaryTreeNode<T> newNode = createNewNode(value, lastNode);//new BinaryTreeNode<>(value, lastNode);
            if (!lastNode.hasLeft()) lastNode.left = newNode;
            else lastNode.right = newNode;
        } else {
            root = new BinaryTreeNode<>(value);
        }
        nodesCount++;
        return this;
    }

    /**
     * Remove all the elements provided
     *
     * @param values Values to be removed
     * @return Array of all the removed values
     */
    public T[] removeAll(T... values) {
        return (T[]) Arrays.stream(values).map(this::add).toArray();
    }

    /**
     * Remove any single value from the tree
     *
     * @param value Value to be removed
     * @return Removed element
     */
    public T remove(T value) {
        if (nodesCount > 0) {
            BinaryTreeNode<T> node = searchNode(value);
            if (node != null) {
                if (node == root) {
                    root = null;
                } else {
                    BinaryTreeNode<T>[] level = levelOrder();
                    BinaryTreeNode<T> lastNode = level[level.length - 1];
                    BinaryTreeNode<T> parent = lastNode.parent;
                    if (parent.left == lastNode) {
                        parent.left = null;
                    } else {
                        parent.right = null;
                    }

                    parent = node.parent;
                    if (parent != null) {
                        lastNode.parent = parent;
                        if (parent.left == node) {
                            parent.left = lastNode;
                        } else {
                            parent.right = lastNode;
                        }
                        node.parent = null;
                    } else {
                        lastNode.parent = null;
                    }
                }
                nodesCount--;
                return value;
            }
        }
        return null;
    }

    /**
     * Calculates and gives back the height og Binary tree
     *
     * @return height of the tree
     */
    public int getHeight() {
        return (int) Math.floor(Math.log(nodesCount) / Math.log(2));
    }

    /**
     * Iterator which is required to traverse all the elements in a tree
     * In order elements will be iterated
     *
     * @return iterator object
     */
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator<>(inOrder());
    }

    /**
     * Level Order iterator which is required to traverse all the elements in a tree
     *
     * @return iterator object
     */
    @Override
    public Iterator<T> levelOrderIterator() {
        return new BinaryTreeIterator<>(levelOrder());
    }

    /**
     * Pre Order iterator which is required to traverse all the elements in a tree
     *
     * @return iterator object
     */
    @Override
    public Iterator<T> preOrderIterator() {
        return new BinaryTreeIterator<>(preOrder());
    }

    /**
     * In Order iterator which is required to traverse all the elements in a tree
     *
     * @return iterator object
     */
    @Override
    public Iterator<T> inOrderIterator() {
        return new BinaryTreeIterator<>(inOrder());
    }

    /**
     * Post Order iterator which is required to traverse all the elements in a tree
     *
     * @return iterator object
     */
    @Override
    public Iterator<T> postOrderIterator() {
        return new BinaryTreeIterator<>(postOrder());
    }

    /**
     * Create a Java 1.8 stream object to traverse all the tree elements
     *
     * @return iterator object
     */
    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * Searches any element in the tree
     *
     * @param value Value to be searched
     * @return true/false ~ found/notfound
     */
    public boolean search(T value) {
        return searchNode(value) != null;
    }

    /**
     * Searches a value and returns the respective node
     *
     * @param value Value to be searched
     * @return Found node
     */
    BinaryTreeNode<T> searchNode(T value) {
        if (root != null) {
            BinaryTreeNode<T>[] elements = new BinaryTreeNode[nodesCount];
            elements[0] = root;
            int curr = 1;
            for (int i = 0; i < nodesCount; i++) {
                if (elements[i] == value) {
                    return elements[i];
                }
                if (elements[i].hasLeft()) {
                    elements[curr++] = elements[i].left;
                }
                if (elements[i].hasRight()) {
                    elements[curr++] = elements[i].right;
                }
            }
        }
        return null;
    }

    /**
     * Get the last vacant node where new value can be inserted
     *
     * @return Last vacant node
     */
    private BinaryTreeNode<T> getLastVacantNode() {
        if (root != null) {
            BinaryTreeNode<T>[] elements = new BinaryTreeNode[nodesCount];
            elements[0] = root;
            int curr = 1;
            for (int i = 0; i < nodesCount; i++) {
                if (!elements[i].hasLeft() || !elements[i].hasRight()) {
                    return elements[i];
                } else {
                    elements[curr++] = elements[i].left;
                    elements[curr++] = elements[i].right;
                }
            }
            return elements[curr - 1];
        }
        return null;
    }

    /**
     * Provides an array of level order traversed nodes for the tree
     *
     * @return array of nodes
     */
    BinaryTreeNode<T>[] levelOrder() {
        if (root != null) {
            BinaryTreeNode<T>[] elements = new BinaryTreeNode[nodesCount];
            elements[0] = root;
            int curr = 1;
            for (int i = 0; i < nodesCount; i++) {
                if (elements[i].hasLeft()) {
                    elements[curr++] = elements[i].left;
                }
                if (elements[i].hasRight()) {
                    elements[curr++] = elements[i].right;
                }
            }
            return elements;
        }
        return null;
    }

    /**
     * Provides an array of pre order traversed nodes for the tree
     *
     * @return array of nodes
     */
    BinaryTreeNode<T>[] preOrder() {
        if (root != null) {
            BinaryTreeNode<T> node = root;
            BinaryTreeNode<T>[] stack = new BinaryTreeNode[getHeight()];
            BinaryTreeNode<T>[] preordered = new BinaryTreeNode[nodesCount];
            int stackPointer = 0;
            int count = 0;
            while (stackPointer >= 0) {
                preordered[count++] = node;
                if (node.hasLeft()) {
                    if (node.hasRight()) {
                        stack[stackPointer++] = node;
                    }
                    node = node.left;
                } else if (node.hasRight()) {
                    node = node.right;
                } else {
                    stackPointer--;
                    node = stackPointer >= 0 ? stack[stackPointer].right : null;
                }
            }
            return preordered;
        }
        return null;
    }

    /**
     * Provides an array of post order traversed nodes for the tree
     *
     * @return array of nodes
     */
    BinaryTreeNode<T>[] postOrder() {
        if (root != null) {
            BinaryTreeNode<T> node = root;
            BinaryTreeNode<T>[] stack = new BinaryTreeNode[getHeight()];
            BinaryTreeNode<T>[] postordered = new BinaryTreeNode[nodesCount];
            int stackPointer = 0;
            int count = nodesCount - 1;
            while (stackPointer >= 0) {
                postordered[count--] = node;
                if (node.hasRight()) {
                    if (node.hasLeft()) {
                        stack[stackPointer++] = node;
                    }
                    node = node.right;
                } else if (node.hasLeft()) {
                    node = node.left;
                } else {
                    stackPointer--;
                    node = stackPointer >= 0 ? stack[stackPointer].left : null;
                }
            }
            return postordered;
        }
        return null;
    }

    /**
     * Provides an array of In order traversed nodes for the tree
     *
     * @return array of nodes
     */
    BinaryTreeNode<T>[] inOrder() {
        if (root != null) {
            BinaryTreeNode<T> node = root;
            BinaryTreeNode<T>[] stack = new BinaryTreeNode[nodesCount];
            BinaryTreeNode<T>[] inorder = new BinaryTreeNode[nodesCount];
            int stackPointer = 0;
            int count = 0;
            while (stackPointer >= 0) {
                if (node.hasLeft()) {
                    stack[stackPointer++] = node;
                    node = node.left;
                } else if (node.hasRight()) {
                    inorder[count++] = node;
                    node = node.right;
                } else {
                    inorder[count++] = node;
                    while (stackPointer > 0 && !node.hasRight()) {
                        node = stack[stackPointer - 1];
                        inorder[count++] = node;
                        stackPointer--;
                    }
                    if (node.hasRight()) {
                        node = node.right;
                    } else {
                        stackPointer--;
                    }
                }
            }
            return inorder;
        }
        return null;
    }

    /**
     * Creates a new node with provide values
     *
     * @param value  Value to be inserted in the new node
     * @param parent Parent which links to the new node
     * @return newely created node
     */
    BinaryTreeNode<T> createNewNode(T value, BinaryTreeNode<T> parent) {
        BinaryTreeNode<T> newNode = new BinaryTreeNode<>(value);
        newNode.parent = parent;
        return newNode;
    }

    /**
     * Get the number of values stored inside the tree at any point of time
     *
     * @return no of values
     */
    public int size() {
        return nodesCount;
    }

    public void printTrace() {
        System.out.println("\n_____________________________________________");
        System.out.println(getClass().getSimpleName() + ":");
        System.out.println("----------------------");
        System.out.print("Level Order: ");
        for (BinaryTreeNode<T> node : levelOrder()) {
            System.out.print(node + ", ");
        }
        System.out.print("\nPre Order: ");
        for (BinaryTreeNode<T> node : preOrder()) {
            System.out.print(node + ", ");
        }
        System.out.print("\nIn Order: ");
        for (BinaryTreeNode<T> node : inOrder()) {
            System.out.print(node + ", ");
        }
        System.out.print("\nPost Order: ");
        for (BinaryTreeNode<T> node : postOrder()) {
            System.out.print(node + ", ");
        }
        System.out.println("\n_____________________________________________");
    }
}
