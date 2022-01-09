package com.isd.ict.capstoneproject.subsystem.interbank;

/**
 * The {@link InterbankConfigs interbankConfigs} class provides interbank configurations
 *
 */
public class InterbankConfigs {
    /**
     * interbank api gateway
     */
    static String PROCESS_TRANSACTION_URL = "https://ecopark-system-api.herokuapp.com/api/card/processTransaction";
    /**
     * public key
     */
    static final String PUBLIC_KEY = "CH7Dh0L8E/Y=";
    /**
     * secret key
     */
    static final String SECRET_KEY = "BDDkQaKokiA=";
    /**
     * pay command
     */
    static final String PAY_COMMAND = "pay";
    /**
     * refund command
     */
    static final String REFUND_COMMAND = "refund";
    /**
     * version
     */
    static final String VERSION = "1.0.1";
}
