package it.unipi.trustgraphmanager.factories;

import it.unipi.trustgraphmanager.strategies.pathevaluation.AvgTopFiveTrustPaths;
import it.unipi.trustgraphmanager.strategies.pathevaluation.MaxTrustPath;
import it.unipi.trustgraphmanager.strategies.pathevaluation.MinMaxTrustPath;
import it.unipi.trustgraphmanager.strategies.pathevaluation.PathsEvaluationStrategy;
import it.unipi.trustgraphmanager.utilities.Constants;

import java.util.HashMap;
import java.util.Map;

public class PathStrategyFactory {

    public final static Map<String, PathsEvaluationStrategy> strategyMapper = new HashMap<>();

    private final static PathsEvaluationStrategy defaultStrategy = new MaxTrustPath();

    static {
        strategyMapper.put(Constants.MAX_TRUST_PATH, defaultStrategy);
        strategyMapper.put(Constants.TOP_FIVE_TRUST_PATHS, new AvgTopFiveTrustPaths());
        strategyMapper.put(Constants.MIN_MAX_TRUST_PATHS, new MinMaxTrustPath());
    }

    public static <T> PathsEvaluationStrategy getEvaluationStrategy(final String evaluationStrategyName) {
        return strategyMapper.getOrDefault(evaluationStrategyName, defaultStrategy);
    }
}
