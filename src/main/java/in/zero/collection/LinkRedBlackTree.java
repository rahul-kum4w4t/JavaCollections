package in.zero.collection;

/**
 * Reb-Black Tree:
 * An R-B tree is majorly used in systems where insertion and/or deletion is performed frequently.
 * ----- Rules of a red black tree ------------------
 * 1. A red-black tree is a binary search tree with one extra bit of storage per node for its color (red/black)
 * 2. This tree is approximately balanced.
 * 3. Every node is either red or black.
 * 4. The root is black
 * 5. Every leaf (NIL) is black
 * 6. If a node is red, then both its children are black.
 * 7. For each node, all simple paths from the node to descendant leaves contain the same number of black nodes.
 * -----------------------------------------------------
 * ---------- Time Complexities for Red-Black Tree --------
 * 1. Insertion: O(log(n))
 * 2. Deletion: O(log(n))
 * 3. Search: O(log(n))
 * ------------------------------------------------------
 * -------------- Pros of RedBlack Tree -----------------
 * 1. At most two rotations are required when adding/deleting elements to Red-Black tree
 * 2. Approximately balanced tree which balances itself
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

    /**
     * Adds a value to Red-Black tree
     * - Addition to red black tree is same as BST, Just after addition
     * need to make sure that no rules of Reb Black tree violates
     * ------------ Rules to revalidate Reb-Black Tree after Insertion -------------------
     * 1. Perform standard BST insertion and make the colour of newly inserted nodes as RED.
     * 2. If x is the root, change the colour of x as BLACK (Black height of complete tree increases by 1).
     * 3. Do the following if the color of x’s parent is not BLACK and x is not the root.
     * a) If x’s uncle is RED (Grandparent must have been black from property 4)
     * (i) Change the colour of parent and uncle as BLACK.
     * (ii) Colour of a grandparent as RED.
     * (iii) Change x = x’s grandparent, repeat steps 2 and 3 for new x.
     * b) If x’s uncle is BLACK, then there can be four configurations for x, x’s parent (p) and x’s grandparent (g) (This is similar to AVL Tree)
     * (i) Left Left Case (p is left child of g and x is left child of p)
     * (ii) Left Right Case (p is left child of g and x is the right child of p)
     * (iii) Right Right Case (Mirror of case i)
     * (iv) Right Left Case (Mirror of case ii)
     * 5. Re-coloring after rotations:
     * For Left Left Case [3.b (i)] and Right Right case [3.b (iii)], swap colors of grandparent and parent after rotations
     * For Left Right Case [3.b (ii)]and Right Left Case [3.b (iv)], swap colors of grandparent and inserted node after rotations
     *
     * @param {any} value Value to be added to the tree
     */
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

    /**
     * Removes an element from Reb Black Tree
     * - Deletion from Reb Black tree remains same as BST, Just after deletion of
     * node we need to make sure that the resultant tree must follow rules of Reb Black Tree
     * - When we remove a black node from the tree it leaves Double black Nil space at that place
     * which is required to be tackled by following mentioned rules.
     * ------------ Rules to revalidate Reb-Black Tree after deletion -------------------
     * Case 1: If node to be deleted is a red leaf node.
     * Action: Just remove it from the tree without any action
     * Case 2: If Double Black (DB) node is root node.
     * Action: Remove the DB and root node becomes black.
     * Case 3: If DB's sibling is black and DB's sibling's children are also black
     * Action:
     * a). Remove the DB (null remains null and normal black node remain normal black)
     * b). Make DB's sibling Red
     * c). If DB's parent is black, make it DB, else make it black
     * Case 4: If DB's sibling is red.
     * Action:
     * a). Swap color DB's parent with DB's sibling
     * b). Perform rotation at parent node in the direction of DB node
     * c). Check which case can be applied to this new tree and perform that action (This mainly converts the tree to black sibling case)
     * Case 5: Db's sibling is black AND DB's sibling's child which is far from DB is black AND DB's sibling's child which is near to DB is red
     * Action:
     * a). Swap color of the sibling with sibling's red child
     * b). Perform rotation at sibling node in direction opposite of DB node
     * c). Apply case 6
     * Case 6: DB's sibling is black AND DB's sibling's far child is Red
     * Action:
     * a). Swap color of DB's parent with DB's sibling's color
     * b). Perform rotation at DB's parent in direction of DB
     * c). Remove DB sign and make the node normal balck node
     * d). Change color of DB's sibling's far red child to black
     * --------------------------------------------------------------------------------------
     *
     * @param {any} value Value to de deleted
     */
    @Override
    public T remove(T value) {
        // Searches for the node which needs to be deleted
        RedBlackTreeNode<T> node = (RedBlackTreeNode<T>) super.searchNode(value);

        // If node to be deleted is not null
        if (node != null) {
            boolean isRoot = node == this.root;
            boolean isLeaf;
            nodesCount--;
            if (node.hasLeft() && node.hasRight()) {
                RedBlackTreeNode<T> inOrderPred = (RedBlackTreeNode<T>) inOrderPredecessor(node);
                node.data = inOrderPred.data;
                node = inOrderPred;
                isRoot = false;
                isLeaf = !(node.hasLeft() || node.hasRight());
            } else {
                isLeaf = true;
            }

            RedBlackTreeNode<T> parent = (RedBlackTreeNode<T>) replaceNode(node);
            if (!(isLeaf && node.color)) {
                RedBlackTreeNode<T> sibling, siblingRight, siblingLeft;
                boolean siblingColor, siblingRightColor, siblingLeftColor;
                int nodeDir;

                while (node != null && parent != null && !isRoot) {
                    nodeDir = parent.left == node ? -1 : 1;
                    sibling = (RedBlackTreeNode<T>) (nodeDir == -1 ? parent.right : parent.left);
                    if (sibling != null) {
                        siblingLeft = (RedBlackTreeNode<T>) sibling.left;
                        siblingRight = (RedBlackTreeNode<T>) sibling.right;
                        siblingColor = sibling.color;
                        siblingLeftColor = siblingLeft != null && siblingLeft.color;
                        siblingRightColor = siblingRight != null && siblingRight.color;
                    } else {
                        siblingLeft = null;
                        siblingRight = null;
                        siblingColor = false;
                        siblingRightColor = false;
                        siblingLeftColor = false;
                    }
                    // Sibling Black
                    if (!siblingColor) {
                        if (!siblingLeftColor && !siblingRightColor) {
                            if (sibling != null) sibling.color = true;
                            if (!parent.color) {
                                node = parent;
                                parent = (RedBlackTreeNode<T>) parent.parent;
                                isRoot = node == this.root;
                            } else {
                                node.color = false;
                                node = null;
                            }
                        } else {
                            RedBlackTreeNode<T> nearNephew = siblingLeft, farNephew = siblingRight;
                            int dirOppDB = -1, dirDB = 1;
                            boolean nearNephewColor = siblingLeftColor, farNephewColor = siblingRightColor;
                            if (nodeDir == 1) {
                                nearNephew = siblingRight;
                                farNephew = siblingLeft;
                                dirOppDB = 1;
                                dirDB = -1;
                                nearNephewColor = siblingRightColor;
                                farNephewColor = siblingLeftColor;
                            }
                            if (nearNephewColor && !farNephewColor) {
                                rule5(sibling, nearNephew, dirOppDB);
                            } else {
                                rule6(parent, sibling, farNephew, dirDB);
                                node = null;
                            }
                        }
                    } else { // Sibling Red
                        int childDir = parent.left == sibling ? -1 : 1;
                        swapColor(sibling, parent);
                        rotate(parent, childDir, childDir, childDir);
                    }
                }
            }
            return value;
        }
        return null;
    }

    private void rule5(RedBlackTreeNode<T> sibling, RedBlackTreeNode<T> redChild, int rotateDir) {
        sibling.color = true;
        redChild.color = false;
        rotate(sibling, rotateDir, rotateDir, rotateDir);
    }

    private void rule6(RedBlackTreeNode<T> parent, RedBlackTreeNode<T> sibling, RedBlackTreeNode<T> farRedChild, int rotateDir) {
        swapColor(parent, sibling);
        rotate(parent, rotateDir, rotateDir, rotateDir);
        farRedChild.color = false;
    }

    private void swapColor(RedBlackTreeNode<T> node1, RedBlackTreeNode<T> node2) {
        boolean temp = node1.color;
        node1.color = node2.color;
        node2.color = temp;
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
