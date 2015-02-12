package languagegames;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import org.junit.Test;

public class TestStaticPairer {

	@Test
	public void createsPairingOrdersFromProvidedList() {
		final StaticPairer pairer = new StaticPairer(asList(0, 1, 4, 2, 7, 3, 3, 6));

		assertThat(pairer.pairingOrder(4), contains(0, 1, 4, 2));
		assertThat(pairer.pairingOrder(4), contains(7, 3, 3, 6));
	}

}
