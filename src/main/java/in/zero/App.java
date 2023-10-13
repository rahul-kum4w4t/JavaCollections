package in.zero;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        /**
         * Simple Binary Tree Tests
         */
        LinkBinaryTree<Integer> tree = getCompleteLinkBinaryTree();
        System.out.println("\n----------- Complete Binary Tree ---------------");
        printTraversals(tree);
        LinkBinaryTree<Integer> leftTree = getLeftSkewedLinkBinaryTree();
        System.out.println("\n\n----------- Left Skewed Binary Tree ---------------");
        printTraversals(leftTree);
        LinkBinaryTree<Integer> rightTree = getLeftSkewedLinkBinaryTree();
        System.out.println("\n\n----------- Right Skewed Binary Tree ---------------");
        printTraversals(rightTree);


        /**
         * Iterator tests
         */
        System.out.println("\n\n\n-------------Iterator Check ------------------");
        tree = App.getCompleteLinkBinaryTree();

        Iterator<Integer> it = tree.levelOrderIterator();
        while(it.hasNext()){
            System.out.print(it.next() + ", ");
        }
        System.out.println();

        it = tree.inOrderIterator();
        while(it.hasNext()){
            System.out.print(it.next() + ", ");
        }
        System.out.println();

        it = tree.preOrderIterator();
        while(it.hasNext()){
            System.out.print(it.next() + ", ");
        }
        System.out.println();

        it = tree.postOrderIterator();
        while(it.hasNext()){
            System.out.print(it.next() + ", ");
        }
        System.out.println();


        /**
         * For each with default pre order traversal
         */
        System.out.println("\n\n---------------- For Loop check ---------------");
        for(int i : tree){
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

    public static void printTraversals(LinkBinaryTree<Integer> tree){
        Consumer<BinaryTreeNode<?>> disp = node -> System.out.print(((BinaryTreeNode<Integer>)node).getData()+", ");
        System.out.print("Level Order Traversal: ");
        Stream.of(tree.levelOrder()).forEach(disp);
        System.out.print("\nIn Order Traversal: ");
        Stream.of(tree.inOrder()).forEach(disp);
        System.out.print("\nPre Order Traversal: ");
        Stream.of(tree.preOrder()).forEach(disp);
        System.out.print("\nPost Order Traversal: ");
        Stream.of(tree.postOrder()).forEach(disp);
    }

    public static LinkBinaryTree<Integer> getCompleteLinkBinaryTree(){
        LinkBinaryTree<Integer> tree = new LinkBinaryTree<>();
        IntStream.range(1,32).forEach(val -> tree.add(val));
        return tree;
    }

    public static LinkBinaryTree<Integer> getLeftSkewedLinkBinaryTree(){
        LinkBinaryTree<Integer> tree = new LinkBinaryTree<>();

        BinaryTreeNode<Integer> node = new BinaryTreeNode<>(1);
        tree.root = node;

        BinaryTreeNode<Integer> temp = new BinaryTreeNode<>(2, node);
        node.setLeft(temp);
        node = temp;

        temp = new BinaryTreeNode<>(3, node);
        node.setLeft(temp);
        node = temp;

        temp = new BinaryTreeNode<>(4, node);
        node.setLeft(temp);
        node = temp;

        temp = new BinaryTreeNode<>(5, node);
        node.setLeft(temp);
        node = temp;

        temp = new BinaryTreeNode<>(6, node);
        node.setLeft(temp);
        node = temp;

        temp = new BinaryTreeNode<>(7, node);
        node.setLeft(temp);

        tree.nodesCount = 7;

        return tree;
    }

    public static LinkBinaryTree<Integer> getRightSkewedLinkBinaryTree(){
        LinkBinaryTree<Integer> tree = new LinkBinaryTree<>();

        BinaryTreeNode<Integer> node = new BinaryTreeNode<>(1);
        tree.root = node;

        BinaryTreeNode<Integer> temp = new BinaryTreeNode<>(2, node);
        node.setRight(temp);
        node = temp;

        temp = new BinaryTreeNode<>(3, node);
        node.setRight(temp);
        node = temp;

        temp = new BinaryTreeNode<>(4, node);
        node.setRight(temp);
        node = temp;

        temp = new BinaryTreeNode<>(5, node);
        node.setRight(temp);
        node = temp;

        temp = new BinaryTreeNode<>(6, node);
        node.setRight(temp);
        node = temp;

        temp = new BinaryTreeNode<>(7, node);
        node.setRight(temp);

        tree.nodesCount = 7;

        return tree;
    }
}
