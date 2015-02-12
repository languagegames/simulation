package agent;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.PerceptualObject;


public class BasicAgent implements Agent {

	private final List<Concept> concepts = new ArrayList<>();
	private final double weight;

	public BasicAgent(final double weight, final List<Concept> concepts) {
		this.weight = weight;
		this.concepts.addAll(concepts);
	}

	public BasicAgent(final double weight, final Concept...concepts) {
		this(weight, asList(concepts));
	}

	@Override
	public double convergenceWith(final List<Concept> concepts) {
		double result = 0;
		for (int i = 0; i < concepts.size(); i++) {
			result += this.concepts.get(i).hausdorffDistanceFrom(concepts.get(i)) / concepts.size();
		}
		return result;
	}

	@Override
	public double convergenceWith(final Agent other) {
		return other.convergenceWith(concepts);
	}

	@Override
	public Assertion classify(final PerceptualObject object) {
		Concept mostAppropriate = concepts.get(0);
		double maxAppropriateness = 0;
		for (final Concept concept : concepts) {
			if (concept.appropriatenessOf(object.observation()) > maxAppropriateness) {
				mostAppropriate = concept;
				maxAppropriateness = mostAppropriate.appropriatenessOf(object.observation());
			}
		}
		return new Assertion(object, concepts.indexOf(mostAppropriate), weight);
	}

	@Override
	public Agent learn(final Assertion assertion) {
		final Concept toUpdate = concepts.get(assertion.label);
		final Concept updated = toUpdate.update(assertion);
		final List<Concept> newConcepts = new ArrayList<>(concepts);
		newConcepts.set(assertion.label, updated);
		return new BasicAgent(weight, newConcepts);
	}

	@Override
	public String toString() {
		return "Basic agent with " + concepts.toString();
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
