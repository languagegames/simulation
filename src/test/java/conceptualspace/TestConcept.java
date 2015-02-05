package conceptualspace;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import experiment.Assertion;

public class TestConcept {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock Material material;

	@Test
	public void updatesAccordingToTargetAndWeight() {
		final Concept concept = new Concept(new Point(0.5, 0.7), 0.5);

		context.checking(new Expectations() {{
			oneOf(material).observation(); will(returnValue(new Point(0.9, 0.1)));
		}});

		final Concept updatedConcept = new Concept(new Point(0.7756239843019817, 0.2865640235470274), 1.1211102550927978);

		assertThat(concept.update(new Assertion(material, 42, 0.8)),
				equalTo(updatedConcept));
	}

}
