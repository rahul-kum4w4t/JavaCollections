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
        BinaryTreeNode<T> newNode;
        if (root != null) {
            BinaryTreeNode<T> node = searchNodePlace(value);
            newNode = createNewNode(value, node);
            int comp = node.data.compareTo(value);
            if (comp > 0) {
                node.left = newNode;
            } else if (comp < 0) {
                node.right = newNode;
            }
        } else {
            newNode = createNewNode(value, null);
            this.root = newNode;
        }
        nodesCount++;
        return newNode;
    }

    BinaryTreeNode<T> createNewNode(T value, BinaryTreeNode<T> parent) {
        BinaryTreeNode<T> newNode = new BinaryTreeNode<>(value);
        newNode.parent = parent;
        return newNode;
    }

    @Override
    public boolean remove(T value) {
        BinaryTreeNode<T> node = searchNode(value);
        if (node != null) {
            BinaryTreeNode<T> parent = node.parent;
            if (node.hasLeft()) {
                if (node.hasRight()) {
                    BinaryTreeNode<T> inOrderPred = inOrderPredecessor(node);
                    node.data = inOrderPred.data;
                    parent = inOrderPred.parent;
                    inOrderPred.parent = null;
                    if (inOrderPred.hasLeft()) {
                        parent.right = inOrderPred.left;
                    } else {
                        parent.right = null;
                    }
                } else {
                    if (parent != null) {
                        if (parent.left == node) {
                            parent.left = node.left;
                        } else {
                            parent.right = node.left;
                        }
                    } else {
                        this.root = node.left;
                        node.left = null;
                    }
                }
            } else {
                if (node.hasRight()) {
                    if (parent != null) {
                        if (parent.left == node) {
                            parent.left = node.right;
                        } else {
                            parent.right = node.right;
                        }
                    } else {
                        this.root = node.right;
                        node.right = null;
                    }
                } else {
                    if (parent != null) {
                        node.parent = null;
                        if (parent.left == node) {
                            parent.left = null;
                        } else {
                            parent.right = null;
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
                comp = node.data.compareTo(value);
                if (comp > 0) {
                    node = node.left;
                } else if (comp < 0) {
                    node = node.right;
                } else {
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public int getHeight() {
        BinaryTreeNode<T> node = this.root;
        int level = -1;
        if (node != null) {
            if (!node.hasChildren()) {
                return 0;
            } else {
                BinaryTreeNode<T>[] stack = new BinaryTreeNode[nodesCount];
                stack[0] = node;
                int bottom = 0;
                int top = 1;
                int counter;
                while (bottom < top) {
                    counter = 0;
                    while (bottom < top) {
                        node = stack[bottom++];
                        if (node.hasLeft()) {
                            stack[top + (counter++)] = node.left;
                        }
                        if (node.hasRight()) {
                            stack[top + (counter++)] = node.right;
                        }
                    }
                    bottom = top;
                    top += counter;
                    level++;
                }
            }
        }
        return level;
    }

    BinaryTreeNode<T> searchNodePlace(T value) {
        BinaryTreeNode<T> node = this.root;
        int comp = 0;
        while (node != null) {
            comp = node.data.compareTo(value);
            if (comp > 0) {
                if (node.hasLeft()) node = node.left;
                else return node;
            } else if (comp < 0) {
                if (node.hasRight()) node = node.right;
                else return node;
            } else {
                throw new IllegalArgumentException("BST can't accept duplicate values");
            }
        }
        return null;
    }

    BinaryTreeNode<T> inOrderPredecessor(BinaryTreeNode<T> node) {
        node = node.left;
        while (node.hasRight()) {
            node = node.right;
        }
        return node;
    }

    BinaryTreeNode<T> rotate(BinaryTreeNode<T> node, int childDir, int grandDir, int greatDir) {
        //System.out.println("Rotate: " + node.data + ", " + childDir + ", " + grandDir + ", " + greatDir);
        BinaryTreeNode<T> child = childDir == -1 ? node.left : node.right;
        BinaryTreeNode<T> parent = node.parent;

        if (childDir != grandDir) child = rotate(child, grandDir, greatDir, greatDir);

        child.parent = parent;
        node.parent = child;
        if (parent != null) {
            if (parent.left == node) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        }
        if (childDir == -1) {
            node.left = child.right;
            child.right = node;
            if (node.hasLeft()) {
                node.left.parent = node;
            }
        } else {
            node.right = child.left;
            child.left = node;
            if (node.hasRight()) {
                node.right.parent = node;
            }
        }
        if (parent == null) {
            this.root = child;
        }
        return child;
        //System.out.println("Rotate: " + node.data + " completed");
    }
}
