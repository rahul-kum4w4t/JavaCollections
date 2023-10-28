package in.zero.collection.link;

/**
 * Heap A Heap is a Complete binary Tree that meets heap Properties- Min Heap:
 * The element of each node is larger than or equal to the element at its
 * parent. The node with the lowest value is the root. Max Heap: The element of
 * each node is smaller than the element at its parent. The highest value
 * element is at the root. Usage:-------------------- - Heap's are used as
 * priority queues
 */
public class Heap<T extends Comparable<T>> extends BinaryTree<T> {

	public enum Type {
		MIN, MAX
	}

	private Type type;

	public Heap() {
		this.type = Type.MIN;
	}

	public Heap(Type type) {
		this.type = type != null ? type : Type.MIN;
	}

	/**
	 * Add value to the heap
	 */
	@Override
	public Heap<T> add(T value) {
		BinaryTreeNode<T> newNode = super.addNode(value);
		upHeapify(newNode);
		return this;
	}

	/**
	 * Restricted operation
	 */
	@Override
	public T remove(T value) {
		throw new RuntimeException("Operation not supported for heaps");
	}

	/**
	 * Pop out an element from the heap
	 * 
	 * @return popped out element
	 */
	@SuppressWarnings("unchecked")
	public T remove() {
		if (root != null) {
			BinaryTreeNode<T> node = this.root;
			final T data = node.data;
			BinaryTreeNode<T>[] nodes = new BinaryTreeNode[nodesCount];
			int counter = 1;
			nodes[0] = node;
			for (int i = 1; i < nodesCount && counter < nodesCount; i++) {
				if (node.hasLeft())
					nodes[counter++] = node.left;
				if (node.hasRight())
					nodes[counter++] = node.right;
				node = nodes[i];
			}
			BinaryTreeNode<T> lastNode = nodes[nodesCount - 1];
			if (this.root != lastNode) {
				this.root.data = lastNode.data;
				if (lastNode.parent.left == lastNode) {
					lastNode.parent.left = null;
				} else {
					lastNode.parent.right = null;
				}
				lastNode.parent = null;
				downHeapify(root);
			} else {
				this.root = null;
			}
			nodesCount--;
			return data;
		}
		return null;
	}

	/**
	 * Down-heapify a node after deletion of any node
	 * 
	 * @param node down heapify from this node onwards
	 */
	private void downHeapify(BinaryTreeNode<T> node) {
		while (node != null) {
			if (node.hasLeft()) {
				if (node.hasRight()) {
					node = swapNodesBasedOnHeapType(node, node.data.compareTo(node.left.data),
							node.data.compareTo(node.right.data));
				} else {
					node = swapNodesBasedOnHeapType(node, node.data.compareTo(node.left.data), 0);
				}
			} else if (node.hasRight()) {
				node = swapNodesBasedOnHeapType(node, 0, node.data.compareTo(node.right.data));
			} else {
				node = null;
			}
		}
	}

	/**
	 * Upheapify after adding any new value to the heap
	 * 
	 * @param node node from which heapify operation will start
	 */
	private void upHeapify(BinaryTreeNode<T> node) {
		while (node.parent != null && (type == Type.MAX ? (node.data.compareTo(node.parent.data) > 0)
				: (node.data.compareTo(node.parent.data) < 0))) {
			T data = node.data;
			node.data = node.parent.data;
			node.parent.data = data;
			node = node.parent;
		}
	}

	/**
	 * Decides which child node it will use to swap the values with provided node
	 * 
	 * @param node      from which heapification will start
	 * @param compLeft  comparison of provided node and left child
	 * @param compRight comparison of provided node and right child
	 * @return node with which node value swapped
	 */
	private BinaryTreeNode<T> swapNodesBasedOnHeapType(BinaryTreeNode<T> node, int compLeft, int compRight) {

		BinaryTreeNode<T> swapWith;
		if (type == Type.MAX) {
			swapWith = compLeft < 0
					? (compRight < 0 ? (node.left.data.compareTo(node.right.data) > 0 ? node.left : node.right)
							: node.left)
					: compRight < 0 ? node.right : null;

		} else {
			swapWith = compLeft > 0
					? (compRight > 0 ? (node.left.data.compareTo(node.right.data) < 0 ? node.left : node.right)
							: node.left)
					: compRight > 0 ? node.right : null;
		}
		if (swapWith != null) {
			swapData(node, swapWith);
			return swapWith;
		} else {
			return null;
		}
	}

	/**
	 * Swaps data between two nodes
	 * 
	 * @param node1
	 * @param node2
	 */
	private void swapData(BinaryTreeNode<T> node1, BinaryTreeNode<T> node2) {
		T data = node1.data;
		node1.data = node2.data;
		node2.data = data;
	}

	/**
	 * Searches any values existence in the heap
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean search(final T value) {
		BinaryTreeNode<T> node = this.root;
		BinaryTreeNode<T>[] nodes = new BinaryTreeNode[nodesCount];
		int counter = 1;
		nodes[0] = node;
		for (int i = 1; i <= nodesCount && counter <= nodesCount; i++) {
			if (node.data.compareTo(value) == 0) {
				return true;
			}
			if (node.hasLeft())
				nodes[counter++] = node.left;
			if (node.hasRight())
				nodes[counter++] = node.right;
			node = nodes[i];
		}
		return false;
	}

	/**
	 * Returns type of heap
	 * 
	 * @return
	 */
	public String heapType() {
		return type.toString();
	}
}
