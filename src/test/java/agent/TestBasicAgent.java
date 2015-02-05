package agent;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import conceptualspace.Material;
import conceptualspace.Point;
import conceptualspace.SimpleMaterial;
import experiment.Assertion;

public class TestBasicAgent {

	private final double someWeight = 0.42;

	@Test
	public void classifiesAccordingToMostAppropriateConcept() {
		final BasicAgent agent = new BasicAgent(
				someWeight,
				new Concept(new Point(0.2), 0.7),
				new Concept(new Point(0.5), 0.5));

		final Material material0 = new SimpleMaterial(new Point(0.35));
		final Material material1 = new SimpleMaterial(new Point(0.4));

		assertThat(agent.classify(material0), equalTo(new Assertion(material0, 0, someWeight)));
		assertThat(agent.classify(material1), equalTo(new Assertion(material1, 1, someWeight)));
	}

	@Test
	public void learnsByUpdatingAppropriateConcept() {
		final BasicAgent agent = new BasicAgent(
				someWeight,
				new Concept(new Point(0.42), 0.42),
				new Concept(new Point(0.5), 0.5));

		final Agent updatedAgent = new BasicAgent(
				someWeight,
				new Concept(new Point(0.42), 0.42),
				new Concept(new Point(0.74), 0.8));

		assertThat(agent.learn(new Assertion(new SimpleMaterial(new Point(0.9)), 1, 0.8)),
				equalTo(updatedAgent));
	}

}
