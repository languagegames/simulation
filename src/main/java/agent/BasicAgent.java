package agent;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import agent.assertions.Assertion;
import agent.assertions.AssertionModel;
import agent.concept.Concept;
import agent.concept.RandomConceptFactory;
import conceptualspace.Point;


public class BasicAgent implements Agent {

	private final List<Concept> concepts = new ArrayList<>();
	private final double weight;
	private final AssertionModel assertionModel;

	public BasicAgent(final List<Concept> concepts, final double weight, final AssertionModel assertionModel) {
		this.concepts.addAll(concepts);
		this.weight = weight;
		this.assertionModel = assertionModel;
	}

	public static BasicAgent randomAgent(
			final int numDimensions,
			final int numLabels,
			final double weight,
			final AssertionModel assertionModel,
			final RandomConceptFactory factory)
	{
		return new BasicAgent(randomConcepts(numDimensions, numLabels, factory), weight, assertionModel);
	}

	private static List<Concept> randomConcepts(
			final int numDimensions,
			final int numLabels,
			final RandomConceptFactory factory)
	{
		final List<Concept> concepts = new ArrayList<>();
		for (int i=0; i<numLabels; i++) {
			concepts.add(factory.randomConcept(numDimensions));
		}
		return concepts;
	}

	@Override
	public int guess(final List<Point> guessingSet, final Assertion assertion) {
		int guess = 0; double highestAppropriateness = 0;
		for (final Point observation : guessingSet) {
			final double appropriateness = appropriateness(assertion, observation);
			if (appropriateness > highestAppropriateness) {
				guess = guessingSet.indexOf(observation);
				highestAppropriateness = appropriateness;
			}
		}
		return guess;
	}

	@Override
	public Assertion assertion(final Point observation) {
		return assertionModel.assertion(observation, concepts, weight);
	}

	@Override
	public Agent learn(final Point observation, final Assertion assertion) {
		final Concept toUpdate = concepts.get(assertion.label);
		final Concept updated = toUpdate.update(observation, assertion);
		final List<Concept> newConcepts = new ArrayList<>(concepts);
		newConcepts.set(assertion.label, updated);
		return new BasicAgent(newConcepts, weight, assertionModel);
	}

	private double appropriateness(final Assertion assertion, final Point observation) {
		double appropriateness = 1;
		for (final Integer label : assertion.labels()) {
			appropriateness *= concepts.get(label).appropriatenessOf(observation);
		}
		return appropriateness;
	}

	@Override
	public Agent incrementWeight(final double weightIncrement) {
		double newWeight = weight + weightIncrement;
		if (newWeight > 0.9) {
			newWeight = 0.1;
		}
		return new BasicAgent(concepts, newWeight, assertionModel);
	}

	@Override
	public double weight() {
		return weight;
	}

	@Override
	public String toString() {
		return "Basic agent with " + concepts.toString();
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
