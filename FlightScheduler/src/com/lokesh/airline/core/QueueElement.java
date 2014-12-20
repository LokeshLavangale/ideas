package com.lokesh.airline.core;

/**
 * QueueElement objects are contain information about the shortest path vertices
 * including each vertex's parent, the shortest distance to the start vertex,
 * and the edge length between it and its parent At initialization, parent is
 * set to null, and dist is set to INF.
 * 
 * @author Lokesh Lavangale
 * 
 */
public class QueueElement {

	private Vertex vert;

	private QueueElement parent;

	private int dist;

	private Handle handle;

	private Edge edge;

	public QueueElement(Vertex v) {
		setParent(null);
		setVert(v);
		setDist(Integer.MAX_VALUE);
	}

	/**
	 * The string representation for QElements is its vertex's string
	 * representation and its distance from the start vertex
	 */
	public String toString() {
		String str = getVert().toString() + "," + getDist();
		return str;
	}

	/**
	 * @return the dist
	 */
	public int getDist() {
		return dist;
	}

	/**
	 * @param dist the dist to set
	 */
	public void setDist(int dist) {
		this.dist = dist;
	}

	/**
	 * @return the vert
	 */
	public Vertex getVert() {
		return vert;
	}

	/**
	 * @param vert the vert to set
	 */
	public void setVert(Vertex vert) {
		this.vert = vert;
	}

	/**
	 * @return the parent
	 */
	public QueueElement getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(QueueElement parent) {
		this.parent = parent;
	}

	/**
	 * @return the edge
	 */
	public Edge getEdge() {
		return edge;
	}

	/**
	 * @param edge the edge to set
	 */
	public void setEdge(Edge edge) {
		this.edge = edge;
	}

}
