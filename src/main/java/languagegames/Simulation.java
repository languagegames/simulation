package languagegames;

import java.util.ArrayList;
import java.util.List;



public class Simulation {

	private final Population population;
	private final double weightIncrement;

	public Simulation(final Population population, final double weightIncrement) {
		this.population = population;
		this.weightIncrement = weightIncrement;
	}

	public SimulationHistory run(final int timeSteps) {
		final List<Population> populations = new ArrayList<>();
		populations.add(population);
		for (int t = 0; t < timeSteps; t++) {
			final Population next = population.runLanguageGames().incrementWeights(weightIncrement);
			populations.add(next);
		}
		return new SimulationHistory(populations);
	}

}
