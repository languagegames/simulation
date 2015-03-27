package experiment;

import static agent.AgentFactory.randomAgent;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static utility.ResultsPrinter.print;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import languagegames.ObjectPool;
import languagegames.RandomObjectPool;
import agent.Agent;
import agent.Assertion;
import agent.ConjunctionAssertionModel;
import agent.concept.FuzzyConceptFactory;
import agent.concept.RandomConceptFactory;
import conceptualspace.PerceptualObject;


public class GuessingGameExperiment {

	private final int threshold;
	private final double pMin, pMax;

	private final int numRuns = 100;
	private final int numTrials = 10000;
	private final int numDimensions = 3;
	private final int numLabels = 10;
	private final int numObjects = 20;
	private final double weight = 0.42;
	private final ObjectPool objectPool = new RandomObjectPool(numDimensions);
	final RandomConceptFactory factory;

	public static void main(final String[] args) {
		final GuessingGameExperiment experiment = new GuessingGameExperiment(2, 0, 1);
		experiment.run();
	}

	public GuessingGameExperiment(final int threshold, final double pMin, final double pMax) {
		this.threshold = threshold;
		this.pMin = pMin;
		this.pMax = pMax;
		factory = new FuzzyConceptFactory(threshold);
	}

	public void run() {
		final StringBuilder builder = new StringBuilder();
		for (final Double p : pValues(pMin, pMax)) {
			final List<Double> scores = scores(p, numRuns);
			builder.append(p + ", " + mean(scores) + ", " + std(scores) + "\n");
		}
		print(builder.toString(), "b" + threshold + ".txt");
	}

	private List<Double> pValues(final double pMin, final double pMax) {
		final double step = (pMax-pMin) / 10;
		final List<Double> pValues = new ArrayList<>();
		for (int i = 0; i<11; i++) {
			pValues.add(pMin + i*step);
		}
		return pValues;
	}

	private List<Double> scores(final double p, final int numRuns) {
		final List<Double> scores = new ArrayList<>();
		for (int i=0; i<numRuns; i++) {
			final ConjunctionAssertionModel assertionModel = new ConjunctionAssertionModel(p);
			double score = 0;
			for (int j = 0; j<numTrials; j++) {
				final Agent describer = randomAgent(numDimensions, numLabels, weight, assertionModel, factory);
				final Agent guesser = describer;
				score += success(describer, guesser) ? 1 : 0;
			}
			scores.add(score / numTrials);
		}
		return scores;
	}

	private double std(final List<Double> scores) {
		final double mean = mean(scores);
		double sumOfSquares = 0;
		for (final Double score : scores) {
			sumOfSquares += pow(score - mean, 2);
		}
		return sqrt(sumOfSquares / (scores.size()-1));
	}

	private double mean(final List<Double> scores) {
		double sum = 0;
		for (final Double score : scores) {
			sum += score;
		}
		return sum / scores.size();
	}

	private boolean success(final Agent describer, final Agent guesser) {
		final Random random = new Random();
		final List<PerceptualObject> guessingSet = objectPool.pick(numObjects);
		final int targetIndex = random.nextInt(numObjects);
		final Assertion assertion = describer.assertion(guessingSet.get(targetIndex));
		return (guesser.guess(guessingSet, assertion) == targetIndex);
	}

}
