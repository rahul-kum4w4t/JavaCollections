package in.zero.collection;

/**
 * Extension of Binary Tree Node with AVL tree specific functionalities
 * @param <T>
 */
class AVLTreeNode<T> extends BinaryTreeNode<T> {

    /**
     * Param constructor
     * @param value
     */
    AVLTreeNode(T value) {
        super(value);
    }

    int height;

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
     * @return String
     */
    @Override
    public String toString() {
        return String.format("%s:%s", data, height);
    }
}
