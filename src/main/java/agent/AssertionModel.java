package agent;

import java.util.List;

import conceptualspace.PerceptualObject;

public interface AssertionModel {

	Assertion assertion(PerceptualObject object, List<Concept> concepts, double weight);

}