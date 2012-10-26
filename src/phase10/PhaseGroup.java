/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class contains all of the information for each phase group that is laid
 * down in a round.
 * 
 * @author Evan Forbes
 * 
 */
public class PhaseGroup implements Serializable {
	
	private static final long serialVersionUID = 20121L;
	
	ArrayList<Card> cards;
	int type; //0:set, 1:run, 2:same color 
	
	public PhaseGroup(int t) {
		cards = new ArrayList<Card>();
		type = t;
	}

	/**
	 * @param c The card to add to this phase group
	 * @return true if valid, false if the card does not fit in the phase group
	 */
	public boolean addCard(Card c) {
		cards.add(c);
		//TODO: check to make sure the card matches the right type
		return true;
	}

	/**
	 * @param index
	 * @return the card at the index
	 */
	public Card getCard(int index) {
		return cards.get(index);
	}

	/**
	 * @return the size of the hand
	 */
	public int getNumberOfCards() {
		return cards.size();
	}
	
	
}
