package experiment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Test;

import utility.DataReader;
import agent.Agent;
import agent.BasicAgentBuilder;
import agent.LabelMapping;
import agent.OracleAgent;
import agent.concept.FuzzyConcept;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestClassificationCrossValidation {

	private final Agent pupil = new BasicAgentBuilder()
			.withConcepts(
					new FuzzyConcept(new Point(0.3, 0.4), 1.0),
					new FuzzyConcept(new Point(0.6, 0.5), 0.7))
			.build();

	@Test
	public void fiveObjectsTwoConceptsUsingDataFromFile() {

		final List<PerceptualObject> objects = SimpleObject.makeListFrom(
				DataReader.points("testdata.csv", 2));
		final List<Integer> labels = DataReader.integers("testlabels.csv");

		final Agent teacher = new OracleAgent(new LabelMapping(objects, labels), 0.95);

		final ClassificationCrossValidation experiment = new ClassificationCrossValidation(pupil, teacher, objects, 2);

		final List<PerceptualObject> firstSet = objects.subList(0, 3);
		final List<PerceptualObject> secondSet = objects.subList(3, 6);

		final ClassificationTrial trial0 =
				new ClassificationTrial(pupil, teacher, firstSet, secondSet);
		final ClassificationTrial trial1 =
				new ClassificationTrial(pupil, teacher, secondSet, firstSet);

		assertThat(experiment.score(),
				equalTo((trial0.classificationScore() + trial1.classificationScore())/2));
	}

}
