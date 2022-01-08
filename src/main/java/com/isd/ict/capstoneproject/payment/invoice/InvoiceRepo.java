package com.isd.ict.capstoneproject.payment.invoice;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.repository.BaseRepo;
/**
 * The {@link InvoiceRepo invoiceRepo} interface provides functionalities of invoice's repository.
 *
 *
 */
public interface InvoiceRepo extends BaseRepo<Invoice, Integer>{
    /**
     * Insert invoice to DB
     * @param invoice
     * @return {@link Invoice invoice}
     * @throws DataSourceException
     */
    Invoice insert(Invoice invoice) throws DataSourceException;

    /**
     * Get deposit invoice by rental id
     * @param rentalId
     * @return {@link Invoice invoice}
     * @throws DataSourceException
     */
    Invoice getDepositInvoiceByRentalId(int rentalId) throws DataSourceException;
}