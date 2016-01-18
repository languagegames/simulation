package population;

import java.util.List;

import agent.Agent;
import conceptualspace.ObjectPool;

public interface AgentInteractor {

	void updateListener(List<Agent> agents, List<Agent> updatedAgents, Agent speaker, Agent listener, ObjectPool objectPool);

}
