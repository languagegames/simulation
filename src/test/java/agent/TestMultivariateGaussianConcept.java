package agent;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import jama.Matrix;

import org.junit.Test;

import agent.concept.Concept;
import agent.concept.MultivariateGaussianConcept;
import conceptualspace.Point;
import conceptualspace.SimpleObject;
import datareader.DataReader;

public class TestMultivariateGaussianConcept {

	@Test
	public void stores20PointsAtMost() {
		final double[][] old = {{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},
				{11},{12},{13},{14},{15},{16},{17},{18},{19},{20}};
		final double[][] updated = {{2},{3},{4},{5},{6},{7},{8},{9},{10},
				{11},{12},{13},{14},{15},{16},{17},{18},{19},{20},{21}};
		final MultivariateGaussianConcept concept = new MultivariateGaussianConcept(new Matrix(old));
		final Concept updatedConcept = new MultivariateGaussianConcept(new Matrix(updated));
		assertThat(concept.update(new Assertion(new SimpleObject(new Point(21.)), 42, 0.42)),
				equalTo(updatedConcept));
	}

	@Test
	public void addsNewPointToData() {
		final Matrix data = new Matrix(new double[][] {{0.0}, {0.1}});
		final Matrix updatedData = new Matrix(new double[][] {{0.0}, {0.1}, {0.2}});
		final Point point = new Point(0.2);
		final MultivariateGaussianConcept concept = new MultivariateGaussianConcept(data);
		final Assertion assertion = new Assertion(new SimpleObject(point), 42, 0.42);
		final Concept updated = new MultivariateGaussianConcept(updatedData);
		assertThat(concept.update(assertion), equalTo(updated));
	}

	@Test
	public void computesLikelihoodGivenPoint() {
		final Matrix data = new Matrix(new DataReader().array2d("randomdata.csv"));
		final MultivariateGaussianConcept concept = new MultivariateGaussianConcept(data);

		final Point observation = new Point(0.5, 0.5);

		assertThat(concept.appropriatenessOf(observation), closeTo(1.528, 0.001));
	}

}
