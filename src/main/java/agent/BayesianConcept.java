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

public class BayesianConcept {

	private final List<Point> points = new ArrayList<>();
	private final Point mean;
	private final Point stdDev;

	public BayesianConcept(final List<Point> points) {
		this.points.addAll(points);
		mean = mean(points);
		stdDev = standardDeviation(points);
	}

	public BayesianConcept(final Point...points) {
		this(asList(points));
	}

	public static BayesianConcept randomConcept(final int numDimensions, final Random random) {
		final List<Point> points = new ArrayList<>();
		points.add(randomPoint(numDimensions, random));
		points.add(randomPoint(numDimensions, random));
		return new BayesianConcept(points);
	}

	private static Point randomPoint(final int numDimensions, final Random random) {
		final List<Double> coordinates = new ArrayList<>();
		for (int i=0; i<numDimensions; i++) {
			coordinates.add(random.nextDouble());
		}
		return new Point(coordinates);
	}

	public BayesianConcept update(final Point point) {
		final List<Point> points = new ArrayList<>(this.points);
		points.add(point);
		return new BayesianConcept(points);
	}

	public double likelihoodGiven(final Point point) {
		double result = 1;
		for (int i=0; i<point.numDimensions(); i++) {
			result *= pdf(point.get(i), mean.get(i), stdDev.get(i));
		}
		return result * points.size();
	}

	private double pdf(final double x, final double mu, final double sigma) {
		return 1/(sigma*sqrt(2*PI))*exp(-pow(x-mu,2)/(2*pow(sigma,2)));
	}

	@Override
	public String toString() {
		return "Bayesian concept with mean " + mean + ", standard deviation" + stdDev;
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
