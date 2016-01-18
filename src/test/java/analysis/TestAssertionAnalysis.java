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

	@Mock Agent agent1, agent2;
	@Mock ObjectPool pool;

	private final Point observation = new Point(0.42);
	private final PerceptualObject object = new SimpleObject(observation);
	private final List<Agent> agents = new ArrayList<>();
	private final Assertion assertion1 = new Assertion(1, 0.42);
	private final Assertion assertion2 = new Assertion(2, 0.42);

	@Test
	public void returnsFrequencyOverMultipleAgents() {
		final AssertionAnalysis analysis = new AssertionAnalysis(pool);
		agents.add(agent1);
		agents.add(agent2);

		context.checking(new Expectations() {{
			exactly(20).of(pool).pick(); will(returnValue(object));
			exactly(8).of(agent1).assertion(observation); will(returnValue(assertion1));
			exactly(2).of(agent1).assertion(observation); will(returnValue(assertion2));
			exactly(6).of(agent2).assertion(observation); will(returnValue(assertion1));
			exactly(4).of(agent2).assertion(observation); will(returnValue(assertion2));
		}});

		assertThat(analysis.analyse(agents), equalTo(0.7));
	}

	@Test
	public void returnsFrequencyOfAnAgentsMostCommonLabel() {
		final AssertionAnalysis analysis = new AssertionAnalysis(pool);
		agents.add(agent1);

		context.checking(new Expectations() {{
			exactly(10).of(pool).pick(); will(returnValue(object));
			exactly(8).of(agent1).assertion(observation); will(returnValue(assertion1));
			exactly(2).of(agent1).assertion(observation); will(returnValue(assertion2));
		}});

		assertThat(analysis.analyse(agents), equalTo(0.8));
	}

}
