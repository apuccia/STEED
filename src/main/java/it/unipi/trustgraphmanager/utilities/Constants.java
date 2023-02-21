package it.unipi.trustgraphmanager.utilities;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Constants {

    public final static String INFURA = "https://goerli.infura.io/v3/";

    public final static BigInteger GAS_PRICE = new BigInteger("10000000000");  //10gwei

    public final static BigInteger GAS_LIMIT = new BigInteger("1500000");

    public final static BigDecimal ETH = new BigDecimal("1000000000000000000");

    public final static String MULTIPLICATIVE_TRUST = "MULTIPLICATIVE TRUST";

    public final static String MAX_TRUST_PATH = "MAX TRUST PATH";

    public final static String TOP_FIVE_TRUST_PATHS = "TOP FIVE TRUST PATHS";

    public final static String MIN_MAX_TRUST_PATHS = "MIN MAX TRUST PATHS";

    public final static String ISSUER_NODE = "ISSUERS";

    public final static String CONTEXT_NODE = "CONTEXTS";
}  
