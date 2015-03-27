package end2end;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import languagegames.AgentPairer;
import languagegames.BasicPopulation;
import languagegames.ObjectPool;
import languagegames.Population;
import languagegames.Simulation;
import languagegames.SimulationHistory;
import languagegames.StaticObjectPool;
import languagegames.StaticPairer;
import languagegames.analysis.Analysis;
import languagegames.analysis.CommunicationAnalysis;
import languagegames.analysis.TimeSeries;

import org.junit.Before;
import org.junit.Test;

import agent.Agent;
import agent.BasicAgentBuilder;
import agent.concept.FuzzyConcept;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;
import datareader.DataReader;

public class TestPopulationSimulation {

	private final DataReader dataReader = new DataReader();
	private Agent agent0, agent1, agent2, agent3;
	private List<PerceptualObject> objects;
	private ObjectPool objectPool, analysisPool;
	private AgentPairer staticPairer;

	@Before
	public void setUp() {
		agent0 = agent()
					.withConcepts(
							new FuzzyConcept(new Point(0.7, 0.5), 1.3),
							new FuzzyConcept(new Point(0.2, 0.8), 0.7))
					.withWeight(0.8)
					.build();

		agent1 = agent()
				.withConcepts(
						new FuzzyConcept(new Point(0.4, 0.6), 1.6),
						new FuzzyConcept(new Point(0.7, 0.6), 1.3))
				.withWeight(0.2)
				.build();

		agent2 = agent()
				.withConcepts(
						new FuzzyConcept(new Point(0.7, 0.2), 0.9),
						new FuzzyConcept(new Point(0.3, 0.4), 2.0))
				.withWeight(0.3)
				.build();

		agent3 = agent()
				.withConcepts(
						new FuzzyConcept(new Point(0.9, 1.0), 1.9),
						new FuzzyConcept(new Point(0.9, 0.1), 1.6))
				.withWeight(0.4)
				.build();

		objects = SimpleObject.makeListFrom(dataReader.points("randomdata.csv", 2));
		objectPool = new StaticObjectPool(objects);
		analysisPool = new StaticObjectPool(objects);
		staticPairer = new StaticPairer(dataReader.integers("interaction-specs.csv"));
	}

	@Test
	public void fourAgentsTwoConceptsThreeTimeSteps() {
		final Population population = new BasicPopulation(
				asList(agent0, agent1, agent2, agent3), objectPool, staticPairer);

		final Simulation simulation = new Simulation(population, 0.1);
		final SimulationHistory history = simulation.run(3);

		final Analysis communicationAnalysis = new CommunicationAnalysis(1, staticPairer, analysisPool);

		assertThat(history.timeSeriesFrom(communicationAnalysis), equalTo(
				new TimeSeries(0.5, 0.0, 0.5, 0.5)));

	}

	private BasicAgentBuilder agent() {
		return new BasicAgentBuilder();
	}

}
