package conceptualspace;

import java.util.List;

public interface ObjectPool {

	PerceptualObject pick();

	List<PerceptualObject> pick(int numObjects);

}
