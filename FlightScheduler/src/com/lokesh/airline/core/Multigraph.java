package com.lokesh.airline.core;

import java.util.*;

/**
 * MULTIGRAPH.JAVA Multigraph class
 * 
 * A Multigraph contains a collection of Vertex objects, each of which maintains
 * its own adjacency list of edges (see the Vertex and Edge classes). 
 * To enumerate the vertices in the Multigraph, you can simply ask for the number
 * of vertices n, then call get() successively on 0 .. n-1.
 * 
 * @author Lokesh Lavangale
 * 
 */
public class Multigraph {

	private ArrayList<Vertex> vertices;
	
	public Multigraph() {
		vertices = new ArrayList<Vertex>();
	}

	/**
	 * nVertices()	 * 
	 * @return Return number of vertices in graph.
	 */
	public int nVertices() {
		return vertices.size();
	}
	 
	/**
	 * get()
	 * @param id
	 * @return Return a specified vertex
	 */
	public Vertex get(int id) {
		return vertices.get(id);
	}

	
	/**
	 * addVertex()
	 * Add a vertex to the graph.
	 * @param v
	 */
	public void addVertex(Vertex v) {
		vertices.add(v);
	}

}
