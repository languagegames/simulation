package experiment.classification;

import agent.concept.FuzzyConceptFactory;
import agent.concept.RandomConceptFactory;

public class ClassificationExperimentBuilder {

	private String fileID = "default";
	private int numDimensions = 3;
	private int numLabels = 10;
	private int numRuns = 50;
	private int crossValFactor = 10;
	private RandomConceptFactory conceptFactory = new FuzzyConceptFactory(2.0);
	private String dataFile = "data.csv";
	private String labelsFile = "labels10d.csv";

	public ClassificationExperimentBuilder withName(final String fileID) {
		this.fileID = fileID;
		return this;
	}

	public ClassificationExperimentBuilder withNumDimensions(final int numDimensions) {
		this.numDimensions = numDimensions;
		return this;
	}

	public ClassificationExperimentBuilder withNumLabels(final int numLabels) {
		this.numLabels = numLabels;
		return this;
	}

	public ClassificationExperimentBuilder withNumRuns(final int numRuns) {
		this.numRuns = numRuns;
		return this;
	}

	public ClassificationExperimentBuilder withCrossValFactor(final int crossValFactor) {
		this.crossValFactor = crossValFactor;
		return this;
	}

	public ClassificationExperimentBuilder withConceptFactory(final RandomConceptFactory conceptFactory) {
		this.conceptFactory = conceptFactory;
		return this;
	}

	public ClassificationExperimentBuilder withDataFrom(final String dataFile) {
		this.dataFile = dataFile;
		return this;
	}

	public ClassificationExperimentBuilder withLabelsFrom(final String labelsFile) {
		this.labelsFile = labelsFile;
		return this;
	}

	public ClassificationExperiment build() {
		return new ClassificationExperiment(fileID, numDimensions, numLabels, numRuns,
				crossValFactor, conceptFactory, dataFile, labelsFile);
	}

}
