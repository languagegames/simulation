package datareader;

import static java.lang.Double.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import conceptualspace.Point;

public class DataReader {

	public List<Point> points(final String file) {
		final List<Point> points = new ArrayList<>();
		try {
			final List<String> lines = read(file);
			for (final String line : lines) {
				addNextPoint(points, line);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return points;
	}

	private void addNextPoint(final List<Point> points, final String line) {
		if (! line.isEmpty()) {
			final List<String> lineStrings = new ArrayList<>();
			lineStrings.addAll(asList(line.split(",")));
			points.add(point(lineStrings));
		}
	}

	private Point point(final List<String> lineStrings) {
		final List<Double> coordinates = new ArrayList<>();
		for (final String string : lineStrings) {
			coordinates.add(valueOf(string));
		}
		return new Point(coordinates);
	}

	private List<String> read(final String file) throws IOException {
		final Path path = get(file);
		return readAllLines(path, UTF_8);
	}

	public List<Integer> labels(final String file) {
		// TODO Auto-generated method stub
		return null;
	}

}
