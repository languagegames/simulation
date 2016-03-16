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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import population.AgentInteractor;
import population.AgentPairer;
import population.DifferentObservationInteractor;
import population.StaticPairer;
import utility.FakeRandom;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestGuessingAnalysis {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    Agent agent0, agent1;
    @Mock
    ObjectPool objectPool;

    private final AgentPairer agentPairer = new StaticPairer(asList(1, 0));
    private final Point observation = new Point(0.42);
    private final PerceptualObject target = new SimpleObject(observation);
    private final PerceptualObject otherObject = new SimpleObject(new Point(0.43));
    private final List<Agent> agents = new ArrayList<Agent>();
    private final List<PerceptualObject> guessingSet = new ArrayList<PerceptualObject>();
    private final AgentInteractor agentInteractor = new DifferentObservationInteractor();

    @Before
    public void setUp() {
        agents.addAll(asList(agent0, agent1));
    }

    @Test
    public void targetObjectIsSelectedAtRandom() {
        final int targetIndex = 2;
        guessingSet.addAll(asList(otherObject, otherObject, target, otherObject, otherObject));
        final GuessingAnalysis analysis = new GuessingAnalysis(
                1, 5, agentPairer, objectPool, new FakeRandom(targetIndex), agentInteractor);

        final Assertion assertion = new Assertion(targetIndex, 0.42);

        context.checking(new Expectations() {{
            oneOf(objectPool).pick(5);
            will(returnValue(guessingSet));
            oneOf(agent1).assertion(observation);
            will(returnValue(assertion));
            oneOf(agent0).guess(observations(guessingSet), assertion);
            will(returnValue(targetIndex));
        }});

        assertThat(analysis.analyse(agents), equalTo(1.0));
    }

    @Test
    public void pairSucceedsIfTargetObjectIsGuessedCorrectly() {
        guessingSet.addAll(asList(target, otherObject, otherObject, otherObject, otherObject));
        final GuessingAnalysis analysis = new GuessingAnalysis(
                1, 5, agentPairer, objectPool, new FakeRandom(0), agentInteractor);

        final Assertion assertion = new Assertion(0, 0.42);

        context.checking(new Expectations() {{
            oneOf(objectPool).pick(5);
            will(returnValue(guessingSet));
            oneOf(agent1).assertion(observation);
            will(returnValue(assertion));
            oneOf(agent0).guess(observations(guessingSet), assertion);
            will(returnValue(0));
        }});

        assertThat(analysis.analyse(agents), equalTo(1.0));
    }

    private List<Point> observations(final List<PerceptualObject> guessingSet) {
        final List<Point> observations = new ArrayList<Point>();
        for (final PerceptualObject object : guessingSet) {
            observations.add(object.observation());
        }
        return observations;
    }

}
