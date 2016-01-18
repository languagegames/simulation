package analysis;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

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

public class TestEntropyAnalysis {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock Agent agent1, agent2;
	@Mock ObjectPool pool;

	private final Point observation = new Point(0.42);
	private final PerceptualObject object = new SimpleObject(observation);
	private final List<Agent> agents = new ArrayList<>();
	private final Assertion assertion1 = new Assertion(1, 0.42);
	private final Assertion assertion2 = new Assertion(2, 0.42);

	@Test
	public void returnsLabelEntropyOverMultipleAgents() {
		final EntropyAnalysis analysis = new EntropyAnalysis(pool);
		agents.add(agent1);
		agents.add(agent2);

		context.checking(new Expectations() {{
			exactly(20).of(pool).pick(); will(returnValue(object));
			exactly(8).of(agent1).assertion(observation); will(returnValue(assertion1));
			exactly(2).of(agent1).assertion(observation); will(returnValue(assertion2));
			exactly(6).of(agent2).assertion(observation); will(returnValue(assertion1));
			exactly(4).of(agent2).assertion(observation); will(returnValue(assertion2));
		}});

		assertThat(analysis.analyse(agents), closeTo(0.88129, 0.00001));
	}

	@Test
	public void returnsEntropyOverAnAgentsLabelFrequencies() {
		final EntropyAnalysis analysis = new EntropyAnalysis(pool);
		agents.add(agent1);

		context.checking(new Expectations() {{
			exactly(10).of(pool).pick(); will(returnValue(object));
			exactly(8).of(agent1).assertion(observation); will(returnValue(assertion1));
			exactly(2).of(agent1).assertion(observation); will(returnValue(assertion2));
		}});

		assertThat(analysis.analyse(agents), closeTo(0.72193, 0.00001));
	}

}
