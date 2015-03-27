package experiment.analysis;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class TimeSeries {

	private final List<Double> values = new ArrayList<>();
	private final List<Integer> timeSteps = new ArrayList<>();

	public TimeSeries(final List<Double> values, final List<Integer> timeSteps) {
		this.values.addAll(values);
		this.timeSteps.addAll(timeSteps);
	}

	public TimeSeries(final List<Double> values) {
		int t = 0;
		for (final Double value : values) {
			this.values.add(value);
			timeSteps.add(t++);
		}
	}

	public TimeSeries(final Double...values) {
		this(asList(values));
	}

	public static TimeSeriesAverage average(final List<TimeSeries> results) {
		return new TimeSeriesAverage(
				results.get(0).timeSteps,
				mean(results),
				standardDeviation(results));
	}

	private static List<Double> standardDeviation(final List<TimeSeries> results) {
		final List<Double> mean = mean(results);
		final List<Double> standardDeviation = new ArrayList<>();
		final int numTimeSteps = results.get(0).values.size();
		for (int timeStep=0; timeStep<numTimeSteps; timeStep++) {
			standardDeviation.add(standardDeviation(results, mean, timeStep));
		}
		return standardDeviation;
	}

	private static double standardDeviation(
			final List<TimeSeries> results, final List<Double> mean, final int dimension) {
		double sumOfSquares = 0;
		for (final TimeSeries timeSeries : results) {
			sumOfSquares += pow(timeSeries.values.get(dimension) - mean.get(dimension), 2);
		}
		return sqrt(sumOfSquares / (results.size()-1));
	}

	private static List<Double> mean(final List<TimeSeries> results) {
		final List<Double> mean = new ArrayList<>();
		final int numTimeSteps = results.get(0).values.size();
		for (int timeStep=0; timeStep<numTimeSteps; timeStep++) {
			mean.add(mean(results, timeStep));
		}
		return mean;
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
		final StringBuilder string = new StringBuilder();
		for (int t = 0; t < values.size(); t++) {
			string.append(timeSteps.get(t));
			string.append(", " + values.get(t) + "\n");
		}
		return string.toString();
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
