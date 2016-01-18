package agent.concept;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import agent.assertions.Assertion;
import conceptualspace.Point;

public class FuzzyConcept implements Concept {

	private final Point prototype;
	private final double threshold;

	public FuzzyConcept(final Point prototype, final double threshold) {
		this.prototype = prototype;
		this.threshold = threshold;
	}

	public double overlapWith(final FuzzyConcept other) {
		final double prototypeDistance = prototype.minus(other.prototype).norm();
		final double sumOfThresholds = threshold + other.threshold;
		if (prototypeDistance > sumOfThresholds) {
			return 0;
		}
		return 1 - prototypeDistance / sumOfThresholds;
	}

	public double hausdorffDistanceFrom(final FuzzyConcept other) {
		return prototype.minus(other.prototype).norm() + abs(threshold-other.threshold)/2;
	}

	@Override
	public double appropriatenessOf(final Point observation) {
		return max(1 - observation.minus(prototype).norm()/threshold, 0);
	}

	@Override
	public FuzzyConcept update(final Point observation, final Assertion assertion) {
		if (appropriatenessOf(observation) < assertion.weight) {
			final Point newPrototype = prototype.plus((observation.minus(prototype)).times(lambda(assertion.weight, observation)));
			final double newThreshold = alpha(assertion.weight, observation) * threshold;
			return new FuzzyConcept(newPrototype, newThreshold);
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
