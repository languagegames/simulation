package languagegames.analysis;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Test;

public class TestTimeSeries {

	@Test
	public void calculatesStandardDeviationFromList() {
		final List<TimeSeries> results = asList(
				new TimeSeries(0.2, 0.5), new TimeSeries(0.9, 0.1), new TimeSeries(0.4, 0.3));

		assertThat(TimeSeries.standardDeviation(results), equalTo(new TimeSeries(0.36055512754639896, 0.2)));
	}

	@Test
	public void calculatesMeanFromList() {
		final List<TimeSeries> results = asList(
				new TimeSeries(0.2, 0.5), new TimeSeries(0.9, 0.1), new TimeSeries(0.4, 0.3));

		assertThat(TimeSeries.mean(results), equalTo(new TimeSeries(0.5, 0.3)));
	}

}
