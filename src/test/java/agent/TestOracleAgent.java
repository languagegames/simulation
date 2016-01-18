package agent;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import agent.assertions.Assertion;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestOracleAgent {

	private final PerceptualObject object0 = someObject(0.42);
	private final PerceptualObject object1 = someObject(0.43);
	private final PerceptualObject object2 = someObject(0.44);
	private final List<PerceptualObject> objects = new ArrayList<>();
	private final double someWeight = 0.42;

	@Before
	public void setUp() {
		objects.addAll(asList(object0, object1, object2));
	}

	@Test
	public void guessesZeroIfNoObjectMatchesAssertionLabel() {
		final OracleAgent agent = new OracleAgent(new LabelMapping(objects, asList(1, 2, 0)), someWeight);

		final List<PerceptualObject> guessingSet = new ArrayList<>();
		guessingSet.addAll(asList(object0, object1));

		assertThat(agent.guess(guessingSet, new Assertion(0, someWeight)), equalTo(0));
	}

	@Test
	public void guessesFirstObjectWhichMatchesAssertionLabel() {
		final OracleAgent agent = new OracleAgent(new LabelMapping(objects, asList(1, 2, 0)), someWeight);

		final List<PerceptualObject> guessingSet = new ArrayList<>();
		guessingSet.addAll(objects);

		assertThat(agent.guess(guessingSet, new Assertion(0, someWeight)), equalTo(2));
	}

	@Test
	public void classifiesAccordingToPredefinedLabelMapping() {
		final OracleAgent agent = new OracleAgent(new LabelMapping(objects, asList(1, 2, 0)), someWeight);

		assertThat(agent.assertion(object0), equalTo(new Assertion(1, someWeight)));
		assertThat(agent.assertion(object1), equalTo(new Assertion(2, someWeight)));
		assertThat(agent.assertion(object2), equalTo(new Assertion(0, someWeight)));
	}

	private PerceptualObject someObject(final double d) {
		return new SimpleObject(new Point(d));
	}

}
