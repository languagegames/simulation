package analysis;

import static agent.concept.LabelCount.sum;
import static java.lang.Math.log;

import java.util.ArrayList;
import java.util.List;

import agent.Agent;
import agent.concept.LabelCount;

public class EntropyAnalysis implements Analysis {

	@Override
	public double analyse(final List<Agent> agents) {
		final List<LabelCount> counts = new ArrayList<>();
		for (final Agent agent : agents) {
			counts.add(agent.labelCount());
		}
		return entropy(sum(counts));
	}

	private double entropy(final LabelCount counts) {
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
