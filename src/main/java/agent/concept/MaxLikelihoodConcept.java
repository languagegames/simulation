package agent.concept;

import agent.assertions.Assertion;
import conceptualspace.Point;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import utility.matrix.Matrix;

public class MaxLikelihoodConcept implements Concept {

    private final MultivariateGaussianModel likelihoodModel;

    public MaxLikelihoodConcept(final Matrix data) {
        this.likelihoodModel = new MultivariateGaussianModel(data);
    }

    public MaxLikelihoodConcept(final MultivariateGaussianModel likelihoodModel) {
        this.likelihoodModel = likelihoodModel;
    }

    @Override
    public MaxLikelihoodConcept update(final Point observation, final Assertion assertion) {
        return new MaxLikelihoodConcept(likelihoodModel.update(observation));
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
