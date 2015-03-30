package experiment;


public class Main {

	public static void main(final String[] args) {
		final PopulationExperiment experiment = new PopulationExperimentBuilder()
														.build();
		experiment.run();
	}

}
