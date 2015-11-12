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
import agent.concept.LabelCount;

public class TestEntropyAnalysis {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final List<Agent> agents = new ArrayList<>();

	@Mock Agent agent1,agent2;

	@Test
	public void sumsAgentsLabelCountsThenCalculatesEntropy() {
		final EntropyAnalysis analysis = new EntropyAnalysis();
		agents.add(agent1);
		agents.add(agent2);
		context.checking(new Expectations() {{
			atLeast(1).of(agent1).labelCount(); will(returnValue(new LabelCount(2,4,1)));
			atLeast(1).of(agent2).labelCount(); will(returnValue(new LabelCount(5,1,1)));
		}});
		assertThat(analysis.analyse(agents), closeTo(1.4316, 0.0001));
	}

	@Test
	public void calculatesEntropyOfLabelCountVector() {
		final EntropyAnalysis analysis = new EntropyAnalysis();
		agents.add(agent1);
		context.checking(new Expectations() {{
			atLeast(1).of(agent1).labelCount(); will(returnValue(new LabelCount(2,4,1)));
		}});
		assertThat(analysis.analyse(agents), closeTo(1.3788, 0.0001));
	}

}
