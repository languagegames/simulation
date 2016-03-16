package experiment.classification;

import agent.Agent;
import agent.assertions.Assertion;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestClassificationTrial {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    Agent teacher, pupil, trainedPupil;

    private final PerceptualObject trainingSample = someObject(),
            testSample0 = someObject(), testSample1 = someObject();
    private final Assertion assertion = new Assertion(0, 0.42),
            anotherAssertion = new Assertion(1, 0.42);

    private final List<PerceptualObject> trainingData = new ArrayList<PerceptualObject>();
    private final List<PerceptualObject> testData = new ArrayList<PerceptualObject>();

    @Before
    public void setUp() {
        trainingData.addAll(asList(trainingSample, trainingSample, trainingSample));
        testData.addAll(asList(testSample0, testSample1));
    }

    @Test
    public void getAverageClassificationScoreOverTestSet() {
        final ClassificationTrial experiment =
                new ClassificationTrial(pupil, teacher, trainingData, testData);

        context.checking(new Expectations() {{
            exactly(3).of(teacher).assertion(trainingSample.observation());
            will(returnValue(assertion));
            oneOf(pupil).learn(someObject().observation(), assertion);
            will(returnValue(trainedPupil));
            exactly(2).of(trainedPupil).learn(someObject().observation(), assertion);
            will(returnValue(trainedPupil));
            oneOf(teacher).assertion(testSample0.observation());
            will(returnValue(assertion));
            oneOf(teacher).assertion(testSample1.observation());
            will(returnValue(assertion));
            oneOf(trainedPupil).assertion(testSample0.observation());
            will(returnValue(assertion));
            oneOf(trainedPupil).assertion(testSample1.observation());
            will(returnValue(anotherAssertion));
        }});

        assertThat(experiment.classificationScore(), equalTo(0.5));
    }

    private PerceptualObject someObject() {
        return new SimpleObject(new Point(0.42));
    }

}
