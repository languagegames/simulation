package languagegames;

import java.util.ArrayList;
import java.util.List;

import languagegames.analysis.Analysis;
import languagegames.analysis.TimeSeries;

public class SimulationHistory {

	private final List<Population> populations = new ArrayList<>();

	public SimulationHistory(final List<Population> populations) {
		this.populations.addAll(populations);
	}

	public TimeSeries timeSeriesFrom(final Analysis analysis) {
		final List<Double> results = new ArrayList<>();
		for (final Population population : populations) {
			results.add(analysis.analyse(population));
		}
		return new TimeSeries(results);
	}

}
