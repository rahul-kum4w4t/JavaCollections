package in.zero;

class AVLTreeNode<T> extends BinaryTreeNode<T> {

    AVLTreeNode(T value) {
        super(value);
    }

    int height;

    boolean updateHeight() {
        final int height = this.height;
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
        return this.height != height;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", data, height);
    }
}
