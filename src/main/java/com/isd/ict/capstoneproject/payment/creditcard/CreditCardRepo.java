package com.isd.ict.capstoneproject.payment.creditcard;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.repository.BaseRepo;
/**
 * The {@link CreditCardRepo creditCardRepo} interface provides functionalities of credit card's repository.
 *
 *
 */
public interface CreditCardRepo extends BaseRepo<CreditCard, String>{
    /**
     * Insert card to DB
     *
     * @param card
     * @return {@link CreditCard creditCard}
     * @throws DataSourceException
     */
    CreditCard insert(CreditCard card) throws DataSourceException;

    /**
     * Update card in DB
     * @param card
     * @return {@link CreditCard creditCard}
     * @throws DataSourceException
     */
    CreditCard update(CreditCard card) throws DataSourceException;
}
