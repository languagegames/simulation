package languagegames;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import conceptualspace.PerceptualObject;

public class SuppliedObjectPool implements ObjectPool {

	private final List<PerceptualObject> objects = new ArrayList<>();
	private final Random random;

	public SuppliedObjectPool(final List<PerceptualObject> objects) {
		this.objects.addAll(objects);
		random = new Random();
	}

	@Override
	public PerceptualObject pick() {
		return objects.get(random.nextInt(objects.size()));
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
