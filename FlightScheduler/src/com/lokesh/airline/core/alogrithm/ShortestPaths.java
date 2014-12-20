package com.lokesh.airline.core.alogrithm;

import com.lokesh.airline.core.Edge;
import com.lokesh.airline.core.FlightQueue;
import com.lokesh.airline.core.Input;
import com.lokesh.airline.core.Multigraph;
import com.lokesh.airline.core.QueueElement;
import com.lokesh.airline.core.Vertex;

/**
 * SHORTESTPATHS.JAVA Compute shortest paths in a graph.
 * 
 * @author Lokesh Lavangale
 * 
 */
public class ShortestPaths {

	private int INF = Integer.MAX_VALUE;
	private FlightQueue<QueueElement> queue;
	// an array to hold the vertex info so it can be accessed later
	private QueueElement[] queueElements;
	private Multigraph graph;

	//
	// constructor
	//
	public ShortestPaths(Multigraph graph, int startId, Input input,
			int startTime) {
		this.graph = graph;
		InitializeQueue(graph, startId);

		while (!queue.isEmpty()) {
			// if the queue is not empty, extract the min element
			QueueElement element = queue.extractMin();

			if (element.getDist() == INF)
				break;
			Vertex.EdgeIterator t = element.getVert().adj();
			// if the element is connected to any elements still in the queue
			// update those adjacent elements with a new distance if decreaseKey
			// returns true
			while (t.hasNext()) {
				Edge edge = t.next();
				int w = edge.weight();
				QueueElement vertexElement = queue.handleGetValue(edge.to().getHandle());

				if (queue.decreaseKey(edge.to().getHandle(), element.getDist() + w)) {
					vertexElement.setDist(element.getDist() + w);
					vertexElement.setParent(element);
					vertexElement.setEdge(edge);
				}
			}
		}
	}

	/**
	 * 
	 * Return an array containing a list of edge ID's forming a shortest path
	 * from the start vertex to the specified end vertex.
	 * 
	 * @param endId
	 * @return
	 */
	public int[] returnPath(int endId) {

		QueueElement v = queueElements[endId];
		QueueElement v_end = v;

		// count how many vertices are in the path
		int n = 1;
		if (v.getParent() == null)
			System.out.println("check2");
		while (v.getParent() != null) {
			v = v.getParent();
			n++;
		}

		int path[] = new int[n - 1];

		// working backward, add each in the path to the array path[]
		v = v_end;
		int i = n - 2;
		while (v.getParent() != null) {
			path[i] = v.getEdge().id();
			v = v.getParent();
			i--;
		}

		return path;
	}

	/**
	 * Initialize Queue
	 * 
	 * @param graph
	 * @param startId
	 */
	private void InitializeQueue(Multigraph graph, int startId) {
		// set the start distance to 0 and put it in the queue
		queue = new FlightQueue<QueueElement>();
		queueElements = new QueueElement[graph.nVertices()];
		Vertex vertex = graph.get(startId);
		QueueElement s = new QueueElement(vertex);
		s.setDist(0);

		queueElements[s.getVert().id()] = s;
		vertex.setHandle(queue.insert(0, s));

		// set all other distances to INF and put them in the queue as well
		for (int i = 0; i < graph.nVertices(); i++) {
			if (i != startId) {
				vertex = graph.get(i);
				QueueElement u = new QueueElement(vertex);
				queueElements[u.getVert().id()] = u;
				vertex.setHandle(queue.insert(INF, u));
			}
		}
	}
}
