package languagegames;

import static languagegames.analysis.TimeSeries.average;
import static utility.ResultsPrinter.print;

import java.util.ArrayList;
import java.util.List;

import languagegames.analysis.Analysis;
import languagegames.analysis.CommunicationAnalysis;
import languagegames.analysis.GuessingAnalysis;
import languagegames.analysis.TimeSeries;
import conceptualspace.GaussianObject;
import conceptualspace.PerceptualObject;
import datareader.DataReader;

public class Experiment {

	public static void main(final String[] args) {

		final double minThreshold = 0.5;
		final double maxThreshold = 2;
		final int numAgents = 100;
		final int numDimensions = 3;
		final int numRuns = 50;
		final int timeSteps = 10000;
		final double weightIncrement = 0.0001;

		final Experiment experiment = new Experiment(
				minThreshold, maxThreshold, numAgents, numDimensions, numRuns, timeSteps, weightIncrement);

		experiment.run();
	}

	private final double minThreshold;
	private final double maxThreshold;
	private final int numAgents;
	private final int numDimensions;
	private final int numRuns;
	private final int timeSteps;
	private final double weightIncrement;

	public Experiment(
			final double minThreshold,
			final double maxThreshold,
			final int numAgents,
			final int numDimensions,
			final int numRuns,
			final int timeSteps,
			final double weightIncrement)
	{
				this.minThreshold = minThreshold;
				this.maxThreshold = maxThreshold;
				this.numAgents = numAgents;
				this.numDimensions = numDimensions;
				this.numRuns = numRuns;
				this.timeSteps = timeSteps;
				this.weightIncrement = weightIncrement;
	}

	public void run() {
		final List<TimeSeries> communicationResults = new ArrayList<>();
		final List<TimeSeries> guessingResults = new ArrayList<>();
		for (int run=0; run<numRuns; run++) {

			final List<PerceptualObject> objects =
					GaussianObject.makeListFrom(new DataReader().points("normalised.csv"), 20, numDimensions);
			final ObjectPool objectPool = new SuppliedObjectPool(objects);
			final Population population = population(numAgents, numDimensions, minThreshold, maxThreshold, objectPool);

			final Simulation simulation = new Simulation(population, weightIncrement);
			final SimulationHistory history = simulation.run(timeSteps);

			final Analysis communicationAnalysis = new CommunicationAnalysis(objectPool);
			final Analysis guessingAnalysis = new GuessingAnalysis(objectPool);
			communicationResults.add(history.timeSeriesFrom(communicationAnalysis, 10));
			guessingResults.add(history.timeSeriesFrom(guessingAnalysis, 10));
		}
		print(average(guessingResults).toString(), "communication.txt");
		print(average(guessingResults).toString(), "guessing.txt");
	}

	private Population population(
			final int numAgents,
			final int numDimensions,
			final double minThreshold,
			final double maxThreshold,
			final ObjectPool objectPool)
	{
		return new BasicPopulationFactory().createRandom(
				numAgents, numDimensions, minThreshold, maxThreshold, objectPool);
	}

}
