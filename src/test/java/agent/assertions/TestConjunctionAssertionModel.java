package agent.assertions;

import agent.concept.Concept;
import agent.concept.FuzzyConcept;
import conceptualspace.Point;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestConjunctionAssertionModel {

    private final Point observation = new Point(0.9, 0.5);
    private final List<Concept> concepts = new ArrayList<Concept>();

    @Before
    public void setUp() {
        concepts.addAll(asList(
                new FuzzyConcept(new Point(0.0, 0.5), 2.2),
                new FuzzyConcept(new Point(0.5, 0.0), 1.7),
                new FuzzyConcept(new Point(0.6, 0.2), 1.9),
                new FuzzyConcept(new Point(0.6, 0.9), 2.0),
                new FuzzyConcept(new Point(0.6, 0.2), 2.2)));
    }

    @Test
    public void assertsConjunctionAppropriately() {
        final ConjunctionAssertionModel assertionModel = new ConjunctionAssertionModel(0.7);
        assertThat(assertionModel.assertion(observation, concepts, 0.42),
                equalTo(new Assertion(4, 2, 0.42)));
    }

    @Test
    public void assertsSingleLabelAppropriately() {
        final ConjunctionAssertionModel assertionModel = new ConjunctionAssertionModel(0.6);
        assertThat(assertionModel.assertion(observation, concepts, 0.42),
                equalTo(new Assertion(4, 0.42)));
    }

}
