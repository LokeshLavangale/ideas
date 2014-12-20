package com.lokesh.airline.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.lokesh.airline.core.Query;

/**
 * QUERYREADER.JAVA Parse a file of flight queries. Return an array of all
 * queries as Query objects.
 * 
 * A query contains a start airport followed by one or more end airports, all
 * separated by whitespace on a single line.
 * 
 * 
 * @author Lokesh Lavangale
 * 
 */
public class QueryReader {

	/**
	 * Given the name of a query file, read all the queries in that file and
	 * return them as an array of Query objects. If any error is encountered
	 * parsing the file, return a NULL array.
	 * 
	 * 
	 * @param fileName
	 * @return
	 */
	public static Query[] readQueries(String fileName) {
		BufferedReader r = null;

		// This array temporarily holds the queries as they are read.
		ArrayList<Query> a = new ArrayList<Query>();

		try {
			InputStream is = new FileInputStream(fileName);

			r = new BufferedReader(new InputStreamReader(is));

			// Read each query, one per line.
			while (true) {

				String nextLine = r.readLine();
				if (nextLine == null) // EOF
					break;
				else {
					String ts = nextLine.trim();
					if (ts.equals("")) // skip blank lines
						continue;
					else {
						Query q = parseQuery(ts);
						if (q == null)
							return null;
						a.add(q);
						// System.out.println(q);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("IOException opening query file " + fileName
					+ "\n" + e);
			return null;
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		Query queries[] = new Query[a.size()];
		return a.toArray(queries);
	}

	/**
	 * Parse a single query from a line read from the query file. Return null if
	 * we cannot parse the query.
	 * 
	 * @param queryString
	 * @return
	 */
	static Query parseQuery(String queryString) {
		Tokenizer tokenizer = new Tokenizer(queryString);
		ArrayList<String> tokens = new ArrayList<String>();
		Query query = new Query();

		try {
			query.setStartTime(PrettyTime.toTime(Integer.valueOf(tokenizer
					.nextToken())));
		} catch (NumberFormatException ex) {
			System.out.println("Could not parse start time from query '"
					+ queryString + "'");
			return null;
		}

		query.setFrom(tokenizer.nextToken().toUpperCase());

		if (query.getFrom().equals("")) {
			System.out.println("No 'from' airport in query '" + queryString
					+ "'");
			return null;
		}

		while (true) {
			String s = tokenizer.nextToken().toUpperCase();
			if (s.equals(""))
				break;
			else
				tokens.add(s);
		}

		if (tokens.size() == 0) {
			System.out
					.println("No 'to' airport in query '" + queryString + "'");
			return null;
		} else {
			query.setTokens(new String[tokens.size()]);
			tokens.toArray(query.getTokens());
			return query;
		}
	}
}
