package agent;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import conceptualspace.Material;
import conceptualspace.Point;
import conceptualspace.SimpleMaterial;
import experiment.Assertion;
import experiment.ExperimentData;

public class TestOracleAgent {

	private final Material material0 = someMaterial(0.42);
	private final Material material1 = someMaterial(0.43);
	private final Material material2 = someMaterial(0.44);
	private final ExperimentData data = new ExperimentData(
			asList(material0, material1, material2), 0.42);
	private final double someWeight = 0.42;

	@Test
	public void classifiesAccordingToPredefinedLabelMapping() {
		final OracleAgent agent = new OracleAgent(new LabelMapping(data, asList(1, 2, 0)), someWeight);

		assertThat(agent.classify(material0), equalTo(new Assertion(material0, 1, someWeight)));
		assertThat(agent.classify(material1), equalTo(new Assertion(material1, 2, someWeight)));
		assertThat(agent.classify(material2), equalTo(new Assertion(material2, 0, someWeight)));
	}

	private Material someMaterial(final double d) {
		return new SimpleMaterial(new Point(d));
	}

}
