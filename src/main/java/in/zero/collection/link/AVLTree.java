package in.zero.collection.link;

/**
 * AVL tree based on link lists
 * Can hold undefined number of values (Only restricted by the JVM heap size)
 * ------------------------------------------
 * 1. AVL tree is strictly balanced tree
 * 2. Can have N number of rotations to balance any node
 *
 * @param <T> Any Comparable object can be stored
 * @author Rahul Kumawat
 * @since 19-10.2023
 */
public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    public AVLTree() {
    }

    public AVLTree(Sort order) {
        super(order);
    }

    /**
     * Extension of Binary Tree Node with AVL tree specific functionalities
     *
     * @param <T>
     */
    private static class AVLTreeNode<T> extends BinaryTreeNode<T> {

        int height;

        /**
         * Param constructor
         *
         * @param value
         */
        AVLTreeNode(T value) {
            super(value);
        }

        /**
         * Updates height of any node based on its child nodes
         */
        void updateHeight() {
            if (this.hasLeft()) {
                AVLTreeNode<T> left = (AVLTreeNode<T>) this.left;
                if (this.hasRight()) {
                    AVLTreeNode<T> right = (AVLTreeNode<T>) this.right;
                    this.height = Math.max(left.height, right.height) + 1;
                } else {
                    this.height = left.height + 1;
                }
            } else {
                if (this.hasRight()) {
                    AVLTreeNode<T> right = (AVLTreeNode<T>) this.right;
                    this.height = right.height + 1;
                } else {
                    this.height = 0;
                }
            }
        }

        /**
         * AVL node specific implementation
         *
         * @return String
         */
        @Override
        public String toString() {
            return String.format("%s:%s", data, height);
        }
    }

    /**
     * Overriden method to add any value to the tree
     *
     * @param val Value to be added
     * @return reference to the tree
     */
    @Override
    public AVLTree<T> add(T val) {
        AVLTreeNode<T> newNode = (AVLTreeNode<T>) this.addNode(val);
        newNode.height = 0;
        AVLTreeNode<T> node = (AVLTreeNode<T>) newNode.parent; // Parent Node
        int leftHeight, rightHeight, height; // Because from newNode to its parent height is 1
        int grandDir, childDir, greatDir;
        if (node != null) { // Parent is not null
            node.updateHeight();
            grandDir = node.left == newNode ? -1 : 1;
            greatDir = grandDir;
            newNode = node;
            node = (AVLTreeNode<T>) node.parent;
            while (node != null) {
                if (node.left == newNode) {
                    childDir = -1;
                    leftHeight = ((AVLTreeNode<T>) node.left).height;
                    rightHeight = node.hasRight() ? ((AVLTreeNode<T>) node.right).height : -1;
                } else {
                    childDir = 1;
                    rightHeight = ((AVLTreeNode<T>) node.right).height;
                    leftHeight = node.hasLeft() ? ((AVLTreeNode<T>) node.left).height : -1;
                }
                if (Math.abs(leftHeight - rightHeight) == 2) {
                    height = node.height;
                    node = this.rotate(node, childDir, grandDir, greatDir);
                    if (node.height == height) {
                        node = null;
                    }
                } else {
                    node.height = Math.max(leftHeight, rightHeight) + 1;
                    newNode = node;
                    node = (AVLTreeNode<T>) node.parent;
                    greatDir = grandDir;
                    grandDir = childDir;
                }
            }
        }
        return this;
    }

    /**
     * Removes any value from the tree
     *
     * @param val Value which needs to be removed
     * @return The removed value
     */
    @Override
    public T remove(T val) {
        AVLTreeNode<T> parent = (AVLTreeNode<T>) super.removeNode(val);
        AVLTreeNode<T> left, right;
        int leftHeight, rightHeight, height;
        int grandDir, childDir, greatDir;
        while (parent != null) {
            childDir = getDominatingChildDir(parent, 0);
            grandDir = getDominatingChildDir(childDir == -1 ? parent.left : parent.right, childDir);
            greatDir = grandDir;
            left = (AVLTreeNode<T>) parent.left;
            right = (AVLTreeNode<T>) parent.right;
            leftHeight = left != null ? left.height : -1;
            rightHeight = right != null ? right.height : -1;
            if (Math.abs(leftHeight - rightHeight) == 2) {
                parent = rotate(parent, childDir, grandDir, greatDir);
            } else {
                height = Math.max(leftHeight, rightHeight) + 1;
                if (height != parent.height) {
                    parent.height = height;
                    parent = (AVLTreeNode<T>) parent.parent;
                } else {
                    parent = null;
                }
            }
        }
        return val;
    }

    /**
     * Get the direction of child node which decides the height of the concerned node
     *
     * @param node      Concerned node
     * @param parentDir Parent node direction
     * @return child node direction (-1 = left node/ 1 = right node)
     */
    private int getDominatingChildDir(BinaryTreeNode<T> node, int parentDir) {
        if (node != null) {
            AVLTreeNode<T> left = (AVLTreeNode<T>) node.left;
            AVLTreeNode<T> right = (AVLTreeNode<T>) node.right;
            return left != null ? (
                    right != null ? (
                            left.height > right.height ? -1 : (left.height == right.height ? parentDir : 1)
                    ) : -1
            ) : (right != null ? 1 : -1);
        } else return -1;
    }

    /**
     * Create a new node with provided values
     *
     * @param value  Value to put inside the node
     * @param parent Parent for the new node (If any otherwise parent = null)
     * @return newely created node
     */
    @Override
    AVLTreeNode<T> createNewNode(T value, BinaryTreeNode<T> parent) {
        AVLTreeNode<T> newNode = new AVLTreeNode<>(value);
        newNode.parent = parent;
        return newNode;
    }

    /**
     * Overriden method to put avl tree specific functionality for rotation
     *
     * @param node     Node which needs to be rotated
     * @param childDir Direction of child noce
     * @param grandDir Direction of grand child node (defaults to child node direction if not present)
     * @param greatDir Direction of great grand child node (defaults to grand child node direction if not present)
     * @return the node which took place of the provided node after rotation
     */
    @Override
    AVLTreeNode<T> rotate(BinaryTreeNode<T> node, int childDir, int grandDir, int greatDir) {
        AVLTreeNode<T> newNode = (AVLTreeNode<T>) super.rotate(node, childDir, grandDir, greatDir);
        ((AVLTreeNode<T>) node).updateHeight();
        return newNode;
    }

    /**
     * Get height of the tree
     *
     * @return height of avl tree
     */
    @Override
    public int getHeight() {
        return this.root != null ? ((AVLTreeNode<T>) this.root).height : -1;
    }
}