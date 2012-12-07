/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10;

import java.util.ArrayList;

/**
 * This keeps a log of Strings to let the human players what happens
 * 
 * @author Evan Forbes
 * 
 */
public class UserLog {

	private ArrayList<String> log;

	UserLog() {
		log = new ArrayList<String>();
	}

	/**
	 * adds a string to the log
	 * 
	 * @param message
	 *            the string to add
	 */
	void add(String message) {
		log.add(message);
	}

	/**
	 * Get a string containing all logged strings
	 * 
	 * @return the log in a single string
	 */
	public String get() {
		StringBuilder sb = new StringBuilder();
		for (String e : log) {
			sb.append(e);
			sb.append(" \r\n");
		}
		return sb.toString();
	}

	/**
	 * 
	 * @return the number of strings in the log
	 */
	public int getSize() {
		return log.size();
	}

	/**
	 * removes all log entries
	 */
	void deleteAll() {
		log = new ArrayList<String>();
	}

}
