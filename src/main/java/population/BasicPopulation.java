package population;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import agent.Agent;
import analysis.Analysis;
import conceptualspace.ObjectPool;

public class BasicPopulation implements Population {

	private final List<Agent> agents = new ArrayList<>();
	private final ObjectPool objectPool;
	private final AgentPairer agentPairer;
	private final AgentInteractor agentInteractor;

	public BasicPopulation(
			final List<Agent> agents,
			final ObjectPool objectPool,
			final AgentPairer agentPairer,
			final AgentInteractor agentInteractor)
	{
		this.agents.addAll(agents);
		this.objectPool = objectPool;
		this.agentPairer = agentPairer;
		this.agentInteractor = agentInteractor;
	}

	@Override
	public double apply(final Analysis analysis) {
		return analysis.analyse(agents);
	}

	@Override
	public BasicPopulation runLanguageGames() {
		final List<Integer> pairingOrder = agentPairer.pairingOrder(agents.size());
		final List<Agent> updatedAgents = new ArrayList<>(agents);
		for (int i=0; i<agents.size(); i+=2) {
			Agent speaker = agents.get(pairingOrder.get(i));
			Agent listener = agents.get(pairingOrder.get(i+1));
			if (speaker.weight() < listener.weight()) {
				speaker = agents.get(pairingOrder.get(i+1));
				listener = agents.get(pairingOrder.get(i));
			}
			agentInteractor.updateListener(agents, updatedAgents, speaker, listener, objectPool);
		}
		return new BasicPopulation(updatedAgents, objectPool, agentPairer, agentInteractor);
	}

	@Override
	public BasicPopulation incrementWeights(final double weightIncrement) {
		final List<Agent> updatedAgents = new ArrayList<>();
		for (final Agent agent : agents) {
			updatedAgents.add(agent.incrementWeight(weightIncrement));
		}
		return new BasicPopulation(updatedAgents, objectPool, agentPairer, agentInteractor);
	}

	@Override
	public String toString() {
		return agents.toString();
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
