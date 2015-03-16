package agent;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import conceptualspace.PerceptualObject;
import conceptualspace.Point;

public class BayesianAgent implements Agent {

	private final List<BayesianConcept> concepts = new ArrayList<>();

	public BayesianAgent(final List<BayesianConcept> concepts) {
		this.concepts.addAll(concepts);
	}

	public BayesianAgent(final BayesianConcept...concepts) {
		this(asList(concepts));
	}

	@Override
	public int guess(final List<PerceptualObject> guessingSet, final Assertion assertion) {
		final BayesianConcept assertionConcept = concepts.get(assertion.label);
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
	public Agent learn(final Assertion assertion) {
		final BayesianConcept toUpdate = concepts.get(assertion.label);
		final BayesianConcept updated = toUpdate.update(assertion);
		final List<BayesianConcept> newConcepts = new ArrayList<>(concepts);
		newConcepts.set(assertion.label, updated);
		return new BayesianAgent(newConcepts);
	}

	@Override
	public Assertion assertion(final PerceptualObject object) {
		BayesianConcept mostLikely = concepts.get(0);
		double maxLikelihood = 0;
		for (final BayesianConcept concept : concepts) {
			final Point observation = object.observation();
			if (concept.appropriatenessOf(observation) > maxLikelihood) {
				mostLikely = concept;
				maxLikelihood = mostLikely.appropriatenessOf(observation);
			}
		}
		return new Assertion(object, concepts.indexOf(mostLikely), weight());
	}

	@Override
	public double weight() {
		return 0;
	}

	@Override
	public Agent incrementWeight(final double weightIncrement) {
		return this;
	}

	@Override
	public double convergenceWith(final Agent other) {
		throw new UnsupportedOperationException("Cannot calculate convergence with oracles.");
	}

	@Override
	public double convergenceWith(final List<FuzzyConcept> concepts) {
		throw new UnsupportedOperationException("Cannot calculate convergence with oracles.");
	}

	@Override
	public double labelOverlap() {
		throw new UnsupportedOperationException("Cannot calculate overlap in oracle agent.");
	}

}
