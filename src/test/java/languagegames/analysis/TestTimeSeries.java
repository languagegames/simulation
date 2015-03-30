package languagegames.analysis;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Test;

import analysis.TimeSeries;
import analysis.TimeSeriesAverage;

public class TestTimeSeries {

	@Test
	public void calculatesMeanAndStandardDeviationFromList() {
		final List<TimeSeries> results = asList(
				new TimeSeries(0.2, 0.5), new TimeSeries(0.9, 0.1), new TimeSeries(0.4, 0.3));

		assertThat(TimeSeries.average(results), equalTo(
				new TimeSeriesAverage(
						asList(0, 1),
						asList(0.5, 0.3),
						asList(0.36055512754639896, 0.2))));
	}

}
