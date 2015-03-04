package agent;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import conceptualspace.Point;

public class TestBayesianConcept {

	@Test
	public void updatesByAddingNewPointToData() {
		final Point point0 = new Point(0.0);
		final Point point1 = new Point(0.1);
		final Point point2 = new Point(0.2);
		final BayesianConcept concept = new BayesianConcept(point0, point1);
		assertThat(concept.update(point2), equalTo(new BayesianConcept(point0, point1, point2)));
	}

	@Test
	public void computesLikelihoodGivenPoint() {
		final BayesianConcept concept = new BayesianConcept(
				new Point(0.5, 0.2), new Point(0.3, 0.4), new Point(0.7, 0.4));

		final Point observation = new Point(0.5, 0.5);

		assertThat(concept.likelihoodGiven(observation), closeTo(7.295, 0.001));
	}

}
