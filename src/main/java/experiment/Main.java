package experiment;

import experiment.population.PopulationExperiment;
import experiment.population.PopulationExperimentBuilder;


public class Main {

	public static void main(final String[] args) {
		final PopulationExperiment experiment = new PopulationExperimentBuilder()
														.build();
		experiment.run();
	}

}
