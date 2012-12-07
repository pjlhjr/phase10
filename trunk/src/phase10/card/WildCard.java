/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10.card;

import phase10.util.Configuration;

/**
 * This class represents the wild card
 * @author Evan Forbes
 *
 */
public class WildCard extends Card {

	private static final long serialVersionUID = 20121L;
	private int hiddenValue;
	private boolean changeable;
	
	public WildCard() {
		super(Configuration.WILD_VALUE);
		hiddenValue=-1;
		changeable = true;
	}

	/**
	 * @return the hidden value
	 */
	public int getHiddenValue() {
		return hiddenValue;
	}

	/**
	 * @param hidden value the hiddenValue to set
	 */
	public void setHiddenValue(int hiddenValue) {
		this.hiddenValue = hiddenValue;
	}

	/**
	 * @return the changeable
	 */
	public boolean isChangeable() {
		return changeable;
	}

	/**
	 * @param changeable the changeable to set
	 */
	public void setChangeable(boolean changeable) {
		this.changeable = changeable;
	}
	
	/**
	 * @return string representation of a wild card
	 */
	public String toString() {
		return "Wild ("+hiddenValue+")";
	}
	

}
