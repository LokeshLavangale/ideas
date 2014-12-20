package com.lokesh.airline.core;


/**
 * Flight information
 * NB: all times are GMT, in minutes since midnight
 *  
 * @author Lokesh Lavangale
 *
 */
public class Flight {
	private String name; // flight name
	private Airport startAirport, endAirport; // flight terminal
	private int startTime, endTime; // departure and arrival times
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the startAirport
	 */
	public Airport getStartAirport() {
		return startAirport;
	}
	/**
	 * @param startAirport the startAirport to set
	 */
	public void setStartAirport(Airport startAirport) {
		this.startAirport = startAirport;
	}
	/**
	 * @return the endAirport
	 */
	public Airport getEndAirport() {
		return endAirport;
	}
	/**
	 * @param endAirport the endAirport to set
	 */
	public void setEndAirport(Airport endAirport) {
		this.endAirport = endAirport;
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
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endAirport == null) ? 0 : endAirport.hashCode());
		result = prime * result + endTime;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((startAirport == null) ? 0 : startAirport.hashCode());
		result = prime * result + startTime;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Flight)) {
			return false;
		}
		Flight other = (Flight) obj;
		if (endAirport == null) {
			if (other.endAirport != null) {
				return false;
			}
		} else if (!endAirport.equals(other.endAirport)) {
			return false;
		}
		if (endTime != other.endTime) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (startAirport == null) {
			if (other.startAirport != null) {
				return false;
			}
		} else if (!startAirport.equals(other.startAirport)) {
			return false;
		}
		if (startTime != other.startTime) {
			return false;
		}
		return true;
	}
		
}
