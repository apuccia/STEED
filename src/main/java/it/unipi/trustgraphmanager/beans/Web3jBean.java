package it.unipi.trustgraphmanager.beans;

import it.unipi.trustgraphmanager.properties.ContractsProperties;
import it.unipi.trustgraphmanager.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jBean {

    @Autowired
    private ContractsProperties contractsProperties;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(Constants.INFURA + contractsProperties.getInfuraApiKey()));
    }
}
