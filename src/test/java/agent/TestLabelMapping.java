package agent;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Test;

import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestLabelMapping {

	@Test
	public void matchesObservationToSimpleObject() {
		final PerceptualObject observation0 = new SimpleObject(new Point(0.0));
		final PerceptualObject observation1 = new SimpleObject(new Point(0.1));
		final PerceptualObject observation2 = new SimpleObject(new Point(0.2));

		final List<PerceptualObject> data = asList(observation0, observation1, observation2);
		final List<Integer> labels = asList(0,1,2);

		final Point toMatch = new Point(0.1);

		final LabelMapping labelMapping = new LabelMapping(data, labels);

		assertThat(labelMapping.label(toMatch), equalTo(1));
	}

}
