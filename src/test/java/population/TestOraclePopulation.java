package population;

import agent.Agent;
import agent.assertions.Assertion;
import conceptualspace.ObjectPool;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestOraclePopulation {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    Agent agent1, agent2, agent3, oracle, oracle2, updatedAgent;
    @Mock
    ObjectPool objectPool;

    private final AgentPairer agentPairer = new StaticPairer(asList(1, 0, 3, 2));
    private final PerceptualObject object = new SimpleObject(new Point(0.42));
    private final Assertion assertion0 = new Assertion(42, 0.42);
    private final Assertion assertion1 = new Assertion(43, 0.42);
    private final AgentInteractor agentInteractor = new DifferentObservationInteractor();

    @Test
    public void ifOraclesArePairedThenNothingHappens() {
        final OraclePopulation population = new OraclePopulation(
                asList(agent1, agent2), asList(oracle, oracle2), objectPool, agentPairer, agentInteractor);

        context.checking(new Expectations() {{
            oneOf(objectPool).pick();
            will(returnValue(object));
            oneOf(agent2).assertion(object.observation());
            will(returnValue(assertion0));
            oneOf(agent1).learn(object.observation(), assertion0);
            will(returnValue(updatedAgent));
            atLeast(1).of(oracle).weight(); will(returnValue(0.95));
            atLeast(1).of(oracle2).weight(); will(returnValue(0.95));
            atLeast(1).of(agent1).weight(); will(returnValue(0.7));
            atLeast(1).of(agent2).weight(); will(returnValue(0.8));
        }});

        assertThat(population.runLanguageGames(), equalTo(
                new OraclePopulation(asList(updatedAgent, agent2), asList(oracle, oracle2), objectPool, agentPairer, agentInteractor)));
    }

    @Test
    public void worksWhenNumberOfAllAgentsIsOdd() {
        final OraclePopulation population = new OraclePopulation(
                asList(agent1, agent2), asList(oracle), objectPool, agentPairer, agentInteractor);

        context.checking(new Expectations() {{
            oneOf(objectPool).pick();
            will(returnValue(object));
            oneOf(agent2).assertion(object.observation());
            will(returnValue(assertion0));
            oneOf(agent1).learn(object.observation(), assertion0);
            will(returnValue(updatedAgent));
            ignoring(agent1).weight();
            ignoring(agent2).weight();
        }});

        assertThat(population.runLanguageGames(), equalTo(
                new OraclePopulation(asList(updatedAgent, agent2), asList(oracle), objectPool, agentPairer, agentInteractor)));
    }

    @Test
    public void agentsWeightsAreIncremented() {
        final OraclePopulation population = new OraclePopulation(asList(agent1), asList(oracle), null, null, null);

        final double weightIncrement = 0.42;
        context.checking(new Expectations() {{
            oneOf(agent1).incrementWeight(weightIncrement);
            will(returnValue(updatedAgent));
            oneOf(oracle).incrementWeight(weightIncrement);
            will(returnValue(updatedAgent));
        }});

        assertThat(population.incrementWeights(weightIncrement),
                equalTo(new OraclePopulation(asList(updatedAgent), asList(updatedAgent), null, null, null)));
    }

    @Test
    public void usesAgentsAndOraclesInLanguageGames() {
        final OraclePopulation population = new OraclePopulation(
                asList(agent1, agent2, agent3), asList(oracle), objectPool, agentPairer, agentInteractor);

        context.checking(new Expectations() {{
            exactly(2).of(objectPool).pick();
            will(returnValue(object));
            oneOf(agent2).assertion(object.observation());
            will(returnValue(assertion0));
            oneOf(oracle).assertion(object.observation());
            will(returnValue(assertion1));
            oneOf(agent1).learn(object.observation(), assertion0);
            will(returnValue(updatedAgent));
            oneOf(agent3).learn(object.observation(), assertion1);
            will(returnValue(updatedAgent));
            ignoring(agent1).weight();
            ignoring(agent2).weight();
            ignoring(agent3).weight();
            ignoring(oracle).weight();
        }});

        assertThat(population.runLanguageGames(),
                equalTo(new OraclePopulation(
                        asList(updatedAgent, agent2, updatedAgent), asList(oracle), objectPool, agentPairer, agentInteractor)));

    }

}
