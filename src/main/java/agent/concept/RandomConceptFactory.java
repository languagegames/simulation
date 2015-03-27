package agent.concept;


public interface RandomConceptFactory {

	Concept randomConcept(int numDimensions);

	Concept randomConcept(int numDimensions, double threshold);

}
