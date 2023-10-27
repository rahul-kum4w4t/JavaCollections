package in.zero;

import in.zero.collection.link.*;

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
        testRedBlackTree();
        testSplayTree();
        testMaxHeap();
        testMinHeap();
    }

    public static void testBinaryTree() {
        /**
         * Simple Binary Tree Tests
         */
        BinaryTree<Integer> tree = getCompleteLinkBinaryTree();

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

    public static void testBST() {
        /**
         * BinarySearchTree
         */
        System.out.println("\n\n---------------- BST ---------------");
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        IntStream.of(4, 2, 19, 11, 7, 6, 13, 5, 1, 14, 3, 15, 12, 9, 18, 8).forEach(bst::add);
        bst.remove(11);
        System.out.println("Tree height : " + bst.getHeight());
        bst.printTrace();
    }

    public static void testAVLTree() {
        AVLTree<Integer> avl = new AVLTree<>(BinarySearchTree.Sort.DESC);
        try {
            avl.addAll(10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35);
            //avl.addAll(64, 22, 6, 70, 20, 82, 40, 21, 50, 25, 95, 32, 34, 46, 27, 93, 17, 97, 96, 9, 38, 51, 26, 30, 90, 11, 98, 2, 62, 31, 28, 55, 83, 67, 45, 33, 44, 43, 29, 37, 72, 99, 94, 63, 4, 84, 74, 1, 87, 69, 56, 61, 81, 14, 3, 47, 42, 7, 48, 39, 100, 59, 79, 10, 60, 68, 52, 88, 80, 65, 53, 24, 71, 73, 76, 41, 8, 16, 54, 91, 13, 77, 85, 78, 58, 86, 19, 49, 35, 92, 15, 66, 75, 36, 89, 57, 18, 23, 5, 12);
            avl.printTrace();
            avl.remove(15);
            avl.remove(1);
            avl.remove(33);
            avl.remove(17);
            avl.remove(5);
            avl.printTrace();
            IntStream.of(10, 20, 25, 7, 21, 73, 71, 35).forEach(val -> {
                assert avl.search(val);
                avl.remove(val);
                assert !avl.search(val);
            });
            avl.printTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testRedBlackTree() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.addAll(10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35);
        rbt.printTrace();
        rbt.remove(10);
        rbt.printTrace();
        rbt.remove(1);
        rbt.printTrace();
        rbt.remove(20);
        rbt.printTrace();
        rbt.remove(15);
        rbt.printTrace();
        IntStream.of(25, 17, 7, 21, 5, 33, 73, 71, 35).forEach(val -> {
            assert rbt.search(val);
            rbt.remove(val);
            assert !rbt.search(val);
        });
        rbt.printTrace();
    }

    public static void testSplayTree() {
        SplayTree<Integer> sTree = new SplayTree<>();
        sTree.addAll(10, 1, 20);
        sTree.addAll(15, 25, 17, 7, 21, 5, 33, 73, 31, 35);
        sTree.search(74);
        sTree.remove(17);
        sTree.printTrace();
    }

    public static void testMaxHeap() {
        Heap<Integer> maxHeap = new Heap<>(Heap.Type.MAX);
        maxHeap.addAll(10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 31, 35, 74, 17);
        maxHeap.printTrace();
        maxHeap.remove();
        maxHeap.printTrace();
    }

    public static void testMinHeap() {
        Heap<Integer> maxHeap = new Heap<>();
        maxHeap.addAll(10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 31, 35, 74, 17);
        maxHeap.printTrace();
    }

    public static BinaryTree<Integer> getCompleteLinkBinaryTree() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        IntStream.range(1, 32).forEach(val -> tree.add(val));
        return tree;
    }
}
