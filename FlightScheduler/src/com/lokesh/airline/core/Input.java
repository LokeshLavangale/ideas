package com.lokesh.airline.core;

/**

 */

import java.util.ArrayList;
import java.util.HashMap;

import com.lokesh.airline.util.FileParser;

/**
 * INPUT.JAVA 
 * Input reader for airport and flight data
 * To read all the information necessary 
 * (1) Create an object (say, "input") of type Input.
 * (2) Call input.readAirports(<airportFileName>)
 * (3) Call input.readFlights(<flightFileName>)
 * 
 * Note that you *must* do (3) after (2). 
 * 
 * If all goes well, you will then have access to 
 *  1. input.airports -- an array of Airport objects 
 *  1. input.flights -- an array of Flight objects
 *  3. input.airportMap -- a HashMap mapping airport codes to the corresponding Airport objects
 * 
 * @author Lokesh Lavangale
 * 
 */
public class Input {

	// array of all airports read from input
	private Airport airports[];

	// array of all flights read from input
	private Flight flights[];

	// mapping from airport codes (strings) to Airport objects
	private HashMap<String, Airport> airportMap;

	public Input() {
		airportMap = new HashMap<String, Airport>();
	}

	/**
	 * 
	 * readAirports() Read the airport file
	 * @param filename
	 */
	public void readAirports(String filename) {
		FileParser fileParser = new FileParser(filename);

		// hold the airports as they are read
		ArrayList<Airport> aplist = new ArrayList<Airport>();

		while (!fileParser.isEof()) {
			Airport airport = new Airport();

			airport.setName(fileParser.readWord().toUpperCase());
			airport.setOffset((fileParser.readInt() / 100) * 60);

			if (!fileParser.isEof()) {
				// create mapping from names to objects
				airportMap.put(airport.getName(), airport);
				aplist.add(airport);
			}
		}

		airports = new Airport[aplist.size()];
		aplist.toArray(airports);
	}

	
	/**
	 * readFlights()
	 * read the flight file
	 * @param filename
	 */
	public void readFlights(String filename) {
		FileParser parser = new FileParser(filename);

		// hold the flights as they are read
		ArrayList<Flight> flightList = new ArrayList<Flight>();

		// read the flights and store their times in GMT
		while (!parser.isEof()) {
			Flight flight = new Flight();

			String airline;
			int flightno;
			airline = parser.readWord();
			flightno = parser.readInt();

			flight.setName(airline + "-" + flightno);

			if (parser.isEof())
				break;

			String code1;
			int tm;
			String ampm;

			code1 = parser.readWord().toUpperCase();
			flight.setStartAirport(airportMap.get(code1));

			tm = parser.readInt();
			ampm = parser.readWord();

			flight.setStartTime(toTime(tm, ampm, flight.getStartAirport().getOffset()));

			String code2;
			code2 = parser.readWord().toUpperCase();
			flight.setEndAirport(airportMap.get(code2));

			tm = parser.readInt();
			ampm = parser.readWord();
			try {
				flight.setEndTime(toTime(tm, ampm, flight.getEndAirport().getOffset()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flightList.add(flight);
		}

		flights = new Flight[flightList.size()];
		flightList.toArray(flights);
	}

	
	/**
	 * toTime() convert raw time value and AM/PM in local time, to minutes since
	 * midnight in GMT, using supplied offset from GMT.
	 * 
	 * @param timeRaw
	 * @param ampm
	 * @param offset
	 * @return
	 */
	public int toTime(int timeRaw, String ampm, int offset) {
		int hour = (timeRaw / 100) % 12;
		int minute = timeRaw % 100;

		boolean isPM = (ampm.charAt(0) == 'P');

		int minutes = hour * 60 + minute;
		if (isPM)
			minutes += 12 * 60;

		int finalTime = (minutes - offset + 24 * 60) % (24 * 60);
		return finalTime;
	}

	/**
	 * @return the flights
	 */
	public Flight[] getFlights() {
		return flights;
	}

	/**
	 * @param flights the flights to set
	 */
	public void setFlights(Flight[] flights) {
		this.flights = flights;
	}

	/**
	 * @return the airports
	 */
	public Airport[] getAirports() {
		return airports;
	}

	/**
	 * @param airports the airports to set
	 */
	public void setAirports(Airport[] airports) {
		this.airports = airports;
	}

	/**
	 * @return the airportMap
	 */
	public HashMap<String, Airport> getAirportMap() {
		return airportMap;
	}

	/**
	 * @param airportMap the airportMap to set
	 */
	public void setAirportMap(HashMap<String, Airport> airportMap) {
		this.airportMap = airportMap;
	}
}
