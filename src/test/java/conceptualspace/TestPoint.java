package conceptualspace;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class TestPoint {

	@Test
	public void testNormOperation() {
		final Point a = new Point(0.3, 0.4);

		assertThat(a.norm(), equalTo(0.5));
	}

	@Test
	public void testMinusOperation() {
		final Point a = new Point(0.5, 0.1);
		final Point b = new Point(0.3, 0.7);

		assertThat(a.minus(b), equalTo(new Point(0.2, -0.6)));
	}

}
