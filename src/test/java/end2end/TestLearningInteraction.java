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
import conceptualspace.Material;
import conceptualspace.Point;
import conceptualspace.SimpleMaterial;
import datareader.DataReader;
import experiment.ClassificationExperiment;
import experiment.ExperimentData;

public class TestLearningInteraction {

	private final Agent pupil = new BasicAgent(
			0.42,
			new Concept(new Point(0.3, 0.4), 1.0),
			new Concept(new Point(0.6, 0.5), 0.7)
			);

	@Test
	public void fiveObjectsTwoConceptsUsingDataFromFile() {

		final DataReader dataReader = new DataReader();

		final List<Material> materials = SimpleMaterial.makeListFrom(dataReader.points("testdata"));
		final ExperimentData data = new ExperimentData(materials, 0.8);
		final List<Integer> labels = dataReader.labels("testlabels");

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

	private Material simpleMaterial(final Point point) {
		return new SimpleMaterial(point);
	}

}
