package languagegames;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import agent.Agent;

public class TestPopulation {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock Agent agent0, agent1, agent2;

	private final double result0 = 0.42, result1 = 0.43, result2 = 0.44;

	@Test
	public void getsAverageConvergenceAcrossAllPairsOfAgents() {
		final Population population = new Population(asList(agent0, agent1, agent2), null, null);

		context.checking(new Expectations() {{
			oneOf(agent0).convergenceWith(agent1); will(returnValue(result0));
			oneOf(agent0).convergenceWith(agent2); will(returnValue(result1));
			oneOf(agent1).convergenceWith(agent2); will(returnValue(result2));
		}});

		assertThat(population.convergence(), equalTo((result0+result1+result2)/3));
	}

}
