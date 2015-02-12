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
import agent.Assertion;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestPopulation {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock Agent agent0, agent1, agent2, agent3, updatedAgent0, updatedAgent3;
	@Mock ObjectCreator objectCreator;

	private final AgentPairer agentPairer = new StaticPairer(asList(2, 0, 1, 3));
	private final double result0 = 0.42, result1 = 0.43, result2 = 0.44;

	@Test
	public void pairsOffAgentsAndGetsThemToDoLanguageGames() {
		final Population population =
				new Population(asList(agent0, agent1, agent2, agent3), objectCreator, agentPairer);

		final PerceptualObject object = new SimpleObject(new Point(0.42));
		final Assertion assertion0 = new Assertion(object, 42, 0.42);
		final Assertion assertion1 = new Assertion(object, 43, 0.42);

		context.checking(new Expectations() {{
			exactly(2).of(objectCreator).create(); will(returnValue(object));
			oneOf(agent2).classify(object); will(returnValue(assertion0));
			oneOf(agent0).learn(assertion0); will(returnValue(updatedAgent0));
			oneOf(agent1).classify(object); will(returnValue(assertion1));
			oneOf(agent3).learn(assertion1); will(returnValue(updatedAgent3));
		}});

		assertThat(population.runLanguageGames(), equalTo(
				new Population(asList(updatedAgent0, agent1, agent2, updatedAgent3), objectCreator, agentPairer)));
	}

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
