package in.zero;

public class LinkAVLTree<T extends Comparable<T>> extends LinkBinarySearchTree<T> {

    @Override
    public LinkAVLTree<T> add(T val) {
        if (val != null) {
            AVLTreeNode<T> newNode = (AVLTreeNode<T>) this.addNode(val);
            newNode.height = 0;
            AVLTreeNode<T> node = (AVLTreeNode<T>) newNode.parent; // Parent Node
            int leftHeight, rightHeight; // Because from newNode to its parent height is 1
            int grandDir, childDir, greatDir;
            if (node != null) { // Parent is not null
                node.updateHeight();
                if (node.left == newNode) {
                    grandDir = -1;
                } else {
                    grandDir = 1;
                }
                greatDir = grandDir;
                newNode = node;
                node = (AVLTreeNode<T>) node.parent;
                while (node != null) {
                    node.updateHeight();
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
                        node = this.rotate(node, childDir, grandDir, greatDir);
                    } else {
                        newNode = node;
                        node = (AVLTreeNode<T>) node.parent;
                        greatDir = grandDir;
                        grandDir = childDir;
                    }
                }
            }
        }
        return this;
    }

    @Override
    public boolean remove(T val) {
        return false;
    }

    @Override
    AVLTreeNode<T> createNewNode(T value, BinaryTreeNode<T> parent) {
        AVLTreeNode<T> newNode = new AVLTreeNode<>(value);
        newNode.parent = parent;
        return newNode;
    }

    @Override
    AVLTreeNode<T> rotate(BinaryTreeNode<T> node, int childDir, int grandDir, int greatDir) {
        AVLTreeNode<T> newNode = (AVLTreeNode<T>) super.rotate(node, childDir, grandDir, greatDir);
        ((AVLTreeNode<T>) node).updateHeight();
        return newNode;
    }

    @Override
    public int getHeight() {
        return this.root != null ? ((AVLTreeNode<T>) this.root).height : -1;
    }
}
