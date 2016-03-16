package agent;

import agent.assertions.AssertionModel;
import agent.assertions.BasicAssertionModel;
import agent.concept.Concept;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class BasicAgentBuilder {

    private final List<Concept> concepts = new ArrayList<Concept>();
    private double weight = 0;
    private AssertionModel assertionModel = new BasicAssertionModel();

    public BasicAgentBuilder withConcepts(final List<Concept> concepts) {
        this.concepts.addAll(concepts);
        return this;
    }

    public BasicAgentBuilder withConcepts(final Concept... concepts) {
        this.concepts.addAll(asList(concepts));
        return this;
    }

    public BasicAgentBuilder withWeight(final double weight) {
        this.weight = weight;
        return this;
    }

    public BasicAgentBuilder withAssertionModel(final AssertionModel assertionModel) {
        this.assertionModel = assertionModel;
        return this;
    }

    public BasicAgent build() {
        return new BasicAgent(concepts, weight, assertionModel);
    }

}
