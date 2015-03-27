package agent.assertions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import agent.concept.Concept;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;

public class ConjunctionAssertionModel implements AssertionModel {

	private final double conjunctionPrior;

	public ConjunctionAssertionModel(final double conjunctionPrior) {
		this.conjunctionPrior = conjunctionPrior;
	}

	@Override
	public Assertion assertion(final PerceptualObject object, final List<Concept> concepts, final double weight) {
		final Point observation = object.observation();
		final List<Concept> sortedConcepts = sortConcepts(concepts, observation);
		final List<Double> appropriatenessDifferences = appropriateDifferences(observation, sortedConcepts);
		final double coefficient = coefficient(sortedConcepts, observation, appropriatenessDifferences);
		if (conjunctionProbability(coefficient) >= singleLabelProbability(appropriatenessDifferences, coefficient)) {
			return new Assertion(
					object,
					concepts.indexOf(sortedConcepts.get(0)),
					concepts.indexOf(sortedConcepts.get(1)),
					weight);
		} else {
			return new Assertion(
					object,
					concepts.indexOf(sortedConcepts.get(0)),
					weight);
		}
	}

	private double singleLabelProbability(final List<Double> appropriatenessDifferences, final double coefficient) {
		return appropriatenessDifferences.get(0) + (1-conjunctionPrior) * coefficient;
	}

	private double conjunctionProbability(final double coefficient) {
		return conjunctionPrior * coefficient;
	}

	private List<Double> appropriateDifferences(final Point observation, final List<Concept> sortedConcepts) {
		final List<Double> appropriatenessDifferences = new ArrayList<>();
		for (int i=0; i<sortedConcepts.size()-1; i++) {
			final double a = sortedConcepts.get(i).appropriatenessOf(observation);
			final double b = sortedConcepts.get(i+1).appropriatenessOf(observation);
			appropriatenessDifferences.add(a - b);
		}
		appropriatenessDifferences.add(sortedConcepts.get(sortedConcepts.size()-1).appropriatenessOf(observation));
		return appropriatenessDifferences;
	}

	private List<Concept> sortConcepts(final List<Concept> concepts, final Point observation) {
		final List<Concept> sortedConcepts = new ArrayList<>(concepts);
		Collections.sort(sortedConcepts, new Comparator<Concept>() {
	        @Override public int compare(final Concept c1, final Concept c2) {
	            return c1.appropriatenessOf(observation) - c2.appropriatenessOf(observation) > 0 ? -1 : 1;
	        }
	    });
		return sortedConcepts;
	}

	private double coefficient(
			final List<Concept> concepts, final Point observation, final List<Double> appropriatenessDifferences) {
		final int n = concepts.size();
		double result = 0;
		for (int i=1; i<n; i++) {
			final double coefficient = 1 / ((i+1)*(0.5*conjunctionPrior*((i+1)-3)+1));
			result += appropriatenessDifferences.get(i) * coefficient;
		}
		return result;
	}

}
