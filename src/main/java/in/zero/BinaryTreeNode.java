package in.zero;

class BinaryTreeNode<T> {

    BinaryTreeNode<T> left;
    BinaryTreeNode<T> right;
    BinaryTreeNode<T> parent;

    T data;

    BinaryTreeNode(T data) {
        this.data = data;
    }

    boolean hasChildren() {
        return left != null || right != null;
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
