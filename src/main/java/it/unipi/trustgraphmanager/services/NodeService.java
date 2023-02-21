package it.unipi.trustgraphmanager.services;

import it.unipi.trustgraphmanager.contracts.ContextsGraph;
import it.unipi.trustgraphmanager.contracts.EntrypointsGraph;
import it.unipi.trustgraphmanager.contracts.IssuersGraph;
import it.unipi.trustgraphmanager.dtos.NodeDTO;
import it.unipi.trustgraphmanager.dtos.NodeTransactionResultDTO;
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

import java.util.List;

@Service
@Slf4j
public class NodeService {

    private static final String LOG_INFO_INSERT_CONTEXT_NODE = "addContextNode - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";
    
    private static final String LOG_INFO_INSERT_CONTEXT_NODE_ENTRYPOINT = "addContextNodeInEntrypointLayer - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";
    
    private static final String LOG_INFO_INSERT_ISSUER_NODE = "addIssuerNode - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";
    
    private static final String LOG_INFO_INSERT_ISSUER_NODE_ENTRYPOINT = "addIssuerNodeInEntrypointLayer - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";
    
    private static final String LOG_INFO_DEACTIVATE_CONTEXT_NODE = "deactivateContextNode - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";
    
    private static final String LOG_INFO_DEACTIVATE_CONTEXT_ENTRYPOINT = "deactivateContextNodeInEntrypointLayer - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";
    
    private static final String LOG_INFO_DEACTIVATE_ISSUER_NODE = "deactivateIssuerNode - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";
    
    private static final String LOG_INFO_DEACTIVATE_ISSUER_NODE_ENTRYPOINT = "deactivateIssuerNodeInEntrypointLayer - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";

    @Autowired
    private IssuersGraph[] issuersGraph;

    @Autowired
    private ContextsGraph[] contextsGraph;

    @Autowired
    private EntrypointsGraph[] entrypointsGraph;

    @Autowired
    private Web3j web3j;

    public NodeTransactionResultDTO insertContextNode(final NodeDTO node) throws ServiceException {
        try {
            final TransactionReceipt transactionReceipt = contextsGraph[0].insertContextNode(node.getDid()).send();
            final Log event = transactionReceipt.getLogs().get(0);
            final List<Type> parameters = FunctionReturnDecoder.decode(event.getData(),
                    ContextsGraph.CONTEXTNODEADDED_EVENT.getParameters());

            final NodeDTO nodeDTO = NodeDTO.builder().did((String) parameters.get(0).getValue()).build();
            final TransactionReceiptInfoDTO transactionReceiptInfo =
                    Utilities.getTransactionReceiptInfo(web3j, transactionReceipt);
            transactionReceipt.setContractAddress(contextsGraph[0].getContractAddress());
            log.info(LOG_INFO_INSERT_CONTEXT_NODE, contextsGraph[0].getContractAddress(),
                    transactionReceiptInfo.getGasUsed(), transactionReceiptInfo.getGasPrice(),
                    transactionReceiptInfo.getTransactionCost(), transactionReceiptInfo.getTransactionHash(),
                    transactionReceiptInfo.getBlockHash(), transactionReceiptInfo.getBlockTimestamp(),
                    transactionReceiptInfo.getBlockDateTime(), transactionReceiptInfo.getBlockNumber(),
                    transactionReceiptInfo.getBlockSize(), transactionReceiptInfo.getBlockAuthor(),
                    transactionReceiptInfo.getBlockMiner());
            return NodeTransactionResultDTO.builder().node(nodeDTO).transactionReceiptInfo(transactionReceiptInfo)
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

    public NodeTransactionResultDTO insertContextNodeInEntrypointLayer(final NodeDTO node) throws ServiceException {
        try {
            final TransactionReceipt transactionReceipt = entrypointsGraph[0].insertContextNode(node.getDid()).send();
            final Log event = transactionReceipt.getLogs().get(0);
            final List<Type> parameters = FunctionReturnDecoder.decode(event.getData(),
                    EntrypointsGraph.CONTEXTNODEADDED_EVENT.getParameters());

            final NodeDTO nodeDTO = NodeDTO.builder().did((String) parameters.get(0).getValue()).build();
            final TransactionReceiptInfoDTO transactionReceiptInfo =
                    Utilities.getTransactionReceiptInfo(web3j, transactionReceipt);
            transactionReceipt.setContractAddress(entrypointsGraph[0].getContractAddress());
            log.info(LOG_INFO_INSERT_CONTEXT_NODE_ENTRYPOINT, entrypointsGraph[0].getContractAddress(),
                    transactionReceiptInfo.getGasUsed(), transactionReceiptInfo.getGasPrice(),
                    transactionReceiptInfo.getTransactionCost(), transactionReceiptInfo.getTransactionHash(),
                    transactionReceiptInfo.getBlockHash(), transactionReceiptInfo.getBlockTimestamp(),
                    transactionReceiptInfo.getBlockDateTime(), transactionReceiptInfo.getBlockNumber(),
                    transactionReceiptInfo.getBlockSize(), transactionReceiptInfo.getBlockAuthor(),
                    transactionReceiptInfo.getBlockMiner());
            return NodeTransactionResultDTO.builder().node(nodeDTO).transactionReceiptInfo(transactionReceiptInfo)
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

    public NodeTransactionResultDTO insertIssuerNode(final NodeDTO node) throws ServiceException {
        try {
            final TransactionReceipt transactionReceipt = issuersGraph[0].insertIssuerNode(node.getDid()).send();
            final Log event = transactionReceipt.getLogs().get(0);
            final List<Type> parameters = FunctionReturnDecoder.decode(event.getData(),
                    IssuersGraph.ISSUERNODEADDED_EVENT.getParameters());

            final NodeDTO nodeDTO = NodeDTO.builder().did((String) parameters.get(0).getValue()).build();
            final TransactionReceiptInfoDTO transactionReceiptInfo =
                    Utilities.getTransactionReceiptInfo(web3j, transactionReceipt);
            transactionReceipt.setContractAddress(issuersGraph[0].getContractAddress());
            log.info(LOG_INFO_INSERT_ISSUER_NODE, issuersGraph[0].getContractAddress(),
                    transactionReceiptInfo.getGasUsed(), transactionReceiptInfo.getGasPrice(),
                    transactionReceiptInfo.getTransactionCost(), transactionReceiptInfo.getTransactionHash(),
                    transactionReceiptInfo.getBlockHash(), transactionReceiptInfo.getBlockTimestamp(),
                    transactionReceiptInfo.getBlockDateTime(), transactionReceiptInfo.getBlockNumber(),
                    transactionReceiptInfo.getBlockSize(), transactionReceiptInfo.getBlockAuthor(),
                    transactionReceiptInfo.getBlockMiner());
            return NodeTransactionResultDTO.builder().node(nodeDTO).transactionReceiptInfo(transactionReceiptInfo)
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

    public NodeTransactionResultDTO insertIssuerNodeInEntrypointLayer(final NodeDTO node) throws ServiceException {
        try {
            final TransactionReceipt transactionReceipt = entrypointsGraph[0].insertIssuerNode(node.getDid()).send();
            final Log event = transactionReceipt.getLogs().get(0);
            final List<Type> parameters = FunctionReturnDecoder.decode(event.getData(),
                    EntrypointsGraph.ISSUERNODEADDED_EVENT.getParameters());

            final NodeDTO nodeDTO = NodeDTO.builder().did((String) parameters.get(0).getValue()).build();
            final TransactionReceiptInfoDTO transactionReceiptInfo =
                    Utilities.getTransactionReceiptInfo(web3j, transactionReceipt);
            transactionReceipt.setContractAddress(entrypointsGraph[0].getContractAddress());
            log.info(LOG_INFO_INSERT_ISSUER_NODE_ENTRYPOINT, entrypointsGraph[0].getContractAddress(),
                    transactionReceiptInfo.getGasUsed(), transactionReceiptInfo.getGasPrice(),
                    transactionReceiptInfo.getTransactionCost(), transactionReceiptInfo.getTransactionHash(),
                    transactionReceiptInfo.getBlockHash(), transactionReceiptInfo.getBlockTimestamp(),
                    transactionReceiptInfo.getBlockDateTime(), transactionReceiptInfo.getBlockNumber(),
                    transactionReceiptInfo.getBlockSize(), transactionReceiptInfo.getBlockAuthor(),
                    transactionReceiptInfo.getBlockMiner());
            return NodeTransactionResultDTO.builder().node(nodeDTO).transactionReceiptInfo(transactionReceiptInfo)
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

    public NodeTransactionResultDTO deactivateIssuerNode(final NodeDTO node) throws ServiceException {
        try {
            final TransactionReceipt transactionReceipt = issuersGraph[0].deactivateIssuerNode(node.getDid()).send();
            final Log event = transactionReceipt.getLogs().get(0);
            final List<Type> parameters = FunctionReturnDecoder.decode(event.getData(),
                    IssuersGraph.ISSUERNODEDEACTIVATED_EVENT.getParameters());

            final NodeDTO nodeDTO = NodeDTO.builder().did((String) parameters.get(0).getValue()).build();
            final TransactionReceiptInfoDTO transactionReceiptInfo =
                    Utilities.getTransactionReceiptInfo(web3j, transactionReceipt);
            transactionReceipt.setContractAddress(issuersGraph[0].getContractAddress());
            log.info(LOG_INFO_DEACTIVATE_ISSUER_NODE, issuersGraph[0].getContractAddress(),
                    transactionReceiptInfo.getGasUsed(), transactionReceiptInfo.getGasPrice(),
                    transactionReceiptInfo.getTransactionCost(), transactionReceiptInfo.getTransactionHash(),
                    transactionReceiptInfo.getBlockHash(), transactionReceiptInfo.getBlockTimestamp(),
                    transactionReceiptInfo.getBlockDateTime(), transactionReceiptInfo.getBlockNumber(),
                    transactionReceiptInfo.getBlockSize(), transactionReceiptInfo.getBlockAuthor(),
                    transactionReceiptInfo.getBlockMiner());
            return NodeTransactionResultDTO.builder().node(nodeDTO).transactionReceiptInfo(transactionReceiptInfo)
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

    public NodeTransactionResultDTO deactivateIssuerNodeInEntrypointLayer(final NodeDTO node) throws ServiceException {
        try {
            final TransactionReceipt transactionReceipt = entrypointsGraph[0].deactivateIssuerNode(node.getDid()).send();
            final Log event = transactionReceipt.getLogs().get(0);
            final List<Type> parameters = FunctionReturnDecoder.decode(event.getData(),
                    EntrypointsGraph.ISSUERNODEDEACTIVATED_EVENT.getParameters());

            final NodeDTO nodeDTO = NodeDTO.builder().did((String) parameters.get(0).getValue()).build();
            final TransactionReceiptInfoDTO transactionReceiptInfo =
                    Utilities.getTransactionReceiptInfo(web3j, transactionReceipt);
            transactionReceipt.setContractAddress(entrypointsGraph[0].getContractAddress());
            log.info(LOG_INFO_DEACTIVATE_ISSUER_NODE_ENTRYPOINT, entrypointsGraph[0].getContractAddress(),
                    transactionReceiptInfo.getGasUsed(), transactionReceiptInfo.getGasPrice(),
                    transactionReceiptInfo.getTransactionCost(), transactionReceiptInfo.getTransactionHash(),
                    transactionReceiptInfo.getBlockHash(), transactionReceiptInfo.getBlockTimestamp(),
                    transactionReceiptInfo.getBlockDateTime(), transactionReceiptInfo.getBlockNumber(),
                    transactionReceiptInfo.getBlockSize(), transactionReceiptInfo.getBlockAuthor(),
                    transactionReceiptInfo.getBlockMiner());
            return NodeTransactionResultDTO.builder().node(nodeDTO).transactionReceiptInfo(transactionReceiptInfo)
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

    public NodeTransactionResultDTO deactivateContextNode(final NodeDTO node) throws ServiceException {
        try {
            final TransactionReceipt transactionReceipt = contextsGraph[0].deactivateContextNode(node.getDid()).send();
            final Log event = transactionReceipt.getLogs().get(0);
            final List<Type> parameters = FunctionReturnDecoder.decode(event.getData(),
                    ContextsGraph.CONTEXTNODEDEACTIVATED_EVENT.getParameters());

            final NodeDTO nodeDTO = NodeDTO.builder().did((String) parameters.get(0).getValue()).build();
            final TransactionReceiptInfoDTO transactionReceiptInfo =
                    Utilities.getTransactionReceiptInfo(web3j, transactionReceipt);
            transactionReceipt.setContractAddress(contextsGraph[0].getContractAddress());
            log.info(LOG_INFO_DEACTIVATE_CONTEXT_NODE, contextsGraph[0].getContractAddress(),
                    transactionReceiptInfo.getGasUsed(), transactionReceiptInfo.getGasPrice(),
                    transactionReceiptInfo.getTransactionCost(), transactionReceiptInfo.getTransactionHash(),
                    transactionReceiptInfo.getBlockHash(), transactionReceiptInfo.getBlockTimestamp(),
                    transactionReceiptInfo.getBlockDateTime(), transactionReceiptInfo.getBlockNumber(),
                    transactionReceiptInfo.getBlockSize(), transactionReceiptInfo.getBlockAuthor(),
                    transactionReceiptInfo.getBlockMiner());
            return NodeTransactionResultDTO.builder().node(nodeDTO).transactionReceiptInfo(transactionReceiptInfo)
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

    public NodeTransactionResultDTO deactivateContextNodeInEntrypointLayer(final NodeDTO node) throws ServiceException {
         try {
            final TransactionReceipt transactionReceipt = entrypointsGraph[0].deactivateContextNode(node.getDid()).send();
            final Log event = transactionReceipt.getLogs().get(0);
            final List<Type> parameters = FunctionReturnDecoder.decode(event.getData(),
                    EntrypointsGraph.CONTEXTNODEDEACTIVATED_EVENT.getParameters());

            final NodeDTO nodeDTO = NodeDTO.builder().did((String) parameters.get(0).getValue()).build();
            final TransactionReceiptInfoDTO transactionReceiptInfo =
                    Utilities.getTransactionReceiptInfo(web3j, transactionReceipt);
            transactionReceipt.setContractAddress(entrypointsGraph[0].getContractAddress());
            log.info(LOG_INFO_DEACTIVATE_CONTEXT_ENTRYPOINT, entrypointsGraph[0].getContractAddress(),
                    transactionReceiptInfo.getGasUsed(), transactionReceiptInfo.getGasPrice(),
                    transactionReceiptInfo.getTransactionCost(), transactionReceiptInfo.getTransactionHash(),
                    transactionReceiptInfo.getBlockHash(), transactionReceiptInfo.getBlockTimestamp(),
                    transactionReceiptInfo.getBlockDateTime(), transactionReceiptInfo.getBlockNumber(),
                    transactionReceiptInfo.getBlockSize(), transactionReceiptInfo.getBlockAuthor(),
                    transactionReceiptInfo.getBlockMiner());
            return NodeTransactionResultDTO.builder().node(nodeDTO).transactionReceiptInfo(transactionReceiptInfo)
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

    public Boolean isIssuerNodeActive(final String did) throws ServiceException {
        try {
            return issuersGraph[1].isIssuerNodeActive(did).send();
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

    public Boolean isContextNodeActive(final String did) throws ServiceException {
        try {
            return contextsGraph[1].isContextNodeActive(did).send();
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
