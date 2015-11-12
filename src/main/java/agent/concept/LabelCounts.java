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

}
