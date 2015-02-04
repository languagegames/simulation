package experiment;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.Material;

public class Assertion {

	public final Material material;
	public final int label;
	public final double weight;

	public Assertion(final Material material, final int label, final double weight) {
		this.material = material;
		this.label = label;
		this.weight = weight;
	}

	public boolean matches(final Assertion other) {
		return label == other.label && material.equals(other.material);
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
