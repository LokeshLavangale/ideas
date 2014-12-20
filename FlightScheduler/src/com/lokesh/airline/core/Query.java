package com.lokesh.airline.core;

import com.lokesh.airline.util.PrettyTime;

/**
 * 
 * QUERY.JAVA
 * 
 * Describes a flight query
 * 
 * 
 * @author Lokesh Lavangale
 * 
 */
public class Query {

	private int startTime;
	
	private String from;
	
	private String[] tokens;

	// print method
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (getStartTime() != 0)
			sb.append("@" + PrettyTime.toString(getStartTime()) + " ");

		if (getTokens().length == 1)
			sb.append(getFrom() + " to " + getTokens()[0] + "?");
		else {
			sb.append(getFrom() + " to {" + getTokens()[0]);

			for (int j = 1; j < getTokens().length; j++)
				sb.append(", " + getTokens()[j]);

			sb.append("}?");
		}

		return sb.toString();
	}

	/**
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the tokens
	 */
	public String[] getTokens() {
		return tokens;
	}

	/**
	 * @param tokens the tokens to set
	 */
	public void setTokens(String[] tokens) {
		this.tokens = tokens;
	}
}
