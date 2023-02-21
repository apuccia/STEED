package it.unipi.trustgraphmanager.services;

import it.unipi.trustgraphmanager.contracts.ContextsGraph;
import it.unipi.trustgraphmanager.dtos.TransactionReceiptInfoDTO;
import it.unipi.trustgraphmanager.dtos.VerifyEdgeDTO;
import it.unipi.trustgraphmanager.dtos.VerifyEdgeTransactionResultDTO;
import it.unipi.trustgraphmanager.exceptions.ServiceException;
import it.unipi.trustgraphmanager.utilities.Constants;
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
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PathService {

    private static final String LOG_INFO_VERIFY_PATH = "verifyPath - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}, exists: {}]";

    @Autowired
    private ContextsGraph[] contextsGraph;

    @Autowired
    private Web3j web3j;

    public VerifyEdgeTransactionResultDTO verifyPath(final List<VerifyEdgeDTO> path) throws ServiceException {
        try {
            final List<ContextsGraph.Edge> edgePath = new ArrayList<>();
            path.forEach(e -> edgePath.add(new ContextsGraph.Edge(e.getDid(), e.getContext(),
                    new BigInteger(e.getLayer().equals(Constants.CONTEXT_NODE) ? "0" : "1"))));
            final TransactionReceipt transactionReceipt = contextsGraph[0].verifyPath(edgePath).send();
            final Log event = transactionReceipt.getLogs().get(0);
            final List<Type> parameters = FunctionReturnDecoder.decode(event.getData(),
                    ContextsGraph.PATHVERIFIED_EVENT.getParameters());

            final TransactionReceiptInfoDTO transactionReceiptInfo =
                    Utilities.getTransactionReceiptInfo(web3j, transactionReceipt);
            transactionReceipt.setContractAddress(contextsGraph[0].getContractAddress());
            log.info(LOG_INFO_VERIFY_PATH, contextsGraph[0].getContractAddress(),
                    transactionReceiptInfo.getGasUsed(), transactionReceiptInfo.getGasPrice(),
                    transactionReceiptInfo.getTransactionCost(), transactionReceiptInfo.getTransactionHash(),
                    transactionReceiptInfo.getBlockHash(), transactionReceiptInfo.getBlockTimestamp(),
                    transactionReceiptInfo.getBlockDateTime(), transactionReceiptInfo.getBlockNumber(),
                    transactionReceiptInfo.getBlockSize(), transactionReceiptInfo.getBlockAuthor(),
                    transactionReceiptInfo.getBlockMiner(), (Boolean) parameters.get(0).getValue());
            return VerifyEdgeTransactionResultDTO.builder().verifyEdge(path)
                    .exists((Boolean) parameters.get(0).getValue())
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
}