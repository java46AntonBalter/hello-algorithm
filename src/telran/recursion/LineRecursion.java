package telran.recursion;

public class LineRecursion {
public static long factorial(int n) {
	
	if(n == 0) {
		return 1;
	}
	return  n * factorial(n - 1);
}
/**
 * 
 * @param a either negative or positive
 * @param b positive
 * @return a ^ b
 */
public static long pow(int a, int b) {
	if (b == 0) {
		return 1;
	}
	//TODO 
	//no * / allowed
	//no cycles
	//no standard Math methods
	return a * pow(a, b - 1);
}
/**
 * 
 * @param x
 * @return x ^ 2
 */
public static int square(int x) {
	//TODO
	//no cycles
	//no * / allowed
	//no call any additional function
	//no static fields
	return 0;
}
/**
 * 
 * @param ar - array of in teger numbers
 * @return sum of all numbers from the given array
 */
public static int sum(int ar[]) {
	//TODO
	//no cycles
	
	return sum(0, ar);
}
private static int sum(int firstIndex, int[] ar) {
	if (firstIndex == ar.length) {
		return 0;
	}
	return ar[firstIndex] + sum(firstIndex + 1, ar);
	
}
}
