package agent;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestBayesianAgent {

	@Test
	public void assertsLabelWithMaximumLikelihood() {
		final BayesianConcept concept0 = new BayesianConcept(new Point(0.1), new Point(0.4));
		final BayesianConcept concept1 = new BayesianConcept(new Point(0.5), new Point(0.7));
		final BayesianAgent agent = new BayesianAgent(asList(concept0, concept1));
		final SimpleObject object = new SimpleObject(new Point(0.9));
		assertThat(agent.assertion(object), equalTo(new Assertion(object, 1, 0)));
	}

}
