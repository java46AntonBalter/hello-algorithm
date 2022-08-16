package telran.recursion;

public class LineRecursion {
	public static long factorial(int n) {

		if (n == 0) {
			return 1;
		}
		return n * factorial(n - 1);
	}

	public static long pow(int a, int b) throws Exception {
		if (b < 0) {
			throw new IllegalArgumentException();
		}

		if (a >= 0) {
			return powPositiveValue(a, b);
		}

		a = -a;
		long res = powPositiveValue(a, b);
		return b % 2 == 0 ? res : -res;
	}

	private static long powPositiveValue(int a, int b) {

		if (b == 0) {
			return 1;
		}
		if (a == 0) {
			return 0;
		}
		if (b == 1) {
			return a;
		}
		return multiply(a, powPositiveValue(a, b - 1));
	}

	private static long multiply(int a, long b) {
		if (b != 0) {
			return (a + multiply(a, b - 1));
		} else
			return 0;
	}

	/**
	 * 
	 * @param x
	 * @return x ^ 2
	 */
	public static int square(int x) {
		if (x == 0) {
			return 0;
		}
		if (x < 0) {
			x = -x;
		}
		
		return x + x - 1 + square(x-1);
	}

	/**
	 * 
	 * @param ar - array of integer numbers
	 * @return sum of all numbers from the given array
	 */
	public static int sum(int ar[]) {
		// TODO
		// no cycles

		return sum(0, ar);
	}

	private static int sum(int firstIndex, int[] ar) {
		if (firstIndex == ar.length) {
			return 0;
		}
		return ar[firstIndex] + sum(firstIndex + 1, ar);

	}
}
