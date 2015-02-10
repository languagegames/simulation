package languagegames.analysis;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class TimeSeries {

	private final List<Double> values = new ArrayList<>();

	public TimeSeries(final List<Double> values) {
		this.values.addAll(values);
	}

	public TimeSeries(final Double...values) {
		this(asList(values));
	}

	@Override
	public String toString() {
		return values.toString();
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
