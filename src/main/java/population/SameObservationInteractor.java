package population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import agent.Agent;
import agent.assertions.Assertion;
import conceptualspace.ObjectPool;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;

public class SameObservationInteractor implements AgentInteractor {

	@Override
	public void categoryGame(final List<Agent> agents, final List<Agent> updatedAgents,
			final Agent speaker, final Agent listener, final ObjectPool objectPool) {
		final Point observation = objectPool.pick().observation();
		final Assertion speakerAssertion = speaker.assertion(observation);
		updatedAgents.set(agents.indexOf(listener), listener.learn(observation, speakerAssertion));
	}

	@Override
	public int communicationGameResult(final Agent agent, final Agent other,
			final PerceptualObject object) {
		final Point observation = object.observation();
		final Assertion first = agent.assertion(observation);
		final Assertion second = other.assertion(observation);
		return first.matches(second) ? 1 : 0;
	}

	@Override
	public int guessingGameResult(final Agent describer, final Agent guesser,
			final ObjectPool objectPool, final int numObjects, final Random random) {
		final List<PerceptualObject> guessingSet = objectPool.pick(numObjects);
		final int targetIndex = random.nextInt(numObjects);
		final List<Point> observations = observations(guessingSet);
		final Assertion assertion = describer.assertion(observations.get(targetIndex));
		return (guesser.guess(observations, assertion) == targetIndex) ? 1 : 0;
	}

	private List<Point> observations(final List<PerceptualObject> guessingSet) {
		final List<Point> observations = new ArrayList<>();
		for (final PerceptualObject object : guessingSet) {
			observations.add(object.observation());
		}
		return observations;
	}

}
