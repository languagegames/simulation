package experiment.population;

import analysis.Analysis;
import analysis.TimeSeries;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import population.Population;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestSimulationHistory {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    Analysis analysis;
    @Mock
    Population population0, population1, population2;

    private final double result0 = 0.42, result1 = 0.43, result2 = 0.44;

    @Test
    public void createsTimeSeriesFromSpecifiedNumberOfTimeStepsPlusInitialTimeStep() {
        final SimulationHistory history = new SimulationHistory(asList(population0, population1, population2));

        context.checking(new Expectations() {{
            oneOf(population0).apply(analysis);
            will(returnValue(result0));
            oneOf(population2).apply(analysis);
            will(returnValue(result2));
        }});

        assertThat(history.timeSeriesFrom(analysis, 1),
                equalTo(new TimeSeries(asList(result0, result2), asList(0, 2))));
    }

    @Test
    public void createsTimeSeriesFromSpecifiedTimeSteps() {
        final SimulationHistory history = new SimulationHistory(asList(population0, population1, population2));

        context.checking(new Expectations() {{
            oneOf(population0).apply(analysis);
            will(returnValue(result0));
            oneOf(population2).apply(analysis);
            will(returnValue(result2));
        }});

        assertThat(history.timeSeriesFrom(analysis, asList(0, 2)),
                equalTo(new TimeSeries(asList(result0, result2), asList(0, 2))));
    }

    @Test
    public void createsTimeSeriesOfResultsFromAnalysis() {
        final SimulationHistory history = new SimulationHistory(asList(population0, population1, population2));

        context.checking(new Expectations() {{
            oneOf(population0).apply(analysis);
            will(returnValue(result0));
            oneOf(population1).apply(analysis);
            will(returnValue(result1));
            oneOf(population2).apply(analysis);
            will(returnValue(result2));
        }});

        assertThat(history.timeSeriesFrom(analysis), equalTo(new TimeSeries(result0, result1, result2)));
    }

}
