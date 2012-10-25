/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */

package phase10;

import phase10.gui.GuiManager;

/**
 * This class manages the back end functions such as save/load/new game
 * 
 * @author Evan Forbes
 */
public class GameManager {

	private static Phase10 game;
	private static GuiManager gui;

	public static void main(String[] args) {
		newGame();
		//TODO call gui method(s)
	}

	public static void newGame() {
		game = new Phase10();
	}

	public static Phase10 getGame() {
		return game;
	}

	public static boolean loadGame(String fileName) {
		// TODO not yet implemented
		return false;
	}

	public static void saveGame(String fileName) {
		// TODO not yet implemented
	}

}
