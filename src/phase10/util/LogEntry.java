/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10.util;

import java.io.Serializable;
import java.util.Formatter;

import phase10.Player;

/**
 * Holds each log for the logging utility
 * 
 * @author Evan Forbes
 *
 */
public class LogEntry implements Serializable {

	private static final long serialVersionUID = 20121L;

	private int turnNumber;
	private Player player;
	private String event;

	public LogEntry(int turn, Player p, String event) {
		turnNumber = turn;
		player = p;
		this.event = event;
	}

	/**
	 * @return the turnNumber
	 */
	public int getTurnNumber() {
		return turnNumber;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}

	public String toString() {
		StringBuffer out = new StringBuffer();
		Formatter format = new Formatter(out);

		if (player != null) {
			format.format("%3d %10.9s %s", turnNumber, player, event);
		} else {
			System.out.printf("%3d          %s", turnNumber, event);
		}
		return out.toString();
	}
}
