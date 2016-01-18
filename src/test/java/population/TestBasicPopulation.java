package population;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import agent.Agent;
import agent.BasicAgentBuilder;
import agent.assertions.Assertion;
import conceptualspace.ObjectPool;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestBasicPopulation {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock Agent agent0, agent1, agent2, agent3, updatedAgent;
	@Mock ObjectPool objectPool;

	private final AgentPairer agentPairer = new StaticPairer(asList(1, 0, 2, 3));
	private final PerceptualObject object = new SimpleObject(new Point(0.42));
	private final Assertion assertion0 = new Assertion(object, 42, 0.42);
	private final Assertion assertion1 = new Assertion(object, 43, 0.42);
	private final double highWeight = 0.8, lowWeight = 0.4;
	private final AgentInteractor agentInteractor = new DifferentObservationInteractor();

	@Test
	public void agentsWeightsAreIncremented() {
		final Agent agent0 = agent().withWeight(0.5).build();
		final Agent agent1 = agent().withWeight(0.9).build();
		final BasicPopulation population = new BasicPopulation(asList(agent0, agent1), null, null, null);

		final Agent updatedAgent0 = agent().withWeight(0.6).build();
		final Agent updatedAgent1 = agent().withWeight(0.1).build();

		assertThat(population.incrementWeights(0.1),
				equalTo(new BasicPopulation(asList(updatedAgent0, updatedAgent1), null, null, null)));
	}

	private BasicAgentBuilder agent() {
		return new BasicAgentBuilder();
	}

	@Test
	public void agentWithHigherWeightIsAlwaysSpeaker() {
		final BasicPopulation population =
				new BasicPopulation(asList(agent0, agent1), objectPool, agentPairer, agentInteractor);

		context.checking(new Expectations() {{
			oneOf(agent0).weight(); will(returnValue(highWeight));
			oneOf(agent1).weight(); will(returnValue(lowWeight));
			oneOf(objectPool).pick(); will(returnValue(object));
			oneOf(agent0).assertion(object); will(returnValue(assertion0));
			oneOf(agent1).learn(object.observation(), assertion0); will(returnValue(updatedAgent));
		}});

		assertThat(population.runLanguageGames(), equalTo(
				new BasicPopulation(asList(agent0, updatedAgent), objectPool, agentPairer, agentInteractor)));
	}

	@Test
	public void pairsOffAgentsAndGetsThemToDoLanguageGames() {
		final BasicPopulation population =
				new BasicPopulation(asList(agent0, agent1, agent2, agent3), objectPool, agentPairer, agentInteractor);

		context.checking(new Expectations() {{
			exactly(2).of(objectPool).pick(); will(returnValue(object));
			oneOf(agent1).assertion(object); will(returnValue(assertion0));
			oneOf(agent2).assertion(object); will(returnValue(assertion1));
			oneOf(agent0).learn(object.observation(), assertion0); will(returnValue(updatedAgent));
			oneOf(agent3).learn(object.observation(), assertion1); will(returnValue(updatedAgent));
			ignoring(agent0).weight(); ignoring(agent1).weight(); ignoring(agent2).weight(); ignoring(agent3).weight();
		}});

		assertThat(population.runLanguageGames(), equalTo(
				new BasicPopulation(asList(updatedAgent, agent1, agent2, updatedAgent), objectPool, agentPairer, agentInteractor)));
	}

}
