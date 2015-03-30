package analysis;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import population.AgentPairer;
import population.StaticPairer;
import utility.FakeRandom;
import agent.Agent;
import agent.assertions.Assertion;
import analysis.GuessingAnalysis;
import conceptualspace.ObjectPool;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestGuessingAnalysis {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock Agent agent0, agent1;
	@Mock ObjectPool objectPool;

	private final AgentPairer agentPairer = new StaticPairer(asList(1, 0));
	private final PerceptualObject target = new SimpleObject(new Point(0.42));
	private final PerceptualObject otherObject = new SimpleObject(new Point(0.43));
	private final List<Agent> agents = new ArrayList<>();
	private final List<PerceptualObject> guessingSet = new ArrayList<>();

	@Before
	public void setUp() {
		agents.addAll(asList(agent0, agent1));
	}

	@Test
	public void targetObjectIsSelectedAtRandom() {
		final int targetIndex = 2;
		guessingSet.addAll(asList(otherObject, otherObject, target, otherObject, otherObject));
		final GuessingAnalysis analysis = new GuessingAnalysis(1, 5, agentPairer, objectPool, new FakeRandom(targetIndex));

		final Assertion assertion = new Assertion(otherObject, targetIndex, 0.42);

		context.checking(new Expectations() {{
			oneOf(objectPool).pick(5); will(returnValue(guessingSet));
			oneOf(agent1).assertion(target); will(returnValue(assertion));
			oneOf(agent0).guess(guessingSet, assertion); will(returnValue(targetIndex));
		}});

		assertThat(analysis.analyse(agents), equalTo(1.0));
	}

	@Test
	public void pairSucceedsIfTargetObjectIsGuessedCorrectly() {
		guessingSet.addAll(asList(target, otherObject, otherObject, otherObject, otherObject));
		final GuessingAnalysis analysis = new GuessingAnalysis(1, 5, agentPairer, objectPool, new FakeRandom(0));

		final Assertion assertion = new Assertion(otherObject, 0, 0.42);

		context.checking(new Expectations() {{
			oneOf(objectPool).pick(5); will(returnValue(guessingSet));
			oneOf(agent1).assertion(target); will(returnValue(assertion));
			oneOf(agent0).guess(guessingSet, assertion); will(returnValue(0));
		}});

		assertThat(analysis.analyse(agents), equalTo(1.0));
	}

}
