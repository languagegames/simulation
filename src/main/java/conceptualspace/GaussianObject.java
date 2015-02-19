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
			coordinates.add(nextCoordinate(mean.get(i), standardDeviation.get(i)));
		}
		return new Point(coordinates);
	}

	private double nextCoordinate(final double mean, final double standardDeviation) {
		double coordinate = mean + standardDeviation*random.nextGaussian();
		while (coordinate > 1) {
			coordinate = mean + standardDeviation*random.nextGaussian();
		}
		return coordinate;
	}

}
