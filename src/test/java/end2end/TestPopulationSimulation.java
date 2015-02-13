package end2end;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import languagegames.AgentPairer;
import languagegames.ObjectPool;
import languagegames.BasicPopulation;
import languagegames.Simulation;
import languagegames.SimulationHistory;
import languagegames.StaticObjectPool;
import languagegames.StaticPairer;
import languagegames.analysis.Analysis;
import languagegames.analysis.ConvergenceAnalysis;
import languagegames.analysis.TimeSeries;

import org.junit.Test;

import agent.Agent;
import agent.BasicAgent;
import agent.Concept;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;
import datareader.DataReader;

public class TestPopulationSimulation {

	@Test
	public void populationWithAnOracle() {

	}

	@Test
	public void fourAgentsTwoConceptsThreeTimeSteps() {
		final DataReader dataReader = new DataReader();
		final List<PerceptualObject> materials = SimpleObject.makeListFrom(
				dataReader.points("randomdata.csv"));
		final ObjectPool objectCreator = new StaticObjectPool(materials);
		final AgentPairer staticPairer = new StaticPairer(
				dataReader.integers("interaction-specs.csv"));

		final Agent agent0 = new BasicAgent(0.8,
				new Concept(new Point(0.7, 0.5), 1.3),
				new Concept(new Point(0.2, 0.8), 0.7));
		final Agent agent1 = new BasicAgent(0.2,
				new Concept(new Point(0.4, 0.6), 1.6),
				new Concept(new Point(0.7, 0.6), 1.3));
		final Agent agent2 = new BasicAgent(0.3,
				new Concept(new Point(0.7, 0.2), 0.9),
				new Concept(new Point(0.3, 0.4), 2.0));
		final Agent agent3 = new BasicAgent(0.4,
				new Concept(new Point(0.9, 1.0), 1.9),
				new Concept(new Point(0.9, 0.1), 1.6));

		final BasicPopulation population = new BasicPopulation(
				asList(agent0, agent1, agent2, agent3), objectCreator, staticPairer);

		final Simulation simulation = new Simulation(population, 0.1);
		final SimulationHistory history = simulation.run(3);

		final Analysis convergenceAnalysis = new ConvergenceAnalysis();

		assertThat(history.timeSeriesFrom(convergenceAnalysis), equalTo(
				new TimeSeries(0.8722504001664029, 0.8254160763067372, 0.8818362054736917, 0.8818362054736917)));

	}

}
