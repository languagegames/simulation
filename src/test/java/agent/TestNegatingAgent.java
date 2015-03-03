package agent;

import static agent.Assertion.negativeAssertion;
import static agent.Assertion.positiveAssertion;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestNegatingAgent {

	private final double somePrior = 0.42;
	private final double someWeight = 0.42;

	@Test
	public void negativeUpdateIfAssertionIsNegative() {
		final NegatingAgent agent = new NegatingAgent(
				somePrior,
				someWeight,
				new Concept(new Point(0.42), 0.42),
				new Concept(new Point(0.5), 0.5));

		final Agent updatedAgent = new NegatingAgent(
				somePrior,
				someWeight,
				new Concept(new Point(0.42), 0.42),
				new Concept(new Point(0.425), 0.25));

		assertThat(agent.learn(negativeAssertion(new SimpleObject(new Point(0.6)), 1, 0.7)),
				equalTo(updatedAgent));
	}

	@Test
	public void learnsByUpdatingAppropriateConcept() {
		final NegatingAgent agent = new NegatingAgent(
				somePrior,
				someWeight,
				new Concept(new Point(0.42), 0.42),
				new Concept(new Point(0.5), 0.5));

		final Agent updatedAgent = new NegatingAgent(
				somePrior,
				someWeight,
				new Concept(new Point(0.42), 0.42),
				new Concept(new Point(0.74), 0.8));

		assertThat(agent.learn(new Assertion(new SimpleObject(new Point(0.9)), 1, 0.8)),
				equalTo(updatedAgent));
	}

	@Test
	public void assertionDependsOnPositivePrior() {
		final List<Concept> concepts = asList(
				new Concept(new Point(0.8), 0.6),
				new Concept(new Point(0.2), 0.5),
				new Concept(new Point(0.9), 0.7),
				new Concept(new Point(0.4), 0.4),
				new Concept(new Point(0.2), 0.2));

		final NegatingAgent anAgent = new NegatingAgent(0.5, someWeight, concepts);
		final NegatingAgent anotherAgent = new NegatingAgent(0.4, someWeight, concepts);

		final PerceptualObject object = new SimpleObject(new Point(0.5));

		assertThat(anAgent.assertion(object), equalTo(positiveAssertion(object, 3, someWeight)));
		assertThat(anotherAgent.assertion(object), equalTo(negativeAssertion(object, 1, someWeight)));
	}

	@Test
	public void guessesMostAppropriateObjectGivenAssertionConcept() {
		final PerceptualObject mostAppropriate = object(0.6);
		final PerceptualObject other = object(0.5);
		final Concept concept0 = new Concept(new Point(0.7), 0.9);
		final NegatingAgent agent = new NegatingAgent(somePrior, someWeight, concept0);

		final List<PerceptualObject> guessingSet = new ArrayList<>();
		guessingSet.addAll(asList(other, mostAppropriate));

		assertThat(agent.guess(guessingSet, new Assertion(mostAppropriate, 0, someWeight)), equalTo(1));
	}

	@Test
	public void calculatesOverlapAcrossAllPairsOfConcepts() {
		final Concept concept0 = new Concept(new Point(0.7), 0.9);
		final Concept concept1 = new Concept(new Point(0.5), 0.5);
		final Concept concept2 = new Concept(new Point(0.4), 0.2);
		final NegatingAgent agent = new NegatingAgent(somePrior, someWeight, concept0, concept1, concept2);

		final double averageOverlap =
				(overlap(concept0, concept1) + overlap(concept0, concept2) + overlap(concept1, concept2)) / 3;

		assertThat(agent.labelOverlap(), equalTo(averageOverlap));
	}

	private double overlap(final Concept concept, final Concept other) {
		return concept.overlapWith(other);
	}

	@Test
	public void incrementsWeight() {
		final NegatingAgent agent = new NegatingAgent(somePrior, 0.5, new ArrayList<Concept>());
		final Agent newAgent = agent.incrementWeight(0.1);
		assertThat(newAgent.weight(), equalTo(0.6));
	}

	@Test
	public void getsConvergenceFromPairsOfCorrespondingConcepts() {
		final NegatingAgent agent = new NegatingAgent(
				somePrior,
				someWeight,
				new Concept(new Point(0.2), 0.7),
				new Concept(new Point(0.5), 0.5));
		final Agent other = new NegatingAgent(
				somePrior,
				someWeight,
				new Concept(new Point(0.4), 0.4),
				new Concept(new Point(0.7), 0.8));

		assertThat(agent.convergenceWith(other), equalTo(0.35));
	}

	private SimpleObject object(final double d) {
		return new SimpleObject(new Point(d));
	}

}
