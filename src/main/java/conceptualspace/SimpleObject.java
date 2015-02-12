package conceptualspace;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class SimpleObject implements PerceptualObject {

	private final Point point;

	public SimpleObject(final Point point) {
		this.point = point;
	}

	@Override
	public Point observation() {
		return point;
	}

	public static List<PerceptualObject> makeListFrom(final List<Point> points) {
		final List<PerceptualObject> materials = new ArrayList<>();
		for (final Point point : points) {
			materials.add(new SimpleObject(point));
		}
		return materials;
	}

	@Override
	public String toString() {
		return "Simple object @ " + point.toString();
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
