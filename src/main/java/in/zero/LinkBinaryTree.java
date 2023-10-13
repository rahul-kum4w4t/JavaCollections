package in.zero;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class LinkBinaryTree<T> implements LinkBinaryTreeIterable<T> {
    BinaryTreeNode<T> root;
    int nodesCount = 0;

    public static class BinaryTreeIterator<T> implements Iterator<T> {

        private BinaryTreeNode<?>[] nodes;
        private int index = 0;

        BinaryTreeIterator(BinaryTreeNode<?>[] nodes) {
            this.nodes = nodes;
        }

        @Override
        public boolean hasNext() {
            return index < nodes.length;
        }

        @Override
        public T next() {
            if (hasNext()) return ((BinaryTreeNode<T>) nodes[index++]).getData();
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
            BinaryTreeNode<T> newNode = new BinaryTreeNode<>(value, lastNode);
            if (!lastNode.hasLeft()) lastNode.setLeft(newNode);
            else lastNode.setRight(newNode);
        } else {
            root = new BinaryTreeNode<>(value);
        }
        nodesCount++;
        return this;
    }

    public boolean remove(T value) {
        if (nodesCount > 0) {
            BinaryTreeNode<T> node = searchInt(value);
            if (node != null) {
                if (node == root) {
                    root = null;
                } else {
                    BinaryTreeNode<?>[] level = levelOrder();
                    BinaryTreeNode<T> lastNode = (BinaryTreeNode<T>) level[level.length - 1];
                    BinaryTreeNode<T> parent = lastNode.getParent();
                    if (parent.getLeft() == lastNode) {
                        parent.setLeft(null);
                    } else {
                        parent.setRight(null);
                    }

                    parent = node.getParent();
                    if (parent != null) {
                        lastNode.setParent(parent);
                        if (parent.getLeft() == node) {
                            parent.setLeft(lastNode);
                        } else {
                            parent.setRight(lastNode);
                        }
                        node.setParent(null);
                    } else {
                        lastNode.setParent(null);
                    }
                }
                nodesCount--;
                return true;
            }
        }
        return false;
    }

    private BinaryTreeNode<T> searchInt(T value) {
        if (root != null) {
            BinaryTreeNode<?>[] elements = new BinaryTreeNode[nodesCount];
            elements[0] = root;
            int curr = 1;
            for (int i = 0; i < nodesCount; i++) {
                if (elements[i] == value) {
                    return (BinaryTreeNode<T>) elements[i];
                }
                if (elements[i].hasLeft()) {
                    elements[curr++] = elements[i].getLeft();
                }
                if (elements[i].hasRight()) {
                    elements[curr++] = elements[i].getRight();
                }
            }
        }
        return null;
    }

    private BinaryTreeNode<T> getLastVacantNode() {
        if (root != null) {
            BinaryTreeNode<?>[] elements = new BinaryTreeNode[nodesCount];
            elements[0] = root;
            int curr = 1;
            for (int i = 0; i < nodesCount; i++) {
                if (!elements[i].hasLeft() || !elements[i].hasRight()) {
                    return (BinaryTreeNode<T>) elements[i];
                } else {
                    elements[curr++] = elements[i].getLeft();
                    elements[curr++] = elements[i].getRight();
                }
            }
            return (BinaryTreeNode<T>) elements[curr - 1];
        }
        return null;
    }

    BinaryTreeNode<?>[] levelOrder() {
        if (root != null) {
            BinaryTreeNode<?>[] elements = new BinaryTreeNode[nodesCount];
            elements[0] = root;
            int curr = 1;
            for (int i = 0; i < nodesCount; i++) {
                if (elements[i].hasLeft()) {
                    elements[curr++] = elements[i].getLeft();
                }
                if (elements[i].hasRight()) {
                    elements[curr++] = elements[i].getRight();
                }
            }
            return elements;
        }
        return null;
    }

    BinaryTreeNode<?>[] preOrder() {
        if (root != null) {
            BinaryTreeNode<?> node = root;
            BinaryTreeNode<?>[] stack = new BinaryTreeNode[getHeight()];
            BinaryTreeNode<?>[] preordered = new BinaryTreeNode[nodesCount];
            int stackPointer = 0;
            int count = 0;
            while (stackPointer >= 0) {
                preordered[count++] = node;
                if (node.hasLeft()) {
                    if (node.hasRight()) {
                        stack[stackPointer++] = node;
                    }
                    node = node.getLeft();
                } else if (node.hasRight()) {
                    node = node.getRight();
                } else {
                    stackPointer--;
                    node = stackPointer >= 0 ? stack[stackPointer].getRight() : null;
                }
            }
            return preordered;
        }
        return null;
    }

    BinaryTreeNode<?>[] postOrder() {
        if (root != null) {
            BinaryTreeNode<?> node = root;
            BinaryTreeNode<?>[] stack = new BinaryTreeNode[getHeight()];
            BinaryTreeNode<?>[] postordered = new BinaryTreeNode[nodesCount];
            int stackPointer = 0;
            int count = nodesCount - 1;
            while (stackPointer >= 0) {
                postordered[count--] = node;
                if (node.hasRight()) {
                    if (node.hasLeft()) {
                        stack[stackPointer++] = node;
                    }
                    node = node.getRight();
                } else if (node.hasLeft()) {
                    node = node.getLeft();
                } else {
                    stackPointer--;
                    node = stackPointer >= 0 ? stack[stackPointer].getLeft() : null;
                }
            }
            return postordered;
        }
        return null;
    }

    BinaryTreeNode<?>[] inOrder() {
        if (root != null) {
            BinaryTreeNode<?> node = root;
            BinaryTreeNode<?>[] stack = new BinaryTreeNode[nodesCount];
            BinaryTreeNode<?>[] inorder = new BinaryTreeNode[nodesCount];
            int stackPointer = 0;
            int count = 0;
            while (stackPointer >= 0) {
                if (node.hasLeft()) {
                    stack[stackPointer++] = node;
                    node = node.getLeft();
                } else if (node.hasRight()) {
                    inorder[count++] = node;
                    node = node.getRight();
                } else {
                    inorder[count++] = node;
                    while (stackPointer > 0 && !node.hasRight()) {
                        node = stack[stackPointer - 1];
                        inorder[count++] = node;
                        stackPointer--;
                    }
                    if (node.hasRight()) {
                        node = node.getRight();
                    } else {
                        stackPointer--;
                    }
                }
            }
            return inorder;
        }
        return null;
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

    public Stream<T> stream(){
        return StreamSupport.stream(spliterator(), false);
    }
}
