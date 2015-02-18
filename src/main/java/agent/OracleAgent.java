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
	public OracleAgent incrementWeight(final double weightIncrement) {
		return this;
	}

	@Override
	public double weight() {
		return weight;
	}

	@Override
	public Assertion classify(final PerceptualObject object) {
		return new Assertion(object, labelMapping.label(object), weight);
	}

	@Override
	public OracleAgent learn(final Assertion assertion) {
		return this;
	}

	@Override
	public double convergenceWith(final Agent other) {
		throw new UnsupportedOperationException("Cannot calculate convergence with oracles");
	}

	@Override
	public double convergenceWith(final List<Concept> concepts) {
		throw new UnsupportedOperationException("Cannot calculate convergence with oracles");
	}

	@Override
	public double labelOverlap() {
		// TODO Auto-generated method stub
		return 0;
	}

}
