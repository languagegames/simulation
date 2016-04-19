package agent.concept;

import agent.assertions.Assertion;
import conceptualspace.Point;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import utility.matrix.Matrix;

public class BayesianConcept implements Concept {

    private final MultivariateGaussianModel likelihoodModel;
    private final int numObservations;

    public BayesianConcept(final Matrix data, final int numObservations) {
        this.likelihoodModel = new MultivariateGaussianModel(data);
        this.numObservations = numObservations;
    }

    public BayesianConcept(final MultivariateGaussianModel likelihoodModel, final int numObservations) {
        this.likelihoodModel = likelihoodModel;
        this.numObservations = numObservations;
    }

    @Override
    public BayesianConcept update(final Point observation, final Assertion assertion) {
        return new BayesianConcept(likelihoodModel.update(observation), numObservations + 1);
    }

    @Override
    public double appropriatenessOf(final Point point) {
        return likelihoodModel.likelihood(point) * numObservations;
    }

    @Override
    public String toString() {
        return "Bayesian concept with " + likelihoodModel.toString();
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
