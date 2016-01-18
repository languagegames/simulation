package analysis;

import java.util.List;

import agent.Agent;
import agent.assertions.Assertion;
import conceptualspace.ObjectPool;
import conceptualspace.PerceptualObject;

public class AssertionAnalysis implements Analysis {

	private final ObjectPool objectPool;

	public AssertionAnalysis(final ObjectPool objectPool) {
		this.objectPool = objectPool;
	}

	@Override
	public double analyse(final List<Agent> agents) {
		AssertionCount count = new AssertionCount();
		for (final Agent agent : agents) {
			count = updateCountFor(count, agent);
		}
		return (double) (count.max()) / count.total();
	}

	private AssertionCount updateCountFor(AssertionCount count, final Agent agent) {
		for (int i=0; i<10; i++) {
			final PerceptualObject object = objectPool.pick();
			final Assertion assertion = agent.assertion(object.observation());
			count = count.add(assertion.label);
		}
		return count;
	}

}
