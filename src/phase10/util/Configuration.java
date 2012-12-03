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

	public static final boolean PRINT_LOG = true;
	public static final boolean SAVE_LOG = true;
	public static final String LOG_FILE = "log.txt";

	public static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN,
			Color.YELLOW};

	public static final String[] COLOR_NAMES = {"Red", "Blue", "Green",
			"Yellow"};

	private static int[] numPhasesExpected = {2, 2, 2, 1, 1, 1, 2, 1, 2, 2};

	/**
	 * Gets the number of phase groups required given the phase
	 * 
	 * @param phase
	 *            the phase
	 * @return the number of phase groups required
	 */
	public static int getNumberOfPhaseGroupsRequired(int phase) {
		return numPhasesExpected[phase - 1];
	}
}
