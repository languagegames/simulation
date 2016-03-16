package conceptualspace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuppliedObjectPool implements ObjectPool {

    private final List<PerceptualObject> objects = new ArrayList<PerceptualObject>();
    private final Random random;

    public SuppliedObjectPool(final List<PerceptualObject> objects) {
        this.objects.addAll(objects);
        random = new Random();
    }

    public PerceptualObject pick() {
        return objects.get(random.nextInt(objects.size()));
    }

    public List<PerceptualObject> pick(final int numObjects) {
        final List<PerceptualObject> objects = new ArrayList<PerceptualObject>();
        for (int i = 0; i < numObjects; i++) {
            objects.add(pick());
        }
        return objects;
    }

}
