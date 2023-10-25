package in.zero.collection.link;

/**
 * Node to store any value in the Binary trees
 * @param <T> Object of particular concern
 */
class BinaryTreeNode<T> {

    BinaryTreeNode<T> left;
    BinaryTreeNode<T> right;
    BinaryTreeNode<T> parent;

    T data;

    /**
     * Param constructor
     * @param data
     */
    BinaryTreeNode(T data) {
        this.data = data;
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
