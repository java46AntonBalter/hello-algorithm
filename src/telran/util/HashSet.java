package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashSet<T> implements Set<T> {
	private static final double DEFAULT_FACTOR = 0.75;
	private static final int DEFAULT_HASH_TABLE_CAPACITY = 16;
	private List<T>[] hashTable;
	private int size;
	private double factor;

	@SuppressWarnings("unchecked")
	public HashSet(int hashTableCapacity, double factor) {
		this.factor = factor;
		hashTable = new List[hashTableCapacity];
	}

	public HashSet(int hashTableCapacity) {
		this(hashTableCapacity, DEFAULT_FACTOR);
	}

	public HashSet() {
		this(DEFAULT_HASH_TABLE_CAPACITY, DEFAULT_FACTOR);
	}

	private class HashSetIterator implements Iterator<T> {
//TODO
		private T currObj;
		private int currentIndex = 0;
		private boolean flNext = false;
		private Iterator<T> childIt;

		@Override
		public boolean hasNext() {
			return currentIndex < hashTable.length;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			flNext = true;
			getCurrentObject();
			return currObj;
		}

		@Override
		public void remove() {
			if (!flNext) {
				throw new IllegalStateException();
			}
			int currHashTableIndex = getHashTableIndex(currObj.hashCode());
			if (hashTable[currHashTableIndex] != null) {
				HashSet.this.remove(currObj);
				if (hashTable[currHashTableIndex].size() == 0) {
					hashTable[currHashTableIndex] = null;
				}
			}
			flNext = false;
		}

		private boolean getCurrentObject() {
			for (int i = currentIndex; i < hashTable.length; i++) {
				if (hashTable[i] != null) {
					if (childIt == null) {
						childIt = hashTable[i].iterator();
					}
					while (childIt.hasNext()) {
						currObj = childIt.next();
						currentIndex = i;
						return true;
					}
				}
				currentIndex = i + 1;
				childIt = null;
			}
			return false;
		}
	}

	@Override
	public boolean add(T obj) {
		// set can not have two equal objects
		// that's why the method returns false at adding an object that already exists
		boolean res = false;
		if (!contains(obj)) {
			res = true;
			if (size >= hashTable.length * factor) {
				recreateHashTable();
			}
			int hashTableInd = getHashTableIndex(obj.hashCode());
			if (hashTable[hashTableInd] == null) {
				hashTable[hashTableInd] = new LinkedList<T>();
			}
			hashTable[hashTableInd].add(obj);
			size++;
		}
		return res;
	}

	private void recreateHashTable() {
		HashSet<T> tmp = new HashSet<>(hashTable.length * 2); // tmp hashset has table with twice capacity
		for (List<T> list : hashTable) {
			if (list != null) {
				for (T obj : list) {
					tmp.add(obj);
				}
			}
		}
		hashTable = tmp.hashTable;
	}

	private int getHashTableIndex(int hashCode) {
		int res = Math.abs(hashCode) % hashTable.length;
		return res;
	}

	@Override
	public boolean remove(Object pattern) {
		int index = getHashTableIndex(pattern.hashCode());
		boolean res = false;
		if (hashTable[index] != null) {
			res = hashTable[index].remove(pattern);
			if (res) {
				size--;
			}
		}
		return res;
	}

	@Override
	public boolean contains(Object pattern) {
		int index = getHashTableIndex(pattern.hashCode());
		boolean res = false;
		if (hashTable[index] != null) {
			res = hashTable[index].contains(pattern);
		}
		return res;
	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public Iterator<T> iterator() {

		return new HashSetIterator();
	}

}
