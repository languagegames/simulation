package conceptualspace;

import java.util.ArrayList;
import java.util.List;

public class StaticObjectPool implements ObjectPool {

	List<PerceptualObject> objects = new ArrayList<>();

	public StaticObjectPool(final List<PerceptualObject> objects) {
		this.objects.addAll(objects);
	}

	@Override
	public PerceptualObject pick() {
		return objects.remove(0);
	}

	@Override
	public List<PerceptualObject> pick(final int numObjects) {
		final List<PerceptualObject> objects = new ArrayList<>();
		for (int i=0; i<numObjects; i++) {
			objects.add(pick());
		}
		return objects;
	}

}
