/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10;

import java.io.Serializable;
import java.util.ArrayList;

import phase10.exceptions.Phase10Exception;
import phase10.util.Configuration;
import phase10.util.Log;
import phase10.util.LogEntry;

/**
 * This class contains and manages the information for each Phase 10 game
 * 
 * @author Evan Forbes
 */
public final class Phase10 implements Serializable {

	private static final long serialVersionUID = 20121L;

	private ArrayList<Player> players;
	private Round round;
	private int roundNumber;
	private int dealer;
	private boolean started;
	private Log log;

	private transient GameManager gameManager;

	Phase10(GameManager gm) {
		players = new ArrayList<Player>();
		dealer = -1;
		roundNumber = 0;
		started = false;
		gameManager = gm;

		log = new Log();
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

	/**
	 * Gets the player object at the given index
	 * 
	 * @param index
	 *            the player's index
	 * @return the player object
	 */
	public Player getPlayer(int index) {
		return players.get(index);
	}

	/**
	 * Gets the Player object of who is currently playing their turn
	 * 
	 * @return the current Player object;
	 * 
	 * @throws Phase10Exception
	 *             if the game has not yet started
	 */
	public Player getCurrentPlayer() {
		if (!started)
			throw new Phase10Exception(
					"Phase10 Has not yet been started. Must call startGame before this action can be done.");
		return getPlayer(round.getCurPlayerNum());
	}

	/**
	 * Adds a player to the game
	 * 
	 * @param p
	 *            the player to add
	 * @throws Phase10Exception
	 *             if the game has already started
	 */
	public void addPlayer(Player p) {
		if (!started) {
			players.add(p);
			log.addEntry(new LogEntry(-1, p, "New player added"));
		} else
			throw new Phase10Exception(
					"Cannot add player after game has started");
	}

	/**
	 * 
	 * @return the number of the player that is currently dealer
	 */
	public int getDealer() {
		return dealer;
	}

	GameManager getGameManager() {
		return gameManager;
	}

	/**
	 * Starts the first round of the game
	 * 
	 * @throws Phase10Exception
	 *             if the game has already started or there are less than 2
	 *             players added.
	 */
	public void startGame() {
		if (!started) {
			dealer = getNumberOfPlayers() - 1;
			// System.out.println("dealer0: "+dealer);
			if (getNumberOfPlayers() >= 2) {
				started = true;
				nextRound();
			} else
				throw new Phase10Exception(
						"Cannot start game with less than 2 players");

		} else
			throw new Phase10Exception("Game has already started");
	}

	/**
	 * Resets the necessary player data and checks to see if there is a winner
	 */
	void nextRound() {
		finishRound();
		ArrayList<Player> winners = checkWinners();
		if (winners.size() == 0) {
			roundNumber++;
			nextDealer();

			// TODO Call Gui- say new round has started
			round = new Round(this);

		} else {
			for (Player e : winners) {
				log.addEntry(new LogEntry(-1, e, " Won the game with "
						+ e.getScore() + " points"));
			}
			if (Configuration.PRINT_LOG) {
				log.printLog();
			}
			// TODO call gui- winner(s)
		}
	}

	/**
	 * Checks to see if someone has completed the tenth phase. If so, the proper
	 * GUI method will be called.
	 * 
	 * @return the list of all winning players. (empty if there are no winning
	 *         players)
	 */
	private ArrayList<Player> checkWinners() {
		ArrayList<Player> winners = new ArrayList<Player>();
		int winnerScore = Integer.MAX_VALUE;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getPhase() == 11) {
				if (players.get(i).getScore() < winnerScore) {
					winners = new ArrayList<Player>();
					winnerScore = players.get(i).getScore();
					winners.add(players.get(i));
				} else if (players.get(i).getScore() == winnerScore) {
					winners.add(players.get(i));
				}
			}
		}
		return winners;
	}

	/**
	 * Increments the dealer counter (resets to zero if it goes out of bounds)
	 */
	private void nextDealer() {
		dealer++;
		if (dealer >= getNumberOfPlayers()) {
			dealer = 0;
		}
	}

	/**
	 * This resets the player's hand, phase groups, and adds the points to their
	 * score.
	 */
	private void finishRound() {
		for (Player p : players) {
			// add points for remaining cards, and remove them from the hand
			Hand hand = p.getHand();
			for (int c = 0; c < hand.getNumberOfCards(); c++) {
				p.addToScore(hand.getAnyCard(c).getPointValue());
				hand.removeCard(c);
			}

			// clear phasegroups
			for (int pg = 0; pg < p.getNumberOfPhaseGroups(); pg++) {
				p.removePhaseGroup(pg);
			}
			// increment phase if has laid down
			if (p.hasLaidDownPhase()) {
				p.setLaidDownPhase(false);
				p.incrementPhase();
			}
			// reset has drawn card
			p.setHasDrawnCard(false);
		}
	}

	void setGameManager(GameManager gm) {
		gameManager = gm;
	}

	/**
	 * @return the log
	 * 
	 */
	public Log getLog() {
		return log;
	}

}
