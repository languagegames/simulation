package population;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import org.junit.Test;

import population.RandomPairer;

public class TestRandomPairer {

	@Test
	public void returnedListContainsCorrectIntegers() {
		final RandomPairer pairer = new RandomPairer();
		assertThat(pairer.pairingOrder(6), containsInAnyOrder(0,1,2,3,4,5));
	}

}
