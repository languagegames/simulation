package experiment;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import agent.Agent;
import conceptualspace.Material;
import conceptualspace.Point;
import conceptualspace.SimpleMaterial;

public class TestClassificationExperiment {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock Agent teacher, pupil, trainedPupil;

	private final Material trainingSample = someMaterial(), testSample0 = someMaterial(), testSample1 = someMaterial();
	private final Assertion assertion = new Assertion(someMaterial(), "0", 0.42),
			anotherAssertion = new Assertion(someMaterial(), "1", 0.42);

	private final ExperimentData data =
			new ExperimentData(asList(
					trainingSample, trainingSample, trainingSample, testSample0, testSample1),
					0.6);

	@Test
	public void getAverageClassificationScoreOverTestSet() {
		final ClassificationExperiment experiment = new ClassificationExperiment(data, pupil, teacher);

		context.checking(new Expectations() {{
			exactly(3).of(teacher).classify(trainingSample); will(returnValue(assertion));
			oneOf(pupil).learn(assertion); will(returnValue(trainedPupil));
			exactly(2).of(trainedPupil).learn(assertion); will(returnValue(trainedPupil));
			oneOf(teacher).classify(testSample0); will(returnValue(assertion));
			oneOf(teacher).classify(testSample1); will(returnValue(assertion));
			oneOf(trainedPupil).classify(testSample0); will(returnValue(assertion));
			oneOf(trainedPupil).classify(testSample1); will(returnValue(anotherAssertion));
		}});

		assertThat(experiment.classificationScore(), equalTo(0.5));
	}

	private Material someMaterial() {
		return new SimpleMaterial(new Point(0.42));
	}

}
