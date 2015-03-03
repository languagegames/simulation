package agent;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.PerceptualObject;

public class Assertion {

	public static Assertion positiveAssertion(final PerceptualObject object, final int label, final double weight) {
		return new Assertion(object, label, weight, true);
	}

	public static Assertion negativeAssertion(final PerceptualObject object, final int label, final double weight) {
		return new Assertion(object, label, weight, false);
	}

	public final PerceptualObject object;
	public final int label;
	public final double weight;
	public final boolean isPositive;

	public Assertion(final PerceptualObject object, final int label, final double weight, final boolean isPositive) {
		this.object = object;
		this.label = label;
		this.weight = weight;
		this.isPositive = isPositive;
	}

	public Assertion(final PerceptualObject object, final int label, final double weight) {
		this(object, label, weight, true);
	}

	public boolean matches(final Assertion other) {
		return label == other.label && object.equals(other.object);
	}

	@Override
	public String toString() {
		if (!isPositive) {
			return "Assertion: " + object.toString() + " is NOT " + label + ", weight " + weight;
		}
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
