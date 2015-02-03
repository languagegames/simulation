package agent;

import conceptualspace.Material;
import experiment.Label;

public interface Agent {

	Label classify(Material material);

	Agent learn(Material material, Label label);

}
