package conceptualspace;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import utility.DataReader;
import utility.FakeRandom;
import utility.matrix.Matrix;

public class TestNoisyObject {

	@Test
	public void drawsObservationsFromMultivariateGaussianDistribution() {
		final Matrix data = new Matrix(DataReader.array2d("randomdata.csv"));
		final double gaussianValue0 = 0.2, gaussianValue1 = 0.5;
		final NoisyObject object = new NoisyObject(data, new FakeRandom(gaussianValue0, gaussianValue1));

		assertThat(object.observation(), equalTo(new Point(0.4616333333333334, 0.4285666666666667)));
	}

}
