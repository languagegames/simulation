package agent.concept;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import utility.DataReader;
import utility.matrix.Matrix;
import agent.assertions.Assertion;
import agent.concept.Concept;
import agent.concept.MultivariateConcept;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestMultivariateGaussianConcept {

	@Test
	public void stores20PointsAtMost() {
		final double[][] old = {{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},
				{11},{12},{13},{14},{15},{16},{17},{18},{19},{20}};
		final double[][] updated = {{2},{3},{4},{5},{6},{7},{8},{9},{10},
				{11},{12},{13},{14},{15},{16},{17},{18},{19},{20},{21}};
		final MultivariateConcept concept = new MultivariateConcept(new Matrix(old));
		final Concept updatedConcept = new MultivariateConcept(new Matrix(updated));
		assertThat(concept.update(new Assertion(new SimpleObject(new Point(21.)), 42, 0.42)),
				equalTo(updatedConcept));
	}

	@Test
	public void addsNewPointToData() {
		final Matrix data = new Matrix(new double[][] {{0.0}, {0.1}});
		final Matrix updatedData = new Matrix(new double[][] {{0.0}, {0.1}, {0.2}});
		final Point point = new Point(0.2);
		final MultivariateConcept concept = new MultivariateConcept(data);
		final Assertion assertion = new Assertion(new SimpleObject(point), 42, 0.42);
		final Concept updated = new MultivariateConcept(updatedData);
		assertThat(concept.update(assertion), equalTo(updated));
	}

	@Test
	public void computesLikelihoodGivenPoint() {
		final Matrix data = new Matrix(new DataReader().array2d("randomdata.csv"));
		final MultivariateConcept concept = new MultivariateConcept(data);

		final Point observation = new Point(0.5, 0.5);

		assertThat(concept.appropriatenessOf(observation), closeTo(1.528, 0.001));
	}

}
