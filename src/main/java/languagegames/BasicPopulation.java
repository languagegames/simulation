package languagegames;

import java.util.ArrayList;
import java.util.List;

import languagegames.analysis.Analysis;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import agent.Agent;
import agent.Assertion;

public class BasicPopulation implements Population {

	private final List<Agent> agents = new ArrayList<>();
	private final ObjectPool objectPool;
	private final AgentPairer agentPairer;

	public BasicPopulation(
			final List<Agent> agents,
			final ObjectPool objectPool,
			final AgentPairer agentPairer)
	{
		this.agents.addAll(agents);
		this.objectPool = objectPool;
		this.agentPairer = agentPairer;
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
			updateListener(updatedAgents, speaker, listener);
		}
		return new BasicPopulation(updatedAgents, objectPool, agentPairer);
	}

	private void updateListener(
			final List<Agent> updatedAgents, final Agent speaker, final Agent listener) {
		final Assertion speakerAssertion = speaker.classify(objectPool.pick());
		updatedAgents.set(agents.indexOf(listener), listener.learn(speakerAssertion));
	}

	@Override
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

	@Override
	public BasicPopulation incrementWeights(final double weightIncrement) {
		final List<Agent> updatedAgents = new ArrayList<>();
		for (final Agent agent : agents) {
			updatedAgents.add(agent.incrementWeight(weightIncrement));
		}
		return new BasicPopulation(updatedAgents, objectPool, agentPairer);
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
