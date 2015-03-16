package end2end;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import languagegames.AgentPairer;
import languagegames.BasicPopulation;
import languagegames.ObjectPool;
import languagegames.OraclePopulation;
import languagegames.Population;
import languagegames.Simulation;
import languagegames.SimulationHistory;
import languagegames.StaticObjectPool;
import languagegames.StaticPairer;
import languagegames.analysis.Analysis;
import languagegames.analysis.ConvergenceAnalysis;
import languagegames.analysis.TimeSeries;

import org.junit.Before;
import org.junit.Test;

import agent.Agent;
import agent.BasicAgent;
import agent.FuzzyConcept;
import agent.LabelMapping;
import agent.OracleAgent;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;
import datareader.DataReader;

public class TestPopulationSimulation {

	private final DataReader dataReader = new DataReader();
	private Agent agent0, agent1, agent2, agent3;
	private List<PerceptualObject> objects;
	private ObjectPool objectCreator;
	private AgentPairer staticPairer;

	@Before
	public void setUp() {
		agent0 = new BasicAgent(0.8,
				new FuzzyConcept(new Point(0.7, 0.5), 1.3),
				new FuzzyConcept(new Point(0.2, 0.8), 0.7));
		agent1 = new BasicAgent(0.2,
				new FuzzyConcept(new Point(0.4, 0.6), 1.6),
				new FuzzyConcept(new Point(0.7, 0.6), 1.3));
		agent2 = new BasicAgent(0.3,
				new FuzzyConcept(new Point(0.7, 0.2), 0.9),
				new FuzzyConcept(new Point(0.3, 0.4), 2.0));
		agent3 = new BasicAgent(0.4,
				new FuzzyConcept(new Point(0.9, 1.0), 1.9),
				new FuzzyConcept(new Point(0.9, 0.1), 1.6));
		objects = SimpleObject.makeListFrom(dataReader.points("randomdata.csv"));
		objectCreator = new StaticObjectPool(objects);
		staticPairer = new StaticPairer(dataReader.integers("interaction-specs.csv"));
	}

	@Test
	public void populationWithAnOracle() {
		final Agent oracle = new OracleAgent(new LabelMapping(objects, asList(0, 1, 1, 0, 0, 1)), 0.9);

		final Population population = new OraclePopulation(
				asList(agent0, agent1, agent2), asList(oracle), objectCreator, staticPairer);

		final Simulation simulation = new Simulation(population, 0.1);
		final SimulationHistory history = simulation.run(3);

		final Analysis convergenceAnalysis = new ConvergenceAnalysis();

		assertThat(history.timeSeriesFrom(convergenceAnalysis), equalTo(
				new TimeSeries(0.7523780674653353, 0.7870977036456587, 0.8075786742883934, 0.7316300842449858)));
	}

	@Test
	public void fourAgentsTwoConceptsThreeTimeSteps() {
		final Population population = new BasicPopulation(
				asList(agent0, agent1, agent2, agent3), objectCreator, staticPairer);

		final Simulation simulation = new Simulation(population, 0.1);
		final SimulationHistory history = simulation.run(3);

		final Analysis convergenceAnalysis = new ConvergenceAnalysis();

		assertThat(history.timeSeriesFrom(convergenceAnalysis), equalTo(
				new TimeSeries(0.8722504001664029, 0.8254160763067372, 0.8818362054736917, 0.8818362054736917)));

	}

}
