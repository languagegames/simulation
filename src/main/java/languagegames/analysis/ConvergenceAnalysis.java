package languagegames.analysis;

import java.util.List;

import languagegames.Population;
import agent.Agent;

public class ConvergenceAnalysis implements Analysis {

	@Override
	public double analyse(final Population population) {
		return population.convergence();
	}

	@Override
	public double analyse(final List<Agent> agents) {
		double sum = 0;
		final int numberOfPairs = agents.size()*(agents.size()-1)/2;
		for (int i = 0; i < agents.size()-1; i++) {
			for (int j = i+1; j < agents.size(); j++) {
				sum += agents.get(i).convergenceWith(agents.get(j));
			}
		}
		return sum / numberOfPairs;
	}

}
