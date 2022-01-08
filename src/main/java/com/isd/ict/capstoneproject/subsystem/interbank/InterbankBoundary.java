package com.isd.ict.capstoneproject.subsystem.interbank;

import com.isd.ict.capstoneproject.common.exception.interbank.UnrecognizedException;
import com.isd.ict.capstoneproject.utils.ApplicationProgrammingInterface;
/**
 * The {@link InterbankBoundary interbankBoundary} class provides functionalities for connect interbank.
 *
 * @author Group 3
 *
 */
public class InterbankBoundary {

	/**
	 * Send query
	 *
	 * @param url
	 * @param data
	 * @return {@link String string}
	 * @throws UnrecognizedException
	 */
	String query(String url, String data) throws UnrecognizedException {
		String response;
		try {
			response = ApplicationProgrammingInterface.post(url, data);
		} catch (Exception e) {
			throw new UnrecognizedException();
		}
		return response;
	}
}
