package com.isd.ict.capstoneproject.subsystem.interbank;

import com.isd.ict.capstoneproject.common.exception.interbank.*;
import com.isd.ict.capstoneproject.payment.creditcard.CreditCard;
import com.isd.ict.capstoneproject.payment.transaction.PaymentTransaction;
import com.isd.ict.capstoneproject.utils.MyMap;
import com.isd.ict.capstoneproject.utils.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The {@link InterbankDataConverter interbankDataConverter} class processes input and output data for Interbank API
 * @author thanhld
 */
public class InterbankDataConverter {

    private static final Logger LOGGER = Utils.getLogger(InterbankDataConverter.class.getName());

    /**
     * Compile data into interbank standard payload
     * @param card
     * @param amount
     * @param contents
     * @param interbankCommand
     * @return {@link String} payload - JSON-formatted String contains data for interbank request body
     */
    String convertToPayload(CreditCard card, int amount, String contents, String interbankCommand) {
        Map<String, Object> transaction = new MyMap();

        try {
            transaction.putAll(MyMap.toMyMap(card));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LOGGER.info("Error occurred: " + e.getMessage());
            e.printStackTrace();
            throw new InvalidCardException();
        }
        transaction.put("command", interbankCommand);
        transaction.put("transactionContent", contents);
        transaction.put("amount", amount);
        transaction.put("createdAt", getToday());

        MyMap requestMap = new MyMap();
        requestMap.put("secretKey", InterbankConfigs.SECRET_KEY);
        requestMap.put("transaction", transaction);
        String hashCode = md5(generateData(requestMap));
        requestMap.put("version", InterbankConfigs.VERSION);
        requestMap.put("appCode", InterbankConfigs.PUBLIC_KEY);
        requestMap.put("hashCode", hashCode);

        LOGGER.info(generateData(requestMap));
        return generateData(requestMap);
    }

    /**
     * Make payment transaction
     *
     * @param responseText - JSON formatted returned by Interbank server
     * @return {@link PaymentTransaction paymentTransaction}
     * @throws UnrecognizedException
     * @throws PaymentException
     */
    PaymentTransaction convertResponse(String responseText) throws UnrecognizedException, PaymentException {
        // validate response format
        MyMap response;
        try {
            response = MyMap.toMyMap(responseText, 0);
        } catch (IllegalArgumentException e) {
            LOGGER.info("Error occurred: " + e.getMessage());
            e.printStackTrace();
            throw new UnrecognizedException();
        }
        if (response == null) return null;

        // read the response content
        MyMap transaction = (MyMap) response.get("transaction");
        CreditCard card =  CreditCard.builder()
                .cardCode((String) transaction.get("cardCode"))
                .owner((String) transaction.get("owner"))
                .cvvCode(Integer.parseInt((String) transaction.get("cvvCode")))
                .dateExpired((String) transaction.get("dateExpired"))
                .build();
        PaymentTransaction trans = PaymentTransaction.builder()
                .errorCode((String) response.get("errorCode"))
                .card(card)
                .transactionId((String) transaction.get("transactionId"))
                .contents((String) transaction.get("transactionContent"))
                .amount(Integer.parseInt((String) transaction.get("amount")))
                .createdAt((String) transaction.get("createdAt"))
                .build();
        convertErrorCodeToException(trans.getErrorCode());
        return trans;
    }

    /**
     * Convert Interbank errorCode to readable system-native Exception
     * @param errorCode
     */
    private void convertErrorCodeToException(String errorCode) {
        switch (errorCode) {
            case "00":
                break;
            case "01":
                throw new InvalidCardException();
            case "02":
                throw new NotEnoughBalanceException();
            case "03":
                throw new InternalServerErrorException();
            case "04":
                throw new SuspiciousTransactionException();
            case "05":
                throw new NotEnoughTransactionInfoException();
            case "06":
                throw new InvalidVersionException();
            case "07":
                throw new InvalidTransactionAmountException();
            default:
                throw new UnrecognizedException();
        }
    }

    /**
     * Convert a map to JSON-formatted String
     * @param data
     * @return
     */
    private String generateData(Map<String, Object> data) {
        return ((MyMap) data).toJSON();
    }

    /**
     * Return a {@link String String} that represents the current time in the format of yyyy-MM-dd HH:mm:ss.
     *
     * @return the current time as {@link String String}.
     */
    private String getToday() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Return a {@link String String} that represents the cipher text
     * encrypted by md5 algorithm.
     *
     * @param message - plain text as {@link String String}.
     * @return cipher text as {@link String String}.
     */
    private String md5(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
            // converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Utils.getLogger(Utils.class.getName());
            digest = "";
        }
        return digest;
    }

}
