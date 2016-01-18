package population;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import agent.Agent;
import agent.assertions.Assertion;
import conceptualspace.ObjectPool;
import conceptualspace.PerceptualObject;

public class DifferentObservationInteractor implements AgentInteractor {

	@Override
	public int communicationGameResult(final Agent agent, final Agent other,
			final PerceptualObject object) {
		final Assertion first = agent.assertion(object.observation());
		final Assertion second = other.assertion(object.observation());
		return first.matches(second) ? 1 : 0;
	}

	@Override
	public void updateListener(
			final List<Agent> agents,
			final List<Agent> updatedAgents,
			final Agent speaker,
			final Agent listener,
			final ObjectPool objectPool)
	{
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
