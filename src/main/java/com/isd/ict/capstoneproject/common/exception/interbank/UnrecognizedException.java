package com.isd.ict.capstoneproject.common.exception.interbank;

/**
 * The {@link UnrecognizedException UnrecognizedException} exception for unrecognized situation.
 *
 */
public class UnrecognizedException extends RuntimeException {
	public UnrecognizedException() {
		super("ERROR: Something went wrowng!");
	}
}
