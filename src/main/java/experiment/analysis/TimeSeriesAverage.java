package experiment.analysis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class TimeSeriesAverage {

	private final List<Integer> timeSteps = new ArrayList<>();
	private final List<Double> mean = new ArrayList<>();
	private final List<Double> standardDeviation = new ArrayList<>();

	public TimeSeriesAverage(final List<Integer> timeSteps, final List<Double> mean, final List<Double> standardDeviation) {
		this.timeSteps.addAll(timeSteps);
		this.mean.addAll(mean);
		this.standardDeviation.addAll(standardDeviation);
	}

	@Override
	public String toString() {
		final StringBuilder string = new StringBuilder();
		for (int t = 0; t < timeSteps.size(); t++) {
			string.append(timeSteps.get(t));
			string.append(", " + mean.get(t));
			string.append(", " + standardDeviation.get(t) + "\n");
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
