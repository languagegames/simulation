package agent.concept;

import conceptualspace.Point;

public interface LikelihoodModel {

	LikelihoodModel update(Point point);

	double likelihood(Point point);

}