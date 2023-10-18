package in.zero;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class LinkBinaryTree<T> implements LinkBinaryTreeIterable<T>, Collection<T> {
    BinaryTreeNode<T> root;
    int nodesCount = 0;

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

    public LinkBinaryTree<T> addAll(T... values) {
        Arrays.stream(values).forEach(this::add);
        return this;
    }

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

    public Boolean[] removeAll(T... values) {
        return (Boolean[]) Arrays.stream(values).map(this::add).toArray();
    }

    public boolean remove(T value) {
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
                return true;
            }
        }
        return false;
    }

    public int getHeight() {
        return (int) Math.floor(Math.log(nodesCount) / Math.log(2));
    }

    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator<>(preOrder());
    }

    @Override
    public Iterator<T> levelOrderIterator() {
        return new BinaryTreeIterator<>(levelOrder());
    }

    @Override
    public Iterator<T> preOrderIterator() {
        return new BinaryTreeIterator<>(preOrder());
    }

    @Override
    public Iterator<T> inOrderIterator() {
        return new BinaryTreeIterator<>(inOrder());
    }

    @Override
    public Iterator<T> postOrderIterator() {
        return new BinaryTreeIterator<>(postOrder());
    }

    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    public boolean search(T value) {
        return searchNode(value) != null;
    }

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

    BinaryTreeNode<T> createNewNode(T value, BinaryTreeNode<T> parent) {
        BinaryTreeNode<T> newNode = new BinaryTreeNode<>(value);
        newNode.parent = parent;
        return newNode;
    }
}
