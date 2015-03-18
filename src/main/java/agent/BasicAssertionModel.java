package agent;

import static agent.Assertion.categoryGameAssertion;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.PerceptualObject;
import conceptualspace.Point;

public class BasicAssertionModel implements AssertionModel {

	@Override
	public Assertion assertion(final PerceptualObject object, final List<Concept> concepts, final double weight) {
		Concept mostAppropriate = concepts.get(0);
		double maxAppropriateness = 0;
		for (final Concept concept : concepts) {
			final Point observation = object.observation();
			if (concept.appropriatenessOf(observation) > maxAppropriateness) {
				mostAppropriate = concept;
				maxAppropriateness = mostAppropriate.appropriatenessOf(observation);
			}
		}
		return categoryGameAssertion(object, concepts.indexOf(mostAppropriate), weight);
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
