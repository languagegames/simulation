package languagegames.analysis;

import languagegames.Population;

public class ConvergenceAnalysis implements Analysis {

	@Override
	public double analyse(final Population population) {
		return population.convergence();
	}

}
