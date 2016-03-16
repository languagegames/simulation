package agent;

import agent.assertions.Assertion;
import agent.concept.Concept;
import agent.concept.FuzzyConcept;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestBasicAgent {

    private final double someWeight = 0.42;
    private final Concept aConcept = new FuzzyConcept(new Point(0.7), 1),
            anotherConcept = new FuzzyConcept(new Point(0.5), 1);

    @Test
    public void guessesMostAppropriateObjectGivenConjunctionAssertion() {
        final BasicAgent agent = agent().withConcepts(aConcept, anotherConcept).build();

        final PerceptualObject other = object(0.75), mostAppropriate = object(0.6);
        final List<PerceptualObject> guessingSet = new ArrayList<PerceptualObject>();
        guessingSet.addAll(asList(other, mostAppropriate));

        assertThat(agent.guess(observations(guessingSet), new Assertion(0, 1, someWeight)), equalTo(1));
    }

    @Test
    public void guessesMostAppropriateObjectGivenAssertion() {
        final BasicAgent agent = agent().withConcepts(aConcept).build();

        final PerceptualObject other = object(0.5), mostAppropriate = object(0.6);
        final List<PerceptualObject> guessingSet = new ArrayList<PerceptualObject>();
        guessingSet.addAll(asList(other, mostAppropriate));

        assertThat(agent.guess(observations(guessingSet), new Assertion(0, someWeight)), equalTo(1));
    }

    private List<Point> observations(final List<PerceptualObject> guessingSet) {
        final List<Point> observations = new ArrayList<Point>();
        for (final PerceptualObject object : guessingSet) {
            observations.add(object.observation());
        }
        return observations;
    }

    @Test
    public void incrementsWeight() {
        final BasicAgent agent = agent().withWeight(0.5).build();
        final Agent newAgent = agent.incrementWeight(0.1);
        assertThat(newAgent.weight(), equalTo(0.6));
    }

    @Test
    public void assertsAccordingToMostAppropriateConcept() {
        final BasicAgent agent = agent()
                .withConcepts(
                        new FuzzyConcept(new Point(0.2), 0.7),
                        new FuzzyConcept(new Point(0.5), 0.5))
                .withWeight(someWeight)
                .build();

        final Point observation0 = new Point(0.35);
        final Point observation1 = new Point(0.4);

        assertThat(agent.assertion(observation0), equalTo(new Assertion(0, someWeight)));
        assertThat(agent.assertion(observation1), equalTo(new Assertion(1, someWeight)));
    }

    @Test
    public void learnsByUpdatingAppropriateConcept() {
        final BasicAgent agent = agent()
                .withConcepts(
                        new FuzzyConcept(new Point(0.42), 0.42),
                        new FuzzyConcept(new Point(0.5), 0.5))
                .withWeight(someWeight)
                .build();

        final Agent updatedAgent = agent()
                .withConcepts(
                        new FuzzyConcept(new Point(0.42), 0.42),
                        new FuzzyConcept(new Point(0.74), 0.8))
                .withWeight(someWeight)
                .build();

        final Point observation = new Point(0.9);
        assertThat(agent.learn(observation, new Assertion(1, 0.8)),
                equalTo(updatedAgent));
    }

    private BasicAgentBuilder agent() {
        return new BasicAgentBuilder();
    }

    private SimpleObject object(final double d) {
        return new SimpleObject(new Point(d));
    }

}
