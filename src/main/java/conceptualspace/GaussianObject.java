package conceptualspace;

import static conceptualspace.Point.mean;
import static conceptualspace.Point.standardDeviation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class GaussianObject implements PerceptualObject {

	private final List<Double> mean = new ArrayList<>();
	private final List<Double> standardDeviation = new ArrayList<>();
	private final Random random;

	public GaussianObject(final List<Double> mean, final List<Double> standardDeviation, final Random random) {
		this.mean.addAll(mean);
		this.standardDeviation.addAll(standardDeviation);
		this.random = random;
	}

	public GaussianObject(final List<Double> mean, final List<Double> standardDeviation) {
		this(mean, standardDeviation, new Random());
	}

	public static List<PerceptualObject> makeListFrom(
			final List<Point> points, final int pointsPerObject, final int numDimensions) {
		final int numObjects = points.size() / pointsPerObject;
		final List<PerceptualObject> objects = new ArrayList<>();
		for (int i=0; i<numObjects; i++) {
			final List<Point> objectPoints = points.subList(i*pointsPerObject, i*pointsPerObject+pointsPerObject);
			final GaussianObject object = new GaussianObject(
					coordinates(mean(objectPoints), numDimensions),
					coordinates(standardDeviation(objectPoints), numDimensions));
			objects.add(object);
		}
		return objects;
	}

	private static List<Double> coordinates(final Point point, final int numDimensions) {
		return point.coordinates().subList(0, numDimensions);
	}

	@Override
	public Point observation() {
		final List<Double> coordinates = new ArrayList<>();
		for (int i=0; i<mean.size(); i++) {
			coordinates.add(nextCoordinate(mean.get(i), standardDeviation.get(i)));
		}
		return new Point(coordinates);
	}

	private double nextCoordinate(final double mean, final double standardDeviation) {
		double coordinate = mean + standardDeviation*random.nextGaussian();
		while (coordinate > 1) {
			coordinate = mean + standardDeviation*random.nextGaussian();
		}
		return coordinate;
	}

	@Override
	public String toString() {
		return "Gaussian object with mean " + mean.toString() + " and SD " + standardDeviation.toString();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof GaussianObject) {
			final GaussianObject other = (GaussianObject) obj;
			return new EqualsBuilder()
			.append(mean, other.mean)
			.append(standardDeviation, other.standardDeviation)
			.isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(mean)
		.append(standardDeviation)
		.toHashCode();
	}

}
