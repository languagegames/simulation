package population;

import experiment.analysis.Analysis;

public interface Population {

	Population runLanguageGames();

	Population incrementWeights(double weightIncrement);

	double apply(Analysis analysis);

}