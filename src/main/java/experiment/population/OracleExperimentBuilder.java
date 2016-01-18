package experiment.population;

import population.AgentInteractor;
import population.DifferentObservationInteractor;
import agent.assertions.AssertionModel;
import agent.assertions.BasicAssertionModel;
import agent.concept.FuzzyConceptFactory;
import agent.concept.RandomConceptFactory;

public class OracleExperimentBuilder {

	private String fileID = "oracle";
	private String dataFile = "data.csv";
	private String labelsFile = "labels10d.csv";
	private int numOracles = 1;
	private int numAgents = 100;
	private int numDimensions = 3;
	private int numLabels = 10;
	private int numRuns = 50;
	private int timeSteps = 10000;
	private double weightIncrement = 0.0001;
	private AssertionModel assertionModel = new BasicAssertionModel();
	private RandomConceptFactory conceptFactory = new FuzzyConceptFactory(2.0);
	private AgentInteractor agentInteractor = new DifferentObservationInteractor();

	public OracleExperimentBuilder withName(final String fileID) {
		this.fileID = fileID;
		return this;
	}

	public OracleExperimentBuilder withDataFrom(final String dataFile) {
		this.dataFile = dataFile;
		return this;
	}

	public OracleExperimentBuilder withLabelsFrom(final String labelsFile) {
		this.labelsFile = labelsFile;
		return this;
	}

	public OracleExperimentBuilder withNumOracles(final int numOracles) {
		this.numOracles = numOracles;
		return this;
	}

	public OracleExperimentBuilder withNumAgents(final int numAgents) {
		this.numAgents = numAgents;
		return this;
	}

	public OracleExperimentBuilder withNumDimensions(final int numDimensions) {
		this.numDimensions = numDimensions;
		return this;
	}

	public OracleExperimentBuilder withNumLabels(final int numLabels) {
		this.numLabels = numLabels;
		return this;
	}

	public OracleExperimentBuilder withNumRuns(final int numRuns) {
		this.numRuns = numRuns;
		return this;
	}

	public OracleExperimentBuilder withTimeSteps(final int timeSteps) {
		this.timeSteps = timeSteps;
		return this;
	}

	public OracleExperimentBuilder withWeightIncrement(final double weightIncrement) {
		this.weightIncrement = weightIncrement;
		return this;
	}

	public OracleExperimentBuilder withAssertionModel(final AssertionModel assertionModel) {
		this.assertionModel = assertionModel;
		return this;
	}

	public OracleExperimentBuilder withConceptFactory(final RandomConceptFactory conceptFactory) {
		this.conceptFactory = conceptFactory;
		return this;
	}

	public OracleExperimentBuilder withAgentInteractor(final AgentInteractor agentInteractor) {
		this.agentInteractor = agentInteractor;
		return this;
	}

	public OracleExperiment build() {
		return new OracleExperiment(fileID, dataFile, labelsFile, numOracles, numAgents, numDimensions,
				numLabels, numRuns, timeSteps, weightIncrement, assertionModel, conceptFactory, agentInteractor);
	}

}
