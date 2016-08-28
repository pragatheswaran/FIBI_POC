package com.fibi.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

/**
 *
 * This class holds all the util functions of FIBI
 *
 * @author pragu
 */

public class FibiUtil {
	
	private static final Logger LOG = Logger.getLogger(FibiUtil.class);

	private final static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * @param str_date
	 * @return timestamp
	 */
	public static Timestamp convertStringToTimestamp(final String str_date) {
		try {
			return str_date != null ? new Timestamp(formatter.parse(str_date).getTime()) : null;
		} catch (final ParseException e) {
			LOG.error("Exception while converting the String to Time stamp :" + e);
			return null;
		}
	}
}
