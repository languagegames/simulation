package languagegames;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FakeRandom extends Random {

	private static final long serialVersionUID = 1L;
	private final List<Integer> integers = new ArrayList<>();

	public FakeRandom(final Integer...integers) {
		this.integers.addAll(asList(integers));
	}

	@Override
	public int nextInt(final int n) {
		return integers.remove(0);
	}

}
