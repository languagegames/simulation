package agent;

import java.util.ArrayList;
import java.util.List;

import conceptualspace.PerceptualObject;

public class LabelMapping {

	private final List<PerceptualObject> data = new ArrayList<>();
	private final List<Integer> labels = new ArrayList<>();

	public LabelMapping(final List<PerceptualObject> data, final List<Integer> labels) {
		this.data.addAll(data);
		this.labels.addAll(labels);
	}

	public int label(final PerceptualObject object) {
		final int index = data.indexOf(object);
		return labels.get(index);
	}

}
