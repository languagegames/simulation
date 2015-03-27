package agent;

import java.util.ArrayList;
import java.util.List;

import agent.concept.Concept;
import agent.concept.RandomConceptFactory;

public class AgentFactory {

	public static BasicAgent randomAgent(
			final int numDimensions,
			final int numLabels,
			final double weight,
			final AssertionModel assertionModel,
			final RandomConceptFactory factory)
	{
		return agent()
				.withConcepts(randomConcepts(numDimensions, numLabels, factory))
				.withWeight(weight)
				.withAssertionModel(assertionModel)
				.build();
	}

	private static List<Concept> randomConcepts(
			final int numDimensions,
			final int numLabels,
			final RandomConceptFactory factory)
	{
		final List<Concept> concepts = new ArrayList<>();
		for (int i=0; i<numLabels; i++) {
			concepts.add(factory.randomConcept(numDimensions));
		}
		return concepts;
	}

	private static BasicAgentBuilder agent() {
		return new BasicAgentBuilder();
	}

}
