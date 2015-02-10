package agent;

import conceptualspace.Material;

public interface Agent {

	Assertion classify(Material material);

	Agent learn(Assertion label);

}
