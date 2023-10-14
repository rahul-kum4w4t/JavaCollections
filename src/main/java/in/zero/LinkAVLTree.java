package in.zero;

public class LinkAVLTree<T extends Comparable<T>> extends LinkBinarySearchTree<T> {

    @Override
    public LinkAVLTree<T> add(T val) {
        if (val != null) {
            BinaryTreeNode<T> newNode = this.addNode(val);
            if(this.getHeightOfNode(newNode) > 0){

            }
            this.rotate(newNode);
        }
        return null;
    }

    @Override
    public boolean remove(T val) {
        return false;
    }

    private void rotate(BinaryTreeNode<T> node) {

    }
}
