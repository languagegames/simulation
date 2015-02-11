package languagegames;

import static java.util.Arrays.asList;



public class Simulation {

	private final Population population;

	public Simulation(final Population population) {
		this.population = population;
	}

	public SimulationHistory run(final int timeSteps) {
		return new SimulationHistory(asList(population));
	}

}
