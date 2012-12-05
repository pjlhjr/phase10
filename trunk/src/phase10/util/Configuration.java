package phase10.util;

import java.awt.Color;

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

	// Set these to false when not testing
	public static final boolean PRINT_LOG = true;
	public static final boolean SAVE_LOG = true;
	public static final String LOG_FILE = "log.txt";

	public static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN,
			Color.YELLOW};

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
