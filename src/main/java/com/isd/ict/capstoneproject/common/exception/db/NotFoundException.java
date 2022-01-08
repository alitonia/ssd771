package com.isd.ict.capstoneproject.common.exception.db;
/**
 * The {@link NotFoundException notFoundException} exception for not found situation.
 *
 */
public class NotFoundException extends DataSourceException {

    public NotFoundException() {
        super("ERROR: No records matched the query");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
