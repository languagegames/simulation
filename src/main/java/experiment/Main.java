package experiment;

import experiment.population.OracleExperiment;
import experiment.population.OracleExperimentBuilder;


public class Main {

    public static void main(final String[] args) {
        final OracleExperiment experiment = new OracleExperimentBuilder()
                .build();
        experiment.run();
    }

}
