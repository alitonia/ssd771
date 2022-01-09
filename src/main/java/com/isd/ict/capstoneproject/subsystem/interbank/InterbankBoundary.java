package com.isd.ict.capstoneproject.subsystem.interbank;

import com.isd.ict.capstoneproject.common.exception.interbank.UnrecognizedException;
import com.isd.ict.capstoneproject.utils.ApplicationProgrammingInterface;
/**
 * The {@link InterbankBoundary interbankBoundary} class provides functionalities for connect interbank.
 *
 *
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
			System.out.println("query");
			System.out.println(url);
			System.out.println(data);


			response = ApplicationProgrammingInterface.post(url, data);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnrecognizedException();
		}
		return response;
	}
}
