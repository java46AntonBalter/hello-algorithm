package telran.util.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.Collection;

abstract class CollectionTests {
	private static final int[] ADDED_ARRAY = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	private static final int ELEMENTS_QUANTITY = 100;
	protected static final int[] LARGE_ARRAY = new int[ELEMENTS_QUANTITY];
	private static final int MAX_INT = 99;
	private static final int MIN_INT = 0;
	private static final Predicate<Integer> PREDICATE1 = new AlwaysFalsePredicate();
	protected Collection<Integer> collection;

	protected abstract Collection<Integer> createCollection();

	@BeforeEach
	void setUp() throws Exception {
		collection = createCollection();
		for (int i : ADDED_ARRAY) {
			collection.add(i);
		}
	}

	@Test
	void addTest() {
		assertTrue(collection.add(10));
		assertTrue(collection.add(5));
		fillArray(LARGE_ARRAY);
		for(int i: LARGE_ARRAY) {
			collection.add(i);
		}
		assertEquals(112, collection.size());
	}
	
	protected void fillArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int)Math.floor(Math.random()*(MAX_INT-MIN_INT+1)+MIN_INT);
		}
	}	
	
	@Test
	void removeTest() {
		assertTrue(collection.remove(5));
		assertEquals(9, collection.size());
		assertFalse(collection.remove(11));
		assertEquals(9, collection.size());
	}
	
	@Test
	void removeIfTest(){
		assertFalse(collection.removeIf(PREDICATE1));
		assertEquals(10, collection.size());
		assertTrue(collection.removeIf(PREDICATE1.negate()));
		assertEquals(0, collection.size());
	}
	
	@Test
	void containsTest() {
		assertTrue(collection.contains(5));
		assertFalse(collection.contains(11));
	}
	
	@Test
	void sizeTest() {
		assertEquals(10, collection.size());
	}
	
	@Test
	void toArrayTest() {
		Integer[] expected1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		assertArrayEquals(expected1, collection.toArray(expected1));
		assertTrue(expected1 == collection.toArray(expected1));
		Integer [] expected2 = new Integer[100];
		assertTrue(expected2 == collection.toArray(expected2));
		assertArrayEquals(expected1, Arrays.copyOf(expected2, collection.size()));
	}

}
