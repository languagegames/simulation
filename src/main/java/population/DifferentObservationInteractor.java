package population;

import agent.Agent;
import agent.assertions.Assertion;
import conceptualspace.ObjectPool;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DifferentObservationInteractor implements AgentInteractor {

    public int guessingGameResult(
            final Agent describer,
            final Agent guesser,
            final ObjectPool objectPool,
            final int numObjects,
            final Random random) {
        final List<PerceptualObject> guessingSet = objectPool.pick(numObjects);
        final int targetIndex = random.nextInt(numObjects);
        final Assertion assertion = describer.assertion(guessingSet.get(targetIndex).observation());
        return (guesser.guess(observations(guessingSet), assertion) == targetIndex) ? 1 : 0;
    }

    private List<Point> observations(final List<PerceptualObject> guessingSet) {
        final List<Point> observations = new ArrayList<Point>();
        for (final PerceptualObject object : guessingSet) {
            observations.add(object.observation());
        }
        return observations;
    }

    public int communicationGameResult(final Agent agent, final Agent other,
                                       final PerceptualObject object) {
        final Assertion first = agent.assertion(object.observation());
        final Assertion second = other.assertion(object.observation());
        return first.matches(second) ? 1 : 0;
    }

    public void categoryGame(
            final List<Agent> agents,
            final List<Agent> updatedAgents,
            final Agent speaker,
            final Agent listener,
            final ObjectPool objectPool) {
        final PerceptualObject object = objectPool.pick();
        final Assertion speakerAssertion = speaker.assertion(object.observation());
        updatedAgents.set(agents.indexOf(listener), listener.learn(object.observation(), speakerAssertion));
    }

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
