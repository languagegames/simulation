package population;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.ObjectPool;
import agent.Agent;
import agent.assertions.Assertion;
import analysis.Analysis;

public class OraclePopulation implements Population {

	private final List<Agent> agents = new ArrayList<>();
	private final List<Agent> oracles = new ArrayList<>();
	private final ObjectPool objectPool;
	private final AgentPairer agentPairer;

	public OraclePopulation(
			final List<Agent> agents,
			final List<Agent> oracles,
			final ObjectPool objectPool,
			final AgentPairer agentPairer)
	{
		this.agents.addAll(agents);
		this.oracles.addAll(oracles);
		this.objectPool = objectPool;
		this.agentPairer = agentPairer;
	}

	@Override
	public double apply(final Analysis analysis) {
		return analysis.analyse(agents);
	}

	@Override
	public OraclePopulation incrementWeights(final double weightIncrement) {
		final List<Agent> updatedAgents = incrementWeights(agents, weightIncrement);
		final List<Agent> updatedOracles = incrementWeights(oracles, weightIncrement);
		return new OraclePopulation(updatedAgents, updatedOracles, objectPool, agentPairer);
	}

	private List<Agent> incrementWeights(final List<Agent> agents, final double weightIncrement) {
		final List<Agent> updatedAgents = new ArrayList<>();
		for (final Agent agent : agents) {
			updatedAgents.add(agent.incrementWeight(weightIncrement));
		}
		return updatedAgents;
	}

	@Override
	public OraclePopulation runLanguageGames() {
		final List<Agent> allAgents = mergeAgents();
		final List<Integer> pairingOrder = agentPairer.pairingOrder(allAgents.size());
		final List<Agent> updatedAgents = runLanguageGames(allAgents, pairingOrder);
		return new OraclePopulation(removeOracles(updatedAgents), oracles, objectPool, agentPairer);
	}

	private List<Agent> mergeAgents() {
		final List<Agent> allAgents = new ArrayList<>(agents);
		allAgents.addAll(oracles);
		return allAgents;
	}

	private List<Agent> removeOracles(final List<Agent> updatedAgents) {
		return updatedAgents.subList(0, agents.size());
	}

	private List<Agent> runLanguageGames(final List<Agent> allAgents, final List<Integer> pairingOrder) {
		final List<Agent> updatedAgents = new ArrayList<>(allAgents);
		for (int i=0; i<allAgents.size()-1; i+=2) {
			Agent speaker = allAgents.get(pairingOrder.get(i));
			Agent listener = allAgents.get(pairingOrder.get(i+1));
			if (speaker.weight() < listener.weight()) {
				speaker = allAgents.get(pairingOrder.get(i+1));
				listener = allAgents.get(pairingOrder.get(i));
			}
			updateListener(updatedAgents, speaker, listener);
		}
		return updatedAgents;
	}

	private void updateListener(final List<Agent> updatedAgents, final Agent speaker, final Agent listener) {
		final Assertion speakerAssertion = speaker.assertion(objectPool.pick());
		updatedAgents.set(agents.indexOf(listener), listener.learn(speakerAssertion));
	}

	@Override
	public String toString() {
		return "Agents: " + agents.toString() + ", Oracles: " + oracles.toString();
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
