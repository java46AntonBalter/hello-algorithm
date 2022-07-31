package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

public class ArrayList<T> implements List<T> {
	private static final int DEFAULT_CAPACITY = 16;
	private T[] array;
	private int size;

	@SuppressWarnings("unchecked")
	public ArrayList(int capacity) {
		array = (T[]) new Object[capacity];
	}

	public ArrayList() {
		this(DEFAULT_CAPACITY);
	}

	private class ArrayListIterator implements Iterator<T> {
		int current = 0;

		@Override
		public boolean hasNext() {
			return current < array.length ? true : false;
		}

		@Override
		public T next() {
			return !hasNext() ? null : (T) array[current++];
		}

	}

	@Override
	public boolean add(T obj) {
		if (array.length == size) {
			array = Arrays.copyOf(array, size * 2);
		}
		array[size++] = obj;
		return true;
	}

	@Override
	public boolean remove(Object pattern) {
		for (int i = 0; i < size; i++) {
			if (array[i] == pattern) {
				System.arraycopy(array, i + 1, array, i, (size--) - (i + 1));
				array[size] = null;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		Predicate<T> removePredicate = predicate;
		boolean flRemoved = false;
		for (int i = 0; i < size; i++) {
			if (removePredicate.test(array[i])) {
				System.arraycopy(array, i + 1, array, i, (size--) - (i--) + 1);
				array[size] = null;
				flRemoved = true;
			}
		}
		return flRemoved;
	}

	@Override
	public boolean contains(Object pattern) {
		for (Object i : array) {
			if (i == pattern) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public Iterator<T> iterator() {

		return new ArrayListIterator();
	}

	@Override
	public boolean add(int index, T obj) {
		if (index < 0) {
			return false;
		}
		if (array.length == size) {
			array = Arrays.copyOf(array, size * 2);
		}

		System.arraycopy(array, index, array, index + 1, (size++) - index);
		array[index] = obj;
		return true;
	}

	@Override
	public T remove(int index) {
		if (index < 0) {
			return null;
		}
		T res = array[index];
		System.arraycopy(array, index + 1, array, index, (size--) - (index + 1));
		array[size] = null;
		return res;
	}

	@Override
	public int indexOf(Object pattern) {
		for(int i = 0; i < size; i++) {
			if(array[i] == pattern) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object pattern) {
		for(int i = size - 1; i >= 0; i--) {
			if(array[i] == pattern) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public T get(int index) {
		return index < 0 || index > size || array[index] == null ? null : array[index];
	}
}
