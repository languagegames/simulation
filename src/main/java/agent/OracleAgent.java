package agent;

import java.util.List;

import agent.assertions.Assertion;
import conceptualspace.Point;


public class OracleAgent implements Agent {

	private final LabelMapping labelMapping;
	private final double weight;

	public OracleAgent(final LabelMapping labelMapping, final double weight) {
		this.labelMapping = labelMapping;
		this.weight = weight;
	}

	@Override
	public Assertion assertion(final Point observation) {
		return new Assertion(labelMapping.label(observation), weight);
	}

	@Override
	public OracleAgent learn(final Point observation, final Assertion assertion) {
		return this;
	}

	@Override
	public int guess(final List<Point> guessingSet, final Assertion assertion) {
		for (int i=0; i<guessingSet.size(); i++) {
			final Point observation = guessingSet.get(i);
			if (labelMapping.label(observation) == assertion.label) {
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

}
