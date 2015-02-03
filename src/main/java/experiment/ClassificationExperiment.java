package experiment;

import java.util.List;

import agent.Agent;
import conceptualspace.Material;

public class ClassificationExperiment {

	private final List<Material> trainingData;
	private final List<Material> testData;
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
		for (final Material material : testData) {
			if (pupilClassifiesAccurately(trainedPupil, material)) {
				score++;
			}
		}
		return score / testData.size();
	}

	private boolean pupilClassifiesAccurately(final Agent trainedPupil, final Material material) {
		final Label target = teacher.classify(material);
		final Label output = trainedPupil.classify(material);
		return target.equals(output);
	}

	private Agent trainPupil() {
		Agent trainedPupil = pupil;
		for (final Material material : trainingData) {
			final Label label = teacher.classify(material);
			trainedPupil = trainedPupil.learn(material, label);
		}
		return trainedPupil;
	}

}
