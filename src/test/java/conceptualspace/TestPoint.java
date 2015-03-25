package conceptualspace;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import jama.Matrix;

import org.junit.Test;

public class TestPoint {

	@Test
	public void createsMatrixOfValues() {
		final Point a = new Point(0.5, 0.1);

		assertThat(a.asMatrix(), equalTo(new Matrix(new double[][] {{0.5, 0.1}})));
	}

	@Test
	public void calculatesStandardDeviationOfListOfPoints() {
		final Point a = new Point(0.5, 0.1);
		final Point b = new Point(0.3, 0.6);

		assertThat(Point.standardDeviation(asList(a, b)),
				equalTo(new Point(0.14142135623730953, 0.35355339059327373)));
	}

	@Test
	public void calculatesMeanOfListOfPoints() {
		final Point a = new Point(0.5, 0.1);
		final Point b = new Point(0.3, 0.6);

		assertThat(Point.mean(asList(a, b)), equalTo(new Point(0.4, 0.35)));
	}

	@Test
	public void testTimesOperation() {
		final Point a = new Point(0.5, 0.9);
		final double k = 1.5;

		assertThat(a.times(k), equalTo(new Point(0.75, 1.35)));
	}

	@Test
	public void testPlusOperation() {
		final Point a = new Point(0.5, 0.1);
		final Point b = new Point(0.3, 0.6);

		assertThat(a.plus(b), equalTo(new Point(0.8, 0.7)));
	}

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
