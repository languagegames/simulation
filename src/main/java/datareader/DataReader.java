package datareader;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import conceptualspace.Point;

public class DataReader {

	public List<Integer> integers(final String file) {
		final List<String> lines = lines(file);
		final List<Integer> integers = new ArrayList<>();
		for (final String line : lines) {
			integers.add(Integer.valueOf(line));
		}
		return integers;
	}

	public List<Point> points(final String file) {
		final List<String> lines = lines(file);
		final List<Point> points = new ArrayList<>();
		for (final String line : lines) {
			points.add(point(line));
		}
		return points;
	}

	private Point point(final String line) {
		final List<String> strings = asList(line.split(","));
		final List<Double> coordinates = new ArrayList<>();
		for (final String string : strings) {
			coordinates.add(Double.valueOf(string));
		}
		return new Point(coordinates);
	}

	private List<String> lines(final String file) {
		final List<String> lines = new ArrayList<>();
		try {
			lines.addAll(read(file));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		removeEmptyLines(lines);
		return lines;
	}

	private void removeEmptyLines(final List<String> lines) {
		for (final Iterator<String> iterator = lines.iterator(); iterator.hasNext();) {
		    final String line = iterator.next();
		    if (line.isEmpty()) {
		        iterator.remove();
		    }
		}
	}

	private List<String> read(final String file) throws IOException {
		final Path path = get(file);
		return readAllLines(path, UTF_8);
	}

}
