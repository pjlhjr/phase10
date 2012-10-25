/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10;

import java.io.Serializable;
import java.util.ArrayList;

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

	/**
	 * Creates the default player object with no name
	 */
	public Player() {
		name = "";
		score = 0;
		phase = 1;
		hand = new Hand();
		phaseGroups = new ArrayList<PhaseGroup>();
		skipNextTurn = false;
		hasLaidDownPhase = false;
	}

	/**
	 * Creates the player object with the given name
	 * 
	 * @param n
	 *            the player's name
	 */
	public Player(String n) {
		name = n;
		score = 0;
		phase = 1;
		hand = new Hand();
		phaseGroups = new ArrayList<PhaseGroup>();
		skipNextTurn = false;
		hasLaidDownPhase = false;
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
	public void addToScore(int points) {
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

	public boolean addPhaseGroups(PhaseGroup... pg) {
		//phaseGroups.add(pg);
		// TODO: not yet implemented- need to check validity
		return true;

	}

	public int getNumberOfPhaseGroups() {
		return phaseGroups.size();
	}

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
}
