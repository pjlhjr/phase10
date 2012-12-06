/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import phase10.card.Card;
import phase10.card.CardValueComparator;
import phase10.card.WildCard;
import phase10.exceptions.Phase10Exception;
import phase10.util.Configuration;
import phase10.util.LogEntry;

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
	private int type;
	private boolean laidDown;

	private Phase10 game;

	/**
	 * Creates a phase group object
	 * 
	 * @param g
	 *            the Phase10 object this phase group belongs to
	 */
	public PhaseGroup(Phase10 g) {
		cards = new ArrayList<Card>();
		laidDown = false;
		type = -1;
		game = g;
	}

	private boolean addCard(Card c, boolean beginning) {
		if (!laidDown) {
			if (!beginning) {
				cards.add(c);
			} else {
				cards.add(0, c);
			}
			return true;
		} else {
			Player p = game.getCurrentPlayer();

			// cannot add to a phase unless you have laid one down this round
			if (!p.hasLaidDownPhase()) {
				return false;
			}
			PhaseGroup temp = new PhaseGroup(game);
			for (int i = 0; i < getNumberOfCards(); i++) {
				temp.addCard(getCard(i));
			}
			game.getLog().addEntry(
					new LogEntry(game.getRound().getTurnNumber(), p,
							"Attempting to add card (" + c
									+ ") to a laid down phase group: " + temp));

			temp.addCard(c, beginning);

			if (PhaseGroup.validate(temp, type, 0)) {
				cards.add(c);

				if (c.getValue() == Configuration.WILD_VALUE) {
					WildCard wc = (WildCard) c;
					wc.setChangeable(false);
				}
				
				Collections.sort(cards, new CardValueComparator());

				p.getHand().removeCard(c);

				game.getLog().addEntry(
						new LogEntry(game.getRound().getTurnNumber(), p,
								"Added card to a laid down phase group: "
										+ this));

				if (p.getHand().getNumberOfCards() == 0) {
					game.nextRound();
				}

				return true;
			}
			return false;
		}

	}

	/**
	 * This method will add cards to an existing phase group, but has some
	 * interesting variations depending on the context.
	 * 
	 * <br>
	 * - if the phase group has not been marked as laid down (you have not
	 * called the addPhaseGroup method of player), the card will be added to the
	 * phase group (without validation), and will not be taken out of the
	 * player's hand <br>
	 * - when you call the addPhaseGroup method of Player, the phase group(s)
	 * will be validated, and if it is valid the cards will be removed from the
	 * player's hand <br>
	 * - now when the addCard method is called, it will validate it before
	 * adding it to the phase group, and if it is valid, will remove the card
	 * from the player's hand.
	 * 
	 * @param c
	 *            The card to add to this phase group
	 * 
	 * @return true if valid to add to the laid down phase group, or if the
	 *         phase group is not laid down; false if the card does not fit in
	 *         the laid down phase group
	 */
	public boolean addCard(Card c) {
		return addCard(c, false);
	}

	/**
	 * Add a card to the front of a phase group (useful for wilds)
	 * 
	 * @param c
	 *            the card
	 * @return true if it is valid, otherwise false
	 */
	public boolean addCardToBeginning(Card c) {
		return addCard(c, true);

	}

	/**
	 * Removes the card from the phase group
	 * 
	 * @param c
	 *            the card to remove
	 * @throws Phase10Exception
	 *             if the phase is laid down
	 */
	public void removeCard(Card c) {
		if (!laidDown) {
			cards.remove(c);
		} else {
			throw new Phase10Exception(
					"Cannot remove card from laid down phase group");
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
	 * @return The type of phase (0: set, 1: run, 2: all 1 color)
	 */
	public int getType() {
		return type;
	}

	/**
	 * 
	 * @param t
	 *            type of phase
	 */
	void setLaidDown(int t) {
		laidDown = true;
		type = t;
		for (int i = 0; i < cards.size(); i++) {
			game.getCurrentPlayer().getHand().removeCard(cards.get(i));
			if (cards.get(i).getValue() == Configuration.WILD_VALUE) {
				WildCard wc = (WildCard) cards.get(i);
				wc.setChangeable(false);
			}
		}
		Collections.sort(cards, new CardValueComparator());
	}

	boolean getLaidDown() {
		return laidDown;
	}

	/**
	 * Validates the given phase group to match the type (set, run, or
	 * all1Color) and minimum length
	 * 
	 * @param pg
	 *            the PhaseGroup to check
	 * @param type
	 *            The type of phase (0: set, 1: run, 2: all 1 color)
	 * @param minLength
	 *            the minimum length the phase must have
	 * @return true if it is a valid phase group, false otherwise
	 */
	public static boolean validate(PhaseGroup pg, int type, int minLength) {
		// make sure there are enough cards
		if (pg.getNumberOfCards() < minLength)
			return false;
		// skips can't be in a phase
		if (!checkSkips(pg))
			return false;

		if (type == Configuration.SET_PHASE) {
			return validateSet(pg);
		} else if (type == Configuration.RUN_PHASE) {
			return validateRun(pg);
		} else if (type == Configuration.COLOR_PHASE) {
			return validateAllOneColor(pg);
		}

		return false;
	}

	private static boolean checkSkips(PhaseGroup pg) {
		for (int i = 0; i < pg.getNumberOfCards(); i++) {
			if (pg.getCard(i).getValue() == Configuration.SKIP_VALUE)
				return false;
		}
		return true;
	}

	private static boolean validateRun(PhaseGroup pg) {
		ArrayList<Integer> values = new ArrayList<Integer>(
				pg.getNumberOfCards());
		int min = pg.getCard(0).getValue();
		int numWilds = 0;
		ArrayList<WildCard> wilds = new ArrayList<WildCard>();

		boolean setFirstAsWild = false;

		for (int i = 0; i < pg.getNumberOfCards(); i++) {
			int curValue = pg.getCard(i).getValue();
			if (curValue == Configuration.WILD_VALUE) {
				WildCard curWild = (WildCard) pg.getCard(i);
				if (curWild.getHiddenValue() < 0 || curWild.isChangeable()) {
//					System.out.println("adding wild");
					numWilds++;
					wilds.add(curWild);
					if (i == 0) {
						setFirstAsWild = true;
					}
				} else {
					values.add(curWild.getHiddenValue());
//					System.out.println("adding hidden value wild "
//							+ curWild.getHiddenValue());
				}
			} else {
				values.add(curValue);
			}
			if (curValue < min)
				min = curValue;
		}

		if (min + values.size() > 12 && numWilds > 0) {
			setFirstAsWild = true;
		}

		if (setFirstAsWild) {
			min--;
//			System.out.println("setting first as wild: "+ (min));
			numWilds--;
			wilds.get(0).setHiddenValue(min);
			wilds.remove(0);
			values.add(min);
			if (min <= 0)
				return false;
		}

		int curValue = min;
		while (!values.isEmpty() || numWilds > 0) {
			boolean found = false;
			for (int i = 0; i < values.size(); i++) {
				if (values.get(i) == curValue) {
//					System.out.println("found " + curValue);
					values.remove(i);
					found = true;
					break;
				}
			}
			if (!found && numWilds > 0) {
				numWilds--;
				wilds.get(numWilds).setHiddenValue(curValue);
//				System.out.println("using a wild for " + curValue);
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

			if (curValue != Configuration.WILD_VALUE) {
				if (valueToMatch < 0) {
					valueToMatch = curValue;
				} else if (valueToMatch != curValue) {
					return false;
				}
			} else {
				WildCard wc = (WildCard) pg.getCard(i);
				wc.setHiddenValue(valueToMatch);
			}
		}
		return true;
	}

	private static boolean validateAllOneColor(PhaseGroup pg) {
		Color valueToMatch = Color.white;
		for (int i = 0; i < pg.getNumberOfCards(); i++) {
			Color curValue = pg.getCard(i).getColor();

			if (pg.getCard(i).getValue() != Configuration.WILD_VALUE) {
				if (valueToMatch.equals(Color.white)) {
					valueToMatch = curValue;
				} else if (valueToMatch != curValue) {
					return false;
				}
			}
		}
		return true;
	}

	public void setType(int type) {
		if (laidDown) {
			throw new Phase10Exception("Cannot change type: already laid down");
		} else {
			this.type = type;
		}
	}

	public String toString() {
		StringBuilder out = new StringBuilder();
		for (Card e : cards) {
			out.append(e + ", ");
		}

		return out.length() > 3 ? out.substring(0, out.length() - 2) : out
				.toString();
	}

//	public static void main(String[] args) {
//		PhaseGroup pg = new PhaseGroup(null);
//
//		pg.addCard(new Card(Configuration.COLORS[0], 3));
//		pg.addCard(new Card(Configuration.COLORS[0], 2));
//		pg.addCard(new Card(Configuration.COLORS[0], 4));
//		WildCard wc = new WildCard();
//		pg.addCard(wc);
//		System.out.println(wc.getHiddenValue());
//		System.out.println(PhaseGroup.validate(pg, Configuration.RUN_PHASE, 1));
//		System.out.println(wc.getHiddenValue());
//
//		pg.laidDown = true;
//		pg.type = Configuration.RUN_PHASE;
//
//		System.out.println(pg.addCard(new Card(Configuration.COLORS[0], 6)));
//		System.out.println(wc.getHiddenValue());
//	}
}
