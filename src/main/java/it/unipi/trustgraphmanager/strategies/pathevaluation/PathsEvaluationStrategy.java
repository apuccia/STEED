package it.unipi.trustgraphmanager.strategies.pathevaluation;

import it.unipi.trustgraphmanager.utilities.wrappers.Path;

import java.util.List;

public interface PathsEvaluationStrategy<T> {
    T evaluatePaths(final List<Path> paths, final String context);
}
