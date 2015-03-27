package agent.concept;

import static conceptualspace.Point.mean;
import static conceptualspace.Point.standardDeviation;
import static java.lang.Math.PI;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import agent.Assertion;
import conceptualspace.Point;

public class MaxLikelihoodConcept implements Concept {

	private final List<Point> points = new ArrayList<>();
	private final Point mean;
	private final Point stdDev;

	public MaxLikelihoodConcept(final List<Point> points) {
		this.points.addAll(points);
		mean = mean(points);
		stdDev = standardDeviation(points);
	}

	public MaxLikelihoodConcept(final Point...points) {
		this(asList(points));
	}

	@Override
	public MaxLikelihoodConcept update(final Assertion assertion) {
		List<Point> points = new ArrayList<>(this.points);
		points.add(0, assertion.object.observation());
		final int maxCapacity = 20;
		if (points.size() > maxCapacity) {
			points = points.subList(0, maxCapacity);
		}
		return new MaxLikelihoodConcept(points);
	}

	@Override
	public double appropriatenessOf(final Point point) {
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
