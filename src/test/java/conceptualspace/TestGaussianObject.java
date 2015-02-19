package conceptualspace;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import utility.FakeRandom;

public class TestGaussianObject {

	@Test
	public void drawsObservationsFromGaussianDistribution() {
		final double gaussianValue0 = 0.2, gaussianValue1 = 0.8;
		final double mean0 = 0.5, mean1 = 0.7;
		final double stdDev0 = 0.8, stdDev1 = 0.4;
		final GaussianObject object = new GaussianObject(
				asList(mean0, mean1),
				asList(stdDev0, stdDev1),
				new FakeRandom(gaussianValue0, gaussianValue1));

		assertThat(object.observation(), equalTo(new Point(
				mean0 + gaussianValue0*stdDev0,
				mean1 + gaussianValue1*stdDev1)));
	}

}
