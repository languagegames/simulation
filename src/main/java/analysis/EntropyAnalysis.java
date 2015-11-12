package analysis;

import static java.lang.Math.log;

import java.util.List;

import agent.Agent;
import agent.concept.LabelCounts;

public class EntropyAnalysis implements Analysis {

	@Override
	public double analyse(final List<Agent> agents) {
		LabelCounts counts = null;
		for (final Agent agent : agents) {
			counts = agent.labelCounts();
		}
		return entropy(counts);
	}

	private double entropy(final LabelCounts counts) {
		double result = 0;
		final List<Double> frequencies = counts.frequencies();
		for (final Double frequency : frequencies) {
			result -= entropy(frequency);
		}
		return result;
	}

	private double entropy(final double x) {
		return x * log2(x);
	}

	private double log2(final double x) {
		return log(x) / log(2);
	}

}
