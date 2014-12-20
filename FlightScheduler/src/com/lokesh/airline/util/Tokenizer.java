package com.lokesh.airline.util;

/**
 * Given a string, break it into whitespace-delimited tokens. Return successive
 * tokens on each nextToken() call.
 * 
 * 
 * @author Lokesh Lavangale
 * 
 */
public class Tokenizer {

	private static String string;

	private static int position;

	public Tokenizer(String is) {
		string = is;
		position = 0;
	}

	/**
	 * 
	 * @return the next token, or an empty string if no tokens remain
	 */
	public String nextToken() {
		int start = position;

		// skip leading whitespace
		while (start < string.length()
				&& Character.isWhitespace(string.charAt(start)))
			start++;

		// move fwd to next whitespce character
		int end = start;
		while (end < string.length()
				&& !Character.isWhitespace(string.charAt(end)))
			end++;

		position = end;
		return string.substring(start, end);
	}

	
	/**
	 * 
	 * @return untokenized suffix of string
	 */
	public String rest() {
		return string.substring(position);
	}
}
