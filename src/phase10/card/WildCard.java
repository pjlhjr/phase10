/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10.card;

/**
 * This class represents the wild card
 * @author Evan Forbes
 *
 */
public class WildCard extends Card {

	private static final long serialVersionUID = 20121L;
	private int hiddenValue;
	
	public WildCard(int v) {
		super(v);
		hiddenValue=-1;
	}

	/**
	 * @return the hiddenValue
	 */
	public int getHiddenValue() {
		return hiddenValue;
	}

	/**
	 * @param hiddenValue the hiddenValue to set
	 */
	public void setHiddenValue(int hiddenValue) {
		this.hiddenValue = hiddenValue;
	}

}
