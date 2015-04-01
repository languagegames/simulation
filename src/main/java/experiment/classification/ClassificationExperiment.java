package experiment.classification;

import static agent.BasicAgent.randomAgent;
import static utility.ResultsPrinter.print;
import static utility.Stats.mean;
import static utility.Stats.standardDeviation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utility.DataReader;
import agent.Agent;
import agent.LabelMapping;
import agent.OracleAgent;
import agent.assertions.BasicAssertionModel;
import agent.concept.RandomConceptFactory;
import conceptualspace.PerceptualObject;
import conceptualspace.SimpleObject;

public class ClassificationExperiment {

	private final String fileID;
	private final int numRuns;
	private final int crossValFactor;
	private final Agent pupil;
	private final Agent teacher;
	private List<PerceptualObject> data = new ArrayList<>();

	public ClassificationExperiment(
			final String fileID,
			final int numDimensions,
			final int numLabels,
			final int numRuns,
			final int crossValFactor,
			final RandomConceptFactory conceptFactory,
			final String dataFile,
			final String labelsFile)
	{
		this.fileID = fileID;
		this.numRuns = numRuns;
		this.crossValFactor = crossValFactor;

		pupil = randomAgent(numDimensions, numLabels, 0, new BasicAssertionModel(), conceptFactory);

		data = SimpleObject.makeListFrom(DataReader.points(dataFile, numDimensions));
		teacher = new OracleAgent(new LabelMapping(data, DataReader.integers(labelsFile)), 0.95);

	}

	public void run() {
		final List<Double> scores = new ArrayList<>();
		for (int i=0; i<numRuns; i++) {
			Collections.shuffle(data);
			final ClassificationCrossValidation crossValidation =
					new ClassificationCrossValidation(pupil, teacher, data, crossValFactor);
			scores.add(crossValidation.score());
		}

		print(mean(scores) + ", " + standardDeviation(scores), fileID + ".txt");
	}

}
