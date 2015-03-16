package datareader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import org.junit.Test;

import conceptualspace.Point;

public class TestDataReader {

	@Test
	public void readsIntegersAcrossRowsThenColumns() {
		final DataReader reader = new DataReader();
		assertThat(reader.integers("interaction-specs.csv").subList(0, 12),
				contains(1, 0, 3, 2, 0, 2, 1, 3, 2, 3, 1, 0));
	}

	@Test
	public void createsListOfIntegersFromDataFile() {
		final DataReader reader = new DataReader();
		assertThat(reader.integers("testlabels.csv"),
				contains(0, 1, 1, 0, 0));
	}

	@Test
	public void createsListOfPointsFromDataFile() {
		final DataReader reader = new DataReader();
		assertThat(reader.points("testdata.csv"), contains(
				point(0.1, 0.8), point(0.5, 0.5), point(0.4, 0.2), point(0.9, 0.8), point(0.7, 0.3)));
	}

	private Point point(final Double...coordinates) {
		return new Point(coordinates);
	}

}
