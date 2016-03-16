package conceptualspace;

import java.util.ArrayList;
import java.util.List;

public class StaticObjectPool implements ObjectPool {

    private List<PerceptualObject> objects = new ArrayList<PerceptualObject>();

    public StaticObjectPool(final List<PerceptualObject> objects) {
        this.objects.addAll(objects);
    }

    public PerceptualObject pick() {
        return objects.remove(0);
    }

    public List<PerceptualObject> pick(final int numObjects) {
        final List<PerceptualObject> objects = new ArrayList<PerceptualObject>();
        for (int i = 0; i < numObjects; i++) {
            objects.add(pick());
        }
        return objects;
    }

}
