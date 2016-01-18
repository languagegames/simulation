package agent.assertions;

import java.util.List;

import agent.concept.Concept;
import conceptualspace.Point;

public interface AssertionModel {

	Assertion assertion(Point observation, List<Concept> concepts, double weight);

}