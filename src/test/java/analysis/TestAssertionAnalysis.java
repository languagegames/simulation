package analysis;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import agent.Agent;
import agent.assertions.Assertion;
import conceptualspace.ObjectPool;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestAssertionAnalysis {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock Agent agent;
	@Mock ObjectPool pool;

	private final PerceptualObject object = new SimpleObject(new Point(0.42));
	private final List<Agent> agents = new ArrayList<>();
	private final Assertion assertion1 = new Assertion(object, 1, 0.42);
	private final Assertion assertion2 = new Assertion(object, 2, 0.42);

	@Test
	public void returnsFrequencyOfAnAgentsMostCommonLabel() {
		final AssertionAnalysis analysis = new AssertionAnalysis(pool);
		agents.add(agent);

		context.checking(new Expectations() {{
			exactly(10).of(pool).pick(); will(returnValue(object));
			exactly(8).of(agent).assertion(object); will(returnValue(assertion1));
			exactly(2).of(agent).assertion(object); will(returnValue(assertion2));
		}});

		assertThat(analysis.analyse(agents), equalTo(0.8));
	}

}
