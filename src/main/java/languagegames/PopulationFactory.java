package languagegames;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import agent.Agent;
import agent.BasicAgent;
import agent.BayesianAgent;
import agent.BayesianConcept;
import agent.FuzzyConcept;

public class PopulationFactory {

	public static BasicPopulation bayesianPopulation(
			final int numAgents,
			final int numDimensions,
			final ObjectPool objectPool)
	{
		final List<Agent> agents = new ArrayList<>();
		for (double i=0; i<numAgents; i++) {
			agents.add(bayesianAgent(numDimensions));
		}
		return new BasicPopulation(agents, objectPool, new RandomPairer());
	}

	private static BayesianAgent bayesianAgent(final int numDimensions) {
		final List<BayesianConcept> concepts = new ArrayList<>();
		final Random random = new Random();
		for (int i=0; i<10; i++) {
			concepts.add(BayesianConcept.randomConcept(numDimensions, random));
		}
		return new BayesianAgent(concepts);
	}

	public static OraclePopulation oraclePopulation(
			final List<Agent> oracles,
			final int numAgents,
			final int numDimensions,
			final double initialThreshold,
			final ObjectPool objectPool) {
		final List<Agent> agents = new ArrayList<>();
		for (double i=0; i<numAgents; i++) {
			final double weight = i/numAgents;
			agents.add(randomAgent(numDimensions, initialThreshold, weight));
		}
		return new OraclePopulation(agents, oracles, objectPool, new RandomPairer());
	}

	public static BasicPopulation basicPopulation(
			final int numAgents,
			final int numDimensions,
			final double initialThreshold,
			final ObjectPool objectPool) {
		final List<Agent> agents = new ArrayList<>();
		for (double i=0; i<numAgents; i++) {
			final double weight = i/numAgents;
			agents.add(randomAgent(numDimensions, initialThreshold, weight));
		}
		return new BasicPopulation(agents, objectPool, new RandomPairer());
	}

	private static BasicAgent randomAgent(
			final int numDimensions,
			final double initialThreshold,
			final double weight) {
		final List<FuzzyConcept> concepts = new ArrayList<>();
		final Random random = new Random();
		for (int i=0; i<10; i++) {
			concepts.add(FuzzyConcept.randomConcept(numDimensions, initialThreshold, random));
		}
		return new BasicAgent(weight, concepts);
	}

}
