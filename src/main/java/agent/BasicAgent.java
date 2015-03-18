package agent;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.PerceptualObject;


public class BasicAgent implements Agent {

	private final List<Concept> concepts = new ArrayList<>();
	private final double weight;
	private final AssertionModel assertionModel;

	public BasicAgent(final double weight, final List<Concept> concepts) {
		this.weight = weight;
		this.concepts.addAll(concepts);
		assertionModel = new BasicAssertionModel();
	}

	public BasicAgent(final double weight, final Concept...concepts) {
		this(weight, asList(concepts));
	}

	@Override
	public int guess(final List<PerceptualObject> guessingSet, final Assertion assertion) {
		final Concept assertionConcept = concepts.get(assertion.label);
		int guess = 0; double highestAppropriateness = 0;
		for (final PerceptualObject object : guessingSet) {
			final double appropriateness = assertionConcept.appropriatenessOf(object.observation());
			if (appropriateness > highestAppropriateness) {
				guess = guessingSet.indexOf(object);
				highestAppropriateness = appropriateness;
			}
		}
		return guess;
	}

	@Override
	public Agent incrementWeight(final double weightIncrement) {
		double newWeight = weight + weightIncrement;
		if (newWeight > 0.9) {
			newWeight = 0.1;
		}
		return new BasicAgent(newWeight, concepts);
	}

	@Override
	public double weight() {
		return weight;
	}

	@Override
	public Assertion assertion(final PerceptualObject object) {
		return assertionModel.assertion(object, concepts, weight);
	}

	@Override
	public Agent learn(final Assertion assertion) {
		final Concept toUpdate = concepts.get(assertion.label);
		final Concept updated = toUpdate.update(assertion);
		final List<Concept> newConcepts = new ArrayList<>(concepts);
		newConcepts.set(assertion.label, updated);
		return new BasicAgent(weight, newConcepts);
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
