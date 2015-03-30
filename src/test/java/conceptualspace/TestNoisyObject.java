package conceptualspace;

import static java.util.Arrays.copyOfRange;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import utility.DataReader;
import utility.FakeRandom;
import utility.matrix.Matrix;

public class TestNoisyObject {

	private final double[][] data = DataReader.array2d("randomdata.csv");

	@Test
	public void createsListOfObjectsWithSpecifiedDimensionality() {
		final double[][] data1d = oneDimensional(data);
		final PerceptualObject object = new NoisyObject(new Matrix(data1d), null);

		assertThat(NoisyObject.makeListFrom(data, 10, 1), contains(object));
	}

	private double[][] oneDimensional(final double[][] data) {
		final double[][] data1d = new double[10][1];
		for (int i=0; i<10; i++) {
			data1d[i][0] = data[i][0];
		}
		return data1d;
	}

	@Test
	public void createsListOfObjectsFromListOfPoints() {
		final PerceptualObject object0 = new NoisyObject(new Matrix(copyOfRange(data, 0, 5)), null);
		final PerceptualObject object1 = new NoisyObject(new Matrix(copyOfRange(data, 5, 10)), null);

		assertThat(NoisyObject.makeListFrom(data, 5, 2), contains(object0, object1));
	}

	@Test
	public void drawsObservationsFromMultivariateGaussianDistribution() {
		final double gaussianValue0 = 0.2, gaussianValue1 = 0.5;
		final NoisyObject object = new NoisyObject(new Matrix(data), new FakeRandom(gaussianValue0, gaussianValue1));

		assertThat(object.observation(), equalTo(new Point(0.4616333333333334, 0.4285666666666667)));
	}

}
