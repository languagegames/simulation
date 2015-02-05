package experiment;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import conceptualspace.Material;

public class ExperimentData {

	private final List<Material> materials = new ArrayList<>();
	private final List<Material> trainingSet;
	private final List<Material> testSet;

	public ExperimentData(final List<Material> materials, final double trainTestSplit) {
		this.materials.addAll(materials);
		final int trainingSetSize = (int) (trainTestSplit * materials.size());
		trainingSet = materials.subList(0, trainingSetSize);
		testSet = materials.subList(trainingSetSize, materials.size());
	}

	public List<Material> allData() {
		return materials;
	}

	public List<Material> trainingSet() {
		return trainingSet;
	}

	public List<Material> testSet() {
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
