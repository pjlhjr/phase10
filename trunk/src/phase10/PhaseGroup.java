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
public final class PhaseGroup implements Serializable {

	private static final long serialVersionUID = 20121L;

	private ArrayList<Card> cards;
	private int type; // 0:set, 1:run, 2:same color
	private boolean laidDown;

	public PhaseGroup() {
		cards = new ArrayList<Card>();
		laidDown = false;
		type = -1;
	}

	/**
	 * @param c
	 *            The card to add to this phase group
	 * @return true if valid, false if the card does not fit in the phase group
	 */
	public boolean addCard(Card c) {
		if (!laidDown) {
			cards.add(c);
			return true;
		} else {
			PhaseGroup temp = new PhaseGroup();
			for (int i = 0; i < getNumberOfCards(); i++) {
				temp.addCard(getCard(i));
			}
			temp.addCard(c);
			if (PhaseGroup.validate(temp, type, 1)) {
				cards.add(c);
				return true;
			}
			return false;
		}

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

	/**
	 * 
	 * @param t
	 *            type of phase
	 */
	void setLaidDown(int t) {
		laidDown = true;
		type = t;
	}

	boolean getLaidDown() {
		return laidDown;
	}

	/**
	 * Validates the given phase group to match the type (set, run, or all1Color) and minimum length
	 * @param pg the PhaseGroup to check
	 * @param type The type of phase (0: set, 1: run, 2: all 1 color)
	 * @param minLength the minimum length the phase must have
	 * @return true if it is a valid phase group, false otherwise
	 */
	public static boolean validate(PhaseGroup pg, int type, int minLength) {
		// make sure there are enough cards
		if (pg.getNumberOfCards() < minLength)
			return false;
		// skips can't be in a phase
		if (!checkSkips(pg))
			return false;

		if (type == 0) {
			return validateSet(pg);
		} else if (type == 1) {
			return validateRun(pg);
		} else if (type == 2) {
			return validateAllOneColor(pg);
		}

		return false;
	}

	private static boolean checkSkips(PhaseGroup pg) {
		for (int i = 0; i < pg.getNumberOfCards(); i++) {
			if (pg.getCard(i).getValue() == Card.SKIP_VALUE)
				return false;
		}
		return true;
	}

	private static boolean validateRun(PhaseGroup pg) {
		// System.out.println("Checking run");
		ArrayList<Integer> values = new ArrayList<Integer>(
				pg.getNumberOfCards());
		int min = pg.getCard(0).getValue();
		int numWilds = 0;
		for (int i = 0; i < pg.getNumberOfCards(); i++) {
			int curValue = pg.getCard(i).getValue();
			if (curValue == Card.WILD_VALUE) {
				WildCard curWild = (WildCard) pg.getCard(i);
				if (curWild.getHiddenValue() < 0)
					numWilds++;
				else
					values.add(curWild.getHiddenValue());
			} else {
				values.add(curValue);
			}
			if (curValue < min)
				min = curValue;
		}
		System.out.println("numWilds: " + numWilds + " min: " + min);
		int curValue = min;
		while (!values.isEmpty()) {
			boolean found = false;
			for (int i = 0; i < values.size(); i++) {
				if (values.get(i) == curValue) {
					System.out.println("Removing " + values.get(i) + " at: "
							+ i);
					values.remove(i);
					found = true;
				}
			}
			if (!found && numWilds > 0) {
				numWilds--;
			} else if (!found && numWilds == 0) {
				return false;
			}
			curValue++;

		}
		return true;
	}

	private static boolean validateSet(PhaseGroup pg) {
		int valueToMatch = -1;
		for (int i = 0; i < pg.getNumberOfCards(); i++) {
			int curValue = pg.getCard(i).getValue();

			if (curValue != Card.WILD_VALUE) {
				if (valueToMatch < 0) {
					valueToMatch = curValue;
				} else if (valueToMatch != curValue) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean validateAllOneColor(PhaseGroup pg) {
		int valueToMatch = -1;
		for (int i = 0; i < pg.getNumberOfCards(); i++) {
			int curValue = pg.getCard(i).getColor();

			if (pg.getCard(i).getValue() != Card.WILD_VALUE) {
				if (valueToMatch < 0) {
					valueToMatch = curValue;
				} else if (valueToMatch != curValue) {
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * public static void main(String[] args){ PhaseGroup pg = new PhaseGroup();
	 * pg.addCard(new Card(1,4)); pg.addCard(new Card(1,4)); pg.addCard(new
	 * Card(2,4)); pg.addCard(new Card(3,4));
	 * System.out.println(PhaseGroup.validate(pg, 0, 4)); }
	 */
}
