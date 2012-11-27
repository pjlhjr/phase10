package phase10.util;

import java.io.Serializable;

import phase10.Player;
import phase10.card.Card;

public class LogEntry implements Serializable {
	
	private static final long serialVersionUID = 20121L;

	private int turnNumber;
	private Player player;
	private Card card;
	private boolean laidDown;
	
	public LogEntry(int turn, Player p, Card c, boolean laidDown){
		turnNumber = turn;
		player = p;
		card = c;
		this.laidDown = laidDown;
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
	 * @return the card
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * @return the laidDown
	 */
	public boolean isLaidDown() {
		return laidDown;
	}
	
	
}
