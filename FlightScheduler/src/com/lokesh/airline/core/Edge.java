package com.lokesh.airline.core;

/**
 * EDGE.JAVA
 * Edge class for the multigraph
 *
 * A (directed) edge contains four values: a unique identifier, a
 * "from" vertex, a "to" vertex, and an integer weight.  Subclass Edge 
 * if you want to store more complicated info.
 * 
 * @author Lokesh Lavangale
 *
 */


public class Edge {

	private Vertex from, to;
	private int weight;
	private int id;
	
	public Edge(int id, Vertex from, Vertex to, int weight) {
		this.id = id;
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public int id() {
		return id;
	}

	public Vertex from() {
		return from;
	}

	public Vertex to() {
		return to;
	}

	public int weight() {
		return weight;
	}

	public String toString() {
		return "(" + id + ": " + from + " --> " + to + ", " + weight + ")";
	}
}
