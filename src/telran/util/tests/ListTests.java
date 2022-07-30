package telran.util.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import telran.util.List;

abstract class ListTests extends CollectionTests {

	@Test
	void addIndexTest() {
		List<Integer> list = (List<Integer>) collection;
		assertTrue(list.add(0, 11));
		assertEquals(11, list.get(0));
		fillArray(LARGE_ARRAY);
		for(int i: LARGE_ARRAY) {
			assertTrue(list.add(2, i));
		}
		assertEquals(111, list.size());
		assertTrue(list.add(list.size(), 12));
		assertEquals(12, list.get(list.size() - 1));
		assertFalse(list.add(-1, 13));
		assertEquals(112, list.size());
	}
	
	@Test
	void removeIndexTest() {
		List<Integer> list = (List<Integer>) collection;
		assertEquals(0, list.remove(0));
		assertEquals(9, list.size());
		assertEquals(1, list.get(0));
		assertEquals(3, list.remove(2));
		assertEquals(8, list.size());
		assertEquals(4, list.get(2));
		assertEquals(9, list.remove(list.size() - 1));
		assertEquals(7, list.size());
		assertEquals(8, list.get(list.size() - 1));
		assertNull(list.remove(-1));
	}
	
	@Test
	void indexOfTest() {
		List<Integer> list = (List<Integer>) collection;
		list.add(5);
		assertEquals(5, list.indexOf(5));
	}
	
	@Test
	void lastIndexOfTest() {
		List<Integer> list = (List<Integer>) collection;
		list.add(5);
		assertEquals(10, list.lastIndexOf(5));
	}
	
	@Test
	void getTest() {
		List<Integer> list = (List<Integer>) collection;
		assertEquals(0, list.get(0));
		assertNull(list.get(-1));
	}

}
