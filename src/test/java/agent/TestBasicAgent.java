package agent;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestBasicAgent {

	private final double someWeight = 0.42;

	@Test
	public void guessesMostAppropriateObjectGivenAssertionConcept() {
		final PerceptualObject mostAppropriate = object(0.6);
		final PerceptualObject other = object(0.5);
		final FuzzyConcept concept0 = new FuzzyConcept(new Point(0.7), 0.9);
		final BasicAgent agent = agent().withConcepts(concept0).withWeight(someWeight).build();

		final List<PerceptualObject> guessingSet = new ArrayList<>();
		guessingSet.addAll(asList(other, mostAppropriate));

		assertThat(agent.guess(guessingSet, new Assertion(mostAppropriate, 0, someWeight)), equalTo(1));
	}

	@Test
	public void incrementsWeight() {
		final BasicAgent agent = agent().withWeight(0.5).build();
		final Agent newAgent = agent.incrementWeight(0.1);
		assertThat(newAgent.weight(), equalTo(0.6));
	}

	@Test
	public void assertsAccordingToMostAppropriateConcept() {
		final BasicAgent agent = agent()
				.withConcepts(
						new FuzzyConcept(new Point(0.2), 0.7),
						new FuzzyConcept(new Point(0.5), 0.5))
				.withWeight(someWeight)
				.build();

		final PerceptualObject material0 = new SimpleObject(new Point(0.35));
		final PerceptualObject material1 = new SimpleObject(new Point(0.4));

		assertThat(agent.assertion(material0), equalTo(new Assertion(material0, 0, someWeight)));
		assertThat(agent.assertion(material1), equalTo(new Assertion(material1, 1, someWeight)));
	}

	@Test
	public void learnsByUpdatingAppropriateConcept() {
		final BasicAgent agent = agent()
				.withConcepts(
						new FuzzyConcept(new Point(0.42), 0.42),
						new FuzzyConcept(new Point(0.5), 0.5))
				.withWeight(someWeight)
				.build();

		final Agent updatedAgent = agent()
				.withConcepts(
						new FuzzyConcept(new Point(0.42), 0.42),
						new FuzzyConcept(new Point(0.74), 0.8))
				.withWeight(someWeight)
				.build();

		assertThat(agent.learn(new Assertion(new SimpleObject(new Point(0.9)), 1, 0.8)),
				equalTo(updatedAgent));
	}

	private BasicAgentBuilder agent() {
		return new BasicAgentBuilder();
	}

	private SimpleObject object(final double d) {
		return new SimpleObject(new Point(d));
	}

}
