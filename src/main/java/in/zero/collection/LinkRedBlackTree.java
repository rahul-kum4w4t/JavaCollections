package in.zero.collection;

/**
 * Red-Black tree is a binary search tree in which every node is colored with either red or black.
 * It is a type of self-balancing binary search tree.
 * It has a good efficient worst case running time complexity.
 * <p>
 * The AVL trees are more balanced compared to Red-Black Trees,
 * but they may cause more rotations during insertion and deletion.
 * So if your application involves frequent insertions and deletions,
 * then Red-Black trees should be preferred.
 * And if the insertions and deletions are less frequent and search is a more frequent operation,
 * then AVL tree should be preferred over the Red-Black Tree.
 *
 * @param <T>
 */
public class LinkRedBlackTree<T extends Comparable<T>> extends LinkBinarySearchTree<T> {

    private static class RedBlackTreeNode<T extends Comparable<T>> extends BinaryTreeNode<T> {

        boolean color;

        /**
         * Param constructor
         *
         * @param data
         */
        RedBlackTreeNode(T data) {
            super(data);
        }

        @Override
        public String toString() {
            return String.format("%s:%s", data, color ? "R" : "B");
        }
    }

    @Override
    public LinkBinarySearchTree<T> add(T value) {

        RedBlackTreeNode<T> newNode = (RedBlackTreeNode<T>) this.addNode(value);
        RedBlackTreeNode<T> parent, uncle, grand;
        int childDir, grandDir;
        while (newNode != null && newNode.parent != null) {
            newNode.color = true;
            parent = (RedBlackTreeNode<T>) newNode.parent;
            if (parent.color) {
                grand = (RedBlackTreeNode<T>) parent.parent;
                grandDir = parent.left == newNode ? -1 : 1;
                if (grand != null) {
                    if (grand.left == parent) {
                        uncle = (RedBlackTreeNode<T>) grand.right;
                        childDir = -1;
                    } else {
                        uncle = (RedBlackTreeNode<T>) grand.left;
                        childDir = 1;
                    }
                    if (uncle != null && uncle.color) {
                        parent.color = false;
                        uncle.color = false;
                        if (grand != root) {
                            grand.color = true;
                            newNode = grand;
                        } else {
                            return this;
                        }
                    } else {
                        rotate(grand, childDir, grandDir, grandDir);
                        grand.color = !grand.color;
                        if (childDir != grandDir) {
                            newNode.color = !newNode.color;
                        } else {
                            parent.color = !parent.color;
                        }
                        return this;
                    }
                } else {
                    return this;
                }
            } else {
                return this;
            }
        }
        return this;
    }

    @Override
    public T remove(T value) {
        RedBlackTreeNode<T> node = (RedBlackTreeNode<T>) super.removeNode(value);

        return value;
    }

    /**
     * Creates a new node with provide values
     *
     * @param value  Value to be inserted in the new node
     * @param parent Parent which links to the new node
     * @return newely created node
     */
    @Override
    BinaryTreeNode<T> createNewNode(T value, BinaryTreeNode<T> parent) {
        RedBlackTreeNode<T> newNode = new RedBlackTreeNode<>(value);
        newNode.parent = parent;
        return newNode;
    }
}
