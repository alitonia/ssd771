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
    static final String PUBLIC_KEY = "BGosAzDQUOU=";
    /**
     * secret key
     */
    static final String SECRET_KEY = "B9yEw2vfsMQ=";
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
    static final String VERSION = "1.0.0";
}
