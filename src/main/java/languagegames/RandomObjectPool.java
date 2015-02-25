package languagegames;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class RandomObjectPool implements ObjectPool {

	private final int numDimensions;
	private final Random random;

	public RandomObjectPool(final int numDimensions) {
		this.numDimensions = numDimensions;
		random = new Random();
	}

	@Override
	public PerceptualObject pick() {
		final List<Double> coordinates = new ArrayList<>();
		for (int i=0; i<numDimensions; i++) {
			coordinates.add(random.nextDouble());
		}
		return new SimpleObject(new Point(coordinates));
	}

	@Override
	public List<PerceptualObject> pick(final int numObjects) {
		final List<PerceptualObject> objects = new ArrayList<>();
		for (int i=0; i<numObjects; i++) {
			objects.add(pick());
		}
		return objects;
	}

}
