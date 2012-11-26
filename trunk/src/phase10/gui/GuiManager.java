package phase10.gui;

import phase10.GameManager;
import phase10.Player;



public class GuiManager {
	protected GameManager mainManager;	
	private SettingsFrame settingsWindow;
	private GameFrame gameWindow;
	private PhaseDescriptionFrame pdWindow;
	private SkipInputFrame siWindow;
	private Language gameLang;


	
	//use main method only for testing
	public static void main(String[] args) {
		GameManager thisGame = new GameManager();
		GuiManager guiMgr = new GuiManager(thisGame);
			guiMgr.initGame();
			//guiMgr.displayGameFrame();
	}



	/*
	 * begin constructors
	 */
	public GuiManager(GameManager m) {
		super();
		mainManager = m; //passes a reference from the game manager into the GUI manager
		gameLang = new Language();
		settingsWindow = new SettingsFrame(this);
		pdWindow = new PhaseDescriptionFrame(1, this);
		siWindow = new SkipInputFrame();
	}
	/*
	 * end constructors
	 */
	
	
	public Language getGameLang() {
		return gameLang;
	}


	/*
	 * Begin display methods
	 */
	void displaySettingsFrame() {
		settingsWindow.setVisible(true);
	}

	void displayGameFrame() {
		GameFrame gameWindow = new GameFrame(this);
		gameWindow.setVisible(true);
	}
	
	void displayScoreFrame() {
		ScoreFrame scoreWindow = new ScoreFrame(this);
		scoreWindow.setVisible(true);
	}
	
	void displayMessageFrame(String message, String title) {
		MessageFrame messageWindow = new MessageFrame(message, title);
		messageWindow.setVisible(true);
	}
	
	void displayPhaseDescriptionFrame() {
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
	public void initGame() {

		displaySettingsFrame();
	}
	
	
	private void newTurnWindowUpdate() {
		//TODO - update necessary frames for the next player's turn
		
	}
	
	public void endGame() {
		
		Player winner = mainManager.getGame().getPlayer(1);
		for(int i = 2; i < mainManager.getGame().getNumberOfPlayers(); i++) {
			if(mainManager.getGame().getPlayer(i).getScore() > winner.getScore())
				winner = mainManager.getGame().getPlayer(i);
		}
		
		String endMessage = "Congratulations " + winner.getName() + "! You Won!";
		
		displayMessageFrame(endMessage, "End Game");
		
		displayScoreFrame();
		
	}
	
	/*
	 * end GUI functional methods
	 */


}