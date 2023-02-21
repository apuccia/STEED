package it.unipi.trustgraphmanager.strategies.pathevaluation;

import it.unipi.trustgraphmanager.dtos.MaxTrustPathDTO;
import it.unipi.trustgraphmanager.dtos.VerifyEdgeDTO;
import it.unipi.trustgraphmanager.utilities.wrappers.Node;
import it.unipi.trustgraphmanager.utilities.wrappers.Path;

import java.util.ArrayList;
import java.util.List;

public class MaxTrustPath implements PathsEvaluationStrategy<MaxTrustPathDTO> {

    @Override
    public MaxTrustPathDTO evaluatePaths(final List<Path> paths, final String context) {
        Float maxTrust = 0F;
        Path bestPath = Path.builder().path(new ArrayList<>()).build();

        for (Path path : paths) {
            final Node lastNode = path.get(path.size() - 1);
            if (lastNode.getTrust() > maxTrust || (lastNode.getTrust()
                    .equals(maxTrust) && path.size() < bestPath.size())) {
                maxTrust = lastNode.getTrust();
                bestPath = path;
            }
        }

        return MaxTrustPathDTO.builder().path(bestPath.getPath().stream().map(n ->
                        VerifyEdgeDTO.builder().did(n.getDid()).layer(n.getType()).context(context).build()).toList())
                .trust(maxTrust).build();
    }
}
