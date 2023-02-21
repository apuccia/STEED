package it.unipi.trustgraphmanager.strategies.trustcomputing;

public class MultiplicativeTrust implements TrustComputingStrategy {

    @Override
    public Float computeTrust(final Float cumulativeTrust, final Float edgeWeight) {
        return cumulativeTrust * edgeWeight;
    }
}
