package datareader;

import static java.util.Arrays.asList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import conceptualspace.Point;

public class DataReader {

	public double[][] array2d(final String fileName) {
		final List<String> lines = lines(fileName);
		final double[][] array = new double[lines.size()][0];
		int index = 0;
		for (final String line : lines) {
			array[index++] = array(line);
		}
		return array;
	}

	private double[] array(final String line) {
		final List<String> strings = asList(line.split(","));
		final double[] array = new double[strings.size()];
		int index = 0;
		for (final String string : strings) {
			array[index++] = Double.valueOf(string);
		}
		return array;
	}

	public List<Integer> integers(final String fileName) {
		final List<String> lines = lines(fileName);
		final List<Integer> integers = new ArrayList<>();
		for (final String line : lines) {
			integers.addAll(lineInts(line));
		}
		return integers;
	}

	private List<Integer> lineInts(final String line) {
		final List<String> strings = asList(line.split(","));
		final List<Integer> integers = new ArrayList<>();
		for (final String string : strings) {
			integers.add(Integer.valueOf(string));
		}
		return integers;
	}

	public List<Point> points(final String fileName, final int numDimensions) {
		final List<String> lines = lines(fileName);
		final List<Point> points = new ArrayList<>();
		for (final String line : lines) {
			points.add(point(line, numDimensions));
		}
		return points;
	}

	private Point point(final String line, final int numDimensions) {
		final List<String> strings = asList(line.split(","));
		final List<Double> coordinates = new ArrayList<>();
		for (int i=0; i<numDimensions; i++) {
			coordinates.add(Double.valueOf(strings.get(i)));
		}
		return new Point(coordinates);
	}

	private List<String> lines(final String fileName) {
		final List<String> lines = read(fileName);
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

	private List<String> read(final String fileName) {
		final List<String> result = new ArrayList<>();
		final ClassLoader classLoader = getClass().getClassLoader();
		final File file = new File(classLoader.getResource(fileName).getFile());
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				final String line = scanner.nextLine();
				result.add(line);
			}
			scanner.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
