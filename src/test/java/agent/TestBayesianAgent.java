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

public class TestBayesianAgent {

	@Test
	public void guessesMostAppropriateObjectGivenAssertionConcept() {
		final PerceptualObject mostAppropriate = object(0.6);
		final PerceptualObject other = object(0.5);
		final BayesianConcept concept0 = new BayesianConcept(new Point(0.7), new Point(0.8));
		final BayesianAgent agent = new BayesianAgent(concept0);

		final List<PerceptualObject> guessingSet = new ArrayList<>();
		guessingSet.addAll(asList(other, mostAppropriate));

		assertThat(agent.guess(guessingSet, new Assertion(mostAppropriate, 0, 0.42)), equalTo(1));
	}

	@Test
	public void assertsLabelWithMaximumLikelihood() {
		final BayesianConcept concept0 = new BayesianConcept(new Point(0.1), new Point(0.4));
		final BayesianConcept concept1 = new BayesianConcept(new Point(0.5), new Point(0.7));
		final BayesianAgent agent = new BayesianAgent(concept0, concept1);
		final SimpleObject object = object(0.9);
		assertThat(agent.assertion(object), equalTo(new Assertion(object, 1, 0)));
	}

	private SimpleObject object(final double d) {
		return new SimpleObject(new Point(d));
	}

}
