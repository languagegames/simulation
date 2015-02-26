package languagegames;

import static agent.Concept.randomConcept;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import agent.Agent;
import agent.BasicAgent;
import agent.Concept;

public class PopulationFactory {

	private final Random random;

	public PopulationFactory() {
		random = new Random();
	}

	public OraclePopulation createWithOracles(
			final List<Agent> oracles,
			final int numAgents,
			final int numDimensions,
			final double minThreshold,
			final double maxThreshold,
			final ObjectPool objectPool) {
		final List<Agent> agents = new ArrayList<>();
		for (double i=0; i<numAgents; i++) {
			final double weight = i/numAgents;
			agents.add(randomAgent(numDimensions, minThreshold, maxThreshold, weight));
		}
		return new OraclePopulation(agents, oracles, objectPool, new RandomPairer());
	}

	public BasicPopulation createRandom(
			final int numAgents,
			final int numDimensions,
			final double minThreshold,
			final double maxThreshold,
			final ObjectPool objectPool) {
		final List<Agent> agents = new ArrayList<>();
		for (double i=0; i<numAgents; i++) {
			final double weight = i/numAgents;
			agents.add(randomAgent(numDimensions, minThreshold, maxThreshold, weight));
		}
		return new BasicPopulation(agents, objectPool, new RandomPairer());
	}

	private BasicAgent randomAgent(
			final int numDimensions,
			final double minThreshold,
			final double maxThreshold,
			final double weight) {
		final List<Concept> concepts = new ArrayList<>();
		for (int i=0; i<10; i++) {
			concepts.add(randomConcept(numDimensions, minThreshold, maxThreshold, random));
		}
		return new BasicAgent(weight, concepts);
	}

}
