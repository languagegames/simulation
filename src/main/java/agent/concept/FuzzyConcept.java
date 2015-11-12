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
	private final int numObservations;

	public FuzzyConcept(final Point prototype, final double threshold, final int numObservations) {
		this.prototype = prototype;
		this.threshold = threshold;
		this.numObservations = numObservations;
	}

	@Override
	public FuzzyConcept update(final Assertion assertion) {
		final Point target = assertion.object.observation();
		if (appropriatenessOf(target) < assertion.weight) {
			final Point newPrototype = prototype.plus((target.minus(prototype)).times(lambda(assertion.weight, target)));
			final double newThreshold = alpha(assertion.weight, target) * threshold;
			return new FuzzyConcept(newPrototype, newThreshold, numObservations+1);
		}
		return this;
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
