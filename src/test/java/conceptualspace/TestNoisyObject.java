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

public class TestNoisyObject {

	@Test
	public void createsListOfObjectsWithSpecifiedDimensionality() {
		final List<Point> points = asList(point(0.2, 0.42), point(0.3, 0.42), point(0.8, 0.42), point(0.5, 0.42));

		final PerceptualObject object0 = new NoisyObject(
				mean(points(0.2, 0.3)).coordinates(), standardDeviation(points(0.2, 0.3)).coordinates());
		final PerceptualObject object1 = new NoisyObject(
				mean(points(0.8, 0.5)).coordinates(), standardDeviation(points(0.8, 0.5)).coordinates());

		assertThat(NoisyObject.makeListFrom(points, 2, 1), contains(object0, object1));
	}

	@Test
	public void createsListOfObjectsFromListOfPoints() {
		final List<Point> points = asList(point(0.2), point(0.3), point(0.8), point(0.5));

		final List<Point> object0Points = points.subList(0, 2);
		final List<Point> object1Points = points.subList(2, 4);

		final PerceptualObject object0 = new NoisyObject(
				mean(object0Points).coordinates(), standardDeviation(object0Points).coordinates());
		final PerceptualObject object1 = new NoisyObject(
				mean(object1Points).coordinates(), standardDeviation(object1Points).coordinates());

		assertThat(NoisyObject.makeListFrom(points, 2, 1), contains(object0, object1));
	}

	private Point point(final Double...values) {
		return new Point(values);
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
		final NoisyObject object = new NoisyObject(asList(mean), asList(stdDev),
				new FakeRandom(gaussianValue0, gaussianValue1));

		assertThat(object.observation(), equalTo(new Point(mean + gaussianValue1*stdDev)));
	}

	@Test
	public void drawsObservationsFromGaussianDistribution() {
		final double gaussianValue0 = 0.2, gaussianValue1 = 0.5;
		final double mean0 = 0.5, mean1 = 0.7;
		final double stdDev0 = 0.8, stdDev1 = 0.4;
		final NoisyObject object = new NoisyObject(
				asList(mean0, mean1),
				asList(stdDev0, stdDev1),
				new FakeRandom(gaussianValue0, gaussianValue1));

		assertThat(object.observation(), equalTo(new Point(
				mean0 + gaussianValue0*stdDev0,
				mean1 + gaussianValue1*stdDev1)));
	}

}
