package it.unipi.trustgraphmanager.factories;

import it.unipi.trustgraphmanager.strategies.trustcomputing.MultiplicativeTrust;
import it.unipi.trustgraphmanager.strategies.trustcomputing.TrustComputingStrategy;
import it.unipi.trustgraphmanager.utilities.Constants;

import java.util.HashMap;
import java.util.Map;

public class TrustStrategyFactory {
    public final static Map<String, TrustComputingStrategy> strategyMapper = new HashMap<>();
    private final static TrustComputingStrategy defaultStrategy = new MultiplicativeTrust();
    static {
        strategyMapper.put(Constants.MULTIPLICATIVE_TRUST, defaultStrategy);
    }

    public static TrustComputingStrategy getComputingStrategy(final String computingStrategyName) {
        return strategyMapper.getOrDefault(computingStrategyName, defaultStrategy);
    }
}
