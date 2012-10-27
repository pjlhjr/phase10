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

/**
 * This class contains and manages the information for each round
 * 
 * @author Evan Forbes
 */
public class Round implements Serializable {

	private static final long serialVersionUID = 20121L;
	private static final int TIMES_TO_SHUFFLE = 5;
	private static final int NUM_WILDS = 8;
	private static final int NUM_SKIPS = 4;
	private static final int NUM_CARDS_TO_DEAL = 10;

	private Phase10 game;
	private ArrayList<Card> deck;
	private Stack<Card> discardStack;
	private int turn; // what player's turn it is

	/**
	 * Creates and initializes the round
	 * 
	 * @param g
	 *            the phase10 game
	 * @param dealer
	 *            the player the round starts with
	 */
	Round(Phase10 g) {
		game = g;
		turn = game.getDealer();

		initiateRound();

		nextTurn();
	}

	/**
	 * Removes the next card from the discard pile and moves it into the
	 * player's hand
	 * 
	 * @param player
	 *            the player to move the card to
	 * @return false if it is an invalid move (attempt to pick up a skip card)
	 */
	public boolean drawFromDiscard(Player player) {
		// cannot pick up a skip
		if (discardStack.peek().getValue() == 14)
			return false;

		player.getHand().addCard(discardStack.pop());
		return true;
	}

	/**
	 * Removes the next card from the deck and moves it to the player's hand
	 * 
	 * @param player
	 *            the player to move the card to
	 */
	public void drawFromDeck(Player player) {
		// TODO What if deck is empty?

		player.getHand().addCard(deck.get(deck.size() - 1));
		deck.remove(deck.size() - 1);
	}

	/**
	 * Removes the card from the player's hand and adds it to the discard pile.
	 * Then, advances to the next player's turn (& checks if the round is over)
	 * 
	 * @param player
	 *            the player to remove the card from
	 * @param card
	 *            the card to discard
	 */
	public void discard(Player player, Card card) {
		discardStack.push(card);
		player.getHand().removeCard(card);

		nextTurn();
	}

	/**
	 * Gets the "showing" card on the discard pile
	 * 
	 * @return the top card from the discard pile
	 */
	public Card getTopOfDiscardPile() {
		return discardStack.peek();
	}

	private void initiateRound() {
		createDeck();
		shuffle();
		deal();
	}

	private void createDeck() {
		deck = new ArrayList<Card>();
		for (int color = 0; color < 4; color++) {
			for (int value = 1; value < 13; value++) {
				deck.add(new Card(color, value));
				deck.add(new Card(color, value));
			}
		}
		for (int i = 0; i < NUM_WILDS; i++)
			deck.add(new Card(-1, 13)); // Wilds
		for (int i = 0; i < NUM_SKIPS; i++)
			deck.add(new Card(-1, 14)); // Skips

	}

	private void shuffle() {
		Random r = new Random();
		for (int t = 0; t < TIMES_TO_SHUFFLE; t++) {
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
		for (int c = 0; c < NUM_CARDS_TO_DEAL; c++) {
			for (int p = 0; p < game.getNumberOfPlayers(); p++) {
				game.getPlayer(p).getHand().addCard(deck.get(deck.size() - 1));
				deck.remove(deck.size() - 1);
			}
		}

		// Add the next card to the discard pile
		discardStack = new Stack<Card>();
		discardStack.push(deck.get(deck.size() - 1));
		deck.remove(deck.size() - 1);
	}

	private void nextTurn() {
		// TODO check if the round is over (someone's hand is empty). if so,
		// call game.nextRound i think
		
		if (roundIsComplete())
		{
			game.nextRound();
		}
		
		turn++;
		if (turn >= game.getNumberOfPlayers()) {
			turn = 0;
		}
		// TODO Call method here of GUI, to prompt the player for their turn?
	}

	private boolean roundIsComplete() {
		for (int p =0; p<game.getNumberOfPlayers();p++)
		{
			if (game.getPlayer(p).getHand().getNumberOfCards()==0)
				return true;
		}
		return false;
	}
	
	/**
	 * Gets the player index of who is currently playing their turn
	 * @return the current player index
	 */
	public int getTurn()
	{
		return turn;
	}
}
