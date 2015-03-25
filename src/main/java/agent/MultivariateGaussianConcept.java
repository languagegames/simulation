package agent;

import static java.lang.Math.PI;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import jama.Matrix;
import conceptualspace.Point;

public class MultivariateGaussianConcept implements Concept {

	private final Matrix data;
	private final Matrix mu;
	private final Matrix sigma;

	public MultivariateGaussianConcept(final Matrix data) {
		this.data = data;
		mu = data.mean();
		sigma = data.covariance();
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
	public Concept update(final Assertion assertion) {
		// TODO Auto-generated method stub
		return null;
	}

}
