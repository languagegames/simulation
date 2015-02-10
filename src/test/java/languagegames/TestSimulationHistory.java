package languagegames;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import languagegames.analysis.Analysis;
import languagegames.analysis.TimeSeries;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import agent.Agent;
import agent.BasicAgent;
import agent.Concept;
import conceptualspace.Point;

public class TestSimulationHistory {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock Analysis analysis;

	private final Population population0 = somePopulation(),
			population1 = somePopulation(), population2 = somePopulation();
	private final double result0 = 0.42, result1 = 0.43, result2 = 0.44;

	@Test
	public void createsTimeSeriesOfResultsFromAnalysis() {
		final SimulationHistory history = new SimulationHistory(asList(population0, population1, population2));

		context.checking(new Expectations() {{
			oneOf(analysis).analyse(population0); will(returnValue(result0));
			oneOf(analysis).analyse(population1); will(returnValue(result1));
			oneOf(analysis).analyse(population2); will(returnValue(result2));
		}});

		assertThat(history.timeSeriesFrom(analysis), equalTo(new TimeSeries(result0, result1, result2)));
	}

	private Population somePopulation() {
		final Agent someAgent = new BasicAgent(0.42, new Concept(new Point(0.42), 0.42));
		return new Population(asList(someAgent));
	}

}
