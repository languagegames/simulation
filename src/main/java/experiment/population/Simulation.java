package experiment.population;

import java.util.ArrayList;
import java.util.List;

import population.Population;



public class Simulation {

	private final List<Population> populations = new ArrayList<>();
	private final double weightIncrement;

	public Simulation(final Population population, final double weightIncrement) {
		populations.add(population);
		this.weightIncrement = weightIncrement;
	}

	public SimulationHistory run(final int timeSteps) {
		for (int t = 0; t < timeSteps; t++) {
			final Population current = populations.get(populations.size()-1);
			final Population next = current.runLanguageGames().incrementWeights(weightIncrement);
			populations.add(next);
		}
		return new SimulationHistory(populations);
	}

}
