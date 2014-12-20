package com.lokesh.airline;

import com.lokesh.airline.core.Airport;
import com.lokesh.airline.core.Edge;
import com.lokesh.airline.core.Flight;
import com.lokesh.airline.core.Input;
import com.lokesh.airline.core.Multigraph;
import com.lokesh.airline.core.Query;
import com.lokesh.airline.core.Vertex;
import com.lokesh.airline.core.alogrithm.ShortestPaths;
import com.lokesh.airline.util.PrettyTime;
import com.lokesh.airline.util.QueryReader;

/**
 * Airline scheduling shortest-paths code
 * 
 * @author Lokesh Lavangale
 * 
 */
public class FlightScheduler {

	static Input input = new Input();
	
	public static void main(String args[]) {
		if (args.length < 3) {
			System.err
					.println("Usage: FlightScheduler <airports> <flights> <queries>");
			return;
		}

		// read the input
		input.readAirports(args[0]);
		System.out.println("Read " + input.getAirports().length + " airports");
		input.readFlights(args[1]);
		System.out.println("Read " + input.getFlights().length + " flights");
	
		// construct the input graph

		Multigraph graph = new Multigraph();

		// Allocate a vertex for every airport. Logically, the jth
		// vertex corresponds to the jth entry in input.getAirports()[].
		//
		for (int j = 0; j < input.getAirports().length; j++) {
			input.getAirports()[j].setId(j);

			Vertex v = new Vertex(j);
			graph.addVertex(v);
		}

		// While we allocate all the edges, set the id for each
		// edge to the index of the corresponding flight in the
		// input.getFlights()[] array.
		//
		for (int k = 0; k < input.getFlights().length; k++) {
			Flight flight = input.getFlights()[k];

			Vertex from = graph.get(flight.getStartAirport().getId());
			Vertex to = graph.get(flight.getEndAirport().getId());

			int flightLength = flight.getEndTime() - flight.getStartTime();
			if (flightLength < 0)
				flightLength += 24 * 60; // next day

			Edge edge = new Edge(k, from, to, flightLength);

			from.addEdge(edge);
		}

		// Do the shortest-paths computation
		findPaths(input, graph, args[2]);
	}

	/**
	 * Construct shortest paths from a specified starting airport to every
	 * vertex of G, and then answer queries asking for the shortest path (least
	 * total travel time) from the start to other airports.
	 * 
	 * 
	 * @param input
	 * @param graph
	 * @param queryFile
	 */
	static void findPaths(Input input, Multigraph graph, String queryFile) {
		Query queries[] = QueryReader.readQueries(queryFile);
		if (queries == null) // I/O error
		{
			return;
		}

		for (Query query : queries) {
			//
			// Fix the starting airport
			//
			System.out.println("> " + query);

			Airport fromAirport = input.getAirportMap().get(query.getFrom());

			/*
			 * System.out.println("fromap.id = " + fromap.id);
			 * System.out.println("fromap.name = " + fromap.name); for(int i=0;
			 * i<input.airports.length; i++){ System.out.println("" +
			 * input.airports[i].name + ",  " + input.airports[i].id); } //
			 */

			if (fromAirport == null) {
				System.err.println("Error: airport code " + query.getFrom()
						+ " is unknown");

				continue;
			}

			//
			// YOUR CODE CALLED HERE -- find shortest paths from start
			//
			ShortestPaths paths = new ShortestPaths(graph, fromAirport.getId(),
					input, query.getStartTime());

			// System.out.println(input.flights[fromap.id].startAirport.name);
			// System.out.println(input.flights[fromap.id].startAirport.id);

			//
			// Field shortest-path queries to other airports
			//
			for (String token : query.getTokens()) {
				Airport toAirport = input.getAirportMap().get(token);
				// System.out.println(toap.name);

				if (toAirport == null) {
					System.err.println("Error: airport code " + token
							+ " is unknown");
					continue;
				}

				if (toAirport == fromAirport) {
					System.out.println("You're already there!");
					continue;
				}

				int flightIds[] = paths.returnPath(toAirport.getId());
				if (flightIds.length == 0)
					System.out.println("No path to " + token);
				else {
					printPath(input, flightIds);
					validatePath(input, query.getStartTime(), flightIds);
				}

				System.out.println("");
			}
		}
	}

	/**
	 * print a path of edges (flight identifiers) between airports
	 * 
	 * @param input
	 * @param flightIds
	 */
	static void printPath(Input input, int flightIds[]) {
		System.out.println(input.getFlights()[flightIds[0]].getStartAirport().getName());

		for (int flightId : flightIds) {
			Flight flight = input.getFlights()[flightId];

			System.out.println("---> "
					+ flight.getEndAirport().getName()
					+ " ("
					+ flight.getName()
					+ ", "
					+ PrettyTime.toString(flight.getStartTime())
					+ "-"
					+ PrettyTime.toString(flight.getEndTime())
					+ ", "
					+ PrettyTime.elapsedToString(flight.getStartTime(),
							flight.getEndTime()) + ")");
		}
	}

	/**
	 * validate that a proposed path of flights is feasible
	 * (i.e., all legs are connected), and compute and print the total time
	 * spent flying (+ time spent waiting if startTime != 0).
	 * 
	 * 
	 * @param input
	 * @param startTime
	 * @param flightIds
	 */
	static void validatePath(Input input, int startTime, int flightIds[]) {
		int prevEndTime = startTime;
		Flight prev = null;
		int totalTime = 0;

		for (int f = 0; f < flightIds.length; f++) {
			Flight flight = input.getFlights()[flightIds[f]];

			// verify that the path of flights is connected
			if (f > 0) {
				if (flight.getStartAirport().getId() != prev.getEndAirport().getId()) {
					System.out
							.println("ERROR: path is not " + "connected in G");
					break;
				}

				if (startTime != 0
						&& PrettyTime.elapsed(prevEndTime, flight.getStartTime()) < 45) {
					System.out.println("ERROR: connection time "
							+ "is too short!");
					break;
				}
			}

			// add the time spent in flight. If the start time is
			// non-zero, we are minimizing time including layovers,
			// and we need to account waiting time as well.
			if (startTime != 0)
				totalTime += PrettyTime.elapsed(prevEndTime, flight.getStartTime());

			totalTime += PrettyTime.elapsed(flight.getStartTime(), flight.getEndTime());

			prev = flight;
			prevEndTime = flight.getEndTime();
		}

		System.out.println("@LENGTH = " + totalTime + " minutes");
	}
}
