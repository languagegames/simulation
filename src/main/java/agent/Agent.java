package agent;

import java.util.List;

import conceptualspace.PerceptualObject;

public interface Agent {

	Assertion classify(PerceptualObject material);

	Agent learn(Assertion label);

	double convergenceWith(Agent other);

	double convergenceWith(List<Concept> concepts);

}
