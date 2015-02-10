package classifier;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import org.junit.Test;

import classifier.ExperimentData;
import conceptualspace.Material;
import conceptualspace.Point;
import conceptualspace.SimpleMaterial;

public class TestExperimentData {

	private final Material someMaterial = new SimpleMaterial(new Point(0.42));
	private final Material material0 = someMaterial, material1 = someMaterial,
			material2 = someMaterial, material3 = someMaterial;

	@Test
	public void splitsDataAccordingToSpecifiedProportion() {
		final ExperimentData data = new ExperimentData(asList(material0, material1, material2, material3), 0.75);

		assertThat(data.trainingSet(), contains(material0, material1, material2));
		assertThat(data.testSet(), contains(material3));
	}

}
