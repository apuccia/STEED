package it.unipi.trustgraphmanager.strategies.pathevaluation;

import it.unipi.trustgraphmanager.dtos.AvgTopFiveTrustPathsDTO;
import it.unipi.trustgraphmanager.dtos.VerifyEdgeDTO;
import it.unipi.trustgraphmanager.utilities.wrappers.Path;

import java.util.List;
import java.util.PriorityQueue;

public class AvgTopFiveTrustPaths implements PathsEvaluationStrategy<AvgTopFiveTrustPathsDTO> {

    @Override
    public AvgTopFiveTrustPathsDTO evaluatePaths(final List<Path> paths, final String context) {
        final PriorityQueue<Path> bestPaths = new PriorityQueue<>((o1, o2) -> {
            if (o1.get(o1.size() - 1).getTrust() > o2.get(o2.size() - 1).getTrust()) {
                return 1;
            } else if (o1.get(o1.size() - 1).getTrust() < o2.get(o2.size() - 1).getTrust()) {
                return -1;
            } else {
                return o1.size() - o2.size();
            }
        });

        bestPaths.addAll(paths);
        final List<Path> topFive = bestPaths.stream().limit(5).toList();
        Float trustSum = topFive.stream().map(p -> p.getPath().get(p.size() - 1).getTrust()).reduce(0F, Float::sum);
        trustSum = topFive.size() == 0 ? 0 : trustSum / topFive.size();
        return AvgTopFiveTrustPathsDTO.builder()
                .paths(topFive.stream().map(p -> p.getPath().stream().map(n ->
                        VerifyEdgeDTO.builder().did(n.getDid()).layer(n.getType()).context(context).build()).toList()).toList())
                .avgTrust(trustSum).build();
    }
}
