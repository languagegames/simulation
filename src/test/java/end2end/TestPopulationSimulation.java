package end2end;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import languagegames.AgentPairer;
import languagegames.ObjectCreator;
import languagegames.Population;
import languagegames.SimpleObjectCreator;
import languagegames.Simulation;
import languagegames.SimulationHistory;
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
	public void fourAgentsTwoConceptsThreeTimeSteps() {
		final DataReader dataReader = new DataReader();
		final List<PerceptualObject> materials = SimpleObject.makeListFrom(
				dataReader.points("src/test/resources/randomdata.csv"));
		final ObjectCreator objectCreator = new SimpleObjectCreator(materials);
		final AgentPairer staticPairer = new StaticPairer(
				dataReader.integers("src/test/resources/interaction-specs.csv"));

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

		final Population population = new Population(
				asList(agent0, agent1, agent2, agent3), objectCreator, staticPairer);

		final Simulation simulation = new Simulation(population, 0.1);
		final SimulationHistory history = simulation.run(3);

		final Analysis convergenceAnalysis = new ConvergenceAnalysis();

		assertThat(history.timeSeriesFrom(convergenceAnalysis), equalTo(
				new TimeSeries(0.8722504001664029, 0.8130174392765093, 0.8853576986309384, 0.8853576986309384)));

	}

}
