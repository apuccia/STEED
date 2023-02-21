package it.unipi.trustgraphmanager.services;

import it.unipi.trustgraphmanager.contracts.ContextsGraph;
import it.unipi.trustgraphmanager.contracts.EntrypointsGraph;
import it.unipi.trustgraphmanager.contracts.IssuersGraph;
import it.unipi.trustgraphmanager.dtos.AvgTopFiveTrustPathsDTO;
import it.unipi.trustgraphmanager.dtos.MaxTrustPathDTO;
import it.unipi.trustgraphmanager.dtos.MinMaxTrustPathDTO;
import it.unipi.trustgraphmanager.dtos.TrustQueryDTO;
import it.unipi.trustgraphmanager.exceptions.ServiceException;
import it.unipi.trustgraphmanager.factories.PathStrategyFactory;
import it.unipi.trustgraphmanager.factories.TrustStrategyFactory;
import it.unipi.trustgraphmanager.strategies.pathevaluation.PathsEvaluationStrategy;
import it.unipi.trustgraphmanager.strategies.trustcomputing.TrustComputingStrategy;
import it.unipi.trustgraphmanager.utilities.Constants;
import it.unipi.trustgraphmanager.utilities.ErrorMessages;
import it.unipi.trustgraphmanager.utilities.wrappers.Edge;
import it.unipi.trustgraphmanager.utilities.wrappers.Node;
import it.unipi.trustgraphmanager.utilities.wrappers.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class TrustQueryService {

    private static final String LOG_INFO_PATHS_FOUND = "Paths found: {}";

    @Autowired
    private IssuersGraph[] issuersGraph;

    @Autowired
    private ContextsGraph[] contextsGraph;

    @Autowired
    private EntrypointsGraph[] entrypointsGraph;

    @Autowired
    private NodeService nodeService;

    private final static Map<String, Map<String, List<Edge>>> contextAdjLists = new HashMap<>();

    private final static Map<String, List<Edge>> entrypointAdjLists = new HashMap<>();

    private final static Map<String, List<Edge>> issuerAdjLists = new HashMap<>();

    private final static Map<String, Boolean> activeContextNodes = new HashMap<>();

    private final static Map<String, Boolean> activeIssuerNodes = new HashMap<>();

    private List<Edge> getEntrypointNodeAdjList(final String fromDid) throws ServiceException {
        try {
            final List<EntrypointsGraph.EntrypointTrust> adjList = entrypointsGraph[1].getAdjList(fromDid).send();
            return adjList.stream().map(entryPointTrust -> Edge.builder().from(fromDid).to(entryPointTrust.to)
                    .weight(entryPointTrust.level.floatValue() / 100).build()).toList();
        } catch (final Exception e) {
            throw new ServiceException(e, ErrorMessages.GENERIC_ERROR);
        }
    }

    private List<Edge> getIssuerNodeAdjList(final String fromDid) throws ServiceException {
        try {
            final List<IssuersGraph.IssuerTrust> adjList = issuersGraph[1].getAdjList(fromDid).send();
            return adjList.stream().map(issuerTrust -> Edge.builder().from(fromDid).to(issuerTrust.to)
                    .weight(issuerTrust.level.floatValue() / 100).build()).toList();
        } catch (Exception e) {
            throw new ServiceException(e, ErrorMessages.GENERIC_ERROR);
        }
    }

    private final List<Edge> getContextNodeAdjList(final String fromDid, final String context) throws ServiceException {
        try {
            final List<ContextsGraph.ContextTrust> adjList = contextsGraph[1].getAdjList(fromDid, context).send();
            return adjList.stream().map(contextTrust -> Edge.builder().from(fromDid).to(contextTrust.to)
                    .weight(contextTrust.level.floatValue() / 100).build()).toList();
        } catch (final Exception e) {
            throw new ServiceException(e, ErrorMessages.GENERIC_ERROR);
        }
    }

    private List<Path> getTrustL1(final TrustQueryDTO trustQuery)
            throws ServiceException {
        final TrustComputingStrategy trustComputingStrategy =
                TrustStrategyFactory.getComputingStrategy(Constants.MULTIPLICATIVE_TRUST);
        final Deque<Path> queue = new LinkedList<>();
        final List<Path> pathsToEntrypoint = new ArrayList<>();

        final Path startPath = Path.builder().path(new ArrayList<>()).build();
        startPath.add(Node.builder().did(trustQuery.getSource()).depth(0).trust(1F)
                .type(Constants.CONTEXT_NODE).build());
        queue.add(startPath);

        while (queue.size() != 0) {
            final Path path = queue.pop();
            final Node last = path.get(path.size() - 1);

            // if we are close by one step to max hops, do not add any more context nodes (the last node of
            // a path must be an issuer node
            if (last.getDepth() < trustQuery.getMaxHops() - 1) {
                // check if we have already read the context layer adjacency list for this node
                contextAdjLists.computeIfAbsent(last.getDid(), k -> new HashMap<>());
                List<Edge> contextAdjList = contextAdjLists.get(last.getDid()).get(trustQuery.getContext());
                if (contextAdjList == null) {
                    // if not, retrieve it from the blockchain
                    contextAdjList = getContextNodeAdjList(last.getDid(), trustQuery.getContext());
                    contextAdjLists.get(last.getDid()).put(trustQuery.getContext(), contextAdjList);
                }

                for (final Edge edge : contextAdjList) {
                    final Node newNode = Node.builder().did(edge.getTo()).depth(last.getDepth() + 1)
                            .trust(trustComputingStrategy.computeTrust(last.getTrust(), edge.getWeight()))
                            .type(Constants.CONTEXT_NODE).build();

                    // check to see if the node status was alread read
                    Boolean isNodeActive = activeContextNodes.get(newNode.getDid());
                    if (isNodeActive == null) {
                        //if not, retrieve it from the blockchain
                        isNodeActive = nodeService.isContextNodeActive(newNode.getDid());
                        activeContextNodes.putIfAbsent(newNode.getDid(), isNodeActive);
                    }

                    // add the connected context node, to a new path, only if it is connected using an edge
                    // that has the context required, if it is not already in the path, if it is not deactivated
                    // and if its insertion does not reduce the trust level below the minimum required
                    if (isNodeActive && !path.contains(newNode) &&
                            newNode.getTrust() >= trustQuery.getTrustThreshold()) {
                        final Path newPath = Path.builder().path(new ArrayList<>(path.getPath())).build();
                        newPath.add(newNode);
                        queue.add(newPath);
                    }
                }
            }

            // add the entrypoints to new paths if the path size is under the max hops threshold
            if (last.getDepth() < trustQuery.getMaxHops()) {
                // check if we have already read the entrypoint layer adjacency list for this node
                List<Edge> entrypointAdjList = entrypointAdjLists.get(last.getDid());
                if (entrypointAdjList == null) {
                    entrypointAdjList = getEntrypointNodeAdjList(last.getDid());
                    entrypointAdjLists.putIfAbsent(last.getDid(), entrypointAdjList);
                }

                for (final Edge edge : entrypointAdjList) {
                    final Node newNode = Node.builder().did(edge.getTo()).depth(last.getDepth() + 1)
                            .trust(trustComputingStrategy.computeTrust(last.getTrust(), edge.getWeight()))
                            .type(Constants.ISSUER_NODE).build();

                    Boolean isNodeActive = activeIssuerNodes.get(newNode.getDid());
                    if (isNodeActive == null) {
                        isNodeActive = nodeService.isIssuerNodeActive(newNode.getDid());
                        activeIssuerNodes.putIfAbsent(newNode.getDid(), isNodeActive);
                    }

                    // add the connected issuer node, to a new path, only if its insertion
                    // does not reduce the trust level below the minimum required
                    if (newNode.getTrust() >= trustQuery.getTrustThreshold() && isNodeActive) {
                        final Path pathToEntrypoint = Path.builder().path(new ArrayList<>(path.getPath())).build();
                        pathToEntrypoint.add(newNode);
                        pathsToEntrypoint.add(pathToEntrypoint);
                    }
                }
            }
        }

        return pathsToEntrypoint;
    }

    private List<Path> getTrustL2(final List<Path> entryPointPaths, final TrustQueryDTO trustQuery)
            throws ServiceException {
        final TrustComputingStrategy trustComputingStrategy =
                TrustStrategyFactory.getComputingStrategy(Constants.MULTIPLICATIVE_TRUST);
        final Deque<Path> queue = new LinkedList<>(entryPointPaths);
        final List<Path> pathsToTarget = new ArrayList<>();

        while (queue.size() != 0) {
            final Path path = queue.pop();
            final Node last = path.get(path.size() - 1);

            // we found a path to our target
            if (last.getDid().equals(trustQuery.getTarget())) {
                pathsToTarget.add(path);
                continue;
            }

            // add the issuers to new paths if the path size is under the max hops threshold
            if (last.getDepth() < trustQuery.getMaxHops()) {
                // check if we have already read the issuer layer adjacency list for this node
                List<Edge> issuerAdjList = issuerAdjLists.get(last.getDid());
                if (issuerAdjList == null) {
                    issuerAdjList = getIssuerNodeAdjList(last.getDid());
                    issuerAdjLists.putIfAbsent(last.getDid(), issuerAdjList);
                }

                for (final Edge edge : issuerAdjList) {
                    final Node newNode = Node.builder().did(edge.getTo()).depth(last.getDepth() + 1)
                            .trust(trustComputingStrategy.computeTrust(last.getTrust(), edge.getWeight()))
                            .type(Constants.ISSUER_NODE).build();

                    Boolean isNodeActive = activeIssuerNodes.get(newNode.getDid());
                    if (isNodeActive == null) {
                        isNodeActive = nodeService.isIssuerNodeActive(newNode.getDid());
                        activeIssuerNodes.putIfAbsent(newNode.getDid(), isNodeActive);
                    }
                    // add the connected issuer node, to a new path, only if its insertion
                    // does not reduce the trust level below the minimum required, if it is not already
                    // in the path and it is not deactivated
                    if (!path.contains(newNode) && newNode.getTrust() >= trustQuery.getTrustThreshold() &&
                            isNodeActive) {
                        final Path newPath = Path.builder().path(new ArrayList<>(path.getPath())).build();
                        newPath.add(newNode);
                        queue.add(newPath);
                    }
                }
            }
        }
        return pathsToTarget;
    }

    public MaxTrustPathDTO maxTrustPathQuery(final TrustQueryDTO trustQuery) throws ServiceException {
        try {
            final PathsEvaluationStrategy<MaxTrustPathDTO> pathsEvaluationStrategy =
                    PathStrategyFactory.getEvaluationStrategy(Constants.MAX_TRUST_PATH);
            final List<Path> paths = getTrustL2(getTrustL1(trustQuery), trustQuery);
            log.info(LOG_INFO_PATHS_FOUND, paths.size());
            return pathsEvaluationStrategy.evaluatePaths(paths, trustQuery.getContext());
        } catch (final Exception e) {
            throw new ServiceException(e, ErrorMessages.GENERIC_ERROR);
        }
    }

    public AvgTopFiveTrustPathsDTO avgTopFiveTrustPathsQuery(final TrustQueryDTO trustQuery) throws ServiceException {
        try {
            final PathsEvaluationStrategy<AvgTopFiveTrustPathsDTO> pathsEvaluationStrategy =
                    PathStrategyFactory.getEvaluationStrategy(Constants.TOP_FIVE_TRUST_PATHS);
            final List<Path> paths = getTrustL2(getTrustL1(trustQuery), trustQuery);
            log.info(LOG_INFO_PATHS_FOUND, paths.size());
            return pathsEvaluationStrategy.evaluatePaths(paths, trustQuery.getContext());
        } catch (final Exception e) {
            throw new ServiceException(e, ErrorMessages.GENERIC_ERROR);
        }
    }

    public MinMaxTrustPathDTO minMaxTrustPathsQuery(final TrustQueryDTO trustQuery) throws ServiceException {
        try {
            final PathsEvaluationStrategy<MinMaxTrustPathDTO> pathsEvaluationStrategy =
                    PathStrategyFactory.getEvaluationStrategy(Constants.MIN_MAX_TRUST_PATHS);
            final List<Path> paths = getTrustL2(getTrustL1(trustQuery), trustQuery);
            log.info(LOG_INFO_PATHS_FOUND, paths.size());
            return pathsEvaluationStrategy.evaluatePaths(paths, trustQuery.getContext());
        } catch (final Exception e) {
            throw new ServiceException(e, ErrorMessages.GENERIC_ERROR);
        }
    }
}
