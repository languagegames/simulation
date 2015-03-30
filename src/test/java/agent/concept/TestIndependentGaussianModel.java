package agent.concept;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import conceptualspace.Point;

public class TestIndependentGaussianModel {

	@Test
	public void stores20PointsAtMost() {
		final Point point = new Point(0.42);
		IndependentGaussianModel concept = new IndependentGaussianModel(point, point);
		final List<Point> points = new ArrayList<>();
		for (int i=0; i<20; i++) {
			points.add(point);
			concept = concept.update(point);
		}
		assertThat(concept, equalTo(new IndependentGaussianModel(points)));
	}

	@Test
	public void addsNewPointToStartOfList() {
		final Point point0 = new Point(0.0);
		final Point point1 = new Point(0.1);
		final Point point2 = new Point(0.2);
		final IndependentGaussianModel concept = new IndependentGaussianModel(point1, point0);
		assertThat(concept.update(point2), equalTo(new IndependentGaussianModel(point2, point1, point0)));
	}

	@Test
	public void computesLikelihoodGivenPoint() {
		final IndependentGaussianModel concept = new IndependentGaussianModel(
				new Point(0.5, 0.2), new Point(0.3, 0.4), new Point(0.7, 0.4));

		final Point observation = new Point(0.5, 0.5);

		assertThat(concept.likelihood(observation), closeTo(2.432, 0.001));
	}

}
