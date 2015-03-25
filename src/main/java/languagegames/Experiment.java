package languagegames;

import static languagegames.analysis.TimeSeries.average;
import static utility.ResultsPrinter.print;

import java.util.ArrayList;
import java.util.List;

import languagegames.analysis.Analysis;
import languagegames.analysis.CommunicationAnalysis;
import languagegames.analysis.GuessingAnalysis;
import languagegames.analysis.TimeSeries;
import agent.AssertionModel;
import agent.BasicAssertionModel;

public class Experiment {

	public static void main(final String[] args) {

		final String fileID = "default";

		final double initialThreshold = 2.0;
		final int numAgents = 100;
		final int numDimensions = 3;
		final int numLabels = 10;
		final int numRuns = 50;
		final int timeSteps = 10000;
		final double weightIncrement = 0.0001;

		final ObjectPool objectPool = new RandomObjectPool(numDimensions);
		final AssertionModel assertionModel = new BasicAssertionModel();

		final Experiment experiment = new Experiment(fileID, initialThreshold, numAgents, numDimensions,
				numLabels, numRuns, timeSteps, weightIncrement, objectPool, assertionModel);

		experiment.run();
	}

	private final String fileID;
	private final double initialThreshold;
	private final int numAgents;
	private final int numDimensions;
	private final int numLabels;
	private final int numRuns;
	private final int timeSteps;
	private final double weightIncrement;
	private final ObjectPool objectPool;
	private final AssertionModel assertionModel;

	public Experiment(
			final String fileID,
			final double initialThreshold,
			final int numAgents,
			final int numDimensions,
			final int numLabels,
			final int numRuns,
			final int timeSteps,
			final double weightIncrement,
			final ObjectPool objectPool,
			final AssertionModel assertionModel)
	{
				this.fileID = fileID;
				this.initialThreshold = initialThreshold;
				this.numAgents = numAgents;
				this.numDimensions = numDimensions;
				this.numLabels = numLabels;
				this.numRuns = numRuns;
				this.timeSteps = timeSteps;
				this.weightIncrement = weightIncrement;
				this.objectPool = objectPool;
				this.assertionModel = assertionModel;
	}

	public void run() {
		final List<TimeSeries> communicationResults = new ArrayList<>();
		final List<TimeSeries> guessingResults = new ArrayList<>();
		for (int run=0; run<numRuns; run++) {

			final Population population = PopulationFactory.basicPopulation(
					numAgents, numDimensions, numLabels, initialThreshold, objectPool, assertionModel);

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
