package experiment.population;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import population.AgentInteractor;
import population.AgentPairer;
import population.BasicPopulation;
import population.DifferentObservationInteractor;
import population.Population;
import population.StaticPairer;
import utility.DataReader;
import agent.Agent;
import agent.BasicAgentBuilder;
import agent.concept.FuzzyConcept;
import analysis.Analysis;
import analysis.CommunicationAnalysis;
import analysis.TimeSeries;
import conceptualspace.ObjectPool;
import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;
import conceptualspace.StaticObjectPool;

public class TestSimulation {

	private Agent agent0, agent1, agent2, agent3;
	private List<PerceptualObject> objects;
	private ObjectPool objectPool, analysisPool;
	private AgentPairer staticPairer;
	private final AgentInteractor agentInteractor = new DifferentObservationInteractor();

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

		objects = SimpleObject.makeListFrom(DataReader.points("randomdata.csv", 2));
		objectPool = new StaticObjectPool(objects);
		analysisPool = new StaticObjectPool(objects);
		staticPairer = new StaticPairer(DataReader.integers("interaction-specs.csv"));
	}

	@Test
	public void fourAgentsTwoConceptsThreeTimeSteps() {
		final Population population = new BasicPopulation(
				asList(agent0, agent1, agent2, agent3), objectPool, staticPairer, new DifferentObservationInteractor());

		final Simulation simulation = new Simulation(population, 0.1);
		final SimulationHistory history = simulation.run(3);

		final Analysis communicationAnalysis = new CommunicationAnalysis(1, staticPairer, analysisPool, agentInteractor);

		assertThat(history.timeSeriesFrom(communicationAnalysis), equalTo(
				new TimeSeries(0.5, 0.0, 0.5, 0.5)));

	}

	private BasicAgentBuilder agent() {
		return new BasicAgentBuilder();
	}

}
