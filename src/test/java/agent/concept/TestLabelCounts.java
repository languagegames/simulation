package agent.concept;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestLabelCounts {

	@Test
	public void sumsTogetherListOfLabelCounts() {
		final List<LabelCount> counts = new ArrayList<>();
		counts.add(new LabelCount(1,2,3));
		counts.add(new LabelCount(2,3,1));
		counts.add(new LabelCount(3,2,1));

		assertThat(LabelCount.sum(counts), equalTo(new LabelCount(6,7,5)));
	}

}
