package experiment;

import java.util.ArrayList;
import java.util.List;

import population.Population;
import experiment.analysis.Analysis;
import experiment.analysis.TimeSeries;

public class SimulationHistory {

	private final List<Population> populations = new ArrayList<>();

	public SimulationHistory(final List<Population> populations) {
		this.populations.addAll(populations);
	}

	public TimeSeries timeSeriesFrom(final Analysis analysis, final List<Integer> timeSteps) {
		final List<Double> results = new ArrayList<>();
		for (final Integer t : timeSteps) {
			results.add(populations.get(t).apply(analysis));
		}
		return new TimeSeries(results, timeSteps);
	}

	public TimeSeries timeSeriesFrom(final Analysis analysis, final int numTimeSteps) {
		return timeSeriesFrom(analysis, indices(numTimeSteps));
	}

	private List<Integer> indices(final int numTimeSteps) {
		final int step = (populations.size()-1) / numTimeSteps;
		final List<Integer> indices = new ArrayList<>();
		for (int i=0; i<populations.size(); i+=step) {
			indices.add(i);
		}
		return indices;
	}

	public TimeSeries timeSeriesFrom(final Analysis analysis) {
		return timeSeriesFrom(analysis, populations.size()-1);
	}

}
