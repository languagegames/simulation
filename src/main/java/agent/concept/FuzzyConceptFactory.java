package agent.concept;

import static conceptualspace.Point.randomPoint;

import java.util.Random;

public class FuzzyConceptFactory implements RandomConceptFactory {

	private final Random random = new Random();

	@Override
	public Concept randomConcept(final int numDimensions, final double threshold) {
		return new FuzzyConcept(randomPoint(numDimensions, random), threshold);
	}

	@Override
	public Concept randomConcept(final int numDimensions) {
		return randomConcept(numDimensions, 2.0);
	}

}
