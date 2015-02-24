package languagegames.analysis;

import java.util.List;
import java.util.Random;

import languagegames.AgentPairer;
import languagegames.ObjectPool;
import languagegames.RandomPairer;
import agent.Agent;
import agent.Assertion;
import conceptualspace.PerceptualObject;

public class GuessingAnalysis implements Analysis {

	private final int numGames;
	private final int numObjects;
	private final AgentPairer agentPairer;
	private final ObjectPool objectPool;
	private final Random random;

	public GuessingAnalysis(
			final int numGames,
			final int numObjects,
			final AgentPairer agentPairer,
			final ObjectPool objectPool,
			final Random random)
	{
		this.numGames = numGames;
		this.numObjects = numObjects;
		this.agentPairer = agentPairer;
		this.objectPool = objectPool;
		this.random = random;
	}

	public GuessingAnalysis(final ObjectPool objectPool) {
		this(10, 5, new RandomPairer(), objectPool, new Random());
	}

	@Override
	public double analyse(final List<Agent> agents) {
		final List<Integer> pairingOrder = agentPairer.pairingOrder(agents.size());
		double sumOfScores = 0;
		for (int i=0; i<pairingOrder.size(); i+=2) {
			final Agent describer = agents.get(pairingOrder.get(i));
			final Agent guesser = agents.get(pairingOrder.get(i+1));
			sumOfScores += guessingScore(describer, guesser);
		}
		return sumOfScores / (0.5 * pairingOrder.size());
	}

	private double guessingScore(final Agent describer, final Agent guesser) {
		double sumOfScores = 0;
		for (int i=0; i<numGames; i++) {
			sumOfScores += success(describer, guesser) ? 1 : 0;
		}
		return sumOfScores / numGames;
	}

	private boolean success(final Agent describer, final Agent guesser) {
		final List<PerceptualObject> guessingSet = objectPool.pick(numObjects);
		final int targetIndex = random.nextInt(numObjects);
		final Assertion assertion = describer.assertion(guessingSet.get(targetIndex));
		return (guesser.guess(guessingSet, assertion) == targetIndex);
	}

}
