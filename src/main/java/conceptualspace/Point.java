package conceptualspace;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Point {

	private final List<Double> coordinates = new ArrayList<>();

	public Point(final List<Double> coordinates) {
		this.coordinates.addAll(coordinates);
	}

	public Point(final Double...coordinates) {
		this(asList(coordinates));
	}

	public double norm() {
		double sumOfSquares = 0;
		for (final Double coordinate : coordinates) {
			sumOfSquares += pow(coordinate, 2);
		}
		return sqrt(sumOfSquares);
	}

	public Point minus(final Point other) {
		final List<Double> resultCoordinates = new ArrayList<>();
		for (int i=0; i<coordinates.size(); i++) {
			resultCoordinates.add(coordinates.get(i) - other.coordinates.get(i));
		}
		return new Point(resultCoordinates);
	}

	@Override
	public String toString() {
		return coordinates.toString();
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
