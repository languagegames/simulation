package experiment.classification;

import java.util.ArrayList;
import java.util.List;

import agent.Agent;
import agent.assertions.Assertion;
import conceptualspace.PerceptualObject;

public class ClassificationTrial {

	private final List<PerceptualObject> trainingData = new ArrayList<>();
	private final List<PerceptualObject> testData = new ArrayList<>();
	private final Agent pupil;
	private final Agent teacher;

	public ClassificationTrial(
			final Agent pupil,
			final Agent teacher,
			final List<PerceptualObject> trainingData,
			final List<PerceptualObject> testData)
	{
		this.pupil = pupil;
		this.teacher = teacher;
		this.trainingData.addAll(trainingData);
		this.testData.addAll(testData);
	}

	public double classificationScore() {
		final Agent trainedPupil = trainPupil();
		return classificationScore(trainedPupil);
	}

	private double classificationScore(final Agent trainedPupil) {
		double score = 0;
		for (final PerceptualObject object : testData) {
			if (pupilClassifiesAccurately(trainedPupil, object)) {
				score++;
			}
		}
		return score / testData.size();
	}

	private boolean pupilClassifiesAccurately(final Agent trainedPupil, final PerceptualObject material) {
		final Assertion target = teacher.assertion(material);
		final Assertion output = trainedPupil.assertion(material);
		return target.matches(output);
	}

	private Agent trainPupil() {
		Agent trainedPupil = pupil;
		for (final PerceptualObject object : trainingData) {
			final Assertion assertion = teacher.assertion(object);
			trainedPupil = trainedPupil.learn(object.observation(), assertion);
		}
		return trainedPupil;
	}

}
