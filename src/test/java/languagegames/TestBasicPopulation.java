package languagegames;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import agent.Agent;
import agent.Assertion;
import agent.BasicAgent;
import agent.Concept;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestBasicPopulation {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock Agent agent0, agent1, agent2, agent3, updatedAgent;
	@Mock ObjectPool objectCreator;

	private final AgentPairer agentPairer = new StaticPairer(asList(1, 0, 2, 3));
	private final double result0 = 0.42, result1 = 0.43, result2 = 0.44;
	private final PerceptualObject object = new SimpleObject(new Point(0.42));
	private final Assertion assertion0 = new Assertion(object, 42, 0.42);
	private final Assertion assertion1 = new Assertion(object, 43, 0.42);
	private final double highWeight = 0.8, lowWeight = 0.4;

	@Test
	public void agentsWeightsAreIncremented() {
		final Agent agent0 = new BasicAgent(0.5, new ArrayList<Concept>());
		final Agent agent1 = new BasicAgent(0.9, new ArrayList<Concept>());
		final BasicPopulation population = new BasicPopulation(asList(agent0, agent1), null, null);

		final Agent updatedAgent0 = new BasicAgent(0.6, new ArrayList<Concept>());
		final Agent updatedAgent1 = new BasicAgent(0.1, new ArrayList<Concept>());

		assertThat(population.incrementWeights(0.1),
				equalTo(new BasicPopulation(asList(updatedAgent0, updatedAgent1), null, null)));
	}

	@Test
	public void agentWithHigherWeightIsAlwaysSpeaker() {
		final BasicPopulation population =
				new BasicPopulation(asList(agent0, agent1), objectCreator, agentPairer);

		context.checking(new Expectations() {{
			oneOf(agent0).weight(); will(returnValue(highWeight));
			oneOf(agent1).weight(); will(returnValue(lowWeight));
			oneOf(objectCreator).pick(); will(returnValue(object));
			oneOf(agent0).classify(object); will(returnValue(assertion0));
			oneOf(agent1).learn(assertion0); will(returnValue(updatedAgent));
		}});

		assertThat(population.runLanguageGames(), equalTo(
				new BasicPopulation(asList(agent0, updatedAgent), objectCreator, agentPairer)));
	}

	@Test
	public void pairsOffAgentsAndGetsThemToDoLanguageGames() {
		final BasicPopulation population =
				new BasicPopulation(asList(agent0, agent1, agent2, agent3), objectCreator, agentPairer);

		context.checking(new Expectations() {{
			exactly(2).of(objectCreator).pick(); will(returnValue(object));
			oneOf(agent1).classify(object); will(returnValue(assertion0));
			oneOf(agent2).classify(object); will(returnValue(assertion1));
			oneOf(agent0).learn(assertion0); will(returnValue(updatedAgent));
			oneOf(agent3).learn(assertion1); will(returnValue(updatedAgent));
			ignoring(agent0).weight(); ignoring(agent1).weight(); ignoring(agent2).weight(); ignoring(agent3).weight();
		}});

		assertThat(population.runLanguageGames(), equalTo(
				new BasicPopulation(asList(updatedAgent, agent1, agent2, updatedAgent), objectCreator, agentPairer)));
	}

	@Test
	public void getsAverageConvergenceAcrossAllPairsOfAgents() {
		final Population population = new BasicPopulation(asList(agent0, agent1, agent2), null, null);

		context.checking(new Expectations() {{
			oneOf(agent0).convergenceWith(agent1); will(returnValue(result0));
			oneOf(agent0).convergenceWith(agent2); will(returnValue(result1));
			oneOf(agent1).convergenceWith(agent2); will(returnValue(result2));
		}});

		assertThat(population.convergence(), equalTo((result0+result1+result2)/3));
	}

}
