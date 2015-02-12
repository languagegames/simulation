package agent;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestConcept {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Test
	public void onlyUpdateIfCurrentAppropriatenessIsLessThanWeight() {
		final Concept concept = new Concept(new Point(0.5, 0.7), 0.5);

		final PerceptualObject object = new SimpleObject(new Point(0.6, 0.8));

		assertThat(concept.update(new Assertion(object, 42, 0.7)),
				equalTo(concept));
	}

	@Test
	public void minimumAppropriatenessIsZero() {
		final Concept concept = new Concept(new Point(0.1, 0.1), 0.5);
		assertThat(concept.appropriatenessOf(new Point(0.9, 0.9)), equalTo(0.0));
	}

	@Test
	public void calculatesHausdorffDistanceFromAnotherConcept() {
		final Concept concept = new Concept(new Point(0.5, 0.7), 0.5);
		final Concept other = new Concept(new Point(0.2, 0.2), 0.8);

		assertThat(concept.hausdorffDistanceFrom(other), closeTo(0.7331, 0.0001));
	}

	@Test
	public void calculatesAppropriatenessOfAnObservation() {
		final Concept concept = new Concept(new Point(0.5, 0.7), 0.5);
		assertThat(concept.appropriatenessOf(new Point(0.4, 0.3)), closeTo(0.1754, 0.0001));
	}

	@Test
	public void updatesAccordingToTargetAndWeight() {
		final Concept concept = new Concept(new Point(0.5, 0.7), 0.5);

		final PerceptualObject object = new SimpleObject(new Point(0.9, 0.1));

		assertThat(concept.update(new Assertion(object, 42, 0.8)),
				equalTo(new Concept(new Point(0.7756239843019817, 0.2865640235470274), 1.1211102550927978)));
	}

}
