package experiment.population;

import static analysis.TimeSeries.average;
import static conceptualspace.SimpleObject.makeListFrom;
import static population.PopulationFactory.oraclePopulation;
import static utility.DataReader.integers;
import static utility.DataReader.points;
import static utility.ResultsPrinter.print;

import java.util.ArrayList;
import java.util.List;

import population.AgentInteractor;
import population.DifferentObservationInteractor;
import population.Population;
import agent.Agent;
import agent.LabelMapping;
import agent.OracleAgent;
import agent.assertions.AssertionModel;
import agent.concept.RandomConceptFactory;
import analysis.Analysis;
import analysis.CommunicationAnalysis;
import analysis.GuessingAnalysis;
import analysis.TimeSeries;
import conceptualspace.ObjectPool;
import conceptualspace.PerceptualObject;
import conceptualspace.SuppliedObjectPool;

public class OracleExperiment {

	private final String fileID;
	private final List<Agent> oracles = new ArrayList<>();
	private final ObjectPool objectPool;
	private final int numAgents;
	private final int numDimensions;
	private final int numLabels;
	private final int numRuns;
	private final int timeSteps;
	private final double weightIncrement;
	private final AssertionModel assertionModel;
	private final RandomConceptFactory conceptFactory;
	private final AgentInteractor agentInteractor;

	public OracleExperiment(
			final String fileID,
			final String dataFile,
			final String labelsFile,
			final int numOracles,
			final int numAgents,
			final int numDimensions,
			final int numLabels,
			final int numRuns,
			final int timeSteps,
			final double weightIncrement,
			final AssertionModel assertionModel,
			final RandomConceptFactory conceptFactory)
	{
				this.fileID = fileID;
				this.numAgents = numAgents;
				this.numDimensions = numDimensions;
				this.numLabels = numLabels;
				this.numRuns = numRuns;
				this.timeSteps = timeSteps;
				this.weightIncrement = weightIncrement;
				this.assertionModel = assertionModel;
				this.conceptFactory = conceptFactory;
				agentInteractor = new DifferentObservationInteractor();

				final List<PerceptualObject> data = makeListFrom(points(dataFile, numDimensions));
				objectPool = new SuppliedObjectPool(data);
				for (int i=0; i<numOracles; i++) {
					oracles.add(new OracleAgent(new LabelMapping(data, integers(labelsFile)), 0.95));
				}
	}

	public void run() {
		final List<TimeSeries> communicationResults = new ArrayList<>();
		final List<TimeSeries> guessingResults = new ArrayList<>();
		for (int run=0; run<numRuns; run++) {
			final Population population = oraclePopulation(
					oracles, numAgents, numDimensions, numLabels, objectPool, assertionModel, conceptFactory);

			final Simulation simulation = new Simulation(population, weightIncrement);
			final SimulationHistory history = simulation.run(timeSteps);

			final Analysis communicationAnalysis = new CommunicationAnalysis(objectPool, agentInteractor);
			final Analysis guessingAnalysis = new GuessingAnalysis(objectPool);
			communicationResults.add(history.timeSeriesFrom(communicationAnalysis, 10));
			guessingResults.add(history.timeSeriesFrom(guessingAnalysis, 10));
		}
		print(average(communicationResults).toString(), "communication." + fileID + ".txt");
		print(average(guessingResults).toString(), "guessing." + fileID + ".txt");
	}

}
