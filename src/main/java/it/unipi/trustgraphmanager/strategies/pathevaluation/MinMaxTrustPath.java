package it.unipi.trustgraphmanager.strategies.pathevaluation;

import it.unipi.trustgraphmanager.dtos.MinMaxTrustPathDTO;
import it.unipi.trustgraphmanager.dtos.VerifyEdgeDTO;
import it.unipi.trustgraphmanager.utilities.wrappers.Node;
import it.unipi.trustgraphmanager.utilities.wrappers.Path;

import java.util.List;

public class MinMaxTrustPath implements PathsEvaluationStrategy<MinMaxTrustPathDTO> {

    @Override
    public MinMaxTrustPathDTO evaluatePaths(final List<Path> paths, final String context) {
        Float maxTrust = 0F, minTrust = 1F;
        Path maxTrustPath = Path.builder().build();
        Path minTrustPath = Path.builder().build();

        for (Path path : paths) {
            final Node lastNode = path.get(path.size() - 1);
            if (lastNode.getTrust() > maxTrust || (lastNode.getTrust().equals(maxTrust) && path.getPath()
                    .size() < maxTrustPath.size())) {
                maxTrust = lastNode.getTrust();
                maxTrustPath = path;
            } else if (lastNode.getTrust() < minTrust || (lastNode.getTrust().equals(minTrust) && path.getPath()
                    .size() < minTrustPath.size())) {
                minTrust = lastNode.getTrust();
                minTrustPath = path;
            }
        }

        return MinMaxTrustPathDTO.builder().maxTrustPath(maxTrustPath.getPath().stream().map(n ->
                        VerifyEdgeDTO.builder().did(n.getDid()).layer(n.getType()).context(context).build()).toList())
                .maxTrust(maxTrust).minTrustPath(minTrustPath.getPath().stream().map(n ->
                        VerifyEdgeDTO.builder().did(n.getDid()).layer(n.getType()).context(context).build()).toList())
                .minTrust(minTrust).build();
    }
}
