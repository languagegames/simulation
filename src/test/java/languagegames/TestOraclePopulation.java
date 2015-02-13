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

public class TestOraclePopulation {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock Agent agent0, agent1, agent2, oracle, updatedAgent;
	@Mock ObjectPool objectPool;

	private final AgentPairer agentPairer = new StaticPairer(asList(1, 0, 3, 2));
	private final PerceptualObject object = new SimpleObject(new Point(0.42));
	private final Assertion assertion0 = new Assertion(object, 42, 0.42);
	private final Assertion assertion1 = new Assertion(object, 43, 0.42);

	@Test
	public void usesAgentsAndOraclesInLanguageGames() {
		final OraclePopulation population = new OraclePopulation(
				asList(agent0, agent1, agent2), asList(oracle), objectPool, agentPairer);

		context.checking(new Expectations() {{
			exactly(2).of(objectPool).pick(); will(returnValue(object));
			oneOf(agent1).classify(object); will(returnValue(assertion0));
			oneOf(oracle).classify(object); will(returnValue(assertion1));
			oneOf(agent0).learn(assertion0); will(returnValue(updatedAgent));
			oneOf(agent2).learn(assertion1); will(returnValue(updatedAgent));
			ignoring(agent0).weight(); ignoring(agent1).weight(); ignoring(agent2).weight(); ignoring(oracle).weight();
		}});

		assertThat(population.runLanguageGames(),
				equalTo(new OraclePopulation(
						asList(updatedAgent, agent1, updatedAgent), asList(oracle), objectPool, agentPairer)));

	}

}
