package utility;

import conceptualspace.Point;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

public class TestDataReader {

    @Test
    public void creates2dArrayFromFile() {
        final double[][] array = {{.1, .8}, {.5, .5}, {.4, .2}, {.9, .8}, {.7, .3}, {.8, .5}};
        assertThat(DataReader.array2d("testdata.csv"), equalTo(array));
    }

    @Test
    public void readsIntegersAcrossRowsThenColumns() {
        assertThat(DataReader.integers("interaction-specs.csv").subList(0, 12),
                contains(1, 0, 3, 2, 0, 2, 1, 3, 2, 3, 1, 0));
    }

    @Test
    public void createsListOfIntegersFromDataFile() {
        assertThat(DataReader.integers("testlabels.csv"),
                contains(0, 1, 1, 0, 0, 1));
    }

    @Test
    public void createsListOfPointsFromDataFile() {
        assertThat(DataReader.points("testdata.csv", 2), contains(
                point(0.1, 0.8), point(0.5, 0.5), point(0.4, 0.2), point(0.9, 0.8), point(0.7, 0.3), point(0.8, 0.5)));
    }

    private Point point(final double... coordinates) {
        return new Point(coordinates);
    }

}
