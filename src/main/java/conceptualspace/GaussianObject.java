package conceptualspace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utility.FakeRandom;

public class GaussianObject implements PerceptualObject {

	private final List<Double> mean = new ArrayList<>();
	private final List<Double> standardDeviation = new ArrayList<>();
	private final Random random;

	public GaussianObject(final List<Double> mean, final List<Double> standardDeviation, final FakeRandom random) {
		this.mean.addAll(mean);
		this.standardDeviation.addAll(standardDeviation);
		this.random = random;
	}

	@Override
	public Point observation() {
		final List<Double> coordinates = new ArrayList<>();
		for (int i=0; i<mean.size(); i++) {
			coordinates.add(mean.get(i) + standardDeviation.get(i)*random.nextGaussian());
		}
		return new Point(coordinates);
	}

}
