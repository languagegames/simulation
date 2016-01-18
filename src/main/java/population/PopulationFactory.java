package population;

import java.util.ArrayList;
import java.util.List;

import agent.Agent;
import agent.BasicAgent;
import agent.BasicAgentBuilder;
import agent.assertions.AssertionModel;
import agent.concept.Concept;
import agent.concept.RandomConceptFactory;
import conceptualspace.ObjectPool;

public class PopulationFactory {

	public static OraclePopulation oraclePopulation(
			final List<Agent> oracles,
			final int numAgents,
			final int numDimensions,
			final int numLabels,
			final ObjectPool objectPool,
			final AssertionModel assertionModel,
			final RandomConceptFactory factory)
	{
		return new OraclePopulation(
				agents(numAgents, numDimensions, numLabels, assertionModel, factory),
				oracles,
				objectPool,
				new RandomPairer());
	}

	public static BasicPopulation basicPopulation(
			final int numAgents,
			final int numDimensions,
			final int numLabels,
			final ObjectPool objectPool,
			final AssertionModel assertionModel,
			final RandomConceptFactory factory)
	{
		return new BasicPopulation(
				agents(numAgents, numDimensions, numLabels, assertionModel, factory),
				objectPool,
				new RandomPairer(),
				new DifferentObservationInteractor());
	}

	private static List<Agent> agents(
			final int numAgents,
			final int numDimensions,
			final int numLabels,
			final AssertionModel assertionModel,
			final RandomConceptFactory factory)
	{
		final List<Agent> agents = new ArrayList<>();
		for (double i=0; i<numAgents; i++) {
			final double weight = i/numAgents;
			agents.add(randomAgent(numDimensions, numLabels, weight, assertionModel, factory));
		}
		return agents;
	}

	private static BasicAgent randomAgent(
			final int numDimensions,
			final int numLabels,
			final double weight,
			final AssertionModel assertionModel,
			final RandomConceptFactory factory)
	{
		final List<Concept> concepts = new ArrayList<>();
		for (int i=0; i<numLabels; i++) {
			concepts.add(factory.randomConcept(numDimensions));
		}
		return agent().withConcepts(concepts).withWeight(weight).withAssertionModel(assertionModel).build();
	}

	private static BasicAgentBuilder agent() {
		return new BasicAgentBuilder();
	}

}
