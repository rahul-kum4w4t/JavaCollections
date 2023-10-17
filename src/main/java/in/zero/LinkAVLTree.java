package in.zero;

public class LinkAVLTree<T extends Comparable<T>> extends LinkBinarySearchTree<T> {

    @Override
    public LinkAVLTree<T> add(T val) {
        if (val != null) {
            BinaryTreeNode<T> newNode = this.addNode(val);
            BinaryTreeNode<T> node = newNode.getParent(); // Parent Node
            int leftHeight, rightHeight, height = 1; // Because from newNode to its parent height is 1
            int grandDir = 0, childDir = 0, greatDir = 0;
            if (node != null) { // Parent is not null
                if (node.getLeft() == newNode) {
                    grandDir = -1;
                } else {
                    grandDir = 1;
                }
                greatDir = grandDir;
                newNode = node;
                node = node.getParent();
                while (node != null) {
                    if (node.getLeft() == newNode) {
                        childDir = -1;
                    } else {
                        childDir = 1;
                    }
                    if (childDir == -1) {
                        leftHeight = height;
                        rightHeight = this.getHeightOfNode(node.getRight());
                    } else {
                        rightHeight = height;
                        leftHeight = this.getHeightOfNode(node.getLeft());
                    }
                    if (Math.abs(leftHeight - rightHeight) == 2) {
                        this.rotate(node, childDir, grandDir, greatDir);
                        node = null;
                    } else {
                        newNode = node;
                        node = node.getParent();
                        greatDir = grandDir;
                        grandDir = childDir;
                        height = leftHeight > rightHeight ? leftHeight + 1 : rightHeight + 1;
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

    private void rotate(BinaryTreeNode<T> node, int childDir, int grandDir, int greatDir) {
        System.out.println("Rotate: " + node.getData() + ", " + childDir + ", " + grandDir + ", " + greatDir);
        BinaryTreeNode<T> child = childDir == -1 ? node.getLeft() : node.getRight();
        BinaryTreeNode<T> parent = node.getParent();
        if (childDir != grandDir) {
            rotate(child, grandDir, greatDir, greatDir);
            child = childDir == -1 ? node.getLeft() : node.getRight();
        }
        child.setParent(parent);
        node.setParent(child);
        if (parent != null) {
            if (parent.getLeft() == node) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
        if (childDir == -1) {
            node.setLeft(child.getRight());
            child.setRight(node);
            if (node.hasLeft()) {
                node.getLeft().setParent(node);
            }
        } else {
            node.setRight(child.getLeft());
            child.setLeft(node);
            if (node.hasRight()) {
                node.getRight().setParent(node);
            }
        }
        if (parent == null) {
            this.root = child;
        }
        System.out.println("Rotate: " + node.getData() +" completed");
    }
}
