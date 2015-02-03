package conceptualspace;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Point {

	private final List<Double> coordinates = new ArrayList<>();

	public Point(final double...coordinates) {
		for (final double coordinate : coordinates) {
			this.coordinates.add(coordinate);
		}
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
