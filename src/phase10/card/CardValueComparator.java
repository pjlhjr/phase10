/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */

package phase10.card;

import java.awt.Color;
import java.util.Comparator;

import phase10.util.Configuration;

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
		int value1 = -1;
		int value2 = -1;
		if (c1 instanceof WildCard) {
			WildCard wc = (WildCard) c1;
			if (!wc.isChangeable()) {
				value1 = wc.getHiddenValue();
			}
		}
		if (c2 instanceof WildCard) {
			WildCard wc = (WildCard) c2;
			if (!wc.isChangeable()) {
				value2 = wc.getHiddenValue();
			}
		}
		if (value1 < 0) {
			value1 = c1.getValue();
		}
		if (value2 < 0) {
			value2 = c2.getValue();
		}
		int comp = value1 - value2;
		if (comp != 0)
			return comp;
		else {
			int colorVal1 = -1;
			int colorVal2 = -1;
			for (int i = 0; i < Configuration.COLORS.length; i++) {
				if (c1.getColor().equals(Configuration.COLORS[i]))
					colorVal1 = i;
				if (c2.getColor().equals(Configuration.COLORS[i]))
					colorVal2 = i;
			}
			//Sort blacks in the rightmost location
			if (c1.getColor().equals(Color.BLACK)) {
				colorVal1 = 5;
			}
			if (c2.getColor().equals(Color.BLACK)) {
				colorVal2 = 5;
			}
			return colorVal1 - colorVal2;
		}
	}
}
