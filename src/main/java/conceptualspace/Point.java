package conceptualspace;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import utility.matrix.Matrix;

public class Point {

	private final List<Double> coordinates = new ArrayList<>();

	public Point(final List<Double> coordinates) {
		this.coordinates.addAll(coordinates);
	}

	public Point(final Double...coordinates) {
		this(asList(coordinates));
	}

	public static Point randomPoint(final int numDimensions, final Random random) {
		final List<Double> coordinates = new ArrayList<>();
		for (int i=0; i<numDimensions; i++) {
			coordinates.add(random.nextDouble());
		}
		return new Point(coordinates);
	}

	public Matrix asMatrix() {
		final double[][] vals = new double[1][coordinates.size()];
		for (int i=0; i<coordinates.size(); i++) {
			vals[0][i] = coordinates.get(i);
		}
		return new Matrix(vals);
	}

	public List<Double> coordinates() {
		return coordinates;
	}

	public int numDimensions() {
		return coordinates.size();
	}

	public double get(final int index) {
		return coordinates.get(index);
	}

	public static Point standardDeviation(final List<Point> points) {
		final Point mean = mean(points);
		final List<Double> coordinates = new ArrayList<>();
		for (int dimension=0; dimension<mean.numDimensions(); dimension++) {
			final double standardDeviation = standardDeviation(points, mean, dimension);
			coordinates.add(standardDeviation);
		}
		return new Point(coordinates);
	}

	private static double standardDeviation(final List<Point> points, final Point mean, final int dimension) {
		double sumOfSquares = 0;
		for (final Point point : points) {
			sumOfSquares += pow(point.get(dimension) - mean.get(dimension), 2);
		}
		return sqrt(sumOfSquares / (points.size()-1));
	}

	public static Point mean(final List<Point> points) {
		Point sum = points.get(0).minus(points.get(0));
		for (final Point point : points) {
			sum = sum.plus(point);
		}
		return sum.times(1.0/points.size());
	}

	public Point minus(final Point other) {
		if (numDimensions() != other.numDimensions()) {
			throw new IllegalArgumentException("Points must have same dimensionality");
		}
		return plus(other.times(-1));
	}

	public Point times(final double k) {
		final List<Double> resultCoordinates = new ArrayList<>();
		for (final Double coordinate : coordinates) {
			resultCoordinates.add(coordinate * k);
		}
		return new Point(resultCoordinates);
	}

	public Point plus(final Point other) {
		if (numDimensions() != other.numDimensions()) {
			throw new IllegalArgumentException("Points must have same dimensionality");
		}
		final List<Double> resultCoordinates = new ArrayList<>();
		for (int i=0; i<numDimensions(); i++) {
			resultCoordinates.add(get(i) + other.get(i));
		}
		return new Point(resultCoordinates);
	}

	public double norm() {
		double sumOfSquares = 0;
		for (final Double coordinate : coordinates) {
			sumOfSquares += pow(coordinate, 2);
		}
		return sqrt(sumOfSquares);
	}

	@Override
	public String toString() {
		return coordinates.toString();
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
