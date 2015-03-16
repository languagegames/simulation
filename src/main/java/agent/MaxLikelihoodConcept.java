package agent;

import static conceptualspace.Point.mean;
import static conceptualspace.Point.standardDeviation;
import static java.lang.Math.PI;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.Point;

public class MaxLikelihoodConcept {

	private final List<Point> points = new ArrayList<>();
	private final int numObservations;
	private final Point mean;
	private final Point stdDev;

	public MaxLikelihoodConcept(final List<Point> points, final int numObservations) {
		this.points.addAll(points);
		this.numObservations = numObservations;
		mean = mean(points);
		stdDev = standardDeviation(points);

	}

	public MaxLikelihoodConcept(final List<Point> points) {
		this(points, points.size());
	}

	public MaxLikelihoodConcept(final Point...points) {
		this(asList(points));
	}

	public static MaxLikelihoodConcept randomConcept(final int numDimensions, final Random random) {
		final List<Point> points = new ArrayList<>();
		points.add(randomPoint(numDimensions, random));
		points.add(randomPoint(numDimensions, random));
		return new MaxLikelihoodConcept(points);
	}

	private static Point randomPoint(final int numDimensions, final Random random) {
		final List<Double> coordinates = new ArrayList<>();
		for (int i=0; i<numDimensions; i++) {
			coordinates.add(random.nextDouble());
		}
		return new Point(coordinates);
	}

	public MaxLikelihoodConcept update(final Point point) {
		List<Point> points = new ArrayList<>(this.points);
		points.add(0, point);
		final int maxCapacity = 20;
		if (points.size() > maxCapacity) {
			points = points.subList(0, maxCapacity);
		}
		return new MaxLikelihoodConcept(points, numObservations+1);
	}

	public double likelihoodGiven(final Point point) {
		double result = 1;
		for (int i=0; i<point.numDimensions(); i++) {
			result *= pdf(point.get(i), mean.get(i), stdDev.get(i));
		}
		return result;
	}

	private double pdf(final double x, final double mu, final double sigma) {
		return 1/(sigma*sqrt(2*PI))*exp(-pow(x-mu,2)/(2*pow(sigma,2)));
	}

	@Override
	public String toString() {
		return "ML concept with mean " + mean + ", standard deviation" + stdDev;
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
