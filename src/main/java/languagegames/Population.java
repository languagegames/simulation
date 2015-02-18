package languagegames;

import languagegames.analysis.Analysis;

public interface Population {

	Population runLanguageGames();

	double convergence();

	Population incrementWeights(double weightIncrement);

	double apply(Analysis analysis);

}