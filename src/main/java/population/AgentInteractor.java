package population;

import java.util.List;
import java.util.Random;

import agent.Agent;
import conceptualspace.ObjectPool;
import conceptualspace.PerceptualObject;

public interface AgentInteractor {

	void updateListener(
			List<Agent> agents, List<Agent> updatedAgents, Agent speaker, Agent listener, ObjectPool objectPool);

	int communicationGameResult(Agent agent, Agent other, PerceptualObject object);

	int guessingGameResult(Agent describer, Agent guesser,
			ObjectPool objectPool, int numObjects, Random random);

}
