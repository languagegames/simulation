package population;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class TestStaticPairer {

    @Test
    public void createsPairingOrdersFromProvidedList() {
        final StaticPairer pairer = new StaticPairer(asList(0, 1, 3, 2, 2, 3, 1, 0));

        assertThat(pairer.pairingOrder(4), contains(0, 1, 3, 2));
        assertThat(pairer.pairingOrder(4), contains(2, 3, 1, 0));
    }

}
