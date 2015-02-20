package utility;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ResultsPrinter {

	public static void print(final String stringToPrint, final String fileName) {
		try{
			final FileWriter fstream = new FileWriter(fileName);
			final BufferedWriter out1 = new BufferedWriter(fstream);
			out1.write(stringToPrint);
			final BufferedWriter out = out1;
			out.close();
		} catch (final Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

}