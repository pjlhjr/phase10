/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */

package phase10;


/**
 * This class manages the back end functions such as save/load/new game
 * 
 * @author Evan Forbes
 */
public class GameManager {
	
	private Phase10 game;
	//TODO private Language language;
	//TODO private GuiManager gui;
	
	public void newGame()
	{
		game = new Phase10();
	}
	
	public Phase10 getGame()
	{
		return game;
	}
	
	public boolean loadGame(String fileName)
	{
		//TODO not yet implemented
		return false;
	}
	
	public void saveGame(String fileName)
	{
		//TODO not yet implemented
	}
	
	
}
