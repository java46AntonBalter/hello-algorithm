package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

public interface Collection<T> extends Iterable<T> {
	/**
	 * adds object of type T in collection
	 * 
	 * @param obj
	 * @return true if added
	 */
	boolean add(T obj);

	/***************************************/
	/**
	 * removes object equaled to the given pattern
	 * 
	 * @param pattern any object
	 * @return true if removed
	 */
	boolean remove(Object pattern);

	/******************************************/
	/**
	 * removes all objects matching the given predicate
	 * 
	 * @param predicate
	 * @return true if a collection has been updated
	 */
	boolean removeIf(Predicate<T> predicate);

	/*************************************************/
	/**
	 * 
	 * @param predicate
	 * @return true if there is an object equaled to the given pattern
	 */
	boolean contains(Object pattern);

	/********************************************************/
	/**
	 * 
	 * @return amount of the objects
	 */
	int size();

	/******************************************************/
	/**
	 * 
	 * @param ar
	 * @return regular Java array containing all the collection object
	 */
	default T[] toArray(T[] ar) {
		int size = size();
		T[] res = ar.length >= size ? ar : Arrays.copyOf(ar, size);
		Iterator<T> it = iterator();
		for (int i = 0; i < res.length; i++) {
			if (!it.hasNext()) {
				if (ar == res) {
					return res;
				} else if (ar.length > i) {
					ar[i] = null;
				}
			}
			res[i] = (T) it.next();
		}
		return res;
	}
}
