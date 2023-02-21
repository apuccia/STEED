package it.unipi.trustgraphmanager.beans;

import it.unipi.trustgraphmanager.properties.ContractsProperties;
import it.unipi.trustgraphmanager.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;

@Configuration
public class TransactionManagersBean {

    @Autowired
    private ContractsProperties contractsProperties;

    @Autowired
    private Web3j web3j;

    @Bean
    public TransactionManager writeTransactionManager() {
        return new FastRawTransactionManager(web3j, Credentials.create(contractsProperties.getPrivateKey()),
                new PollingTransactionReceiptProcessor(web3j, 1000, 1000000));
    }

    @Bean
    TransactionManager readTransactionManager() {
        return new ReadonlyTransactionManager(web3j, contractsProperties.getAddress());
    }
}
