package conceptualspace;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class SimpleMaterial implements Material {

	private final Point point;

	public SimpleMaterial(final Point point) {
		this.point = point;
	}

	@Override
	public Point observation() {
		return point;
	}

	@Override
	public String toString() {
		return "Simple material @ " + point.toString();
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public static List<Material> makeListFrom(final List<Point> points) {
		// TODO Auto-generated method stub
		return null;
	}

}
