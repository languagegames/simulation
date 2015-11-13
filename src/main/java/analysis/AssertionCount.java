package analysis;

import java.util.ArrayList;
import java.util.List;

public class AssertionCount {

	private final List<Integer> counts = new ArrayList<>();
	private final List<Integer> labels = new ArrayList<>();

	public AssertionCount(final List<Integer> counts, final List<Integer> labels) {
		this.counts.addAll(counts);
		this.labels.addAll(labels);
	}

	public AssertionCount() {}

	public int total() {
		int total = 0;
		for (final Integer count : counts) {
			total += count;
		}
		return total;
	}

	public int max() {
		int max = 0;
		for (final Integer count : counts) {
			max = Math.max(count, max);
		}
		return max;
	}

	public AssertionCount add(final int label) {
		if (labels.contains(label)) {
			final int index = labels.indexOf(label);
			counts.set(index, counts.get(index)+1);
		} else {
			labels.add(label);
			counts.add(1);
		}
		return new AssertionCount(counts, labels);
	}

}
