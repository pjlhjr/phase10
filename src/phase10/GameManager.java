/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */

package phase10;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
		// TODO call gui method(s)
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
		try {
			FileInputStream fis = new FileInputStream(fileName);

			ObjectInputStream ois = new ObjectInputStream(fis);

			Object obj = ois.readObject();

			if (obj instanceof Phase10) {
				game = (Phase10) obj;
				ois.close();
				return true;
			}
			ois.close();
			return false;
		} catch (Exception e) {
			System.out.println("Error loading: " + e.getMessage());
			return false;
		}
	}

	public void saveGame(String fileName) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(game);
		} catch (Exception e) {
			System.out.println("Error saving: " + e.getMessage());
		}
	}

}
