package agent;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

import org.junit.Test;

import conceptualspace.Point;

public class TestBayesianConcept {

	@Test
	public void computesLikelihoodGivenPoint() {
		final BayesianConcept concept = new BayesianConcept(
				new Point(0.5, 0.2), new Point(0.3, 0.4), new Point(0.7, 0.4));

		final Point observation = new Point(0.5, 0.5);

		assertThat(concept.likelihoodGiven(observation), closeTo(7.295, 0.001));
	}

}
