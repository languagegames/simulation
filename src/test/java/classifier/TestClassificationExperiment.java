package classifier;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import agent.Agent;
import agent.Assertion;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

public class TestClassificationExperiment {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock Agent teacher, pupil, trainedPupil;

	private final PerceptualObject trainingSample = someMaterial(), testSample0 = someMaterial(), testSample1 = someMaterial();
	private final Assertion assertion = new Assertion(someMaterial(), 0, 0.42),
			anotherAssertion = new Assertion(someMaterial(), 1, 0.42);

	private final ExperimentData data =
			new ExperimentData(asList(
					trainingSample, trainingSample, trainingSample, testSample0, testSample1),
					0.6);

	@Test
	public void getAverageClassificationScoreOverTestSet() {
		final ClassificationExperiment experiment =
				new ClassificationExperiment(pupil, teacher, data.trainingSet(), data.testSet());

		context.checking(new Expectations() {{
			exactly(3).of(teacher).assertion(trainingSample); will(returnValue(assertion));
			oneOf(pupil).learn(assertion); will(returnValue(trainedPupil));
			exactly(2).of(trainedPupil).learn(assertion); will(returnValue(trainedPupil));
			oneOf(teacher).assertion(testSample0); will(returnValue(assertion));
			oneOf(teacher).assertion(testSample1); will(returnValue(assertion));
			oneOf(trainedPupil).assertion(testSample0); will(returnValue(assertion));
			oneOf(trainedPupil).assertion(testSample1); will(returnValue(anotherAssertion));
		}});

		assertThat(experiment.classificationScore(), equalTo(0.5));
	}

	private PerceptualObject someMaterial() {
		return new SimpleObject(new Point(0.42));
	}

}
