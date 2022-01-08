package com.isd.ict.capstoneproject.common.exception.ebr;

/**
 * The {@link InvalidBarcodeException invalidBarcodeException} exception for invalid barcode.
 *
 * @author Group 3
 *
 */
public class InvalidBarcodeException extends EBRException {
    
    public InvalidBarcodeException() {
        super("ERROR: Invalid Barcode, either wrong format or not existed");
    }
}
