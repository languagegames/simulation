package agent.concept;

import agent.Assertion;
import conceptualspace.Point;

public interface Concept {

	double appropriatenessOf(Point observation);

	Concept update(Assertion assertion);

}