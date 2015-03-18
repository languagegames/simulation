package languagegames.analysis;

import static agent.Assertion.categoryGameAssertion;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import languagegames.AgentPairer;
import languagegames.ObjectPool;
import languagegames.StaticPairer;

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

public class TestCommunicationAnalysis {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock Agent agent0, agent1, agent2, agent3;
	@Mock ObjectPool objectPool;

	private final AgentPairer agentPairer = new StaticPairer(asList(1, 0, 2, 3));
	private final PerceptualObject object = new SimpleObject(new Point(0.42));
	private final List<Agent> agents = new ArrayList<>();

	@Test
	public void assertionWeightIsIrrelevant() {
		agents.addAll(asList(agent0, agent1));
		final int numGames = 1;
		final CommunicationAnalysis analysis = new CommunicationAnalysis(numGames, agentPairer, objectPool);

		final Assertion assertion = categoryGameAssertion(object, 0, 0.4);
		final Assertion equivalentAssertion = categoryGameAssertion(object, 0, 0.5);

		context.checking(new Expectations() {{
			oneOf(objectPool).pick(numGames); will(returnValue(asList(object)));
			oneOf(agent1).assertion(object); will(returnValue(assertion));
			oneOf(agent0).assertion(object); will(returnValue(equivalentAssertion));
		}});

		assertThat(analysis.analyse(agents), equalTo(1.0));
	}

	@Test
	public void comparesAssertionsAcrossPairsOfAgents() {
		agents.addAll(asList(agent0, agent1, agent2, agent3));
		final int numGames = 1;
		final CommunicationAnalysis analysis = new CommunicationAnalysis(numGames, agentPairer, objectPool);

		final Assertion assertion0 = categoryGameAssertion(object, 0, 0.42);
		final Assertion assertion1 = categoryGameAssertion(object, 1, 0.42);

		context.checking(new Expectations() {{
			exactly(2).of(objectPool).pick(numGames); will(returnValue(asList(object)));
			oneOf(agent1).assertion(object); will(returnValue(assertion0));
			oneOf(agent0).assertion(object); will(returnValue(assertion0));
			oneOf(agent2).assertion(object); will(returnValue(assertion0));
			oneOf(agent3).assertion(object); will(returnValue(assertion1));
		}});

		assertThat(analysis.analyse(agents), equalTo(0.5));
	}

}
