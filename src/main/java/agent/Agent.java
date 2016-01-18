package agent;

import java.util.List;

import agent.assertions.Assertion;
import conceptualspace.Point;

public interface Agent {

	Assertion assertion(Point observation);

	Agent learn(Point observation, Assertion assertion);

	double weight();

	Agent incrementWeight(double weightIncrement);

	int guess(List<Point> observations, Assertion assertion);

}
