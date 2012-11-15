/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10.card;

import java.awt.Color;
import java.io.Serializable;

import phase10.Configuration;

/**
 * This class contains all of the information for each playing card for the
 * Phase 10 game.
 * 
 * @author Evan Forbes
 * 
 */
public class Card implements Serializable {

	private static final long serialVersionUID = 20121L;
	
	private Color color;
	private int value;

	/**
	 * 
	 * @param c
	 *            the color (0 through 3)
	 * @param v
	 *            the value (1 through 12, wild = 13, skip = 14)
	 */
	public Card(Color c, int v) {
		color = c;
		value = v;
	}
	
	/**
	 * 
	 * @param v
	 *            the value (1 through 12, wild = 13, skip = 14)
	 */
	public Card(int v) {
		this(Color.BLACK, v);
	}
	
	
	/**
	 * @return the color
	 */
	public final Color getColor() {
		return color;
	}

	/**
	 * @return the value
	 */
	public final int getValue() {
		return value;
	}

	/**
	 * Checks to see if two cards contain the same information
	 * 
	 * @param other
	 *            the card to be compared to
	 * @return true if they have the same color and value, otherwise false
	 */
	public boolean equals(Card other) {
		return (color == other.getColor() && value == other.getValue());
	}

	/**
	 * Gives the points value of this card when it is remaining in your hand at
	 * the end of a round
	 * 
	 * @return the points value
	 */
	public final int getPointValue() {
		if (value >= 1 && value <= 9)
			return 5;
		if (value >= 10 && value <= 12)
			return 10;
		if (value == Configuration.SKIP_VALUE)
			return 15;
		if (value == Configuration.WILD_VALUE)
			return 25;

		return 0;
	}

	/**
	 * 
	 * @return string representation of the card
	 */
	public String toString() {
		return getColor() + "." + getValue();
	}

}
