/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */

package phase10;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains and manages the cards in each player's hand
 * 
 * @author Evan Forbes
 */
public final class Hand implements Serializable
{

	private static final long serialVersionUID = 20121L;

	private ArrayList<Card> cards;

	/**
	 * Creates an empty hand object, with no cards
	 */
	Hand()
	{
		cards = new ArrayList<Card>();
	}

	/**
	 * Adds a card to the hand
	 * 
	 * @param card
	 *            the card to add
	 */
	void addCard(Card card)
	{
		cards.add(card);
	}

	/**
	 * Removes a card from the hand
	 * 
	 * @param card
	 *            the card to remove
	 */
	void removeCard(int card)
	{
		cards.remove(card);
	}

	/**
	 * Removes a card from the hand
	 * 
	 * @param card
	 *            the card to remove
	 */
	void removeCard(Card card)
	{
		cards.remove(card);
	}

	/**
	 * @return the number of cards in the hand
	 */
	public int getNumberOfCards()
	{
		return cards.size();
	}

	/**
	 * Gets a card at the specified index
	 * 
	 * @param cardIndex
	 *            the index to get the card at
	 * @return the card, if it exists (null otherwise)
	 */
	public Card getCard(int cardIndex)
	{
		Card out = null;
		try
		{
			out = cards.get(cardIndex);
		} catch (IndexOutOfBoundsException e)
		{
			System.out.println("Invalid hand index: " + cardIndex);
		}
		return out;
	}

	/**
	 * Sorts the hand by color, then value
	 */
	public void sortByColor()
	{
		CardColorComparator colorComp = new CardColorComparator();
		Collections.sort(cards, colorComp);
	}

	/**
	 * Sorts the hand by value, then color
	 */
	public void sortByValue()
	{
		CardValueComparator valueComp = new CardValueComparator();
		Collections.sort(cards, valueComp);
	}

	// For testing
	/*public static void main(String[] args)
	{
		Hand h = new Hand();
		h.addCard(new Card(0, 1));
		h.addCard(new Card(0, 1));
		h.addCard(new Card(1, 1));
		h.addCard(new Card(2, 6));
		h.addCard(new Card(2, 8));
		h.addCard(new Card(3, 2));
		h.sortByColor();
		for (int i = 0; i < h.getNumberOfCards(); i++)
		{
			System.out.println(h.getCard(i));
		}
	}*/

}
