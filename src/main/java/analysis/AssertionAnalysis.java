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
		final Agent agent = agents.get(0);
		for (int i=0; i<10; i++) {
			final PerceptualObject object = objectPool.pick();
			final Assertion assertion = agent.assertion(object);
			count = count.add(assertion.label);
		}
		return (double) (count.max()) / count.total();
	}

}
