/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class contains and manages the information for each Phase 10 game
 * 
 * @author Evan Forbes
 */
public class Phase10 implements Serializable {

	private static final long serialVersionUID = 20121L;

	private ArrayList<Player> players;
	private Round round;
	private int roundNumber;
	private int dealer;

	Phase10() {
		players = new ArrayList<Player>();
		dealer = -1;
		roundNumber = 0;
	}

	/**
	 * @return the current round object
	 */
	public Round getRound() {
		return round;
	}

	/**
	 * 
	 * @return the current round number (the first round is #1)
	 */
	public int getRoundNumber() {
		return roundNumber;
	}

	/**
	 * 
	 * @return the number of players
	 */
	public int getNumberOfPlayers() {
		return players.size();
	}

	public Player getPlayer(int index) {
		return players.get(index);
	}
	
	/**
	 * Gets the Player object of who is currently playing their turn
	 * @return the current Player object; null if the round has not yet been initialized
	 */
	public Player getCurrentPlayer() {
		return (round==null)? null : getPlayer(round.getTurn());
	}

	/**
	 * Adds a player to the game
	 * @param p the player to add
	 */
	void addPlayer(Player p) {
		players.add(p);
	}

	/**
	 * 
	 * @return the number of the player that is currently dealer
	 */
	public int getDealer() {
		return dealer;
	}

	/**
	 * Starts the first round of the game
	 */
	public void startGame() {
		nextRound();
	}

	void nextRound() {
		finishRound();

		roundNumber++;
		nextDealer();
		round = new Round(this);
		// TODO Call Gui- say new round has started
	}

	private void nextDealer() {
		dealer++;
		if (dealer >= getNumberOfPlayers()) {
			dealer = 0;
		}
	}

	/**
	 * This resets the player's hand, phasegroups, and adds the points to their
	 * score.
	 */
	private void finishRound() {
		for (Player p : players) {
			Hand hand = p.getHand();
			if (hand.getNumberOfCards() > 0) {
				for (int c = 0; c < hand.getNumberOfCards(); c++) {
					p.addToScore(hand.getCard(c).getPointValue());
					hand.removeCard(c);
				}
			}

			for (int pg = 0; pg < p.getNumberOfPhaseGroups(); pg++) {
				p.removePhaseGroup(pg);
			}
			p.setLaidDownPhase(false);
		}
	}

}