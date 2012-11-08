/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */

package phase10.card;

import java.util.Comparator;

/**
 * This is used to compare cards to each other and determine the order they
 * should be in. First sorting by value, then color.
 * 
 * @author Evan Forbes
 * 
 */
public class CardValueComparator implements Comparator<Card> {

	/**
	 * This method will compare the two cards by value, then by color if the
	 * colors are equal
	 */
	@Override
	public int compare(Card c1, Card c2) {
		int comp = c1.getValue() - c2.getValue();
		if (comp != 0)
			return comp;
		return c1.getColor() - c2.getColor();
	}

}
