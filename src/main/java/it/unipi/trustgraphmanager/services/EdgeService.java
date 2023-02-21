package it.unipi.trustgraphmanager.services;

import it.unipi.trustgraphmanager.contracts.ContextsGraph;
import it.unipi.trustgraphmanager.contracts.EntrypointsGraph;
import it.unipi.trustgraphmanager.contracts.IssuersGraph;
import it.unipi.trustgraphmanager.dtos.EdgeDTO;
import it.unipi.trustgraphmanager.dtos.EdgeTransactionResultDTO;
import it.unipi.trustgraphmanager.dtos.TransactionReceiptInfoDTO;
import it.unipi.trustgraphmanager.exceptions.ServiceException;
import it.unipi.trustgraphmanager.utilities.ErrorMessages;
import it.unipi.trustgraphmanager.utilities.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import java.math.BigInteger;
import java.util.List;

@Service
@Slf4j
public class EdgeService {

    private static final String LOG_INFO_INSERT_ISSUER_EDGE = "insertIssuerEdge - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";

    private static final String LOG_INFO_INSERT_CONTEXT_EDGE = "insertContextEdge - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";

    private static final String LOG_INFO_INSERT_ENTRYPOINT_EDGE = "insertEntrypointEdge - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";

    private static final String LOG_INFO_MODIFY_ISSUER_EDGE_WEIGHT = "modifyIssuerEdgeWeight - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";

    private static final String LOG_INFO_MODIFY_CONTEXT_EDGE_WEIGHT = "modifyContextEdgeWeight - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";

    private static final String LOG_INFO_MODIFY_ENTRYPOINT_EDGE_WEIGHT = "modifyEntrypointEdgeWeight - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";

    @Autowired
    private IssuersGraph[] issuersGraph;

    @Autowired
    private ContextsGraph[] contextsGraph;

    @Autowired
    private EntrypointsGraph[] entrypointsGraph;

    @Autowired
    private Web3j web3j;

    public EdgeTransactionResultDTO insertIssuerEdge(final EdgeDTO edge) throws ServiceException {
        try {
            final TransactionReceipt transactionReceipt = issuersGraph[0].insertIssuerEdge(edge.getFrom(),
                    new IssuersGraph.IssuerTrust(edge.getTo(),
                            new BigInteger(String.valueOf((int) (edge.getWeight() * 100))))).send();
            final Log event = transactionReceipt.getLogs().get(0);
            final List<Type> parameters = FunctionReturnDecoder.decode(event.getData(),
                    IssuersGraph.ISSUEREDGEADDED_EVENT.getParameters());

            final EdgeDTO edgeDTO = EdgeDTO.builder().from((String) parameters.get(0).getValue())
                    .to((String) parameters.get(1).getValue())
                    .weight((float) (((BigInteger) parameters.get(2).getValue()).intValue()) / 100).build();
            final TransactionReceiptInfoDTO transactionReceiptInfo =
                    Utilities.getTransactionReceiptInfo(web3j, transactionReceipt);
            transactionReceipt.setContractAddress(issuersGraph[0].getContractAddress());
            log.info(LOG_INFO_INSERT_ISSUER_EDGE, issuersGraph[0].getContractAddress(),
                    transactionReceiptInfo.getGasUsed(), transactionReceiptInfo.getGasPrice(),
                    transactionReceiptInfo.getTransactionCost(), transactionReceiptInfo.getTransactionHash(),
                    transactionReceiptInfo.getBlockHash(), transactionReceiptInfo.getBlockTimestamp(),
                    transactionReceiptInfo.getBlockDateTime(), transactionReceiptInfo.getBlockNumber(),
                    transactionReceiptInfo.getBlockSize(), transactionReceiptInfo.getBlockAuthor(),
                    transactionReceiptInfo.getBlockMiner());
            return EdgeTransactionResultDTO.builder().edge(edgeDTO).transactionReceiptInfo(transactionReceiptInfo)
                    .build();
        } catch (final TransactionException e) {
            if (e.getTransactionReceipt().isPresent()) {
                throw new ServiceException(e, e.getTransactionReceipt().get().getRevertReason());
            }
            else {
                throw new ServiceException(e);
            }
        } catch (final Exception e) {
            throw new ServiceException(e, ErrorMessages.GENERIC_ERROR);
        }
    }

    public EdgeTransactionResultDTO insertContextEdge(final EdgeDTO edge) throws ServiceException {
        try {
            final TransactionReceipt transactionReceipt = contextsGraph[0].insertContextEdge(edge.getFrom(),
                    edge.getContext(), new ContextsGraph.ContextTrust(edge.getTo(),
                            new BigInteger(String.valueOf((int) (edge.getWeight() * 100))))).send();
            final Log event = transactionReceipt.getLogs().get(0);
            final List<Type> parameters = FunctionReturnDecoder.decode(event.getData(),
                    ContextsGraph.CONTEXTEDGEADDED_EVENT.getParameters());

            final EdgeDTO contextEdgeDTO = EdgeDTO.builder().from((String) parameters.get(0).getValue())
                    .to((String) parameters.get(1).getValue())
                    .weight((float) (((BigInteger) parameters.get(2).getValue()).intValue()) / 100)
                    .context((String) parameters.get(3).getValue()).build();
            final TransactionReceiptInfoDTO transactionReceiptInfo =
                    Utilities.getTransactionReceiptInfo(web3j, transactionReceipt);
            transactionReceipt.setContractAddress(contextsGraph[0].getContractAddress());
            log.info(LOG_INFO_INSERT_CONTEXT_EDGE, contextsGraph[0].getContractAddress(),
                    transactionReceiptInfo.getGasUsed(), transactionReceiptInfo.getGasPrice(),
                    transactionReceiptInfo.getTransactionCost(), transactionReceiptInfo.getTransactionHash(),
                    transactionReceiptInfo.getBlockHash(), transactionReceiptInfo.getBlockTimestamp(),
                    transactionReceiptInfo.getBlockDateTime(), transactionReceiptInfo.getBlockNumber(),
                    transactionReceiptInfo.getBlockSize(), transactionReceiptInfo.getBlockAuthor(),
                    transactionReceiptInfo.getBlockMiner());
            return EdgeTransactionResultDTO.builder().edge(contextEdgeDTO)
                    .transactionReceiptInfo(transactionReceiptInfo).build();
        } catch (final TransactionException e) {
            if (e.getTransactionReceipt().isPresent()) {
                throw new ServiceException(e, e.getTransactionReceipt().get().getRevertReason());
            }
            else {
                throw new ServiceException(e);
            }
        } catch (final Exception e) {
            throw new ServiceException(e, ErrorMessages.GENERIC_ERROR);
        }
    }

    public EdgeTransactionResultDTO insertEntrypointEdge(final EdgeDTO edge) throws ServiceException {
        try {
            final TransactionReceipt transactionReceipt = entrypointsGraph[0].insertEdge(edge.getFrom(),
                    new EntrypointsGraph.EntrypointTrust(edge.getTo(),
                            new BigInteger(String.valueOf((int) (edge.getWeight() * 100))))).send();
            final Log event = transactionReceipt.getLogs().get(0);
            final List<Type> parameters = FunctionReturnDecoder.decode(event.getData(),
                    EntrypointsGraph.ENTRYPOINTEDGEADDED_EVENT.getParameters());

            final EdgeDTO edgeDTO = EdgeDTO.builder().from((String) parameters.get(0).getValue())
                    .to((String) parameters.get(1).getValue())
                    .weight((float) (((BigInteger) parameters.get(2).getValue()).intValue()) / 100).build();
            final TransactionReceiptInfoDTO transactionReceiptInfo =
                    Utilities.getTransactionReceiptInfo(web3j, transactionReceipt);
            transactionReceipt.setContractAddress(entrypointsGraph[0].getContractAddress());
            log.info(LOG_INFO_INSERT_ENTRYPOINT_EDGE, entrypointsGraph[0].getContractAddress(),
                    transactionReceiptInfo.getGasUsed(), transactionReceiptInfo.getGasPrice(),
                    transactionReceiptInfo.getTransactionCost(), transactionReceiptInfo.getTransactionHash(),
                    transactionReceiptInfo.getBlockHash(), transactionReceiptInfo.getBlockTimestamp(),
                    transactionReceiptInfo.getBlockDateTime(), transactionReceiptInfo.getBlockNumber(),
                    transactionReceiptInfo.getBlockSize(), transactionReceiptInfo.getBlockAuthor(),
                    transactionReceiptInfo.getBlockMiner());
            return EdgeTransactionResultDTO.builder().edge(edgeDTO).transactionReceiptInfo(transactionReceiptInfo)
                    .build();
        } catch (final TransactionException e) {
            if (e.getTransactionReceipt().isPresent()) {
                throw new ServiceException(e, e.getTransactionReceipt().get().getRevertReason());
            }
            else {
                throw new ServiceException(e);
            }
        } catch (final Exception e) {
            throw new ServiceException(e, ErrorMessages.GENERIC_ERROR);
        }
    }

    public EdgeTransactionResultDTO modifyIssuerEdgeWeight(final EdgeDTO edge) throws ServiceException {
         try {
            final TransactionReceipt transactionReceipt = issuersGraph[0].modifyIssuerEdgeWeight(edge.getFrom(),
                    new IssuersGraph.IssuerTrust(edge.getTo(),
                            new BigInteger(String.valueOf((int) (edge.getWeight() * 100))))).send();
            final Log event = transactionReceipt.getLogs().get(0);
            final List<Type> parameters = FunctionReturnDecoder.decode(event.getData(),
                    IssuersGraph.ISSUEREDGEMODIFIED_EVENT.getParameters());

            final EdgeDTO edgeDTO = EdgeDTO.builder().from((String) parameters.get(0).getValue())
                    .to((String) parameters.get(1).getValue())
                    .weight((float) (((BigInteger) parameters.get(2).getValue()).intValue()) / 100).build();
            final TransactionReceiptInfoDTO transactionReceiptInfo =
                    Utilities.getTransactionReceiptInfo(web3j, transactionReceipt);
            transactionReceipt.setContractAddress(issuersGraph[0].getContractAddress());
            log.info(LOG_INFO_MODIFY_ISSUER_EDGE_WEIGHT, issuersGraph[0].getContractAddress(),
                    transactionReceiptInfo.getGasUsed(), transactionReceiptInfo.getGasPrice(),
                    transactionReceiptInfo.getTransactionCost(), transactionReceiptInfo.getTransactionHash(),
                    transactionReceiptInfo.getBlockHash(), transactionReceiptInfo.getBlockTimestamp(),
                    transactionReceiptInfo.getBlockDateTime(), transactionReceiptInfo.getBlockNumber(),
                    transactionReceiptInfo.getBlockSize(), transactionReceiptInfo.getBlockAuthor(),
                    transactionReceiptInfo.getBlockMiner());
            return EdgeTransactionResultDTO.builder().edge(edgeDTO)
                    .transactionReceiptInfo(transactionReceiptInfo).build();
        } catch (final TransactionException e) {
            if (e.getTransactionReceipt().isPresent()) {
                throw new ServiceException(e, e.getTransactionReceipt().get().getRevertReason());
            }
            else {
                throw new ServiceException(e);
            }
        } catch (final Exception e) {
            throw new ServiceException(e, ErrorMessages.GENERIC_ERROR);
        }
    }

    public EdgeTransactionResultDTO modifyContextEdgeWeight(final EdgeDTO edge) throws ServiceException {
        try {
            final TransactionReceipt transactionReceipt = contextsGraph[0].modifyContextEdgeWeight(edge.getFrom(),
                    edge.getContext(), new ContextsGraph.ContextTrust(edge.getTo(),
                            new BigInteger(String.valueOf((int) (edge.getWeight() * 100))))).send();
            final Log event = transactionReceipt.getLogs().get(0);
            final List<Type> parameters = FunctionReturnDecoder.decode(event.getData(),
                    ContextsGraph.CONTEXTEDGEMODIFIED_EVENT.getParameters());

            final EdgeDTO contextEdgeDTO = EdgeDTO.builder().from((String) parameters.get(0).getValue())
                    .to((String) parameters.get(1).getValue())
                    .weight((float) (((BigInteger) parameters.get(2).getValue()).intValue()) / 100)
                    .context((String) parameters.get(3).getValue()).build();
            final TransactionReceiptInfoDTO transactionReceiptInfo =
                    Utilities.getTransactionReceiptInfo(web3j, transactionReceipt);
            transactionReceipt.setContractAddress(contextsGraph[0].getContractAddress());
            log.info(LOG_INFO_MODIFY_CONTEXT_EDGE_WEIGHT, contextsGraph[0].getContractAddress(),
                    transactionReceiptInfo.getGasUsed(), transactionReceiptInfo.getGasPrice(),
                    transactionReceiptInfo.getTransactionCost(), transactionReceiptInfo.getTransactionHash(),
                    transactionReceiptInfo.getBlockHash(), transactionReceiptInfo.getBlockTimestamp(),
                    transactionReceiptInfo.getBlockDateTime(), transactionReceiptInfo.getBlockNumber(),
                    transactionReceiptInfo.getBlockSize(), transactionReceiptInfo.getBlockAuthor(),
                    transactionReceiptInfo.getBlockMiner());
            return EdgeTransactionResultDTO.builder().edge(contextEdgeDTO)
                    .transactionReceiptInfo(transactionReceiptInfo).build();
        } catch (final TransactionException e) {
            if (e.getTransactionReceipt().isPresent()) {
                throw new ServiceException(e, e.getTransactionReceipt().get().getRevertReason());
            }
            else {
                throw new ServiceException(e);
            }
        } catch (final Exception e) {
            throw new ServiceException(e, ErrorMessages.GENERIC_ERROR);
        }
    }

    public EdgeTransactionResultDTO modifyEntrypointEdgeWeight(final EdgeDTO edge) throws ServiceException {
        try {
            final TransactionReceipt transactionReceipt = entrypointsGraph[0].modifyEdgeWeight(edge.getFrom(),
                    new EntrypointsGraph.EntrypointTrust(edge.getTo(),
                            new BigInteger(String.valueOf((int) (edge.getWeight() * 100))))).send();
            final Log event = transactionReceipt.getLogs().get(0);
            final List<Type> parameters = FunctionReturnDecoder.decode(event.getData(),
                    EntrypointsGraph.ENTRYPOINTEDGEMODIFIED_EVENT.getParameters());

            final EdgeDTO edgeDTO = EdgeDTO.builder().from((String) parameters.get(0).getValue())
                    .to((String) parameters.get(1).getValue())
                    .weight((float) (((BigInteger) parameters.get(2).getValue()).intValue()) / 100).build();
            final TransactionReceiptInfoDTO transactionReceiptInfo =
                    Utilities.getTransactionReceiptInfo(web3j, transactionReceipt);
            transactionReceipt.setContractAddress(entrypointsGraph[0].getContractAddress());
            log.info(LOG_INFO_MODIFY_ENTRYPOINT_EDGE_WEIGHT, entrypointsGraph[0].getContractAddress(),
                    transactionReceiptInfo.getGasUsed(), transactionReceiptInfo.getGasPrice(),
                    transactionReceiptInfo.getTransactionCost(), transactionReceiptInfo.getTransactionHash(),
                    transactionReceiptInfo.getBlockHash(), transactionReceiptInfo.getBlockTimestamp(),
                    transactionReceiptInfo.getBlockDateTime(), transactionReceiptInfo.getBlockNumber(),
                    transactionReceiptInfo.getBlockSize(), transactionReceiptInfo.getBlockAuthor(),
                    transactionReceiptInfo.getBlockMiner());
            return EdgeTransactionResultDTO.builder().edge(edgeDTO).transactionReceiptInfo(transactionReceiptInfo)
                    .build();
        } catch (final TransactionException e) {
            if (e.getTransactionReceipt().isPresent()) {
                throw new ServiceException(e, e.getTransactionReceipt().get().getRevertReason());
            }
            else {
                throw new ServiceException(e);
            }
        } catch (final Exception e) {
            throw new ServiceException(e, ErrorMessages.GENERIC_ERROR);
        }
    }
}