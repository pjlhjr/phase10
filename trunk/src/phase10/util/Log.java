package phase10.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class Log {

	ArrayList<LogEntry> log;

	public Log() {
		log = new ArrayList<LogEntry>();
	}

	public void addEntry(LogEntry entry) {
		log.add(entry);
		if (Configuration.PRINT_LOG) {
			System.out.println(entry);
		}
		if (Configuration.SAVE_LOG) {
			saveLog();
		}
	}

	public void printLog() {
		System.out.println("==LOG==");
		for (LogEntry e : log) {
			System.out.println(e);
		}
		System.out.println("==END LOG==");
	}

	public void saveLog() {
		try {
			FileWriter fstream = new FileWriter(Configuration.LOG_FILE);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("==PHASE 10 LOG==\r\n");
			for (LogEntry e : log) {
				out.write(e + "\r\n");
			}
			out.write("==END PHASE 10 LOG==");
			out.close();
		} catch (Exception e) {
			System.err.println("Log Save Error: " + e.getMessage());
		}
	}
}
