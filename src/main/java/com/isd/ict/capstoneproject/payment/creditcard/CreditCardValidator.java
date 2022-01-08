package com.isd.ict.capstoneproject.payment.creditcard;

import com.isd.ict.capstoneproject.common.exception.interbank.InvalidCardException;
import com.isd.ict.capstoneproject.utils.Utils;

import java.util.Calendar;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * The {@link CreditCardRepoImpl creditCardRepoImpl} class provides functionalities for validating credit card.
 *
 *
 */
public class CreditCardValidator {

    private static final Logger LOGGER = Utils.getLogger(CreditCardValidator.class.getName());

    /**
     * Validate credit card info
     *
     * @param cardCode
     * @param owner
     * @param dateExpired
     * @param cvvCode
     * @throws InvalidCardException - if the string does not represent a valid date
     *                                in the expected format
     */
    public void validateCreditCardInfo(String cardCode, String owner, String dateExpired, String cvvCode) throws InvalidCardException {
        if (!validateCardHolderName(owner)
                || !validateCardNumber(cardCode)
                || !validateExpirationDate(dateExpired)
                || !validateSecurityCode(cvvCode)) {
            throw new InvalidCardException();
        }
    }

    /**
     * Check card number in format 121319_groupSTT_2020
     *
     * @param cardNumber        - the card number
     * @return                  - true if format is correct, if not return false
     */
    private boolean validateCardNumber(String cardNumber) {
        if (Objects.isNull(cardNumber)) { return false; }
        // Spit cardNumber to 3 different part
        String[] elementOfCardNumber = cardNumber.split("_");

        // First part must be 121319 (classCode)
        if (!elementOfCardNumber[0].equals("121319")) { return false; }

        // Second part is group + one numbers
        if (elementOfCardNumber[1].length() != 6) { return false; }
        // Check if first 5 characters is group
        if (!elementOfCardNumber[1].startsWith("group")) { return false;}
        // Check if last characters is numbers
        if (!Character.isDigit(elementOfCardNumber[1].charAt(5))) { return false; }

        // Third part must be 2020
        return elementOfCardNumber[2].equals("2020");
    }

    /**
     * Check card holder name format
     *
     * @param cardHolderName    - the card holder name
     * @return                  - true if format is correct, if not return false
     */
    private boolean validateCardHolderName (String cardHolderName) {
        if (Objects.isNull(cardHolderName)) return false;
        if (Objects.isNull(cardHolderName)) return false;
        String patternString = "^[a-zA-Z0-9\\s]*$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(cardHolderName);
        return matcher.matches();
    }

    /**
     * Check expiration date in format mm/yy
     *
     * @param expirationDate    - the expiration date of card
     * @return                  - true if format is correct, if not return false
     */
    private boolean validateExpirationDate (String expirationDate) {
        if (Objects.isNull(expirationDate)) {
            return false;
        }
        // Check length equal to 5
        if (expirationDate.length() != 5) {
            return false;
        }
        String[] elementOfExpirationDate = expirationDate.split("/");

        if (elementOfExpirationDate[0].length() != 2 || elementOfExpirationDate[1].length() != 2) {
            return false;
        }

        int month = -1;
        int year = -1;
        try {
            month = Integer.parseInt(elementOfExpirationDate[0]);
            year = Integer.parseInt(elementOfExpirationDate[1]);
            if (month < 1 || month > 12 || year < Calendar.getInstance().get(Calendar.YEAR) % 100 || year > 100) {
                return false;
            }
        } catch (NumberFormatException ex) {
            LOGGER.info(ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Check security code in format all character is numeric
     *
     * @param securityCode      - the security code of card
     * @return                  - true if format is correct, if not return false
     */
    private boolean validateSecurityCode (String securityCode) {
        if (Objects.isNull(securityCode)) { return false; }
        try {
            int d = Integer.parseInt(securityCode);
        } catch (NumberFormatException e) {
            LOGGER.info(e.getMessage());
            return false;
        }
        return true;
    }
}
