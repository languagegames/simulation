package agent;

import static agent.Assertion.categoryGameAssertion;
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
	public void calculatesOverlapWithAnotherConcept() {
		final FuzzyConcept concept = new FuzzyConcept(new Point(0.2), 0.8);
		final FuzzyConcept other = new FuzzyConcept(new Point(0.4), 0.5);

		assertThat(concept.overlapWith(other), equalTo(1 - (0.4 - 0.2) / (0.8 + 0.5)));
	}

	@Test
	public void onlyUpdateIfCurrentAppropriatenessIsLessThanWeight() {
		final FuzzyConcept concept = new FuzzyConcept(new Point(0.5, 0.7), 0.5);

		final PerceptualObject object = new SimpleObject(new Point(0.6, 0.8));

		assertThat(concept.update(categoryGameAssertion(object, 42, 0.7)),
				equalTo(concept));
	}

	@Test
	public void minimumAppropriatenessIsZero() {
		final Concept concept = new FuzzyConcept(new Point(0.1, 0.1), 0.5);
		assertThat(concept.appropriatenessOf(new Point(0.9, 0.9)), equalTo(0.0));
	}

	@Test
	public void calculatesHausdorffDistanceFromAnotherConcept() {
		final FuzzyConcept concept = new FuzzyConcept(new Point(0.5, 0.7), 0.5);
		final FuzzyConcept other = new FuzzyConcept(new Point(0.2, 0.2), 0.8);

		assertThat(concept.hausdorffDistanceFrom(other), closeTo(0.7331, 0.0001));
	}

	@Test
	public void calculatesAppropriatenessOfAnObservation() {
		final Concept concept = new FuzzyConcept(new Point(0.5, 0.7), 0.5);
		assertThat(concept.appropriatenessOf(new Point(0.4, 0.3)), closeTo(0.1754, 0.0001));
	}

	@Test
	public void updatesAccordingToTargetAndWeight() {
		final Concept concept = new FuzzyConcept(new Point(0.5, 0.7), 0.5);
		final PerceptualObject object = new SimpleObject(new Point(0.9, 0.1));
		final Concept updated = new FuzzyConcept(new Point(0.7756239843019817, 0.2865640235470274), 1.1211102550927978);

		assertThat(concept.update(categoryGameAssertion(object, 42, 0.8)), equalTo(updated));
	}

}
