package languagegames.analysis;

import java.util.List;

import languagegames.Population;
import agent.Agent;

public interface Analysis {

	double analyse(Population population);

	double analyse(List<Agent> agents);

}
