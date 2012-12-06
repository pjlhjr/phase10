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


	/*
	 * begin constructors
	 */
	public GuiManager(GameManager m) {
		super();
		mainManager = m; //passes a reference from the game manager into the GUI manager
		gameLang = new Language();
		settingsWindow = new SettingsFrame(this);
		siWindow = new SkipInputFrame();
		welcomeWindow = new WelcomeFrame(this);
	}
	/*
	 * end constructors
	 */
	
	public GameFrame getGameFrame() {
		return gameWindow;
	}
	
	
	public Language getGameLang() {
		return gameLang;
	}
	
	public void setGameLang(Language l) {
		gameLang = l;
	}
	
	void initGameWindow() {
		gameWindow = new GameFrame(this);
		displayGameFrame();
	}


	/*
	 * Begin display methods
	 */
	void displaySettingsFrame() {
		settingsWindow.setVisible(true);
	}

	void displayGameFrame() {
		gameWindow.setVisible(true);
	}
	
	void displayScoreFrame() {
		ScoreFrame scoreWindow = new ScoreFrame(this);
		scoreWindow.setVisible(true);
	}
	
	void displayMessageFrame(String message, String title) {
		MessageFrame messageWindow = new MessageFrame(message, title, getGameLang());
		messageWindow.setVisible(true);
	}
	
	void displayPhaseDescriptionFrame() {
		pdWindow = new PhaseDescriptionFrame(this);
		pdWindow.setVisible(true);
	}
	
	void displaySkipInputFrame() {
		siWindow.setVisible(true);
	}

	/*
	 * End display methods
	 */
	
	
	/*
	 * begin GUI functional methods
	 */
	public void initGui() {
		displayWelcomeWindow();
	}
	
	
	private void displayWelcomeWindow() {
		welcomeWindow.setVisible(true);
	}

	/**
	 * updates the necessary frames for the next player's turn
	 */
	public void newTurnWindowUpdate() {
		gameWindow.updateFrame(mainManager.getGame());
	}
	
	public void endGame(ArrayList<Player> winner) {
		String endMessage;
		
		if(winner.size() == 1)
			endMessage = "Congratulations " + winner.get(0).getName() + "! You Won!";
		else {
			endMessage = "It's a tie! The winners are: ";
			for(Player x : winner)
				endMessage += x.getName() + ", ";
		}
			
		
		displayMessageFrame(endMessage, "End Game");
		
		gameWindow.dispose();
		
		displayScoreFrame();
		
		
	}
	
	/*
	 * end GUI functional methods
	 */


}