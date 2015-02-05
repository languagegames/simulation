package agent;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import conceptualspace.Concept;
import conceptualspace.Point;
import conceptualspace.SimpleMaterial;
import experiment.Assertion;

public class TestBasicAgent {

	@Test
	public void learnsByUpdatingAppropriateConcept() {
		final BasicAgent agent = new BasicAgent(
				new Concept(new Point(0.42), 0.42),
				new Concept(new Point(0.5), 0.5));

		final Agent updatedAgent = new BasicAgent(
				new Concept(new Point(0.42), 0.42),
				new Concept(new Point(0.74), 0.8));

		assertThat(agent.learn(new Assertion(new SimpleMaterial(new Point(0.9)), 1, 0.8)),
				equalTo(updatedAgent));
	}

}
