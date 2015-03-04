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

}
