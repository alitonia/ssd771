package com.isd.ict.capstoneproject.common.exception.ebr;
/**
 * The {@link InternalServerException internalServerException} exception for internal server.
 *
 * @author Group 3
 *
 */
public class InternalServerException extends EBRException {

    public InternalServerException() {
        super("ERROR: Internal Server Exception.");
    }
}
