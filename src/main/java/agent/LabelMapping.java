package agent;

import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;

import java.util.ArrayList;
import java.util.List;

public class LabelMapping {

    private final List<PerceptualObject> data = new ArrayList<PerceptualObject>();
    private final List<Integer> labels = new ArrayList<Integer>();

    public LabelMapping(final List<PerceptualObject> data, final List<Integer> labels) {
        this.data.addAll(data);
        this.labels.addAll(labels);
    }

    public int label(final Point observation) {
        final PerceptualObject object = new SimpleObject(observation);
        final int index = data.indexOf(object);
        return labels.get(index);
    }

}
