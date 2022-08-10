package telran.util.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;


import telran.util.Collection;
import telran.util.HashSet;



public class HashSetTests extends SetTests {

	@Override
	protected Collection<Integer> createCollection() {
		
		return new HashSet<>();
	}
	
	@Override
	@Test
	void toArrayTest() {
		Integer expected1[] = { -5, 10 , 13, 15, 20, 40 };
		Integer[] res = collection.toArray(new Integer[0]);
		Arrays.sort(res);
		assertArrayEquals(expected1, res);
	}

}
