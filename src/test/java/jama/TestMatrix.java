package jama;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import datareader.DataReader;

public class TestMatrix {

	@Test
	public void calculateCovariance() {
		final Matrix matrix = new Matrix(new DataReader().array2d("randomdata.csv"));

		final double[][] covariance = {{0.12677777777777777,0.012555555555555554}, {0.012555555555555554,0.07211111111111113}};

		assertThat(matrix.covariance(), equalTo(new Matrix(covariance)));
	}

	@Test
	public void calculateMean() {
		final Matrix matrix = new Matrix(new DataReader().array2d("randomdata.csv"));

		final double[][] mean = {{0.43000000000000005,0.39}};

		assertThat(matrix.mean(), equalTo(new Matrix(mean)));
	}

}
