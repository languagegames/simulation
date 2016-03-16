package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class FakeRandom extends Random {

    private static final long serialVersionUID = 1L;
    private final List<Integer> integers = new ArrayList<Integer>();
    private final List<Double> doubles = new ArrayList<Double>();

    public FakeRandom(final Integer... integers) {
        this.integers.addAll(asList(integers));
    }

    public FakeRandom(final Double... doubles) {
        this.doubles.addAll(asList(doubles));
    }

    @Override
    public double nextGaussian() {
        return doubles.remove(0);
    }

    @Override
    public double nextDouble() {
        return doubles.remove(0);
    }

    @Override
    public int nextInt(final int n) {
        return integers.remove(0);
    }

}
