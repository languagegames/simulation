package agent.concept;

import static conceptualspace.Point.randomPoint;

import java.util.Random;

public class FuzzyConceptFactory implements RandomConceptFactory {

	private final double threshold;
	private final Random random = new Random();

	public FuzzyConceptFactory(final double threshold) {
		this.threshold = threshold;
	}

	@Override
	public Concept randomConcept(final int numDimensions) {
		return new FuzzyConcept(randomPoint(numDimensions, random), threshold, 1);
	}

}
