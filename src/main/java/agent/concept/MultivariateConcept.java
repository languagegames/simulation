package agent.concept;

import static java.lang.Math.PI;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import jama.Matrix;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import agent.Assertion;
import conceptualspace.Point;

public class MultivariateConcept implements Concept {

	private final Matrix data;
	private final Matrix mu;
	private final Matrix sigma;

	public MultivariateConcept(final Matrix data) {
		this.data = data;
		mu = data.mean();
		sigma = data.covariance();
	}

	@Override
	public Concept update(final Assertion assertion) {
		Matrix data = this.data;
		while (data.getRowDimension() >= 20) {
			data = data.removeFirstRow();
		}
		final Matrix x = assertion.object.observation().asMatrix();
		return new MultivariateConcept(data.vertCat(x));
	}

	@Override
	public double appropriatenessOf(final Point observation) {
		final Matrix x = observation.asMatrix();
		return (1./sqrt(sigma.det() * pow(2*PI, data.getColumnDimension()))) * exponent(x);
	}

	private double exponent(final Matrix x) {
		final Matrix subtractedMean = x.minus(mu);
		final Matrix product = subtractedMean.times(sigma.inverse()).times(subtractedMean.transpose());
		return exp(-0.5 * product.get(0, 0));
	}

	@Override
	public String toString() {
		return "Multivariate Gaussian concept with mean " + mu + ", covariance" + sigma;
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
