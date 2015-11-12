package agent.concept;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class LabelCount {

	private final List<Integer> values = new ArrayList<>();

	public LabelCount(final int...values) {
		for (final int value : values) {
			this.values.add(value);
		}
	}

	public LabelCount(final List<Integer> values) {
		this.values.addAll(values);
	}

	public static LabelCount sum(final List<LabelCount> counts) {
		final List<Integer> values = new ArrayList<>();
		final int numValues = counts.get(0).values.size();
		for (int i=0; i<numValues; i++) {
			values.add(sumValues(counts, i));
		}
		return new LabelCount(values);
	}

	private static int sumValues(final List<LabelCount> counts, int i) {
		int sum = 0;
		for (final LabelCount count : counts) {
			sum += count.values.get(i);
		}
		return sum;
	}

	public List<Double> frequencies() {
		final List<Double> frequencies = new ArrayList<>();
		for (final Integer value : values) {
			final double frequency = value.doubleValue()/sum();
			frequencies.add(frequency);
		}
		return frequencies;
	}

	private int sum() {
		int sum = 0;
		for (final Integer value : values) {
			sum+=value;
		}
		return sum;
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return "Label counts:" + values.toString();
	}

}
