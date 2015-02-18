package agent;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.Point;

public class Concept {

	private final Point prototype;
	private final double threshold;

	public Concept(final Point prototype, final double threshold) {
		this.prototype = prototype;
		this.threshold = threshold;
	}

	public double overlapWith(final Concept other) {
		final double prototypeDistance = prototype.minus(other.prototype).norm();
		final double sumOfThresholds = threshold + other.threshold;
		if (prototypeDistance > sumOfThresholds) {
			return 0;
		}
		return 1 - prototypeDistance / sumOfThresholds;
	}

	public double hausdorffDistanceFrom(final Concept other) {
		return prototype.minus(other.prototype).norm() + abs(threshold-other.threshold)/2;
	}

	public double appropriatenessOf(final Point observation) {
		return max(1 - observation.minus(prototype).norm()/threshold, 0);
	}

	public Concept update(final Assertion assertion) {
		final Point target = assertion.object.observation();
		if (appropriatenessOf(target) < assertion.weight) {
			final Point newPrototype = prototype.plus((target.minus(prototype)).times(lambda(assertion.weight, target)));
			final double newThreshold = alpha(assertion.weight, target) * threshold;
			return new Concept(newPrototype, newThreshold);
		}
		return this;
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
