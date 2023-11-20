package in.zero.collection.link;

import in.zero.collection.Collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A tree where each node holds two child nodes Provide ways to iterate and
 * traverse the value inside a binary tree
 *
 * @param <T> Any object which we want to store inside a tree
 */
public class BinaryTree<T> implements LinkBinaryTreeIterable<T>, Collection<T> {

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
            if (hasNext())
                return nodes[index++].data;
            else
                throw new NoSuchElementException("Iterator exhausted");
        }
    }

    /**
     * Add all the values provided
     *
     * @param values Values to be added
     * @return reference to the tree
     */
    @SuppressWarnings("unchecked")
    public BinaryTree<T> addAll(T... values) {
        Arrays.stream(values).forEach(this::add);
        return this;
    }

    /**
     * Add a value to the tree
     *
     * @param value Value to be added
     * @return Reference to the tree
     */
    public BinaryTree<T> add(T value) {
        addNode(value);
        return this;
    }

    BinaryTreeNode<T> addNode(T value) {
        BinaryTreeNode<T> newNode;
        BinaryTreeNode<T> lastNode = getLastVacantNode();
        if (lastNode != null) {
            newNode = createNewNode(value, lastNode);
            if (!lastNode.hasLeft())
                lastNode.left = newNode;
            else
                lastNode.right = newNode;
        } else {
            newNode = new BinaryTreeNode<>(value);
            root = newNode;
        }
        nodesCount++;
        return newNode;
    }

    /**
     * Remove all the elements provided
     *
     * @param values Values to be removed
     * @return Array of all the removed values
     */
    @SuppressWarnings("unchecked")
    public T[] removeAll(T... values) {
        return (T[]) Arrays.stream(values).map(this::remove).toArray();
    }

    /**
     * Remove any single value from the tree
     *
     * @param value Value to be removed
     * @return Removed element
     */
    public T remove(T value) {

        BinaryTreeNode<T> node = searchNode(value);
        if (node != null) {
            if (node == root) {
                root = null;
                nodesCount = 0;
            } else {
                BinaryTreeNode<T>[] level = levelOrder(this.root);
                BinaryTreeNode<T> lastNode = level[level.length - 1];
                BinaryTreeNode<T> parent = lastNode.parent;
                lastNode.parent = null;
                if (parent.left == lastNode) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                node.data = lastNode.data;
                nodesCount--;
            }
        }
        return value;
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
     * Iterator which is required to traverse all the elements in a tree In order
     * elements will be iterated
     *
     * @return iterator object
     */
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator<>(inOrder(this.root));
    }

    /**
     * Level Order iterator which is required to traverse all the elements in a tree
     *
     * @return iterator object
     */
    @Override
    public Iterator<T> levelOrderIterator() {
        return new BinaryTreeIterator<>(levelOrder(this.root));
    }

    /**
     * Pre Order iterator which is required to traverse all the elements in a tree
     *
     * @return iterator object
     */
    @Override
    public Iterator<T> preOrderIterator() {
        return new BinaryTreeIterator<>(preOrder(this.root));
    }

    /**
     * In Order iterator which is required to traverse all the elements in a tree
     *
     * @return iterator object
     */
    @Override
    public Iterator<T> inOrderIterator() {
        return new BinaryTreeIterator<>(inOrder(this.root));
    }

    /**
     * Post Order iterator which is required to traverse all the elements in a tree
     *
     * @return iterator object
     */
    @Override
    public Iterator<T> postOrderIterator() {
        return new BinaryTreeIterator<>(postOrder(this.root));
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
    @SuppressWarnings("unchecked")
    BinaryTreeNode<T> searchNode(T value) {
        if (root != null && value != null) {
            BinaryTreeNode<T>[] elements = new BinaryTreeNode[nodesCount];
            elements[0] = root;
            int curr = 1;
            for (int i = 0; i < nodesCount; i++) {
                if (elements[i].data.equals(value)) {
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    BinaryTreeNode<T>[] levelOrder(BinaryTreeNode<T> node) {
        if (node != null) {
            BinaryTreeNode<T>[] elements = new BinaryTreeNode[nodesCount];
            elements[0] = node;
            int curr = 1;
            for (int i = 0; i < curr; i++) {
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
    @SuppressWarnings("unchecked")
    BinaryTreeNode<T>[] preOrder(BinaryTreeNode<T> node) {
        if (node != null) {
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
    @SuppressWarnings("unchecked")
    BinaryTreeNode<T>[] postOrder(BinaryTreeNode<T> node) {
        if (node != null) {
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
    @SuppressWarnings("unchecked")
    BinaryTreeNode<T>[] inOrder(BinaryTreeNode<T> node) {
        if (node != null) {
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

    /**
     * Print the tree data in all the traversal formats
     */
    public void printTrace() {
        if (root != null) {
            System.out.println("\n_____________________________________________");
            System.out.println(getClass().getSimpleName() + ":");
            System.out.println("----------------------");
            System.out.print("Level Order: ");
            for (BinaryTreeNode<T> node : levelOrder(this.root)) {
                System.out.print(node + ", ");
            }
            System.out.print("\nPre Order: ");
            for (BinaryTreeNode<T> node : preOrder(this.root)) {
                System.out.print(node + ", ");
            }
            System.out.print("\nIn Order: ");
            for (BinaryTreeNode<T> node : inOrder(this.root)) {
                System.out.print(node + ", ");
            }
            System.out.print("\nPost Order: ");
            for (BinaryTreeNode<T> node : postOrder(this.root)) {
                System.out.print(node + ", ");
            }
            System.out.println("\n_____________________________________________");
        } else
            System.out.println("NO DATA in Binary Tree");
    }
}

/**
 * Node to store any value in the Binary trees
 *
 * @param <T> Object of particular concern
 */
class BinaryTreeNode<T> {

    BinaryTreeNode<T> left;
    BinaryTreeNode<T> right;
    BinaryTreeNode<T> parent;

    T data;

    /**
     * Param constructor
     *
     * @param data
     */
    BinaryTreeNode(T data) {
        this.data = data;
    }

    boolean hasLeft() {
        return left != null;
    }

    boolean hasRight() {
        return right != null;
    }

    @Override
    public String toString() {
        return String.valueOf(data);
    }
}
