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

	public Round getRound() {
		return round;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public int getNumberOfPlayers() {
		return players.size();
	}

	public Player getPlayer(int index) {
		return players.get(index);
	}

	void addPlayer(Player p) {
		players.add(p);
	}

	public int getDealer() {
		return dealer;
	}

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
