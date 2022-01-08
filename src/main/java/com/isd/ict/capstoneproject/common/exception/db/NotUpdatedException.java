package com.isd.ict.capstoneproject.common.exception.db;
/**
 * The {@link NotUpdatedException notUpdatedException} exception for not update situation.
 *
 * @author Group 3
 *
 */
public class NotUpdatedException extends DataSourceException {

    public NotUpdatedException() {
        super("ERROR: No records updated after the query");
    }

    public NotUpdatedException(String message) {
        super(message);
    }
}
