/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */

package phase10.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Logging utility to keep track of what happens during the game
 * 
 * @author Evan Forbes
 * 
 */
public class DebugLog implements Serializable {

	private static final long serialVersionUID = 20121L;
	
	ArrayList<DebugLogEntry> log;

	/**
	 * Instantiates the debug log
	 */
	public DebugLog() {
		log = new ArrayList<DebugLogEntry>();
	}

	/**
	 * Adds a new entry to the debug log
	 * @param entry the entry to add
	 */
	public void addEntry(DebugLogEntry entry) {
		log.add(entry);
		if (Configuration.PRINT_DEBUG_LOG) {
			System.out.println(entry);
		}
		if (Configuration.SAVE_DEBUG_LOG) {
			saveLog();
		}
	}

	/**
	 * Prints the entire log to standard out
	 */
	public void printLog() {
		System.out.println("==LOG==");
		for (DebugLogEntry e : log) {
			System.out.println(e);
		}
		System.out.println("==END LOG==");
	}

	/**
	 * Saves the entire log to the file specified in Config
	 */
	public void saveLog() {
		try {
			FileWriter fstream = new FileWriter(Configuration.DEBUG_LOG_FILE);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("==PHASE 10 LOG==\r\n");
			for (DebugLogEntry e : log) {
				out.write(e + "\r\n");
			}
			out.write("==END PHASE 10 LOG==");
			out.close();
		} catch (Exception e) {
			System.err.println("Log Save Error: " + e.getMessage());
		}
	}
}
