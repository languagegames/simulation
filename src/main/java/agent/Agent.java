package agent;

import java.util.List;

import conceptualspace.PerceptualObject;

public interface Agent {

	Assertion classify(PerceptualObject object);

	Agent learn(Assertion assertion);

	double convergenceWith(Agent other);

	double convergenceWith(List<Concept> concepts);

	double weight();

	Agent incrementWeight(double weightIncrement);

	double labelOverlap();

}
