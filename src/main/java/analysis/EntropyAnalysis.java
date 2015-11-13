package analysis;

import static java.lang.Math.log;

import java.util.List;

import agent.Agent;
import agent.assertions.Assertion;
import conceptualspace.ObjectPool;
import conceptualspace.PerceptualObject;

public class EntropyAnalysis implements Analysis {

	private final ObjectPool objectPool;

	public EntropyAnalysis(final ObjectPool objectPool) {
		this.objectPool = objectPool;
	}

	@Override
	public double analyse(final List<Agent> agents) {
		AssertionCount count = new AssertionCount();
		for (final Agent agent : agents) {
			count = updateCountFor(count, agent);
		}
		return entropy(count.frequencies());
	}

	private AssertionCount updateCountFor(AssertionCount count, final Agent agent) {
		for (int i=0; i<10; i++) {
			final PerceptualObject object = objectPool.pick();
			final Assertion assertion = agent.assertion(object);
			count = count.add(assertion.label);
		}
		return count;
	}

	private double entropy(final List<Double> values) {
		double result = 0;
		for (final Double value : values) {
			result -= entropy(value);
		}
		return result;
	}

	private double entropy(final double value) {
		return value * log2(value);
	}

	private double log2(final double value) {
		return log(value)/log(2);
	}

}
