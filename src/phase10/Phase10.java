/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10;

import java.io.Serializable;
import java.util.ArrayList;

import phase10.ai.AIPlayer;
import phase10.exceptions.Phase10Exception;
import phase10.util.Configuration;
import phase10.util.DebugLog;
import phase10.util.DebugLogEntry;

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
	private DebugLog debugLog;
	
	private UserLog userLog;
	
	ArrayList<Player> winners;

	private transient GameManager gameManager;

	Phase10(GameManager gm) {
		players = new ArrayList<Player>();
		dealer = -1;
		roundNumber = 0;
		started = false;
		gameManager = gm;

		debugLog = new DebugLog();
		userLog = new UserLog();
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
			if (p instanceof AIPlayer) {
				AIPlayer ap = (AIPlayer) p;
				debugLog.addEntry(new DebugLogEntry(0, p,
						"New AI player added. Difficulty: "
								+ ap.getDifficulty()));
			} else {
				debugLog.addEntry(new DebugLogEntry(0, p, "New human player added"));
			}

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
				debugLog.addEntry(new DebugLogEntry(0, null, "STARTING GAME"));
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
		if (roundNumber != 0)
			finishRound();
		winners = checkWinners();
		if (winners.size() == 0) {
			roundNumber++;
			nextDealer();

			debugLog.addEntry(new DebugLogEntry(0, null, "Now on round #" + roundNumber));

			round = new Round(this);
			round.startRound();

		} else {
			for (Player e : winners) {
				debugLog.addEntry(new DebugLogEntry(0, e, "Won the game with "
						+ e.getScore() + " points"));
			}
			if (Configuration.PRINT_DEBUG_LOG) {
				debugLog.printLog();
			}
			gameManager.getGui().endGame(winners);
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
			Player p = players.get(i);
			if (p.getPhase() == 11) {
				if (p.getScore() < winnerScore) {
					winners = new ArrayList<Player>();
					winnerScore = p.getScore();
					winners.add(p);
				} else if (p.getScore() == winnerScore) {
					winners.add(p);
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
		debugLog.addEntry(new DebugLogEntry(0, null, "Finishing round#" + roundNumber));
		for (Player p : players) {
			// add points for remaining cards, and remove them from the hand
			Hand hand = p.getHand();
			debugLog.addEntry(new DebugLogEntry(0, p, "Cards in hand: " + hand));

			while (hand.getNumberOfCards() > 0) {
				p.addToScore(hand.getAnyCard(0).getPointValue());
				hand.removeCard(0);
			}
			debugLog.addEntry(new DebugLogEntry(0, p, "New score: " + p.getScore()));

			// clear phasegroups
			while (p.getNumberOfPhaseGroups() != 0) {
				p.removePhaseGroup(0);
			}
			// increment phase if has laid down
			if (p.hasLaidDownPhase()) {
				p.setLaidDownPhase(false);
				p.incrementPhase();
				debugLog.addEntry(new DebugLogEntry(0, p, "Now on phase #" + p.getPhase()));
			} else {
				debugLog.addEntry(new DebugLogEntry(0, p, "Still on phase #"
						+ p.getPhase()));
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
	public DebugLog getDebugLog() {
		return debugLog;
	}

	/**
	 * @return the winners
	 */
	public ArrayList<Player> getWinners() {
		return winners;
	}

	/**
	 * @param winners the winners to set
	 */
	void setWinners(ArrayList<Player> winners) {
		this.winners = winners;
	}

	/**
	 * @return the userLog
	 */
	UserLog getUserLog() {
		return userLog;
	}

}
