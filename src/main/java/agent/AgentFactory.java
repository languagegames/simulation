package agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AgentFactory {

	public static BasicAgent randomAgent(
			final int numDimensions,
			final int numLabels,
			final double initialThreshold,
			final double weight,
			final AssertionModel assertionModel)
	{
		return agent()
				.withConcepts(randomConcepts(numDimensions, numLabels, initialThreshold))
				.withWeight(weight)
				.withAssertionModel(assertionModel)
				.build();
	}

	private static List<Concept> randomConcepts(
			final int numDimensions, final int numLabels, final double initialThreshold) {
		final Random random = new Random();
		final List<Concept> concepts = new ArrayList<>();
		for (int i=0; i<numLabels; i++) {
			concepts.add(FuzzyConcept.randomConcept(numDimensions, initialThreshold, random));
		}
		return concepts;
	}

	private static BasicAgentBuilder agent() {
		return new BasicAgentBuilder();
	}

}
