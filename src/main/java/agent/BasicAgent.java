package agent;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.PerceptualObject;
import conceptualspace.Point;


public class BasicAgent implements Agent {

	private final List<FuzzyConcept> concepts = new ArrayList<>();
	private final double weight;

	public BasicAgent(final double weight, final List<FuzzyConcept> concepts) {
		this.weight = weight;
		this.concepts.addAll(concepts);
	}

	public BasicAgent(final double weight, final FuzzyConcept...concepts) {
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
	public double labelOverlap() {
		double sum = 0;
		final int numberOfPairs = concepts.size()*(concepts.size()-1)/2;
		for (int i = 0; i < concepts.size()-1; i++) {
			for (int j = i+1; j < concepts.size(); j++) {
				sum += concepts.get(i).overlapWith(concepts.get(j));
			}
		}
		return sum / numberOfPairs;
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
	public double convergenceWith(final List<FuzzyConcept> concepts) {
		double result = 0;
		for (int i = 0; i < concepts.size(); i++) {
			result += this.concepts.get(i).hausdorffDistanceFrom(concepts.get(i)) / concepts.size();
		}
		return result;
	}

	@Override
	public double convergenceWith(final Agent other) {
		return other.convergenceWith(concepts);
	}

	@Override
	public Assertion assertion(final PerceptualObject object) {
		Concept mostAppropriate = concepts.get(0);
		double maxAppropriateness = 0;
		for (final Concept concept : concepts) {
			final Point observation = object.observation();
			if (concept.appropriatenessOf(observation) > maxAppropriateness) {
				mostAppropriate = concept;
				maxAppropriateness = mostAppropriate.appropriatenessOf(observation);
			}
		}
		return new Assertion(object, concepts.indexOf(mostAppropriate), weight);
	}

	@Override
	public Agent learn(final Assertion assertion) {
		final FuzzyConcept toUpdate = concepts.get(assertion.label);
		final FuzzyConcept updated = toUpdate.update(assertion);
		final List<FuzzyConcept> newConcepts = new ArrayList<>(concepts);
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
