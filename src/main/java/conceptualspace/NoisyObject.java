package conceptualspace;

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
