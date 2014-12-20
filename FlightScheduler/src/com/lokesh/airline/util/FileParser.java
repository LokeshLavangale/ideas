package com.lokesh.airline.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;

/**
 * 
 * Parse a text file in as sane a way as Java will permit. We open the file in
 * the constructor, then repeatedly call readWord() to get strings and readInt()
 * to get integers until we reach eof. Call isEof() to see if we're there yet.
 * 
 * 
 * @author Lokesh Lavangale
 * 
 */
public class FileParser {

	private StreamTokenizer tokenizer;
	private boolean eof;

	public FileParser(String fileName) {
		try {
			InputStream is = new FileInputStream(fileName);

			Reader r = new BufferedReader(new InputStreamReader(is));

			tokenizer = new StreamTokenizer(r);
			tokenizer.eolIsSignificant(true);
		} catch (IOException e) {
			System.out.println("IOException opening command file " + fileName
					+ "\n" + e);
		}

		eof = false;
	}

	/**
	 * 
	 * @return true if we have received an EOF from the file
	 */
	public boolean isEof() {
		return eof;
	}

	/**
	 * Read an integer from the file, skipping any intervening tokens (result
	 * will be bogus if eof is reached)
	 * 
	 * @return
	 */
	public int readInt() {
		scanTo(StreamTokenizer.TT_NUMBER);
		return ((int) tokenizer.nval);
	}

	/**
	 * read a String from the file, skipping any intervening numeric tokens
	 * (result will be bogus if eof is reached)
	 * 
	 * @return
	 */
	public String readWord() {
		scanTo(StreamTokenizer.TT_WORD);
		if (tokenizer.sval != null)
			return (tokenizer.sval);
		else
			return ("" + ((char) tokenizer.ttype));
	}

	/**
	 * read the next token of type tokenType
	 * 	
	 * @param tokenType
	 */
	private void scanTo(int tokenType) {
		// scans to a given token type:
		// TT_NUMBER or TT_WORD

		boolean found = false;
		try {
			while (!found) {
				int ttype = tokenizer.nextToken();
				if (ttype == tokenType) {
					found = true;
				} else if (ttype == StreamTokenizer.TT_EOF) {
					found = true;
					eof = true;
				} else if (ttype == StreamTokenizer.TT_EOL) {
					// skip over end of line
				} else if ((tokenType == StreamTokenizer.TT_WORD)
						&& (ttype != StreamTokenizer.TT_NUMBER)) {
					found = true;
				}
			}
		} catch (IOException e) {
			System.out.println("IOException while scanning for input." + e.getMessage());
		}
	}
}
