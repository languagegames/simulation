package utility;

import conceptualspace.Point;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class DataReader {

    public static double[][] array2d(final String fileName) {
        final List<String> lines = lines(fileName);
        final double[][] array = new double[lines.size()][0];
        int index = 0;
        for (final String line : lines) {
            array[index++] = array(line);
        }
        return array;
    }

    private static double[] array(final String line) {
        final List<String> strings = asList(line.split(","));
        final double[] array = new double[strings.size()];
        int index = 0;
        for (final String string : strings) {
            array[index++] = Double.valueOf(string);
        }
        return array;
    }

    public static List<Integer> integers(final String fileName) {
        final List<String> lines = lines(fileName);
        final List<Integer> integers = new ArrayList<>();
        for (final String line : lines) {
            integers.addAll(lineInts(line));
        }
        return integers;
    }

    private static List<Integer> lineInts(final String line) {
        final List<String> strings = asList(line.split(","));
        return strings.stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    public static List<Point> points(final String fileName, final int numDimensions) {
        return lines(fileName).stream()
                .map(line -> point(line, numDimensions))
                .collect(Collectors.toList());
    }

    private static Point point(final String line, final int numDimensions) {
        final List<String> strings = asList(line.split(","));
        final List<Double> coordinates = new ArrayList<>();
        for (int i = 0; i < numDimensions; i++) {
            coordinates.add(Double.valueOf(strings.get(i)));
        }
        return new Point(coordinates);
    }

    private static List<String> lines(final String fileName) {
        final List<String> lines = read(fileName);
        removeEmptyLines(lines);
        return lines;
    }

    private static void removeEmptyLines(final List<String> lines) {
        for (final Iterator<String> iterator = lines.iterator(); iterator.hasNext(); ) {
            final String line = iterator.next();
            if (line.isEmpty()) {
                iterator.remove();
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static List<String> read(final String fileName) {
        final List<String> result = new ArrayList<>();
        final ClassLoader classLoader = DataReader.class.getClassLoader();
        final InputStream stream = classLoader.getResourceAsStream(fileName);
        Scanner scanner = new Scanner(stream);
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            result.add(line);
        }
        scanner.close();
        return result;
    }

}
