package experiment.classification;

import java.util.ArrayList;
import java.util.List;

import agent.Agent;
import conceptualspace.PerceptualObject;

public class ClassificationCrossValidation {

	private final Agent pupil;
	private final Agent teacher;
	private final List<PerceptualObject> data = new ArrayList<>();
	private final int crossValFactor;

	public ClassificationCrossValidation(
			final Agent pupil,
			final Agent teacher,
			final List<PerceptualObject> data,
			final int crossValFactor)
	{
		this.pupil = pupil;
		this.teacher = teacher;
		this.data.addAll(data);
		this.crossValFactor = crossValFactor;
	}

	public double score() {
		final int setSize = data.size() / crossValFactor;
		double sum = 0;
		for (int i=0; i<crossValFactor; i++) {
			final List<PerceptualObject> testData = testData(setSize, i);
			final List<PerceptualObject> trainingData = trainingData(testData);
			sum += classificationScore(testData, trainingData);
		}
		return sum / crossValFactor;
	}

	private List<PerceptualObject> trainingData(final List<PerceptualObject> testData) {
		final List<PerceptualObject> trainingData = new ArrayList<>(data);
		trainingData.removeAll(testData);
		return trainingData;
	}

	private List<PerceptualObject> testData(final int setSize, final int i) {
		return data.subList(i*setSize, (i+1)*setSize);
	}

	private double classificationScore(
			final List<PerceptualObject> testData, final List<PerceptualObject> trainingData) {
		final ClassificationTrial trial = new ClassificationTrial(pupil, teacher, trainingData, testData);
		return trial.classificationScore();
	}

}
