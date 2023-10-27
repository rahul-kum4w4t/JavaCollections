package in.zero.collection.link;

public class SplayTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    public SplayTree() {
    }

    public SplayTree(Sort order) {
        super(order);
    }

    @Override
    public SplayTree<T> add(T value) {
        BinaryTreeNode<T> node = super.addNode(value);
        splay(node);
        return this;
    }

    @Override
    public T remove(T value) {
        BinaryTreeNode<T> parent = super.removeNode(value);
        if (parent != null) splay(parent);
        return value;
    }

    @Override
    public boolean search(T value) {
        BinaryTreeNode<T> node = super.searchNodePlace(value, true);
        if (node != null) {
            splay(node);
            return node.data.compareTo(value) == 0;
        }
        return false;
    }

    private void splay(BinaryTreeNode<T> node) {
        int childDir, grandDir;
        BinaryTreeNode<T> parent, grand;
        while (node != this.root) {
            parent = node.parent;
            grand = parent.parent;
            childDir = parent.left == node ? -1 : 1;
            grandDir = grand != null ? (grand.left == parent ? -1 : 1) : childDir;
            rotate(childDir != grandDir ? grand : parent, grandDir, childDir, childDir);
        }
    }
}
