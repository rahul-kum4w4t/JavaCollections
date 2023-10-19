package in.zero;

import in.zero.collection.LinkAVLTree;
import in.zero.collection.LinkBinarySearchTree;
import in.zero.collection.LinkBinaryTree;

import java.util.Iterator;
import java.util.stream.IntStream;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        testBinaryTree();
        testBST();
        testAVLTree();
    }

    public static void testBinaryTree() {
        /**
         * Simple Binary Tree Tests
         */
        LinkBinaryTree<Integer> tree = getCompleteLinkBinaryTree();
        System.out.println("\n----------- Complete Binary Tree ---------------");
        printTraversals(tree);

        /**
         * For each with default pre order traversal
         */
        System.out.println("\n\n---------------- For Loop check ---------------");
        for (int i : tree) {
            System.out.print(i + ", ");
        }
        /**
         * forEach check
         */
        System.out.println("\n\n---------------- forEach check ---------------");
        tree.forEach(i -> System.out.print(i + ", "));
        /**
         * Stream check
         */
        System.out.println("\n\n---------------- Stream check ---------------");
        tree.stream().forEach(i -> System.out.print(i + ", "));
    }

    public static void testBST() {
        /**
         * LinkBinarySearchTree
         */
        System.out.println("\n\n---------------- BST ---------------");
        LinkBinarySearchTree<Integer> bst = new LinkBinarySearchTree<>();
        IntStream.of(4, 2, 19, 11, 7, 6, 13, 5, 1, 14, 3, 15, 12, 9, 18, 8).forEach(bst::add);
        bst.remove(11);
        System.out.println("Tree height : " + bst.getHeight());
        printTraversals(bst);
    }

    public static void testAVLTree() {
        System.out.println("\n\n------------ AVL tree ----------------- ");
        LinkAVLTree<Integer> avl = new LinkAVLTree<>();
        try {
            avl.addAll(10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35);
            //avl.addAll(64, 22, 6, 70, 20, 82, 40, 21, 50, 25, 95, 32, 34, 46, 27, 93, 17, 97, 96, 9, 38, 51, 26, 30, 90, 11, 98, 2, 62, 31, 28, 55, 83, 67, 45, 33, 44, 43, 29, 37, 72, 99, 94, 63, 4, 84, 74, 1, 87, 69, 56, 61, 81, 14, 3, 47, 42, 7, 48, 39, 100, 59, 79, 10, 60, 68, 52, 88, 80, 65, 53, 24, 71, 73, 76, 41, 8, 16, 54, 91, 13, 77, 85, 78, 58, 86, 19, 49, 35, 92, 15, 66, 75, 36, 89, 57, 18, 23, 5, 12);
            printTraversals(avl);
            avl.remove(15);
            avl.remove(1);
            avl.remove(33);
            avl.remove(17);
            avl.remove(5);
            printTraversals(avl);
        } catch (Exception e) {
            e.printStackTrace();
            printTraversals(avl);
        }
    }

    public static void printTraversals(LinkBinaryTree<Integer> tree) {
        System.out.print("\nLevel Order Traversal: ");
        Iterator<Integer> level = tree.levelOrderIterator();
        while (level.hasNext()) System.out.print(level.next() + ", ");
        System.out.print("\nIn Order Traversal: ");
        Iterator<Integer> in = tree.inOrderIterator();
        while (in.hasNext()) System.out.print(in.next() + ", ");
        System.out.print("\nPre Order Traversal: ");
        Iterator<Integer> pre = tree.preOrderIterator();
        while (pre.hasNext()) System.out.print(pre.next() + ", ");
        System.out.print("\nPost Order Traversal: ");
        Iterator<Integer> post = tree.postOrderIterator();
        while (post.hasNext()) System.out.print(post.next() + ", ");
    }

    public static LinkBinaryTree<Integer> getCompleteLinkBinaryTree() {
        LinkBinaryTree<Integer> tree = new LinkBinaryTree<>();
        IntStream.range(1, 32).forEach(val -> tree.add(val));
        return tree;
    }
}
