package agent;

import java.util.ArrayList;
import java.util.List;

import classifier.ExperimentData;
import conceptualspace.Material;

public class LabelMapping {

	private final List<Material> data = new ArrayList<>();
	private final List<Integer> labels = new ArrayList<>();

	public LabelMapping(final ExperimentData data, final List<Integer> labels) {
		this.data.addAll(data.allData());
		this.labels.addAll(labels);
	}

	public int label(final Material material) {
		final int index = data.indexOf(material);
		return labels.get(index);
	}

}
