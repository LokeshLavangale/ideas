package com.lokesh.airline.core;

//
/**
 *
 * the queue holds objects of type Record these records hold a key and a value
 * @author Lokesh Lavangale
 *
 * @param <T>
 */
class Record<T> {
	// Link to next element in the list.
	private int key;  
	
	// Link to the element in the same pillar directly below
	private T value;  
	
	private Handle handle;
	
	public Record(int key, T value, Handle handle){
		this.key = key;
		this.value = value;
		this.handle = handle;
	}

	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(int key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * @return the handle
	 */
	public Handle getHandle() {
		return handle;
	}

	/**
	 * @param handle the handle to set
	 */
	public void setHandle(Handle handle) {
		this.handle = handle;
	}
}
