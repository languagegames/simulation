package conceptualspace;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import experiment.Assertion;

public class Concept {

	private final Point prototype;
	private final double threshold;

	public Concept(final Point prototype, final double threshold) {
		this.prototype = prototype;
		this.threshold = threshold;
	}

	public Concept update(final Assertion assertion) {
		final Point target = assertion.material.observation();
		final Point newPrototype = prototype.plus((target.minus(prototype)).times(lambda(assertion.weight, target)));
		final double newThreshold = alpha(assertion.weight, target) * threshold;
		return new Concept(newPrototype, newThreshold);
	}

	private double lambda(final double weight, final Point target) {
		return weight * (1-threshold*(1-weight)/target.minus(prototype).norm());
	}

	private double alpha(final double weight, final Point target) {
		return target.minus(prototype).norm() / threshold + weight;
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
		return "Concept(" + prototype.toString() + ", " + threshold + ")";
	}

}
