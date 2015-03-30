package conceptualspace;

import static java.util.Arrays.copyOfRange;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import utility.matrix.Matrix;

public class NoisyObject implements PerceptualObject {

	private final Matrix mu;
	private final Matrix sigma;
	private final Random random;

	public NoisyObject(final Matrix data, final Random random) {
		mu = data.mean();
		sigma = data.covariance();
		this.random = random;
	}

	public static List<NoisyObject> makeListFrom(
			final double[][] data, final int pointsPerObject, final int numDimensions) {
		final int numObjects = data.length / pointsPerObject;
		final List<NoisyObject> objects = new ArrayList<>();
		for (int i=0; i<numObjects; i++) {
			objects.add(object(data, pointsPerObject, numDimensions, i));
		}
		return objects;
	}

	private static NoisyObject object(final double[][] data,
			final int pointsPerObject, final int numDimensions, final int i) {
		final double[][] objectData = copyOfRange(data, i*pointsPerObject, i*pointsPerObject+pointsPerObject);
		final NoisyObject object = new NoisyObject(
				new Matrix(dimensionalise(objectData, numDimensions)), new Random());
		return object;
	}

	private static double[][] dimensionalise(final double[][] data, final int numDimensions) {
		final double[][] result = new double[data.length][numDimensions];
		for (int i=0; i<data.length; i++) {
			result[i] = copyOfRange(data[i], 0, numDimensions);
		}
		return result;
	}

	@Override
	public Point observation() {
		final int numDimensions = mu.getColumnDimension();
		final double[][] coordinates = new double[1][numDimensions];
		for (int i=0; i<numDimensions; i++) {
			coordinates[0][i] = random.nextGaussian();
		}
		final Matrix standardObservation = new Matrix(coordinates);
		final Matrix observation = standardObservation.times(sigma).plus(mu);
		return new Point(observation.getArray()[0]);
	}

	@Override
	public String toString() {
		return "Noisy object with mean " + mu.toString() + " and covariance " + sigma.toString();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof NoisyObject) {
			final NoisyObject other = (NoisyObject) obj;
			return new EqualsBuilder()
			.append(mu, other.mu)
			.append(sigma, other.sigma)
			.isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(mu)
		.append(sigma)
		.toHashCode();
	}

}
