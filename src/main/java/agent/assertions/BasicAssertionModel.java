package agent.assertions;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import agent.concept.Concept;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;

public class BasicAssertionModel implements AssertionModel {

	@Override
	public Assertion assertion(final PerceptualObject object, final List<Concept> concepts, final double weight) {
		Concept mostAppropriate = concepts.get(new Random().nextInt(concepts.size()));
		double maxAppropriateness = 0;
		for (final Concept concept : concepts) {
			final Point observation = object.observation();
			if (concept.appropriatenessOf(observation) > maxAppropriateness) {
				mostAppropriate = concept;
				maxAppropriateness = mostAppropriate.appropriatenessOf(observation);
			}
		}
		return new Assertion(concepts.indexOf(mostAppropriate), weight);
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
