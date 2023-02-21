package it.unipi.trustgraphmanager.utilities;

import it.unipi.trustgraphmanager.dtos.TransactionReceiptInfoDTO;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.EthBlock.Block;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Utilities {

    public static TransactionReceiptInfoDTO getTransactionReceiptInfo(final Web3j web3j,
            final TransactionReceipt transactionReceipt) throws Exception {
        final Block block = web3j.ethGetBlockByHash(transactionReceipt.getBlockHash(),true)
                .send().getBlock();
        if (block != null) {
                final LocalDateTime blockDateTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(block.getTimestamp().longValueExact()),
                        ZoneId.systemDefault());
                final Long blockTimestamp = block.getTimestamp().longValueExact();
                final BigInteger blockNumber = block.getNumber();
                final Integer blockSize = block.getTransactions().size();
                final String blockAuthor = block.getAuthor();
                final String blockMiner = block.getMiner();
        
                return TransactionReceiptInfoDTO.builder().gasUsed(transactionReceipt.getGasUsed()).gasPrice(Constants.GAS_PRICE)
                        .blockDateTime(blockDateTime).blockNumber(blockNumber).blockSize(blockSize).blockTimestamp(blockTimestamp)
                        .contractAddress(transactionReceipt.getContractAddress()).blockHash(transactionReceipt.getBlockHash())
                        .transactionHash(transactionReceipt.getTransactionHash()).blockAuthor(blockAuthor)
                        .blockMiner(blockMiner).transactionCost(computeTransactionCostInEth(transactionReceipt.getGasUsed()
                                , Constants.GAS_PRICE)).build();
        } else {
                return TransactionReceiptInfoDTO.builder().gasUsed(transactionReceipt.getGasUsed()).gasPrice(Constants.GAS_PRICE)
                        .blockDateTime(null).blockNumber(BigInteger.ZERO).blockSize(0).blockTimestamp(new Date().getTime())
                        .contractAddress(transactionReceipt.getContractAddress()).blockHash(transactionReceipt.getBlockHash())
                        .transactionHash(transactionReceipt.getTransactionHash()).blockAuthor(null)
                        .blockMiner(null).transactionCost(computeTransactionCostInEth(transactionReceipt.getGasUsed()
                                , Constants.GAS_PRICE)).build();
        }
    }

    private static BigDecimal computeTransactionCostInEth(final BigInteger gasUsed, final BigInteger gasPrice) {
        final BigDecimal priceInWei = new BigDecimal(gasUsed.multiply(gasPrice));

        return priceInWei.divide(Constants.ETH, new MathContext(18, RoundingMode.UNNECESSARY));
    }
}
