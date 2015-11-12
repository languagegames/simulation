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
import agent.concept.LabelCounts;

public class TestEntropyAnalysis {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final List<Agent> agents = new ArrayList<>();

	@Mock Agent agent1,agent2;

	@Test
	public void calculatesEntropyOfLabelCountVector() {
		final EntropyAnalysis analysis = new EntropyAnalysis();
		agents.add(agent1);
		context.checking(new Expectations() {{
			oneOf(agent1).labelCounts(); will(returnValue(new LabelCounts(2,4,1)));
		}});
		assertThat(analysis.analyse(agents), closeTo(1.3788, 0.0001));
	}

}
