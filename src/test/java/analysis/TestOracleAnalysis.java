package analysis;

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
import population.AgentInteractor;
import population.DifferentObservationInteractor;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestOracleAnalysis {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    Agent oracle, agent0, agent1;
    @Mock
    ObjectPool objectPool;

    private final Point observation = new Point(0.42);
    private final PerceptualObject object = new SimpleObject(observation);
    private final List<Agent> agents = new ArrayList<Agent>();
    private final AgentInteractor agentInteractor = new DifferentObservationInteractor();

    @Test
    public void comparesAssertionsAcrossPairsOfAgents() {
        agents.addAll(asList(agent0, agent1));
        final int numGames = 1;
        final OracleAnalysis analysis = new OracleAnalysis(oracle, numGames, objectPool, agentInteractor);

        final Assertion assertion0 = new Assertion(0, 0.42);
        final Assertion assertion1 = new Assertion(1, 0.42);

        context.checking(new Expectations() {{
            exactly(2).of(objectPool).pick(numGames);
            will(returnValue(asList(object)));
            oneOf(agent1).assertion(observation);
            will(returnValue(assertion0));
            oneOf(agent0).assertion(observation);
            will(returnValue(assertion0));
            oneOf(oracle).assertion(observation);
            will(returnValue(assertion0));
            oneOf(oracle).assertion(observation);
            will(returnValue(assertion1));
        }});

        assertThat(analysis.analyse(agents), equalTo(0.5));
    }

}
