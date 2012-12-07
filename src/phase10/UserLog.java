package phase10;

import java.util.ArrayList;

public class UserLog {

	private ArrayList<String> log;

	UserLog() {
		log = new ArrayList<String>();
	}

	void add(String message) {
		log.add(message);
	}

	public String get() {
		StringBuilder sb = new StringBuilder();
		for (String e: log){
			sb.append(e);
			sb.append(" \r\n");
		}
		return sb.toString();
	}

	public int getSize() {
		return log.size();
	}
	
	void deleteAll() {
		log = new ArrayList<String>();
	}
	
}
