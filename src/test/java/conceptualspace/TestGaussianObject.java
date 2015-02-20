package conceptualspace;

import static conceptualspace.Point.mean;
import static conceptualspace.Point.standardDeviation;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import utility.FakeRandom;

public class TestGaussianObject {

	@Test
	public void createsListOfObjectsFromListOfPoints() {
		final List<Point> points = points(0.2, 0.3, 0.8, 0.5);

		final PerceptualObject object0 = new GaussianObject(
				mean(points(0.2, 0.3)).coordinates(), standardDeviation(points(0.2, 0.3)).coordinates());
		final PerceptualObject object1 = new GaussianObject(
				mean(points(0.8, 0.5)).coordinates(), standardDeviation(points(0.8, 0.5)).coordinates());

		assertThat(GaussianObject.makeListFrom(points, 2), contains(object0, object1));
	}

	private List<Point> points(final double...values) {
		final List<Point> points = new ArrayList<>();
		for (int i=0; i<values.length; i++) {
			points.add(new Point(values[i]));
		}
		return points;
	}

	@Test
	public void doesNotProduceObservationsOutsideConceptualSpace() {
		final double gaussianValue0 = 0.8, gaussianValue1 = 0.2;
		final double mean = 0.5, stdDev = 0.8;
		final GaussianObject object = new GaussianObject(asList(mean), asList(stdDev),
				new FakeRandom(gaussianValue0, gaussianValue1));

		assertThat(object.observation(), equalTo(new Point(mean + gaussianValue1*stdDev)));
	}

	@Test
	public void drawsObservationsFromGaussianDistribution() {
		final double gaussianValue0 = 0.2, gaussianValue1 = 0.5;
		final double mean0 = 0.5, mean1 = 0.7;
		final double stdDev0 = 0.8, stdDev1 = 0.4;
		final GaussianObject object = new GaussianObject(
				asList(mean0, mean1),
				asList(stdDev0, stdDev1),
				new FakeRandom(gaussianValue0, gaussianValue1));

		assertThat(object.observation(), equalTo(new Point(
				mean0 + gaussianValue0*stdDev0,
				mean1 + gaussianValue1*stdDev1)));
	}

}
