package agent;

import static agent.Assertion.categoryGameAssertion;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import classifier.ExperimentData;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestOracleAgent {

	private final PerceptualObject object0 = someObject(0.42);
	private final PerceptualObject object1 = someObject(0.43);
	private final PerceptualObject object2 = someObject(0.44);
	private final ExperimentData data = new ExperimentData(asList(object0, object1, object2), 0.42);
	private final double someWeight = 0.42;

	@Test
	public void guessesZeroIfNoObjectMatchesAssertionLabel() {
		final OracleAgent agent = new OracleAgent(new LabelMapping(data, asList(1, 2, 0)), someWeight);

		final List<PerceptualObject> guessingSet = new ArrayList<>();
		guessingSet.addAll(asList(object0, object1));

		assertThat(agent.guess(guessingSet, categoryGameAssertion(object0, 0, someWeight)), equalTo(0));
	}

	@Test
	public void guessesFirstObjectWhichMatchesAssertionLabel() {
		final OracleAgent agent = new OracleAgent(new LabelMapping(data, asList(1, 2, 0)), someWeight);

		final List<PerceptualObject> guessingSet = new ArrayList<>();
		guessingSet.addAll(asList(object0, object1, object2));

		assertThat(agent.guess(guessingSet, categoryGameAssertion(object0, 0, someWeight)), equalTo(2));
	}

	@Test
	public void classifiesAccordingToPredefinedLabelMapping() {
		final OracleAgent agent = new OracleAgent(new LabelMapping(data, asList(1, 2, 0)), someWeight);

		assertThat(agent.assertion(object0), equalTo(categoryGameAssertion(object0, 1, someWeight)));
		assertThat(agent.assertion(object1), equalTo(categoryGameAssertion(object1, 2, someWeight)));
		assertThat(agent.assertion(object2), equalTo(categoryGameAssertion(object2, 0, someWeight)));
	}

	private PerceptualObject someObject(final double d) {
		return new SimpleObject(new Point(d));
	}

}
