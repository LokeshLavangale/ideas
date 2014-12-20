package com.lokesh.airline.core;

import java.util.*;

/**
 * 
 * VERTEX.JAVA Vertex class for the multigraph
 * 
 * A Vertex is created with an integer identifier. Subclass it if you want to
 * store more complex info.
 * 
 * To enumerate a vertex's adjacency list, you call its adj() method, which
 * returns an iterator "ei" of type EdgeIterator for the list. You can call
 * ei.hasNext() to see if there is another edge available, and ei.next() to get
 * it.
 * 
 * @author Lokesh Lavangale
 * 
 */
public class Vertex {

	private int id;

	private ArrayList<Edge> neighbors;

	private Handle handle;

	// Constructor (takes an identifier)
	public Vertex(int id) {
		this.id = id;
		this.neighbors = new ArrayList<Edge>();
	}

	/**
	 * @return Returns identifier
	 */
	public int id() {
		return id;
	}

	/**
	 * @return Return an iterator to list all of our edges.
	 */
	public EdgeIterator adj() {
		EdgeIterator a = new EdgeIterator(this);
		return a;
	}

	/**
	 * 
	 * Add an edge to our adjacency list.
	 * 
	 * @param e
	 */
	public void addEdge(Edge e) {
		neighbors.add(e);
	}

	/**
	 * Identify us by our id.
	 */
	public String toString() {
		return "" + id;
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

	public class EdgeIterator {

		public EdgeIterator(Vertex iv) {
			v = iv;
			posn = 0;
		}

		public boolean hasNext() {
			return (posn < v.neighbors.size());
		}

		public Edge next() {
			return v.neighbors.get(posn++);
		}

		private Vertex v;
		private int posn;
	}
}
