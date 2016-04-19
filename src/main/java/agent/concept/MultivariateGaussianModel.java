package agent.concept;

import conceptualspace.Point;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import utility.matrix.Matrix;

import static java.lang.Math.*;

public class MultivariateGaussianModel {

    private final Matrix data;
    private final Matrix mu;
    private final Matrix sigma;

    public MultivariateGaussianModel(final Matrix data) {
        this.data = data;
        mu = data.mean();
        sigma = data.covariance();
    }

    public MultivariateGaussianModel update(final Point point) {
        Matrix data = this.data;
        while (data.getRowDimension() >= 20) {
            data = data.removeFirstRow();
        }
        final Matrix x = point.asMatrix();
        return new MultivariateGaussianModel(data.vertCat(x));
    }

    public double likelihood(final Point point) {
        final Matrix x = point.asMatrix();
        return (1. / sqrt(sigma.det() * pow(2 * PI, data.getColumnDimension()))) * exponent(x);
    }

    private double exponent(final Matrix x) {
        final Matrix subtractedMean = x.minus(mu);
        final Matrix product = subtractedMean.times(sigma.inverse()).times(subtractedMean.transpose());
        return exp(-0.5 * product.get(0, 0));
    }

    @Override
    public String toString() {
        return "Multivariate Gaussian model with mean " + mu + ", covariance" + sigma;
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
