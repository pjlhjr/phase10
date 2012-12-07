package phase10.gui;

import java.util.ArrayList;

import phase10.GameManager;
import phase10.Player;



public class GuiManager {
	protected GameManager mainManager;	
	private SettingsFrame settingsWindow;
	private PhaseDescriptionFrame pdWindow;
	private SkipInputFrame siWindow;
	private Language gameLang;
	private GameFrame gameWindow;
	private WelcomeFrame welcomeWindow;


	/**
	 * Constructor for GuiManager. Sets up all of the necessary windows.
	 * 
	 * @param m a reference to a GameManager class
	 */
	public GuiManager(GameManager m) {
		super();
		mainManager = m; //passes a reference from the game manager into the GUI manager
		gameLang = new Language();
		settingsWindow = new SettingsFrame(this);
		siWindow = new SkipInputFrame();
		welcomeWindow = new WelcomeFrame(this);
	}
	
	/**
	 * Returns a reference to GameFrame
	 * 
	 * @return the GameFrame object
	 */
	public GameFrame getGameFrame() {
		return gameWindow;
	}
	
	/**
	 * 
	 * Returns the game's language
	 * 
	 * @return the current language of the game
	 */
	public Language getGameLang() {
		return gameLang;
	}
	
	/**
	 * Initializes and displays the game window
	 */
	void initGameWindow() {
		gameWindow = new GameFrame(this);
		displayGameFrame();
	}


	/*
	 * Begin display methods
	 */
	
	/**
	 * Displays the Settings Frame
	 */
	void displaySettingsFrame() {
		settingsWindow.setVisible(true);
	}

	/**
	 * Displays the Game Frame
	 */
	void displayGameFrame() {
		gameWindow.setVisible(true);
	}
	
	/**
	 * Displays the Score Frame
	 */
	void displayScoreFrame() {
		ScoreFrame scoreWindow = new ScoreFrame(this);
		scoreWindow.setVisible(true);
	}
	
	/**
	 * Displays a message frame
	 * 
	 * @param message the message to be displayed in the message frame
	 * @param title the title the message frame will have
	 */
	void displayMessageFrame(String message, String title) {
		MessageFrame messageWindow = new MessageFrame(message, title, getGameLang());
		messageWindow.setVisible(true);
	}
	
	/**
	 * Displays the Phase DescriptionFrame
	 */
	void displayPhaseDescriptionFrame() {
		pdWindow = new PhaseDescriptionFrame(this);
		pdWindow.setVisible(true);
	}
	
	/**
	 * Displays the Skip Input Frame
	 */
	void displaySkipInputFrame() {
		siWindow.setVisible(true);
	}

	/*
	 * End display methods
	 */
	
	
	/*
	 * begin GUI functional methods
	 */
	
	/**
	 * Initializes the GUI component of the game by displaying the welcome window
	 */
	public void initGui() {
		displayWelcomeWindow();
	}
	
	/**
	 * displays the welcome window
	 */
	private void displayWelcomeWindow() {
		welcomeWindow.setVisible(true);
	}

	/**
	 * updates the necessary frames for the next player's turn
	 */
	public void newTurnWindowUpdate() {
		gameWindow.updateFrame(mainManager.getGame());
	}
	
	/**
	 * Performs GUI operations that occur at the end of the game
	 * 
	 * @param an arrayList of all the players who played the game
	 */
	public void endGame(ArrayList<Player> players) {
		String endMessage;
		
		if(players.size() == 1)
			endMessage = "Congratulations " + players.get(0).getName() + "! You Won!";
		else {
			endMessage = "It's a tie! The winners are: ";
			for(Player x : players)
				endMessage += x.getName() + ", ";
		}
			
		displayWelcomeWindow();
		gameWindow.dispose();
		displayScoreFrame();
		displayMessageFrame(endMessage, "End Game");
	}
	
	/*
	 * end GUI functional methods
	 */
}