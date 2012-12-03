/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10;

import java.io.Serializable;
import java.util.ArrayList;

import phase10.util.Configuration;
import phase10.util.LogEntry;

/**
 * This class contains all of the information for each player for the Phase 10
 * game.
 * 
 * @author Evan Forbes
 * 
 */
public class Player implements Serializable {

	private static final long serialVersionUID = 20121L;

	private String name;
	private int score;
	private Hand hand;
	private int phase;
	private boolean hasLaidDownPhase;
	private boolean skipNextTurn;
	private ArrayList<PhaseGroup> phaseGroups;

	private boolean hasDrawnCard;

	protected Phase10 game;

	/**
	 * Creates the default player object with no name
	 */
	public Player(Phase10 g) {
		this(g, "[Player]");
	}

	/**
	 * Creates the player object with the given name
	 * 
	 * @param n
	 *            the player's name
	 */
	public Player(Phase10 g, String n) {
		name = n;
		score = 0;
		phase = 1;
		game = g;
		hand = new Hand(g, this);
		phaseGroups = new ArrayList<PhaseGroup>();
		skipNextTurn = false;
		hasLaidDownPhase = false;
		hasDrawnCard = false;
	}

	/**
	 * @return the player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param n
	 *            the name to set
	 */
	public void setName(String n) {
		name = n;
	}

	/**
	 * @return the player's score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param points
	 *            the points to add to this player's score
	 */
	void addToScore(int points) {
		score += points;
	}

	/**
	 * @return the phase the player is currently on
	 */
	public int getPhase() {
		return phase;
	}

	/**
	 * Increment the player's phase by 1
	 */
	void incrementPhase() {
		phase++;
	}

	/**
	 * @return the player's hand
	 */
	public Hand getHand() {
		return hand;
	}

	/**
	 * Add the phase group(s) to this player's phase groups.
	 * 
	 * @param pg
	 *            the phase group(s)
	 * @return true if valid to add, otherwise false.
	 */
	public boolean addPhaseGroups(PhaseGroup... pg) {

		for (int i = 0; i < Configuration.getNumberOfPhaseGroupsRequired(phase); i++)
			game.getLog().addEntry(
					new LogEntry(game.getRound().getTurnNumber(), this,
							"Attempt to lay down phase group " + pg[i]));

		// cannot lay down phases if player already has
		if (hasLaidDownPhase)
			return false;

		if (!(pg.length == Configuration.getNumberOfPhaseGroupsRequired(phase)))
			return false;

		boolean success = false;
		switch (phase) {
			case 1 :
				if (PhaseGroup.validate(pg[0], 0, 3)
						&& PhaseGroup.validate(pg[1], 0, 3)) {
					pg[0].setLaidDown(0);
					phaseGroups.add(pg[0]);
					pg[1].setLaidDown(0);
					phaseGroups.add(pg[1]);
					success = true;
				}
				break;
			case 2 :
				if (PhaseGroup.validate(pg[0], 1, 3)
						&& PhaseGroup.validate(pg[1], 0, 4)) {
					pg[0].setLaidDown(1);
					phaseGroups.add(pg[0]);
					pg[1].setLaidDown(0);
					phaseGroups.add(pg[1]);
					success = true;
				} else if (PhaseGroup.validate(pg[1], 1, 3)
						&& PhaseGroup.validate(pg[0], 0, 4)) {
					pg[0].setLaidDown(0);
					phaseGroups.add(pg[0]);
					pg[1].setLaidDown(1);
					phaseGroups.add(pg[1]);
					success = true;
				}
				break;
			case 3 :
				if (PhaseGroup.validate(pg[0], 1, 4)
						&& PhaseGroup.validate(pg[1], 0, 4)) {
					pg[0].setLaidDown(1);
					phaseGroups.add(pg[0]);
					pg[1].setLaidDown(0);
					phaseGroups.add(pg[1]);
					success = true;
				} else if (PhaseGroup.validate(pg[1], 1, 4)
						&& PhaseGroup.validate(pg[0], 0, 4)) {
					pg[0].setLaidDown(0);
					phaseGroups.add(pg[0]);
					pg[1].setLaidDown(1);
					phaseGroups.add(pg[1]);
					success = true;
				}
				break;
			case 4 :
				if (PhaseGroup.validate(pg[0], 1, 7)) {
					pg[0].setLaidDown(1);
					phaseGroups.add(pg[0]);
					success = true;
				}
				break;
			case 5 :
				if (PhaseGroup.validate(pg[0], 1, 8)) {
					pg[0].setLaidDown(1);
					phaseGroups.add(pg[0]);
					success = true;
				}
				break;
			case 6 :
				if (PhaseGroup.validate(pg[0], 1, 9)) {
					pg[0].setLaidDown(1);
					phaseGroups.add(pg[0]);
					success = true;
				}
				break;
			case 7 :
				if (PhaseGroup.validate(pg[0], 0, 4)
						&& PhaseGroup.validate(pg[1], 0, 4)) {
					pg[0].setLaidDown(0);
					phaseGroups.add(pg[0]);
					pg[1].setLaidDown(0);
					phaseGroups.add(pg[1]);
					success = true;
				}
				break;
			case 8 :
				if (PhaseGroup.validate(pg[0], 2, 7)) {
					pg[0].setLaidDown(2);
					phaseGroups.add(pg[0]);
					success = true;
				}
				break;
			case 9 :
				if (PhaseGroup.validate(pg[0], 0, 5)
						&& PhaseGroup.validate(pg[1], 0, 2)) {
					pg[0].setLaidDown(0);
					phaseGroups.add(pg[0]);
					pg[1].setLaidDown(0);
					phaseGroups.add(pg[1]);
					success = true;
				} else if (PhaseGroup.validate(pg[1], 0, 5)
						&& PhaseGroup.validate(pg[0], 0, 2)) {
					pg[0].setLaidDown(0);
					phaseGroups.add(pg[0]);
					pg[1].setLaidDown(0);
					phaseGroups.add(pg[1]);
					success = true;
				}
				break;
			case 10 :
				if (PhaseGroup.validate(pg[0], 0, 5)
						&& PhaseGroup.validate(pg[1], 0, 3)) {
					pg[0].setLaidDown(0);
					phaseGroups.add(pg[0]);
					pg[1].setLaidDown(0);
					phaseGroups.add(pg[1]);
					success = true;
				} else if (PhaseGroup.validate(pg[1], 0, 5)
						&& PhaseGroup.validate(pg[0], 0, 3)) {
					pg[0].setLaidDown(0);
					phaseGroups.add(pg[0]);
					pg[1].setLaidDown(0);
					phaseGroups.add(pg[1]);
					success = true;
				}
				break;
		}
		if (success) {
			setLaidDownPhase(true);
			game.getLog().addEntry(
					new LogEntry(game.getRound().getTurnNumber(), this,
							"Laid down phase"));
			return true;
		} else
			return false;
	}

	/**
	 * Gets the number of phasegroups this person has laid down
	 * 
	 * @return the number of laid down phase groups
	 */
	public int getNumberOfPhaseGroups() {
		return phaseGroups.size();
	}

	/**
	 * Gets the phasegroup at the given index
	 * 
	 * @param index
	 *            the index of the phasegroup to get
	 * @return the phase group
	 */
	public PhaseGroup getPhaseGroup(int index) {
		return phaseGroups.get(index);
	}

	void setSkip(boolean state) {
		skipNextTurn = state;
	}

	/**
	 * 
	 * @return whether this player's next turn is to be skipped
	 */
	public boolean getSkip() {
		return skipNextTurn;
	}

	/**
	 * 
	 * @return whether this player has laid down a phase during the current
	 *         round
	 */
	public boolean hasLaidDownPhase() {
		return hasLaidDownPhase;
	}

	void setLaidDownPhase(boolean state) {
		hasLaidDownPhase = state;
	}

	void removePhaseGroup(int pg) {
		phaseGroups.remove(pg);
	}

	void setHasDrawnCard(boolean state) {
		hasDrawnCard = state;
	}

	boolean getHasDrawnCard() {
		return hasDrawnCard;
	}

	/**
	 * String representation of the player
	 */
	public String toString() {
		return name;
	}
}
