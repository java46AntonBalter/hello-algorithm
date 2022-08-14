package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TreeSet<T> implements SortedSet<T> {
	private static class Node<T> {
		T obj;
		Node<T> parent;
		Node<T> left;
		Node<T> right;

		Node(T obj) {
			this.obj = obj;
		}
	}

	private Node<T> root;
	int size;
	Comparator<T> comp;

	private Node<T> getLeastNodeFrom(Node<T> node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	private class TreeSetIterator implements Iterator<T> {
		Node<T> current = root == null ? null : getLeastNodeFrom(root);
		boolean flNext;
		Node<T> prevNode;

		@Override
		public boolean hasNext() {

			return current != null;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			prevNode = current;
			updateCurrent();
			flNext = true;
			return prevNode.obj;
		}

		private void updateCurrent() {
			current = current.right != null ? getLeastNodeFrom(current.right) : getGreaterParent(current);

		}

		private Node<T> getGreaterParent(Node<T> node) {

			while (node.parent != null && node.parent.left != node) {
				node = node.parent;
			}
			return node.parent;
		}

		@Override
		public void remove() {
			if (!flNext) {
				throw new IllegalStateException();
			}
			if (isJunction(prevNode)) {
				current = getLeastNodeFrom(prevNode.right);
			}
			TreeSet.this.remove(prevNode.obj);

			flNext = false;
		}

	}

	public TreeSet(Comparator<T> comp) {
		this.comp = comp;
	}

	@SuppressWarnings("unchecked")
	public TreeSet() {
		this((Comparator<T>) Comparator.naturalOrder());
	}

	@Override
	public boolean add(T obj) {
		Node<T> parent = getNodeOrParent(obj);
		boolean res = false;
		int compRes = 0;
		if (parent == null || (compRes = comp.compare(obj, parent.obj)) != 0) {
			// obj doesn't exist
			Node<T> newNode = new Node<>(obj);
			if (parent == null) {
				// added first element that is the root
				root = newNode;
			} else if (compRes > 0) {
				parent.right = newNode;
			} else {
				parent.left = newNode;
			}
			res = true;
			newNode.parent = parent;
			size++;
		}
		return res;
	}

	private Node<T> getNodeOrParent(T obj) {
		Node<T> current = root;
		Node<T> parent = null;
		int compRes = 0;
		while (current != null) {
			parent = current;
			compRes = comp.compare(obj, current.obj);
			if (compRes == 0) {
				break;
			}
			current = compRes > 0 ? current.right : current.left;
		}
		return parent;
	}

	@Override
	public boolean remove(Object pattern) {
		Node<T> nodeToRemove = findNodeToRemove(pattern);
		if (nodeToRemove != null) {
			removeNode(nodeToRemove);
			size--;
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private Node<T> findNodeToRemove(Object pattern) {
		Node<T> current = root;
		int compRes = 0;
		while (current != null) {
			compRes = comp.compare((T) pattern, current.obj);
			if (compRes == 0) {
				return current;
			}
			current = compRes > 0 ? current.right : current.left;
		}
		return null;
	}

	private void removeNode(Node<T> node) {
		if (isJunction(node)) {
			removeJunctionNode(node);
		} else {
			removeNonJunctionNode(node);
		}

	}

	private boolean isJunction(Node<T> node) {
		if (node.left != null && node.right != null) {
			return true;
		}
		return false;

	}

	private void removeJunctionNode(Node<T> node) {
		Node<T> minNodeForRight = getLeastNodeFrom(node.right);

		if (node.parent != null) {
			if (comp.compare(minNodeForRight.obj, node.parent.obj) > 0) {
				node.parent.right = minNodeForRight;
			} else {
				node.parent.left = minNodeForRight;
			}
		}
		minNodeForRight.left = node.left;
		node.left.parent = minNodeForRight;
		node.left = null;
		if (comp.compare(node.right.obj, minNodeForRight.obj) != 0) {
			node.right.parent = minNodeForRight;
			minNodeForRight.right = node.right;
			minNodeForRight.parent.left = null;
		}
		node.right = null;
		minNodeForRight.parent = node.parent;
		node.parent = null;
		if (isRoute(node)) {
			root = minNodeForRight;
		}
		
	}

	private void removeNonJunctionNode(Node<T> node) {
		if (node.left == null && node.right == null) {
			if (node.parent == null) {
				root = null;
			} else {
				if (isRightOrLeftChild(node)) {
					node.parent.right = null;
					node.parent = null;

				} else {
					node.parent.left = null;
					node.parent = null;
				}
			}

		} else if (node.left != null) {
			if (node.parent != null) {
				if (isRightOrLeftChild(node)) {
					node.parent.right = node.left;
				} else {
					node.parent.left = node.left;
				}
				if (isRoute(node)) {
					root = node.parent;
				}
			} else {
				if (isRoute(node)) {
					root = node.left;
				}
			}
			node.left.parent = node.parent;
			node.parent = null;
			node.left = null;

		} else {
			if (node.parent != null) {
				if (isRightOrLeftChild(node)) {
					node.parent.right = node.right;
				} else {
					node.parent.left = node.right;
				}
				if (isRoute(node)) {
					root = node.parent;
				}
			} else {
				if (isRoute(node)) {
					root = node.right;
				}
			}
			node.right.parent = node.parent;
			node.parent = null;
			node.right = null;
		}
		

	}

	private boolean isRightOrLeftChild(Node<T> node) {
		return comp.compare(node.obj, node.parent.obj) > 0 ? true : false;
	}

	private boolean isRoute(Node<T> node) {
		return comp.compare(root.obj, node.obj) == 0 ? true : false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object pattern) {
		Node<T> searchedNode = getNodeOrParent((T) pattern);
		int compRes = comp.compare((T) pattern, searchedNode.obj);
		if (compRes == 0) {
			return true;
		}
		return false;
	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public Iterator<T> iterator() {

		return new TreeSetIterator();
	}

	@Override
	public T first() {
		if (root == null) {
			return null;
		}
		return getLeastNodeFrom(root).obj;
	}

	@Override
	public T last() {
		if (root == null) {
			return null;
		}
		return getMostNodeFrom(root).obj;
	}

	private Node<T> getMostNodeFrom(Node<T> node) {
		while (node.right != null) {
			node = node.right;
		}
		return node;
	}

}
