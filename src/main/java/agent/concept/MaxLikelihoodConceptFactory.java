package agent.concept;

import utility.matrix.Matrix;

import java.util.Random;

public class MaxLikelihoodConceptFactory implements RandomConceptFactory {

    private final Random random = new Random();

    @Override
    public Concept randomConcept(final int numDimensions) {
        final double[][] vals = new double[numDimensions + 1][numDimensions];
        for (int i = 0; i < numDimensions + 1; i++) {
            for (int j = 0; j < numDimensions; j++) {
                vals[i][j] = random.nextDouble();
            }
        }
        return new MaxLikelihoodConcept(new Matrix(vals));
    }

}
