package agent;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.PerceptualObject;

public class Assertion {

	public final PerceptualObject object;
	public final int label;
	public final double weight;

	public Assertion(final PerceptualObject object, final int label, final double weight) {
		this.object = object;
		this.label = label;
		this.weight = weight;
	}

	public boolean matches(final Assertion other) {
		return label == other.label && object.equals(other.object);
	}

	@Override
	public String toString() {
		return "Assertion: " + object.toString() + " is " + label + ", weight " + weight;
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
