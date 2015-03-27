package utility;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.List;

public class Stats {

	public static double mean(final List<Double> scores) {
		double sum = 0;
		for (final Double score : scores) {
			sum += score;
		}
		return sum / scores.size();
	}

	public static double standardDeviation(final List<Double> scores) {
		final double mean = mean(scores);
		double sumOfSquares = 0;
		for (final Double score : scores) {
			sumOfSquares += pow(score - mean, 2);
		}
		return sqrt(sumOfSquares / (scores.size()-1));
	}

}
