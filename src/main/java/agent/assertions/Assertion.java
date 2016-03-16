package agent.assertions;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Assertion {

    private final List<Integer> labels = new ArrayList<Integer>();
    public final int label;
    public final double weight;

    private Assertion(final List<Integer> labels, final double weight) {
        this.labels.addAll(labels);
        label = labels.get(0);
        this.weight = weight;
    }

    public Assertion(final int label, final double weight) {
        this(asList(label), weight);
    }

    public Assertion(final int label0, final int label1, final double weight) {
        this(asList(label0, label1), weight);
    }

    public List<Integer> labels() {
        return new ArrayList<Integer>(labels);
    }

    public boolean matches(final Assertion other) {
        return label == other.label;
    }

    @Override
    public String toString() {
        return "Assertion: " + labels.toString() + ", weight " + weight;
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
