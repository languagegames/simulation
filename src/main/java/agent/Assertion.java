package agent;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.PerceptualObject;

public class Assertion {

	public final PerceptualObject object;
	private final List<Integer> labels = new ArrayList<>();
	public final int label;
	public final double weight;

	private Assertion(final PerceptualObject object, final List<Integer> labels, final double weight) {
		this.object = object;
		this.labels.addAll(labels);
		label = labels.get(0);
		this.weight = weight;
	}

	public Assertion(final PerceptualObject object, final int label, final double weight) {
		this(object, asList(label), weight);
	}

	public Assertion(final int label) {
		this(null, asList(label), 0);
	}

	public Assertion(final int label0, final int label1) {
		this(null, asList(label0, label1), 0);
	}

	public List<Integer> labels() {
		return new ArrayList<>(labels);
	}

	public boolean matches(final Assertion other) {
		return label == other.label && object.equals(other.object);
	}

	@Override
	public String toString() {
		return "Assertion: " + object.toString() + " is " + label + ", weight " + weight;
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
