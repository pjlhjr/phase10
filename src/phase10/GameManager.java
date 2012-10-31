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

	private Phase10 game;
	private GuiManager gui;

	public static void main(String[] args) {
		GameManager gm = new GameManager();
		gm.newGame();
		//TODO call gui method(s)
	}

	/**
	 * Create a new Phase10 object
	 */
	public void newGame() {
		game = new Phase10();
	}

	/**
	 * 
	 * @return the current game object
	 */
	public Phase10 getGame() {
		return game;
	}

	
	public boolean loadGame(String fileName) {
		// TODO not yet implemented
		return false;
	}

	public void saveGame(String fileName) {
		// TODO not yet implemented
	}

}
