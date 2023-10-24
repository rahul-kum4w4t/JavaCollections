package in.zero.collection;

/**
 * A type of Binary tree where values get stored in sorted order
 * Uses link list as base for implementation
 * undefined capacity to store values
 * <p>
 * - Stores values in sorted order so that the sorting operation becomes easy
 * - If may be skewed because no logic is there to balance this tree
 * ------------ Time Complexity ------------
 * 1. Insertion: Omega(1), Theta(log(n)), O(n)
 * 2. Deletion: Omega(1), Theta(log(n)), O(n)
 * 3. Searching: Omega(1), Theta(log(n)), O(n)
 *
 * @param <T> Comparable objects which can be stored
 */
public class LinkBinarySearchTree<T extends Comparable<T>> extends LinkBinaryTree<T> {

    /**
     * Add a value to the tree
     *
     * @param value value to be added
     * @return reference to the tree
     */
    @Override
    public LinkBinarySearchTree<T> add(T value) {
        this.addNode(value);
        return this;
    }

    /**
     * Internal method which adds a value to the tree and returns the new node
     *
     * @param value value to be added
     * @return new node which holds the inserted value
     */
    BinaryTreeNode<T> addNode(T value) {
        if (value != null) {
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
        } else {
            throw new IllegalArgumentException("null values can't be stored inside a BST");
        }
    }

    /**
     * Removes any value from the tree
     *
     * @param value Value to be removed
     * @return The removed value
     */
    @Override
    public T remove(T value) {
        return removeNode(value) != null ? value : null;
    }

    /**
     * Removes any value from the tree and returns the parent of deleted node
     *
     * @param value Value to be deleted
     * @return Parent of deleted node
     */
    BinaryTreeNode<T> removeNode(T value) {
        BinaryTreeNode<T> node = searchNode(value);
        if (node != null) {
            if (node.hasLeft() && node.hasRight()) {
                BinaryTreeNode<T> inOrderPred = inOrderPredecessor(node);
                node.data = inOrderPred.data;
                node = inOrderPred;
            }
            nodesCount--;
            return replaceNode(node);
        }
        return null;
    }

    BinaryTreeNode<T> replaceNode(BinaryTreeNode<T> node) {
        // replace node is always having at most one child
        BinaryTreeNode<T> replacer, parent = node.parent;
        if (node.hasRight()) {
            replacer = node.right;
            node.right = null;
        } else {
            replacer = node.left;
            node.left = null;
        }
        if (parent != null) {
            node.parent = null;
            if (parent.left == node) {
                parent.left = replacer;
            } else {
                parent.right = replacer;
            }
        } else {
            this.root = replacer;
        }
        if (replacer != null) {
            replacer.parent = parent;
        }
        return parent;
    }

    /**
     * Search a value inside the tree
     *
     * @param value Value to be searched
     * @return true/false whether found or not respectively
     */
    @Override
    public boolean search(T value) {
        return searchNode(value) != null;
    }

    /**
     * Searches any given value and provides the respective node
     *
     * @param value value to be searched
     * @return Found node
     */
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

    /**
     * Provides the height of the tree
     *
     * @return Tree height
     */
    @Override
    public int getHeight() {
        BinaryTreeNode<T> node = this.root;
        int level = -1;
        if (node != null) {
            if (node.left == null && node.right == null) {
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

    /**
     * Based on value given search the respective node which stores that value
     *
     * @param value Value which needs to be searched in the tree
     * @return Node which contains the value
     */
    BinaryTreeNode<T> searchNodePlace(T value) {
        BinaryTreeNode<T> node = this.root;
        int comp;
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

    /**
     * In order predecessor of the given node
     *
     * @param node node of concern
     * @return In order predecessor node
     */
    BinaryTreeNode<T> inOrderPredecessor(BinaryTreeNode<T> node) {
        node = node.left;
        while (node.hasRight()) {
            node = node.right;
        }
        return node;
    }

    /**
     * Rotates any node based on its child and grandchild directions
     *
     * @param node     Node which needs to be rotated
     * @param childDir Direction of child noce
     * @param grandDir Direction of grand child node (defaults to child node direction if not present)
     * @param greatDir Direction of great grand child node (defaults to grand child node direction if not present)
     * @return the node which took place of the provided node after rotation
     */
    BinaryTreeNode<T> rotate(BinaryTreeNode<T> node, int childDir, int grandDir, int greatDir) {
        //System.out.println("\nRotate: " + node.data + ", " + childDir + ", " + grandDir + ", " + greatDir);
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
        //System.out.println("\nRotate: " + node.data + " completed");
        return child;
    }
}
