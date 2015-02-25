package languagegames.analysis;

import java.util.List;

import agent.Agent;

public class OverlapAnalysis implements Analysis {

	@Override
	public double analyse(final List<Agent> agents) {
		double sum = 0;
		for (final Agent agent : agents) {
			sum += agent.labelOverlap();
		}
		return sum / agents.size();
	}

	@Override
	public String toString() {
		return "overlap";
	}

}
