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
	
	private boolean drewFromDiscard;

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

		for (int i = 0; i < pg.length; i++)
			game.getLog().addEntry(
					new LogEntry(game.getRound().getTurnNumber(), this,
							"Attempt to lay down phase group " + pg[i]));

		// cannot lay down phases if player already has
		if (hasLaidDownPhase)
			return false;

		if (!(pg.length == Configuration.getNumberRequired(phase))) {
			game.getLog().addEntry(
					new LogEntry(game.getRound().getTurnNumber(), this,
							"Attempted to lay down an incorrect number of phase groups: "
									+ pg.length));
			return false;
		}

		boolean success = false;

		pg[0].sortByValue();
		
		if (Configuration.getNumberRequired(phase) == 1) {

			PhaseGroup phaseGroup = pg[0];
			int type = Configuration.getTypeRequired(phase, 0);
			int length = Configuration.getLengthRequired(phase, 0);

			if (PhaseGroup.validate(phaseGroup, type, length)) {
				phaseGroup.setLaidDown(type);
				phaseGroups.add(phaseGroup);
				success = true;
			}
		} else {
			pg[1].sortByValue();
			PhaseGroup phaseGroup1 = pg[0];
			PhaseGroup phaseGroup2 = pg[1];
			int typeA = Configuration.getTypeRequired(phase, 0);
			int typeB = Configuration.getTypeRequired(phase, 1);
			int lengthA = Configuration.getLengthRequired(phase, 0);
			int lengthB = Configuration.getLengthRequired(phase, 1);

			if (PhaseGroup.validate(phaseGroup1, typeA, lengthA)
					&& PhaseGroup.validate(phaseGroup2, typeB, lengthB)) {
				phaseGroup1.setLaidDown(typeA);
				phaseGroups.add(phaseGroup1);
				phaseGroup2.setLaidDown(typeB);
				phaseGroups.add(phaseGroup2);
				success = true;
			} else if (PhaseGroup.validate(phaseGroup1, typeB, lengthB)
					&& PhaseGroup.validate(phaseGroup2, typeA, lengthA)) {
				phaseGroup1.setLaidDown(typeB);
				phaseGroups.add(phaseGroup1);
				phaseGroup2.setLaidDown(typeA);
				phaseGroups.add(phaseGroup2);
				success = true;
			}
		}

		if (success) {
			setLaidDownPhase(true);
			game.getLog().addEntry(
					new LogEntry(game.getRound().getTurnNumber(), this,
							"Laid down phase"));
			
			// Move on to the next round if laying down the phase group makes
			// the player run out of cards
			if (getHand().getNumberOfCards() == 0) {
				game.nextRound();
			}
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

	public boolean getHasDrawnCard() {
		return hasDrawnCard;
	}

	/**
	 * String representation of the player
	 */
	public String toString() {
		return name;
	}

	/**
	 * @return the drewFromDiscard
	 */
	public boolean drewFromDiscard() {
		return drewFromDiscard;
	}

	/**
	 * @param drewFromDiscard the drewFromDiscard to set
	 */
	void setDrewFromDiscard(boolean drewFromDiscard) {
		this.drewFromDiscard = drewFromDiscard;
	}
}
