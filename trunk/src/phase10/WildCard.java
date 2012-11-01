/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10;

/**
 * This class represents the wild card
 * @author Evan Forbes
 *
 */
public class WildCard extends Card {

	private static final long serialVersionUID = 20121L;
	private int hiddenValue;
	
	WildCard(int c, int v) {
		super(c, v);
		hiddenValue=-1;
	}

	/**
	 * @return the hiddenValue
	 */
	int getHiddenValue() {
		return hiddenValue;
	}

	/**
	 * @param hiddenValue the hiddenValue to set
	 */
	void setHiddenValue(int hiddenValue) {
		this.hiddenValue = hiddenValue;
	}

}
