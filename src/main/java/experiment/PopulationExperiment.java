package experiment;

import static experiment.analysis.TimeSeries.average;
import static utility.ResultsPrinter.print;

import java.util.ArrayList;
import java.util.List;

import conceptualspace.ObjectPool;
import conceptualspace.RandomObjectPool;
import population.Population;
import population.PopulationFactory;
import experiment.analysis.Analysis;
import experiment.analysis.CommunicationAnalysis;
import experiment.analysis.GuessingAnalysis;
import experiment.analysis.TimeSeries;
import agent.assertions.AssertionModel;
import agent.assertions.BasicAssertionModel;
import agent.concept.FuzzyConceptFactory;
import agent.concept.RandomConceptFactory;

public class PopulationExperiment {

	public static void main(final String[] args) {

		final String fileID = "default";

		final int numAgents = 100;
		final int numDimensions = 3;
		final int numLabels = 10;
		final int numRuns = 50;
		final int timeSteps = 10000;
		final double weightIncrement = 0.0001;

		final ObjectPool objectPool = new RandomObjectPool(numDimensions);
		final AssertionModel assertionModel = new BasicAssertionModel();
		final RandomConceptFactory factory = new FuzzyConceptFactory(2.0);

		final PopulationExperiment experiment = new PopulationExperiment(fileID, numAgents, numDimensions,
				numLabels, numRuns, timeSteps, weightIncrement, objectPool, assertionModel, factory);

		experiment.run();
	}

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
