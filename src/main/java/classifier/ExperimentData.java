package classifier;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.PerceptualObject;

public class ExperimentData {

	private final List<PerceptualObject> materials = new ArrayList<>();
	private final List<PerceptualObject> trainingSet;
	private final List<PerceptualObject> testSet;

	public ExperimentData(final List<PerceptualObject> materials, final double trainTestSplit) {
		this.materials.addAll(materials);
		final int trainingSetSize = (int) (trainTestSplit * materials.size());
		trainingSet = materials.subList(0, trainingSetSize);
		testSet = materials.subList(trainingSetSize, materials.size());
	}

	public List<PerceptualObject> allData() {
		return materials;
	}

	public List<PerceptualObject> trainingSet() {
		return trainingSet;
	}

	public List<PerceptualObject> testSet() {
		return testSet;
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
