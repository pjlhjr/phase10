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
 * should be in. First sorting by color, then value.
 * 
 * @author Evan Forbes
 * 
 */
public class CardColorComparator implements Comparator<Card> {

	/**
	 * This method will compare the two cards by color, then by value if the
	 * colors are equal
	 */
	@Override
	public int compare(Card c1, Card c2) {
		int val1=-1;
		int val2=-1;
		for (int i=0;i<Configuration.COLORS.length;i++){
			if (c1.getColor().equals(Configuration.COLORS[i])) val1 = i;
			if (c2.getColor().equals(Configuration.COLORS[i])) val2 = i;
		}
		if (c1.getColor().equals(Color.BLACK)){
			val1 = 5;
		}
		if (c2.getColor().equals(Color.BLACK)){
			val2 = 5;
		}
		int comp = val1-val2;
		if (comp != 0)
			return comp;
		else
			return c1.getValue() - c2.getValue();
		
	}

}
