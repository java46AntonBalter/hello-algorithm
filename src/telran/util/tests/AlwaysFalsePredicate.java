package telran.util.tests;

import java.util.function.Predicate;

public class AlwaysFalsePredicate implements Predicate<Integer>{

	@Override
	public boolean test(Integer t) {
		return false;
	}

}
