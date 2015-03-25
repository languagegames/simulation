package agent;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import jama.Matrix;

import org.junit.Test;

import conceptualspace.Point;
import datareader.DataReader;

public class TestMultivariateGaussianConcept {

	@Test
	public void computesLikelihoodGivenPoint() {
		final Matrix data = new Matrix(new DataReader().array2d("randomdata.csv"));
		final MultivariateGaussianConcept concept = new MultivariateGaussianConcept(data);

		final Point observation = new Point(0.5, 0.5);

		assertThat(concept.appropriatenessOf(observation), closeTo(1.528, 0.001));
	}

}
