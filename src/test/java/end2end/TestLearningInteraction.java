package end2end;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Test;

import agent.Agent;
import agent.BasicAgent;
import agent.Concept;
import agent.LabelMapping;
import agent.OracleAgent;
import classifier.ClassificationExperiment;
import classifier.ExperimentData;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;
import datareader.DataReader;

public class TestLearningInteraction {

	private final Agent pupil = new BasicAgent(
			0.42,
			new Concept(new Point(0.3, 0.4), 1.0),
			new Concept(new Point(0.6, 0.5), 0.7)
			);

	@Test
	public void fiveObjectsTwoConceptsUsingDataFromFile() {

		final DataReader dataReader = new DataReader();

		final List<PerceptualObject> materials = SimpleObject.makeListFrom(
				dataReader.points("testdata.csv"));
		final ExperimentData data = new ExperimentData(materials, 0.8);
		final List<Integer> labels = dataReader.integers("testlabels.csv");

		final Agent teacher = new OracleAgent(new LabelMapping(data, labels), 0.95);

		final ClassificationExperiment experiment = new ClassificationExperiment(data, pupil, teacher);

		assertThat(experiment.classificationScore(), equalTo(1.0));
	}

	@Test
	public void fiveObjectsTwoConceptsWithCommunicationGame() {

		final ExperimentData data = new ExperimentData(asList(
				simpleMaterial(new Point(0.1, 0.8)),
				simpleMaterial(new Point(0.5, 0.5)),
				simpleMaterial(new Point(0.4, 0.2)),
				simpleMaterial(new Point(0.9, 0.8)),
				simpleMaterial(new Point(0.7, 0.3))),
				0.8
				);

		final Agent teacher = new OracleAgent(new LabelMapping(data, asList(0, 1, 1, 0, 0)), 0.95);

		final ClassificationExperiment experiment = new ClassificationExperiment(data, pupil, teacher);

		assertThat(experiment.classificationScore(), equalTo(1.0));
	}

	private PerceptualObject simpleMaterial(final Point point) {
		return new SimpleObject(point);
	}

}
