package experiment.population;

import static analysis.TimeSeries.average;
import static utility.ResultsPrinter.print;

import java.util.ArrayList;
import java.util.List;

import population.Population;
import population.PopulationFactory;
import agent.assertions.AssertionModel;
import agent.concept.RandomConceptFactory;
import analysis.Analysis;
import analysis.CommunicationAnalysis;
import analysis.GuessingAnalysis;
import analysis.TimeSeries;
import conceptualspace.ObjectPool;

public class PopulationExperiment {

	private final String fileID;
	private final int numAgents;
	private final int numDimensions;
	private final int numLabels;
	private final int numRuns;
	private final int timeSteps;
	private final double weightIncrement;
	private final ObjectPool objectPool;
	private final AssertionModel assertionModel;
	private final RandomConceptFactory factory;

	public PopulationExperiment(
			final String fileID,
			final int numAgents,
			final int numDimensions,
			final int numLabels,
			final int numRuns,
			final int timeSteps,
			final double weightIncrement,
			final ObjectPool objectPool,
			final AssertionModel assertionModel,
			final RandomConceptFactory factory)
	{
				this.fileID = fileID;
				this.numAgents = numAgents;
				this.numDimensions = numDimensions;
				this.numLabels = numLabels;
				this.numRuns = numRuns;
				this.timeSteps = timeSteps;
				this.weightIncrement = weightIncrement;
				this.objectPool = objectPool;
				this.assertionModel = assertionModel;
				this.factory = factory;
	}

	public void run() {
		final List<TimeSeries> communicationResults = new ArrayList<>();
		final List<TimeSeries> guessingResults = new ArrayList<>();
		for (int run=0; run<numRuns; run++) {

			final Population population = PopulationFactory.basicPopulation(
					numAgents, numDimensions, numLabels, objectPool, assertionModel, factory);

			final Simulation simulation = new Simulation(population, weightIncrement);
			final SimulationHistory history = simulation.run(timeSteps);

			final Analysis communicationAnalysis = new CommunicationAnalysis(objectPool);
			final Analysis guessingAnalysis = new GuessingAnalysis(objectPool);
			communicationResults.add(history.timeSeriesFrom(communicationAnalysis, 10));
			guessingResults.add(history.timeSeriesFrom(guessingAnalysis, 10));
		}
		print(average(communicationResults).toString(), "communication." + fileID + ".txt");
		print(average(guessingResults).toString(), "guessing." + fileID + ".txt");
	}

}
