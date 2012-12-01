/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */

package phase10;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import phase10.ai.AIPlayer;
import phase10.card.Card;
import phase10.card.WildCard;
import phase10.exceptions.Phase10Exception;
import phase10.util.Configuration;
import phase10.util.LogEntry;

/**
 * This class contains and manages the information for each round
 * 
 * @author Evan Forbes
 */
public final class Round implements Serializable {

	private static final long serialVersionUID = 20121L;

	private Phase10 game;
	private ArrayList<Card> deck;
	private Stack<Card> discardStack;

	private int curPlayerNum; // what player's turn it is

	private int turnNumber;

	private boolean started;

	private ArrayList<LogEntry> log;

	/**
	 * Creates and initializes the round
	 * 
	 * @param g
	 *            the phase10 game
	 */
	Round(Phase10 g) {
		game = g;
		curPlayerNum = game.getDealer() - 1;
		turnNumber = 0;
		log = new ArrayList<LogEntry>();

		started = false;

		initiateRound();
	}

	public void startRound() {
		if (!started) {
			started = true;
			nextTurn();
		} else {
			throw new Phase10Exception("Round already started");
		}
	}

	/**
	 * Removes the next card from the discard pile and moves it into the current
	 * player's hand
	 * 
	 * @return false if it is an invalid move (attempt to pick up a skip card)
	 * @throws Phase10Exception
	 *             if player has already drawn a card this turn
	 */
	public boolean drawFromDiscard() {
		Player player = game.getCurrentPlayer();
		if (player.getHasDrawnCard())
			throw new Phase10Exception(
					"Cannot draw from discard: has already drawn");

		// cannot pick up a skip
		if (discardStack.peek().getValue() == Configuration.SKIP_VALUE)
			return false;

		Card card = discardStack.pop();
		log.add(new LogEntry(turnNumber, player, card, false));
		player.getHand().addCard(card);
		player.getHand().sortByValue();
		player.setHasDrawnCard(true);
		return true;
	}

	/**
	 * Removes the next card from the deck and moves it to the current player's
	 * hand. If there are no more cards in the deck after this operation, the
	 * discard stack is reshuffled.
	 * 
	 * @throws Phase10Exception
	 *             if player has already drawn a card this turn
	 * 
	 */
	public void drawFromDeck() {
		Player player = game.getCurrentPlayer();
		if (player.getHasDrawnCard())
			throw new Phase10Exception(
					"Cannot draw from deck: has already drawn");

		player.getHand().addCard(deck.get(deck.size() - 1));
		deck.remove(deck.size() - 1);

		player.setHasDrawnCard(true);

		player.getHand().sortByValue();

		if (deck.size() == 0) {
			Card topDiscard = discardStack.pop();
			while (!discardStack.isEmpty()) {
				deck.add(discardStack.pop());
			}
			shuffle();
			discardStack.push(topDiscard);
		}
	}

	/**
	 * Removes the card from the current player's hand and adds it to the
	 * discard pile. Then, advances to the next player's turn (& checks if the
	 * round is over)
	 * 
	 * @param card
	 *            the card to discard
	 * @throws Phase10Exception
	 *             if player has not yet drawn a card this turn
	 */
	public void discard(Card card) {
		Player player = game.getCurrentPlayer();

		if (!player.getHasDrawnCard())
			throw new Phase10Exception("Cannot discard: has not yet drawn");

		log.add(new LogEntry(turnNumber, player, card, true));
		discardStack.push(card);
		player.getHand().removeCard(card);

		player.setHasDrawnCard(false);

		player.getHand().sortByValue();

		if (card.getValue() == Configuration.SKIP_VALUE) {
			int nextPlayer = curPlayerNum + 1;
			if (nextPlayer >= game.getNumberOfPlayers()) {
				nextPlayer = 0;
			}
			game.getPlayer(nextPlayer).setSkip(true);
		}

		nextTurn();
	}

	/**
	 * Gets the "showing" card on the discard pile. Does not change the stack.
	 * 
	 * @return the top card from the discard pile, null if there is no card
	 */
	public Card getTopOfDiscardStack() {
		return discardStack.isEmpty() ? null : discardStack.peek();
	}

	/**
	 * Gets the player index of who is currently playing their turn
	 * 
	 * @return the current player index
	 */
	public int getCurPlayerNum() {
		return curPlayerNum;
	}

	/**
	 * Creates the deck, shuffles it, and deals 10 cards to all players.
	 */
	private void initiateRound() {
		createDeck();
		shuffle();
		deal();
	}

	/**
	 * Creates a deck: Number 1-12, two of each in four different colors +wilds,
	 * skips
	 */
	private void createDeck() {

		deck = new ArrayList<Card>();
		for (int color = 0; color < 4; color++) {
			for (int value = 1; value < 13; value++) {
				deck.add(new Card(Configuration.COLORS[color], value));
				deck.add(new Card(Configuration.COLORS[color], value));
			}
		}
		for (int i = 0; i < Configuration.NUM_WILDS; i++)
			deck.add(new WildCard(Configuration.WILD_VALUE));
		for (int i = 0; i < Configuration.NUM_SKIPS; i++)
			deck.add(new Card(Configuration.SKIP_VALUE));

	}

	private void shuffle() {
		Random r = new Random();
		for (int t = 0; t < Configuration.TIMES_TO_SHUFFLE; t++) {
			ArrayList<Card> newDeck = new ArrayList<Card>();
			while (!deck.isEmpty()) {
				int index = r.nextInt(deck.size());
				newDeck.add(deck.get(index));
				deck.remove(index);
			}
			deck = newDeck;
		}
	}

	private void deal() {
		for (int c = 0; c < Configuration.NUM_CARDS_TO_DEAL; c++) {
			for (int p = 0; p < game.getNumberOfPlayers(); p++) {
				game.getPlayer(p).getHand().addCard(deck.get(deck.size() - 1));
				deck.remove(deck.size() - 1);
			}
		}

		for (int p = 0; p < game.getNumberOfPlayers(); p++) {
			game.getPlayer(p).getHand().sortByValue();
		}
		// Add the next card to the discard pile
		discardStack = new Stack<Card>();
		discardStack.push(deck.get(deck.size() - 1));
		deck.remove(deck.size() - 1);
	}

	/**
	 * Checks if the round is complete; if not, advances play to the next player
	 * (skipping if necessary). If it is an AI player, the playTurn() method is
	 * invoked.
	 */
	private void nextTurn() {
		// System.out.println("next turn");
		if (roundIsComplete()) {
			game.nextRound();
		} else {
			advanceTurn();
			if (game.getPlayer(curPlayerNum).getSkip()) {
				// System.out.println("skip");
				game.getPlayer(curPlayerNum).setSkip(false);
				advanceTurn();
			}
			if (game.getPlayer(curPlayerNum) instanceof AIPlayer) {
				// System.out.println("ai player's turn: "+curPlayerNum);
				AIPlayer p = (AIPlayer) game.getPlayer(curPlayerNum);
				// TODO call gui?
				p.playTurn();
			}

			try {
				game.getGameManager().getGui().newTurnWindowUpdate();
			} catch (NullPointerException e) {
				System.out.println("Too early");
				e.printStackTrace();
			}
		}

	}

	/**
	 * Increases the turn counter by 1, or wraps around back to 0
	 */
	private void advanceTurn() {
		// System.out.print("Advancing player turn from "+curPlayerNum);
		turnNumber++;
		curPlayerNum++;
		if (curPlayerNum >= game.getNumberOfPlayers()) {
			curPlayerNum = 0;
		}
		// System.out.println(" to "+curPlayerNum);
	}

	/**
	 * Checks if any players have no cards left in their hand
	 * 
	 * @return true if the round is over
	 */
	private boolean roundIsComplete() {
		for (int p = 0; p < game.getNumberOfPlayers(); p++) {
			if (game.getPlayer(p).getHand().getNumberOfCards() == 0)
				return true;
		}
		return false;
	}

	/**
	 * Gets the log of actions for the round
	 * 
	 * @return the log
	 */
	public ArrayList<LogEntry> getLog() {
		return log;
	}

	/**
	 * Gets the current turn number for this round
	 * 
	 * @return the turn number
	 */
	public int getTurnNumber() {
		return turnNumber;
	}

}
