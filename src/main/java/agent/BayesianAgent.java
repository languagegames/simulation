package agent;

import java.util.List;

import conceptualspace.PerceptualObject;

public class BayesianAgent implements Agent {

	@Override
	public Assertion assertion(final PerceptualObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Agent learn(final Assertion assertion) {
		// TODO Auto-generated method stub
		return null;
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
