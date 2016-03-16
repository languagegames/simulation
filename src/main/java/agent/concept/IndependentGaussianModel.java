package agent.concept;

import conceptualspace.Point;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

import static conceptualspace.Point.mean;
import static conceptualspace.Point.standardDeviation;
import static java.lang.Math.*;
import static java.util.Arrays.asList;

public class IndependentGaussianModel implements LikelihoodModel {

    private final List<Point> points = new ArrayList<Point>();
    private final Point mean;
    private final Point stdDev;

    public IndependentGaussianModel(final List<Point> points) {
        this.points.addAll(points);
        mean = mean(points);
        stdDev = standardDeviation(points);
    }

    public IndependentGaussianModel(final Point... points) {
        this(asList(points));
    }

    public IndependentGaussianModel update(final Point point) {
        List<Point> points = new ArrayList<Point>(this.points);
        points.add(0, point);
        final int maxCapacity = 20;
        if (points.size() > maxCapacity) {
            points = points.subList(0, maxCapacity);
        }
        return new IndependentGaussianModel(points);
    }

    public double likelihood(final Point point) {
        double result = 1;
        for (int i = 0; i < point.numDimensions(); i++) {
            result *= pdf(point.get(i), mean.get(i), stdDev.get(i));
        }
        return result;
    }

    private double pdf(final double x, final double mu, final double sigma) {
        return 1 / (sigma * sqrt(2 * PI)) * exp(-pow(x - mu, 2) / (2 * pow(sigma, 2)));
    }

    @Override
    public String toString() {
        return "Independent Gaussian model with mean " + mean + ", standard deviation" + stdDev;
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
