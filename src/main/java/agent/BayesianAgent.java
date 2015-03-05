package agent;

import java.util.ArrayList;
import java.util.List;

import conceptualspace.PerceptualObject;
import conceptualspace.Point;

public class BayesianAgent implements Agent {

	private final List<BayesianConcept> concepts = new ArrayList<>();

	public BayesianAgent(final List<BayesianConcept> concepts) {
		this.concepts.addAll(concepts);
	}

	@Override
	public Agent learn(final Assertion assertion) {
		final BayesianConcept toUpdate = concepts.get(assertion.label);
		final BayesianConcept updated = toUpdate.update(assertion.object.observation());
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
			if (concept.likelihoodGiven(observation) > maxLikelihood) {
				mostLikely = concept;
				maxLikelihood = mostLikely.likelihoodGiven(observation);
			}
		}
		return new Assertion(object, concepts.indexOf(mostLikely), weight());
	}

	@Override
	public int guess(final List<PerceptualObject> guessingSet, final Assertion assertion) {
		// TODO Auto-generated method stub
		return 0;
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
	public double convergenceWith(final List<Concept> concepts) {
		throw new UnsupportedOperationException("Cannot calculate convergence with oracles.");
	}

	@Override
	public double labelOverlap() {
		throw new UnsupportedOperationException("Cannot calculate overlap in oracle agent.");
	}

}
