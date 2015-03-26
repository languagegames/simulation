package end2end;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Test;

import agent.Agent;
import agent.BasicAgentBuilder;
import agent.FuzzyConcept;
import agent.LabelMapping;
import agent.OracleAgent;
import classifier.ClassificationTrial;
import classifier.ExperimentData;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;
import datareader.DataReader;

public class TestClassificationExperiment {

	private final Agent pupil = new BasicAgentBuilder()
			.withConcepts(
					new FuzzyConcept(new Point(0.3, 0.4), 1.0),
					new FuzzyConcept(new Point(0.6, 0.5), 0.7))
			.build();

	@Test
	public void fiveObjectsTwoConceptsUsingDataFromFile() {

		final DataReader dataReader = new DataReader();

		final List<PerceptualObject> objects = SimpleObject.makeListFrom(
				dataReader.points("testdata.csv"));
		final ExperimentData data = new ExperimentData(objects, 0.8);
		final List<Integer> labels = dataReader.integers("testlabels.csv");

		final Agent teacher = new OracleAgent(new LabelMapping(objects, labels), 0.95);

		final ClassificationTrial experiment =
				new ClassificationTrial(pupil, teacher, data.trainingSet(), data.testSet());

		assertThat(experiment.classificationScore(), equalTo(1.0));
	}

}
