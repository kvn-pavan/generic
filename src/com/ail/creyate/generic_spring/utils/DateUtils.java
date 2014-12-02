package com.ail.creyate.generic_spring.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String getCurrentDateTimeINYYYYMMDDHHmmss() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static String getCurrentDateTimeINYYYYMMDDHHmmsSa() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:s.S a");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
