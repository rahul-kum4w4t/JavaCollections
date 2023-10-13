package in.zero;

import java.util.Iterator;

interface LinkBinaryTreeIterable<T> extends java.lang.Iterable<T> {

    Iterator<T> levelOrderIterator();

    Iterator<T> preOrderIterator();

    Iterator<T> inOrderIterator();

    Iterator<T> postOrderIterator();
}
