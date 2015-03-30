package agent.concept;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import utility.DataReader;
import utility.matrix.Matrix;
import conceptualspace.Point;

public class TestMultivariateGaussianModel {

	@Test
	public void stores20PointsAtMost() {
		final double[][] old = {{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},
				{11},{12},{13},{14},{15},{16},{17},{18},{19},{20}};
		final double[][] updated = {{2},{3},{4},{5},{6},{7},{8},{9},{10},
				{11},{12},{13},{14},{15},{16},{17},{18},{19},{20},{21}};
		final MultivariateGaussianModel model = new MultivariateGaussianModel(new Matrix(old));
		final MultivariateGaussianModel updatedModel = new MultivariateGaussianModel(new Matrix(updated));
		assertThat(model.update(new Point(21.)), equalTo(updatedModel));
	}

	@Test
	public void addsNewPointToData() {
		final Matrix data = new Matrix(new double[][] {{0.0}, {0.1}});
		final Matrix updatedData = new Matrix(new double[][] {{0.0}, {0.1}, {0.2}});
		final Point point = new Point(0.2);
		final MultivariateGaussianModel model = new MultivariateGaussianModel(data);
		final MultivariateGaussianModel updated = new MultivariateGaussianModel(updatedData);
		assertThat(model.update(point), equalTo(updated));
	}

	@Test
	public void computesLikelihoodGivenPoint() {
		final Matrix data = new Matrix(DataReader.array2d("randomdata.csv"));
		final MultivariateGaussianModel model = new MultivariateGaussianModel(data);

		final Point observation = new Point(0.5, 0.5);

		assertThat(model.likelihood(observation), closeTo(1.528, 0.001));
	}

}
