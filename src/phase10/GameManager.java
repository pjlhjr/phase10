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
		new GameManager();
	}

	GameManager() {
		gui = new GuiManager(this);
		newGame();
		gui.initGame();
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

	/**
	 * Loads a game
	 * @param fileName the file path to the save file
	 * @return true if the load was successful, otherwise false
	 */
	public boolean loadGame(String fileName) {
		if (!fileName.substring(fileName.length()-4).equals(".p10")){
			fileName = fileName + ".p10";
		}
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

	/**
	 * Saves the current game
	 * @param fileName the path to save to (should be *.dat)
	 * @return true if the save was successful, otherwise false
	 */
	public boolean saveGame(String fileName) {
		if (!fileName.substring(fileName.length()-4).equals(".p10")){
			fileName = fileName + ".p10";
		}
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(game);
			
			oos.close();
			return true;
		} catch (Exception e) {
			System.out.println("Error saving: " + e.getMessage());
			return false;
		}
	}

}
