package it.unipi.trustgraphmanager.beans;

import it.unipi.trustgraphmanager.contracts.ContextsGraph;
import it.unipi.trustgraphmanager.contracts.EntrypointsGraph;
import it.unipi.trustgraphmanager.contracts.IssuersGraph;
import it.unipi.trustgraphmanager.dtos.TransactionReceiptInfoDTO;
import it.unipi.trustgraphmanager.exceptions.DeployerUtilityException;
import it.unipi.trustgraphmanager.properties.ContractsProperties;
import it.unipi.trustgraphmanager.utilities.Constants;
import it.unipi.trustgraphmanager.utilities.ErrorMessages;
import it.unipi.trustgraphmanager.utilities.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.HexFormat;

@Slf4j
@Configuration
public class ContractsBean {

    private static final String LAYER_CONTEXTS = "Layer contexts contract - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";

    private static final String LAYER_ENTRYPOINTS = "Layer entrypoints contract - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";

    private static final String LAYER_ISSUERS = "Layer issuers contract - [contractAddress: {}, gasUsed: {}, gasPrice: {}, transactionCost: {}, transactionHash: {}, blockHash: {}, blockTimestamp: {}, blockDateTime: {}, blockNumber: {}, blockSize: {}, blockAuthor: {}, blockMiner: {}]";

    private static final String LOG_ERROR_DECODED = "Decoded error message: ";

    @Autowired
    private ContractsProperties contractsProperties;

    @Autowired
    private Web3j web3j;

    @Autowired
    @Qualifier("writeTransactionManager")
    private TransactionManager writeTransactionManager;

    @Autowired
    @Qualifier("readTransactionManager")
    private TransactionManager readTransactionManager;

    @Bean
    @DependsOn({"issuersGraph", "entrypointsGraph", "writeTransactionManager", "readTransactionManager"})
    public ContextsGraph[] contextsGraph(@Autowired IssuersGraph[] issuersGraph,
            @Autowired EntrypointsGraph[] entrypointsGraph) throws DeployerUtilityException {
        try {
            final ContractGasProvider contractGasProvider = new StaticGasProvider(Constants.GAS_PRICE,
                    Constants.GAS_LIMIT);
            String contextsContractAddress = contractsProperties.getContextsContractAddress();
            final ContextsGraph[] contextsGraph = new ContextsGraph[2];
            if (contextsContractAddress == null || contextsContractAddress.isEmpty()) {
                contextsGraph[0] = ContextsGraph.deploy(web3j, writeTransactionManager, contractGasProvider,
                    issuersGraph[0].getContractAddress(), entrypointsGraph[0].getContractAddress()).send();
                contextsContractAddress = contextsGraph[0].getContractAddress();
                
                final TransactionReceiptInfoDTO transactionReceiptInfo = Utilities
                        .getTransactionReceiptInfo(web3j, contextsGraph[0].getTransactionReceipt().get());
                log.info(LAYER_CONTEXTS, contextsContractAddress, transactionReceiptInfo.getGasUsed(),
                        transactionReceiptInfo.getGasPrice(), transactionReceiptInfo.getTransactionCost(),
                        transactionReceiptInfo.getTransactionHash(), transactionReceiptInfo.getBlockHash(),
                        transactionReceiptInfo.getBlockTimestamp(), transactionReceiptInfo.getBlockDateTime(),
                        transactionReceiptInfo.getBlockNumber(), transactionReceiptInfo.getBlockSize(),
                        transactionReceiptInfo.getBlockAuthor(), transactionReceiptInfo.getBlockMiner());
            } else {
                contextsGraph[0] = ContextsGraph.load(contextsContractAddress, web3j, writeTransactionManager,
                        contractGasProvider);
            }
            contextsGraph[1] = ContextsGraph.load(contextsContractAddress, web3j, readTransactionManager,
                    contractGasProvider);
            log.info("Contexts graph contract address: {}", contextsContractAddress);
            return contextsGraph;
        } catch (final TransactionException e) {
            if (e.getTransactionReceipt().isPresent()) {
                final String decodedRevertReason = new String(HexFormat.of()
                        .parseHex(e.getTransactionReceipt().get().getRevertReason())).trim();
                log.error(LOG_ERROR_DECODED + decodedRevertReason, e.getCause());
                throw new DeployerUtilityException(e, decodedRevertReason);
            }
            else {
                throw new DeployerUtilityException(e);
            }
        } catch (final Exception e) {
            throw new DeployerUtilityException(e, ErrorMessages.GENERIC_ERROR);
        }
    }

    @Bean
    @DependsOn({"writeTransactionManager", "readTransactionManager"})
    public IssuersGraph[] issuersGraph() throws DeployerUtilityException {
        try {
            final ContractGasProvider contractGasProvider = new StaticGasProvider(Constants.GAS_PRICE,
                    Constants.GAS_LIMIT);
            String issuersContractAddress = contractsProperties.getIssuersContractAddress();
            final IssuersGraph[] issuersGraph = new IssuersGraph[2];
            if (issuersContractAddress == null || issuersContractAddress.isEmpty()) {
                issuersGraph[0] = IssuersGraph.deploy(web3j, writeTransactionManager, contractGasProvider).send();
                issuersContractAddress = issuersGraph[0].getContractAddress();
                
                final TransactionReceiptInfoDTO transactionReceiptInfo = Utilities
                        .getTransactionReceiptInfo(web3j, issuersGraph[0].getTransactionReceipt().get());
                log.info(LAYER_ISSUERS, issuersContractAddress, transactionReceiptInfo.getGasUsed(),
                        transactionReceiptInfo.getGasPrice(), transactionReceiptInfo.getTransactionCost(),
                        transactionReceiptInfo.getTransactionHash(), transactionReceiptInfo.getBlockHash(),
                        transactionReceiptInfo.getBlockTimestamp(), transactionReceiptInfo.getBlockDateTime(),
                        transactionReceiptInfo.getBlockNumber(), transactionReceiptInfo.getBlockSize(),
                        transactionReceiptInfo.getBlockAuthor(), transactionReceiptInfo.getBlockMiner());
            } else {
                issuersGraph[0] = IssuersGraph.load(issuersContractAddress, web3j, writeTransactionManager,
                        contractGasProvider);
            }
            issuersGraph[1] = IssuersGraph.load(issuersContractAddress, web3j, readTransactionManager, contractGasProvider);
            log.info("Issuers graph contract address: {}", issuersContractAddress);
            return issuersGraph;
        } catch (final TransactionException e) {
            if (e.getTransactionReceipt().isPresent()) {
                final String decodedRevertReason = new String(HexFormat.of()
                        .parseHex(e.getTransactionReceipt().get().getRevertReason())).trim();
                log.error(LOG_ERROR_DECODED + decodedRevertReason, e.getCause());
                throw new DeployerUtilityException(e, decodedRevertReason);
            }
            else {
                throw new DeployerUtilityException(e);
            }
        } catch (final Exception e) {
            throw new DeployerUtilityException(e, ErrorMessages.GENERIC_ERROR);
        }
    }

    @Bean
    @DependsOn({"writeTransactionManager", "readTransactionManager"})
    public EntrypointsGraph[] entrypointsGraph() throws DeployerUtilityException {
        try {
            final ContractGasProvider contractGasProvider = new StaticGasProvider(Constants.GAS_PRICE,
                    Constants.GAS_LIMIT);
            String entrypointsContractAddress = contractsProperties.getEntrypointsContractAddress();
            final EntrypointsGraph[] entrypointsGraph = new EntrypointsGraph[2];
            if (entrypointsContractAddress == null || entrypointsContractAddress.isEmpty()) {
                entrypointsGraph[0] = EntrypointsGraph.deploy(web3j, writeTransactionManager, contractGasProvider).send();
                entrypointsContractAddress = entrypointsGraph[0].getContractAddress();

                final TransactionReceiptInfoDTO transactionReceiptInfo = Utilities
                        .getTransactionReceiptInfo(web3j, entrypointsGraph[0].getTransactionReceipt().get());
                log.info(LAYER_ENTRYPOINTS, entrypointsContractAddress, transactionReceiptInfo.getGasUsed(),
                        transactionReceiptInfo.getGasPrice(), transactionReceiptInfo.getTransactionCost(),
                        transactionReceiptInfo.getTransactionHash(), transactionReceiptInfo.getBlockHash(),
                        transactionReceiptInfo.getBlockTimestamp(), transactionReceiptInfo.getBlockDateTime(),
                        transactionReceiptInfo.getBlockNumber(), transactionReceiptInfo.getBlockSize(),
                        transactionReceiptInfo.getBlockAuthor(), transactionReceiptInfo.getBlockMiner());
            } else {
                entrypointsGraph[0] = EntrypointsGraph.load(entrypointsContractAddress, web3j, writeTransactionManager,
                        contractGasProvider);
            }
            entrypointsGraph[1] = EntrypointsGraph.load(entrypointsContractAddress, web3j, readTransactionManager,
                    contractGasProvider);
            log.info("Entrypoints graph contract address: {}", entrypointsContractAddress);
            return entrypointsGraph;
        } catch (final TransactionException e) {
            if (e.getTransactionReceipt().isPresent()) {
                final String decodedRevertReason = new String(HexFormat.of()
                        .parseHex(e.getTransactionReceipt().get().getRevertReason())).trim();
                log.error(LOG_ERROR_DECODED + decodedRevertReason, e.getCause());
                throw new DeployerUtilityException(e, decodedRevertReason);
            }
            else {
                throw new DeployerUtilityException(e);
            }
        } catch (final Exception e) {
            throw new DeployerUtilityException(e, ErrorMessages.GENERIC_ERROR);
        }
    }
}
