package in.zero;

public class LinkBinarySearchTree<T extends Comparable<T>> extends LinkBinaryTree<T> {

    @Override
    public LinkBinarySearchTree<T> add(T value) {
        if (value != null) {
            this.addNode(value);
        }
        return this;
    }

    BinaryTreeNode<T> addNode(T value) {
        BinaryTreeNode<T> newNode = null;
        if (root != null) {
            BinaryTreeNode<T> node = searchNodePlace(value);
            newNode = new BinaryTreeNode<>(value, node);
            int comp = node.getData().compareTo(value);
            if (comp > 0) {
                node.setLeft(newNode);
            } else if (comp < 0) {
                node.setRight(newNode);
            }
        } else {
            this.root = new BinaryTreeNode<>(value);
        }
        nodesCount++;
        return newNode;
    }

    @Override
    public boolean remove(T value) {
        BinaryTreeNode<T> node = searchNode(value);
        if (node != null) {
            BinaryTreeNode<T> parent = node.getParent();
            if (node.hasLeft()) {
                if (node.hasRight()) {
                    BinaryTreeNode<T> inOrderPred = inOrderPredecessor(node);
                    node.setData(inOrderPred.getData());
                    parent = inOrderPred.getParent();
                    inOrderPred.setParent(null);
                    if (inOrderPred.hasLeft()) {
                        parent.setRight(inOrderPred.getLeft());
                    } else {
                        parent.setRight(null);
                    }
                } else {
                    if (parent != null) {
                        if (parent.getLeft() == node) {
                            parent.setLeft(node.getLeft());
                        } else {
                            parent.setRight(node.getLeft());
                        }
                    } else {
                        this.root = node.getLeft();
                        node.setLeft(null);
                    }
                }
            } else {
                if (node.hasRight()) {
                    if (parent != null) {
                        if (parent.getLeft() == node) {
                            parent.setLeft(node.getRight());
                        } else {
                            parent.setRight(node.getRight());
                        }
                    } else {
                        this.root = node.getRight();
                        node.setRight(null);
                    }
                } else {
                    if (parent != null) {
                        node.setParent(null);
                        if (parent.getLeft() == node) {
                            parent.setLeft(null);
                        } else {
                            parent.setRight(null);
                        }
                    } else {
                        this.root = null;
                    }
                }
            }
            nodesCount--;
            return true;
        } else return false;
    }

    @Override
    public boolean search(T value) {
        return searchNode(value) != null;
    }

    @Override
    BinaryTreeNode<T> searchNode(T value) {
        if (root != null && value != null) {
            BinaryTreeNode<T> node = this.root;
            int comp = 0;
            while (node != null) {
                comp = node.getData().compareTo(value);
                if (comp > 0) {
                    node = node.getLeft();
                } else if (comp < 0) {
                    node = node.getRight();
                } else {
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public int getHeight() {
        return getHeightOfNode(this.root);
    }

    int getHeightOfNode(BinaryTreeNode<T> node) {
        int level = 0;
        if (node != null) {
            BinaryTreeNode<T>[] stack = new BinaryTreeNode[nodesCount];
            stack[0] = node;
            int bottom = 0;
            int top = 1;
            int counter;
            level = -1;
            while (bottom < top) {
                counter = 0;
                while (bottom < top) {
                    node = stack[bottom++];
                    if (node.hasLeft()) {
                        stack[top + (counter++)] = node.getLeft();
                    }
                    if (node.hasRight()) {
                        stack[top + (counter++)] = node.getRight();
                    }
                }
                bottom = top;
                top += counter;
                level++;
            }
        }
        return level;
    }


    BinaryTreeNode<T> searchNodePlace(T value) {
        BinaryTreeNode<T> node = this.root;
        int comp = 0;
        while (node != null) {
            comp = node.getData().compareTo(value);
            if (comp > 0) {
                if (node.hasLeft()) node = node.getLeft();
                else return node;
            } else if (comp < 0) {
                if (node.hasRight()) node = node.getRight();
                else return node;
            } else {
                throw new IllegalArgumentException("BST can't accept duplicate values");
            }
        }
        return null;
    }

    BinaryTreeNode<T> inOrderPredecessor(BinaryTreeNode<T> node) {
        node = node.getLeft();
        while (node.hasRight()) {
            node = node.getRight();
        }
        return node;
    }
}
