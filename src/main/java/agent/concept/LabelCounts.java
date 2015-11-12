package agent.concept;

import java.util.ArrayList;
import java.util.List;

public class LabelCounts {

	private final List<Integer> values = new ArrayList<>();

	public LabelCounts(final int...values) {
		for (final int value : values) {
			this.values.add(value);
		}
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

}
