package agent;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.Concept;

public class Interpretation {

	private final List<Concept> concepts = new ArrayList<>();

	public Interpretation(final Concept...concepts) {
		for (final Concept concept : concepts) {
			this.concepts.add(concept);
		}
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return concepts.toString();
	}

}
