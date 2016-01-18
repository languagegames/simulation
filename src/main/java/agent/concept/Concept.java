package agent.concept;

import agent.assertions.Assertion;
import conceptualspace.Point;

public interface Concept {

	double appropriatenessOf(Point observation);

	Concept update(Point observation, Assertion assertion);

}