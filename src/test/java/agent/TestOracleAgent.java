package agent;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import classifier.ExperimentData;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestOracleAgent {

	private final PerceptualObject material0 = someMaterial(0.42);
	private final PerceptualObject material1 = someMaterial(0.43);
	private final PerceptualObject material2 = someMaterial(0.44);
	private final ExperimentData data = new ExperimentData(
			asList(material0, material1, material2), 0.42);
	private final double someWeight = 0.42;

	@Test
	public void classifiesAccordingToPredefinedLabelMapping() {
		final OracleAgent agent = new OracleAgent(new LabelMapping(data, asList(1, 2, 0)), someWeight);

		assertThat(agent.assertion(material0), equalTo(new Assertion(material0, 1, someWeight)));
		assertThat(agent.assertion(material1), equalTo(new Assertion(material1, 2, someWeight)));
		assertThat(agent.assertion(material2), equalTo(new Assertion(material2, 0, someWeight)));
	}

	private PerceptualObject someMaterial(final double d) {
		return new SimpleObject(new Point(d));
	}

}
