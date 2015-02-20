package languagegames.analysis;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class TimeSeries {

	private final List<Double> values = new ArrayList<>();

	public TimeSeries(final List<Double> values) {
		this.values.addAll(values);
	}

	public TimeSeries(final Double...values) {
		this(asList(values));
	}

	public static TimeSeries standardDeviation(final List<TimeSeries> results) {
		final TimeSeries mean = mean(results);
		final List<Double> standardDeviation = new ArrayList<>();
		final int numTimeSteps = results.get(0).values.size();
		for (int timeStep=0; timeStep<numTimeSteps; timeStep++) {
			standardDeviation.add(standardDeviation(results, mean, timeStep));
		}
		return new TimeSeries(standardDeviation);
	}

	private static double standardDeviation(
			final List<TimeSeries> results, final TimeSeries mean, final int dimension) {
		double sumOfSquares = 0;
		for (final TimeSeries timeSeries : results) {
			sumOfSquares += pow(timeSeries.values.get(dimension) - mean.values.get(dimension), 2);
		}
		return sqrt(sumOfSquares / (results.size()-1));
	}

	public static TimeSeries mean(final List<TimeSeries> results) {
		final List<Double> mean = new ArrayList<>();
		final int numTimeSteps = results.get(0).values.size();
		for (int timeStep=0; timeStep<numTimeSteps; timeStep++) {
			mean.add(mean(results, timeStep));
		}
		return new TimeSeries(mean);
	}

	private static double mean(final List<TimeSeries> results, final int timeStep) {
		double sum = 0;
		for (final TimeSeries timeSeries : results) {
			sum += timeSeries.values.get(timeStep);
		}
		return sum / results.size();
	}

	@Override
	public String toString() {
		return values.toString();
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
