package datareader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import org.junit.Test;

import conceptualspace.Point;

public class TestDataReader {

	@Test
	public void createsListOfLabelsFromDataFile() {
		final DataReader reader = new DataReader();
		assertThat(reader.labels("src/test/resources/testlabels.csv"),
				contains(0, 1, 1, 0, 0));
	}

	@Test
	public void createsListOfPointsFromDataFile() {
		final DataReader reader = new DataReader();
		assertThat(reader.points("src/test/resources/testdata.csv"), contains(
				point(0.1, 0.8), point(0.5, 0.5), point(0.4, 0.2), point(0.9, 0.8), point(0.7, 0.3)));
	}

	private Point point(final Double...coordinates) {
		return new Point(coordinates);
	}

}
