package agent;

import java.util.List;

import conceptualspace.PerceptualObject;


public class OracleAgent implements Agent {

	private final LabelMapping labelMapping;
	private final double weight;

	public OracleAgent(final LabelMapping labelMapping, final double weight) {
		this.labelMapping = labelMapping;
		this.weight = weight;
	}

	@Override
	public int guess(final List<PerceptualObject> guessingSet, final Assertion assertion) {
		for (int i=0; i<guessingSet.size(); i++) {
			final PerceptualObject object = guessingSet.get(i);
			if (labelMapping.label(object) == assertion.label) {
				return i;
			}
		}
		return 0;
	}

	@Override
	public OracleAgent incrementWeight(final double weightIncrement) {
		return this;
	}

	@Override
	public double weight() {
		return weight;
	}

	@Override
	public Assertion assertion(final PerceptualObject object) {
		return new Assertion(object, labelMapping.label(object), weight);
	}

	@Override
	public OracleAgent learn(final Assertion assertion) {
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
