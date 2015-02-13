package languagegames;

public interface Population {

	Population runLanguageGames();

	double convergence();

	Population incrementWeights(double weightIncrement);

}