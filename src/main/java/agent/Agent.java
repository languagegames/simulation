package agent;

import java.util.List;

import agent.assertions.Assertion;
import agent.concept.LabelCount;
import conceptualspace.PerceptualObject;

public interface Agent {

	Assertion assertion(PerceptualObject object);

	Agent learn(Assertion assertion);

	double weight();

	Agent incrementWeight(double weightIncrement);

	int guess(List<PerceptualObject> guessingSet, Assertion assertion);

	LabelCount labelCount();

}
