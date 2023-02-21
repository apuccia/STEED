package it.unipi.trustgraphmanager.strategies.trustcomputing;

public interface TrustComputingStrategy {

    Float computeTrust(final Float cumulativeTrust, final Float edgeWeight);
}
