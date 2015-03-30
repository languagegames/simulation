package agent.concept;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import agent.assertions.Assertion;
import conceptualspace.Point;

public class MaxLikelihoodConcept implements Concept {

	private final List<Point> points = new ArrayList<>();
	private final LikelihoodModel likelihoodModel;

	public MaxLikelihoodConcept(final LikelihoodModel likelihoodModel) {
		this.likelihoodModel = likelihoodModel;
	}

	public MaxLikelihoodConcept(final List<Point> points, final LikelihoodModel likelihoodModel) {
		this(likelihoodModel);
	}

	public MaxLikelihoodConcept(final List<Point> points) {
		this(points, new IndependentGaussianModel(points));
	}

	public MaxLikelihoodConcept(final Point...points) {
		this(asList(points));
	}

	@Override
	public MaxLikelihoodConcept update(final Assertion assertion) {
		return new MaxLikelihoodConcept(points, likelihoodModel.update(assertion.object.observation()));
	}

	@Override
	public double appropriatenessOf(final Point point) {
		return likelihoodModel.likelihood(point);
	}

	@Override
	public String toString() {
		return "ML concept with " + likelihoodModel.toString();
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
