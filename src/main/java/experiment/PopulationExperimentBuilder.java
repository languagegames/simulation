package experiment;

import agent.assertions.AssertionModel;
import agent.assertions.BasicAssertionModel;
import agent.concept.FuzzyConceptFactory;
import agent.concept.RandomConceptFactory;
import conceptualspace.ObjectPool;
import conceptualspace.RandomObjectPool;

public class PopulationExperimentBuilder {

	private String fileID = "default";
	private int numAgents = 100;
	private int numDimensions = 3;
	private int numLabels = 10;
	private int numRuns = 50;
	private int timeSteps = 10000;
	private double weightIncrement = 0.0001;
	private ObjectPool objectPool = new RandomObjectPool(numDimensions);
	private AssertionModel assertionModel = new BasicAssertionModel();
	private RandomConceptFactory conceptFactory = new FuzzyConceptFactory(2.0);

	public PopulationExperimentBuilder withName(final String fileID) {
		this.fileID = fileID;
		return this;
	}

	public PopulationExperimentBuilder withNumAgents(final int numAgents) {
		this.numAgents = numAgents;
		return this;
	}

	public PopulationExperimentBuilder withNumDimensions(final int numDimensions) {
		this.numDimensions = numDimensions;
		return this;
	}

	public PopulationExperimentBuilder withNumLabels(final int numLabels) {
		this.numLabels = numLabels;
		return this;
	}

	public PopulationExperimentBuilder withNumRuns(final int numRuns) {
		this.numRuns = numRuns;
		return this;
	}

	public PopulationExperimentBuilder withTimeSteps(final int timeSteps) {
		this.timeSteps = timeSteps;
		return this;
	}

	public PopulationExperimentBuilder withWeightIncrement(final double weightIncrement) {
		this.weightIncrement = weightIncrement;
		return this;
	}

	public PopulationExperimentBuilder withObjectsFrom(final ObjectPool objectPool) {
		this.objectPool = objectPool;
		return this;
	}

	public PopulationExperimentBuilder withAssertionModel(final AssertionModel assertionModel) {
		this.assertionModel = assertionModel;
		return this;
	}

	public PopulationExperimentBuilder withConceptFactory(final RandomConceptFactory conceptFactory) {
		this.conceptFactory = conceptFactory;
		return this;
	}

	public PopulationExperiment build() {
		return new PopulationExperiment(fileID, numAgents, numDimensions, numLabels, numRuns,
				timeSteps, weightIncrement, objectPool, assertionModel, conceptFactory);
	}

}
