package agent;

import java.util.List;

import agent.assertions.Assertion;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;

public interface Agent {

	Assertion assertion(PerceptualObject object);

	Agent learn(Point observation, Assertion assertion);

	double weight();

	Agent incrementWeight(double weightIncrement);

	int guess(List<PerceptualObject> guessingSet, Assertion assertion);

}
