package agent;

import conceptualspace.Material;
import experiment.Assertion;

public interface Agent {

	Assertion classify(Material material);

	Agent learn(Assertion label);

}
