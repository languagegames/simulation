package languagegames;

import java.util.List;

import conceptualspace.PerceptualObject;

public interface ObjectPool {

	PerceptualObject pick();

	List<PerceptualObject> pick(int numObjects);

}
