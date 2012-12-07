/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10.util;

import java.awt.Color;

/**
 * Utility class that holds static final constants and static methods to retrieve constants
 *
 */
public class Configuration {

	public static final int TIMES_TO_SHUFFLE = 5;
	public static final int NUM_WILDS = 8;
	public static final int NUM_SKIPS = 4;
	public static final int NUM_CARDS_TO_DEAL = 10;

	public static final int WILD_VALUE = 13;
	public static final int SKIP_VALUE = 14;

	public static final int SET_PHASE = 0;
	public static final int RUN_PHASE = 1;
	public static final int COLOR_PHASE = 2;
	
	public static final int LOW_POINTS_VALUE = 5;
	public static final int HIGH_POINTS_VALUE = 10;
	public static final int SKIP_POINTS_VALUE = 15;
	public static final int WILD_POINTS_VALUE = 25;
	
	public static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN,
		Color.YELLOW};

	// Set these to false when not testing
	public static final boolean PRINT_DEBUG_LOG = false;
	public static final boolean SAVE_DEBUG_LOG = false;
	public static final String DEBUG_LOG_FILE = "log.txt";

	// For logging
	public static final String[] COLOR_NAMES = {"Red", "Blue", "Green",
			"Yellow"};

	private static int[][] typePhasesRequired = {
		{SET_PHASE, SET_PHASE},
		{SET_PHASE, RUN_PHASE},
		{SET_PHASE, RUN_PHASE},
		{RUN_PHASE},
		{RUN_PHASE},
		{RUN_PHASE},
		{SET_PHASE, SET_PHASE},
		{COLOR_PHASE},
		{SET_PHASE, SET_PHASE},
		{SET_PHASE, SET_PHASE}
	};

	private static int[][] minLengthOfPhasesRequired = {
		{3, 3},
		{3, 4},
		{4, 4},
		{7},
		{8},
		{9},
		{4, 4},
		{7},
		{5, 2},
		{5, 3}
	};

	/**
	 * Gets the number of phase groups required given the phase
	 * 
	 * @param phase
	 *            the phase
	 * @return the number of phase groups required
	 */
	public static int getNumberRequired(int phase) {
		return typePhasesRequired[phase - 1].length;
	}

	/**
	 * Gets the types of phases required for the given phase
	 * 
	 * @param phase
	 *            the phase
	 * @param groupIndex
	 *            the phasegroup index
	 * @return the type of phases required
	 */
	public static int getTypeRequired(int phase, int groupIndex) {
		return typePhasesRequired[phase - 1][groupIndex];
	}

	/**
	 * Get the lengths required for each phase group for the given phase
	 * 
	 * @param phase
	 *            the phase
	 * @param groupIndex
	 *            the phase group index
	 * @return the required lengths for the each phase group
	 */
	public static int getLengthRequired(int phase, int groupIndex) {
		return minLengthOfPhasesRequired[phase - 1][groupIndex];
	}
}
