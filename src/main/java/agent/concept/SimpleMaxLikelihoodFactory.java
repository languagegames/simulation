package agent.concept;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import conceptualspace.Point;

public class SimpleMaxLikelihoodFactory implements RandomConceptFactory {

	private final Random random = new Random();

	@Override
	public MaxLikelihoodConcept randomConcept(final int numDimensions) {
		final List<Point> points = new ArrayList<>();
		points.add(randomPoint(numDimensions));
		points.add(randomPoint(numDimensions));
		return new MaxLikelihoodConcept(new IndependentGaussianModel(points), 1);
	}

	private Point randomPoint(final int numDimensions) {
		final List<Double> coordinates = new ArrayList<>();
		for (int i=0; i<numDimensions; i++) {
			coordinates.add(random.nextDouble());
		}
		return new Point(coordinates);
	}

}
