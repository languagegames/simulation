package classifier;

import java.util.List;

import agent.Agent;
import agent.Assertion;
import conceptualspace.PerceptualObject;

public class ClassificationExperiment {

	private final List<PerceptualObject> trainingData;
	private final List<PerceptualObject> testData;
	private final Agent pupil;
	private final Agent teacher;

	public ClassificationExperiment(final ExperimentData data, final Agent pupil, final Agent teacher) {
		trainingData = data.trainingSet();
		testData = data.testSet();
		this.pupil = pupil;
		this.teacher = teacher;
	}

	public double classificationScore() {
		final Agent trainedPupil = trainPupil();
		return classificationScore(trainedPupil);
	}

	private double classificationScore(final Agent trainedPupil) {
		double score = 0;
		for (final PerceptualObject material : testData) {
			if (pupilClassifiesAccurately(trainedPupil, material)) {
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
		for (final PerceptualObject material : trainingData) {
			final Assertion assertion = teacher.assertion(material);
			trainedPupil = trainedPupil.learn(assertion);
		}
		return trainedPupil;
	}

}
