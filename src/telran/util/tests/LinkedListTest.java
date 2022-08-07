package telran.util.tests;


import static org.junit.Assert.assertArrayEquals;

import org.junit.jupiter.api.Test;

import telran.util.Collection;
import telran.util.LinkedList;

public class LinkedListTest extends ListTests {

	@Override
	protected Collection<Integer> createCollection() {
		
		return new LinkedList<>();
	}
	
	@Test
	void reverseTest() {
		LinkedList<Integer> linkedList = (LinkedList<Integer>)collection;;
		Integer expected1[] = { 15, 40, 20, 13, -5, 10 };
		linkedList.reverse();
		Integer[] res = {};		 
		assertArrayEquals(expected1, linkedList.toArray(res));
	}
	
	
}
