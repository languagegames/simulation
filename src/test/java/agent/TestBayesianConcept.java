package agent;

import static agent.Assertion.categoryGameAssertion;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestBayesianConcept {

	@Test
	public void stores20PointsAtMost() {
		final Point point = new Point(0.42);
		final Assertion assertion = categoryGameAssertion(new SimpleObject(point), 42, 0.42);
		BayesianConcept concept = new BayesianConcept(point, point);
		final List<Point> points = new ArrayList<>();
		for (int i=0; i<20; i++) {
			points.add(point);
			concept = concept.update(assertion);
		}
		assertThat(concept, equalTo(new BayesianConcept(points, 22)));
	}

	@Test
	public void addsNewPointToStartOfList() {
		final Point point0 = new Point(0.0);
		final Point point1 = new Point(0.1);
		final Point point2 = new Point(0.2);
		final BayesianConcept concept = new BayesianConcept(point1, point0);
		final Assertion assertion = categoryGameAssertion(new SimpleObject(point2), 42, 0.42);
		assertThat(concept.update(assertion), equalTo(new BayesianConcept(point2, point1, point0)));
	}

	@Test
	public void computesLikelihoodGivenPoint() {
		final BayesianConcept concept = new BayesianConcept(
				new Point(0.5, 0.2), new Point(0.3, 0.4), new Point(0.7, 0.4));

		final Point observation = new Point(0.5, 0.5);

		assertThat(concept.appropriatenessOf(observation), closeTo(7.295, 0.001));
	}

}
