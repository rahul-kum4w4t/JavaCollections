package in.zero;

class BinaryTreeNode<T> {

    private BinaryTreeNode<T> left;
    private BinaryTreeNode<T> right;
    private BinaryTreeNode<T> parent;

    private T data;

    BinaryTreeNode(T data) {
        this.data = data;
    }

    BinaryTreeNode(T data, BinaryTreeNode<T> parent) {
        this.parent = parent;
        this.data = data;
    }

    public BinaryTreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode<T> left) {
        this.left = left;
    }

    public BinaryTreeNode<T> getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode<T> right) {
        this.right = right;
    }

    public BinaryTreeNode<T> getParent() {
        return parent;
    }

    public void setParent(BinaryTreeNode<T> parent) {
        this.parent = parent;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean hasChildren() {
        return left != null || right != null;
    }

    public boolean hasLeft() {
        return left != null;
    }

    public boolean hasRight() {
        return right != null;
    }
}
