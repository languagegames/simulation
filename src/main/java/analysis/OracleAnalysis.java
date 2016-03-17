package analysis;

import agent.Agent;
import conceptualspace.ObjectPool;
import conceptualspace.PerceptualObject;
import population.AgentInteractor;

import java.util.List;

public class OracleAnalysis implements Analysis {

    private Agent oracle;
    private final int numGames;
    private final ObjectPool objectPool;
    private final AgentInteractor agentInteractor;

    public OracleAnalysis(Agent oracle, int numGames, ObjectPool objectPool, AgentInteractor agentInteractor) {
        this.oracle = oracle;
        this.numGames = numGames;
        this.objectPool = objectPool;
        this.agentInteractor = agentInteractor;
    }

    public OracleAnalysis(Agent oracle, ObjectPool objectPool, AgentInteractor agentInteractor) {
        this(oracle, 10, objectPool, agentInteractor);
    }

    public double analyse(List<Agent> agents) {
        double sumOfScores = 0;
        for (final Agent agent : agents) {
            sumOfScores += communicationScore(agent, oracle);
        }
        return sumOfScores / agents.size();
    }

    private double communicationScore(final Agent one, final Agent two) {
        final List<PerceptualObject> objects = objectPool.pick(numGames);
        double sumOfScores = 0;
        for (final PerceptualObject object : objects) {
            sumOfScores += agentInteractor.communicationGameResult(one, two, object);
        }
        return sumOfScores / numGames;
    }

}
