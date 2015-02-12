package languagegames;

import java.util.ArrayList;
import java.util.List;

import conceptualspace.PerceptualObject;

public class StaticObjectPool implements ObjectPool {

	List<PerceptualObject> objects = new ArrayList<>();

	public StaticObjectPool(final List<PerceptualObject> objects) {
		this.objects.addAll(objects);
	}

	@Override
	public PerceptualObject pick() {
		return objects.remove(0);
	}

}
