package experiment;


public class GuessingGameExperimentBuilder {

	private double threshold = 2;
	private double pMin = 0.5;
	private double pMax = 1;
	private int numDimensions = 3;
	private int numLabels = 10;
	private int numObjects = 20;
	private int numRuns = 50;
	private int numTrials = 100000;

	public GuessingGameExperimentBuilder withThreshold(final double threshold) {
		this.threshold = threshold;
		return this;
	}

	public GuessingGameExperimentBuilder withPriorInRange(final double pMin, final double pMax) {
		this.pMin = pMin;
		this.pMax = pMax;
		return this;
	}

	public GuessingGameExperimentBuilder withNumDimensions(final int numDimensions) {
		this.numDimensions = numDimensions;
		return this;
	}

	public GuessingGameExperimentBuilder withNumLabels(final int numLabels) {
		this.numLabels = numLabels;
		return this;
	}

	public GuessingGameExperimentBuilder withNumObjects(final int numObjects) {
		this.numObjects = numObjects;
		return this;
	}

	public GuessingGameExperimentBuilder withNumRuns(final int numRuns) {
		this.numRuns = numRuns;
		return this;
	}

	public GuessingGameExperimentBuilder withNumTrials(final int numTrials) {
		this.numTrials = numTrials;
		return this;
	}

	public GuessingGameExperiment build() {
		return new GuessingGameExperiment(threshold, pMin, pMax, numDimensions, numLabels, numObjects, numRuns, numTrials);
	}

}
