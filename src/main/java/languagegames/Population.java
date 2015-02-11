package languagegames;

import java.util.ArrayList;
import java.util.List;

import agent.Agent;

public class Population {

	private final List<Agent> agents = new ArrayList<>();

	public Population(
			final List<Agent> agents,
			final ObjectCreator objectCreator,
			final AgentPairer agentPairer)
	{
		this.agents.addAll(agents);
	}

	public double convergence() {
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
