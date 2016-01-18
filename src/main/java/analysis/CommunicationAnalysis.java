package analysis;

import java.util.List;

import population.AgentInteractor;
import population.AgentPairer;
import population.RandomPairer;
import agent.Agent;
import conceptualspace.ObjectPool;
import conceptualspace.PerceptualObject;

public class CommunicationAnalysis implements Analysis {

	private final int numGames;
	private final AgentPairer agentPairer;
	private final ObjectPool objectPool;
	private final AgentInteractor agentInteractor;

	public CommunicationAnalysis(
			final int numGames,
			final AgentPairer agentPairer,
			final ObjectPool objectPool,
			final AgentInteractor agentInteractor) {
		this.numGames = numGames;
		this.agentPairer = agentPairer;
		this.objectPool = objectPool;
		this.agentInteractor = agentInteractor;
	}

	public CommunicationAnalysis(final ObjectPool objectPool, final AgentInteractor agentInteractor) {
		this(10, new RandomPairer(), objectPool, agentInteractor);
	}

	@Override
	public double analyse(final List<Agent> agents) {
		final List<Integer> pairingOrder = agentPairer.pairingOrder(agents.size());
		double sumOfScores = 0;
		for (int i=0; i<pairingOrder.size(); i+=2) {
			final Agent agent = agents.get(pairingOrder.get(i));
			final Agent other = agents.get(pairingOrder.get(i+1));
			sumOfScores += communicationScore(agent, other);
		}
		return sumOfScores / (0.5 * pairingOrder.size());
	}

	private double communicationScore(final Agent agent, final Agent other) {
		final List<PerceptualObject> objects = objectPool.pick(numGames);
		double sumOfScores = 0;
		for (final PerceptualObject object : objects) {
			sumOfScores += agentInteractor.communicationGameResult(agent, other, object);
		}
		return sumOfScores / numGames;
	}

	@Override
	public String toString() {
		return "communication";
	}

}
