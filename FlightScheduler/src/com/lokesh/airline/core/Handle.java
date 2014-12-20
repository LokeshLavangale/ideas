package com.lokesh.airline.core;

/**
 * 
 * @author Lokesh Lavangale
 *
 */
public class Handle {

	private int index;

	public Handle(int i) {
		setIndex(i);
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}