package agent;

import static agent.Assertion.negativeAssertion;
import static agent.Assertion.positiveAssertion;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.PerceptualObject;
import conceptualspace.Point;

public class NegatingAgent implements Agent {

	private final List<Concept> concepts = new ArrayList<>();
	private final double positivePrior;
	private final double weight;

	public NegatingAgent(final double positivePrior, final double weight, final List<Concept> concepts) {
		this.positivePrior = positivePrior;
		this.weight = weight;
		this.concepts.addAll(concepts);
	}

	public NegatingAgent(final double positivePrior, final double weight, final Concept...concepts) {
		this(positivePrior, weight, asList(concepts));
	}

	@Override
	public Agent learn(final Assertion assertion) {
		final Concept toUpdate = concepts.get(assertion.label);
		final Concept updated = (assertion.isPositive) ?
				toUpdate.update(assertion) : toUpdate.negativeUpdate(assertion);
		final List<Concept> newConcepts = new ArrayList<>(concepts);
		newConcepts.set(assertion.label, updated);
		return new NegatingAgent(positivePrior, weight, newConcepts);
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
		return new NegatingAgent(positivePrior, newWeight, concepts);
	}

	@Override
	public double weight() {
		return weight;
	}

	@Override
	public double convergenceWith(final List<Concept> concepts) {
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
		final Point observation = object.observation();
		final List<Concept> sortedConcepts = sortConcepts(observation);
		if (sortedConcepts.size() == 0) {
			return positiveAssertion(object, new Random().nextInt(concepts.size()), weight);
		}
		if (positiveProbability(sortedConcepts, observation) >= negativeProbability(sortedConcepts, observation)) {
			return positiveAssertion(object, concepts.indexOf(sortedConcepts.get(0)), weight);
		} else {
			return negativeAssertion(object, concepts.indexOf(sortedConcepts.get(sortedConcepts.size()-1)), weight);
		}
	}

	private double positiveProbability(final List<Concept> concepts, final Point observation) {
		if (positivePrior == 1) {
			return 1;
		} else if (positivePrior == 0) {
			return 0;
		}
		final int k = concepts.size(), n = this.concepts.size();
		final double p = positivePrior, q = 1 - positivePrior;
		final List<Double> appropriatenessDifferences = appropriatenessDifferences(concepts, observation);
		appropriatenessDifferences.add(concepts.get(k-1).appropriatenessOf(observation));
		double result = 0;
		for (int i=0; i<k; i++) {
			final double coefficient = p / ((i+1)*p + (n-(i+1))*q);
			result += appropriatenessDifferences.get(i) * coefficient;
		}
		return result;
	}

	private double negativeProbability(final List<Concept> concepts, final Point observation) {
		if (positivePrior == 0) {
			return 1;
		} else if (positivePrior == 1) {
			return 0;
		}
		final int k = concepts.size(), n = this.concepts.size();
		final double p = positivePrior, q = 1 - positivePrior;
		final List<Double> appropriatenessDifferences = appropriatenessDifferences(concepts, observation);
		appropriatenessDifferences.add(0, 1 - concepts.get(0).appropriatenessOf(observation));
		double result = 0;
		for (int i=0; i<k; i++) {
			final double coefficient = q / (i*p + (n-i)*q);
			result += appropriatenessDifferences.get(i) * coefficient;
		}
		return result;
	}

	private List<Double> appropriatenessDifferences(final List<Concept> concepts, final Point observation) {
		final List<Double> appropriatenessDifferences = new ArrayList<>();
		for (int i=0; i<concepts.size()-1; i++) {
			final double a = concepts.get(i).appropriatenessOf(observation);
			final double b = concepts.get(i+1).appropriatenessOf(observation);
			appropriatenessDifferences.add(a - b);
		}
		return appropriatenessDifferences;
	}

	private List<Concept> sortConcepts(final Point observation) {
		final List<Concept> concepts = new ArrayList<>(this.concepts);
		Collections.sort(concepts, new Comparator<Concept>() {
	        @Override public int compare(final Concept c1, final Concept c2) {
	            return c1.appropriatenessOf(observation) - c2.appropriatenessOf(observation) > 0 ? -1 : 1;
	        }
	    });
		removeZeroAppropriatenesses(concepts, observation);
		return concepts;
	}

	private void removeZeroAppropriatenesses(final List<Concept> concepts, final Point observation) {
		for (final Iterator<Concept> iterator = concepts.iterator(); iterator.hasNext();) {
		    final Concept concept = iterator.next();
		    if (concept.appropriatenessOf(observation) == 0) {
		        iterator.remove();
		    }
		}
	}

	@Override
	public String toString() {
		return "Negating agent with " + concepts.toString();
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
