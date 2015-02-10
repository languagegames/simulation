package languagegames;

import java.util.ArrayList;
import java.util.List;

import agent.Agent;

public class Population {

	private final List<Agent> agents = new ArrayList<>();

	public Population(final List<Agent> agents) {
		this.agents.addAll(agents);
	}

	public double convergence() {
		double result = 0;
		final int numAgents = agents.size();
		for (int i = 0; i < numAgents-1; i++) {
			for (int j = i+1; j < numAgents; j++) {
				result += agents.get(i).convergenceWith(agents.get(j)) / numAgents;
			}
		}
		return result;
	}

}
