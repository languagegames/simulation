package agent;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.Concept;
import conceptualspace.Material;
import experiment.Assertion;


public class BasicAgent implements Agent {

	private final List<Concept> concepts = new ArrayList<>();

	public BasicAgent(final Concept...concepts) {
		for (final Concept concept : concepts) {
			this.concepts.add(concept);
		}
	}

	@Override
	public Assertion classify(final Material material) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Agent learn(final Assertion label) {
		// TODO Auto-generated method stub
		return null;
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
