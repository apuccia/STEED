package it.unipi.trustgraphmanager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("trustgraphmanager")
public class ContractsProperties {

    private String contextsContractAddress;

    private String issuersContractAddress;

    private String entrypointsContractAddress;

    private String infuraApiKey;

    private String privateKey;

    private String address;
}
