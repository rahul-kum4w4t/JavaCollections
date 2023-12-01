package in.zero.link.tree;

import java.util.Iterator;

/**
 * Iterable specially tuned for tree collections
 *
 * @param <T>
 */
interface LinkBinaryTreeIterable<T> extends java.lang.Iterable<T> {

    Iterator<T> levelOrderIterator();

    Iterator<T> preOrderIterator();

    Iterator<T> inOrderIterator();

    Iterator<T> postOrderIterator();

    Iterator<T> reverseOrderIterator();
}
