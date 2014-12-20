package com.lokesh.airline.util;

/**
 * Time printing routines
 * 
 * @author Lokesh Lavangale
 * 
 */
public class PrettyTime {

	/**
	 * convert raw 24-hour time value to minutes since midnight.
	 * 
	 * @param timeRaw
	 * @return
	 */
	public static int toTime(int timeRaw) {
		int hour = (timeRaw / 100) % 24;
		int minute = timeRaw % 100;

		return hour * 60 + minute;
	}

	/**
	 * Pretty-print a time. Input time is in minutes since midnight.
	 * 
	 * @param time
	 * @return
	 */
	public static String toString(int time) {
		int hours = time / 60;
		int minutes = time % 60;

		return String.format("%02d:%02d", hours, minutes);
	}

	/**
	 * Compute elapsed time between two clock times
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int elapsed(int startTime, int endTime) {
		int len = endTime - startTime;
		if (len < 0)
			len += 24 * 60; // next day

		return len;
	}

	/**
	 * Pretty-print an elapsed time interval. Input times are in minutes since
	 * midnight.
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String elapsedToString(int startTime, int endTime) {
		int len = elapsed(startTime, endTime);

		int hours = len / 60;
		int minutes = len % 60;

		String hourstring, minutestring;
		if (hours > 0)
			hourstring = hours + " hours";
		else
			hourstring = "";

		if (minutes > 0)
			minutestring = minutes + " minutes";
		else
			minutestring = "";

		if (hours > 0 && minutes > 0)
			return hourstring + " and " + minutestring;
		else
			return hourstring + minutestring;
	}
}
