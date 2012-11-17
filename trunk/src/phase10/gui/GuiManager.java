package phase10.gui;

import phase10.GameManager;



public class GuiManager {
	protected GameManager mainManager;	
	private SettingsFrame settingsWindow;
	private GameFrame gameWindow;
	private ScoreFrame scoreWindow;
	private PhaseDescriptionFrame pdWindow;
	private SkipInputFrame siWindow;
	private Language gameLang;


	
	//use main method only for testing
	public static void main(String[] args) {
		GuiManager guiMgr = new GuiManager(new GameManager());

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
	public void displaySettingsFrame() {
		settingsWindow.setVisible(true);
	}

	public void displayGameFrame() {
		gameWindow.setVisible(true);
	}
	
	public void displayScoreFrame() {
		scoreWindow.setVisible(true);
	}
	
	public void displayInvalidMoveFrame() {} //still need to implement this frame
	
	public void displayPhaseDescriptionFrame() {
		pdWindow.setVisible(true);
	}
	
	public void displaySkipInputFrame() {
		siWindow.setVisible(true);
	}

	/*
	 * End display methods
	 */
	
	
	/*
	 * begin update methods
	 * 
	 * (might place these methods in their respective classes)
	 */
	
//	private void updateGameFrame() {
//		gameWindow.updateFrame(GameManager.getGame().getPlayer(index));
//	}
	private void updateSettingsFrame() {}
	private void updatePhaseDescriptionFrame() {}
	private void updateSkipInputFrame() {}
	private void updateInvalidMoveFrame() {}
	protected void updateLanguage(Language l) {
		this.gameLang = l;
	}
	
	/*
	 * end update methods
	 */
	
	/*
	 * begin GUI functional methods
	 */
	public void initGame() {

		displaySettingsFrame();
		gameWindow = new GameFrame(this);
		scoreWindow = new ScoreFrame();
		pdWindow = new PhaseDescriptionFrame(1, this);
		siWindow = new SkipInputFrame();
	}
	
	
	public void newTurnWindowUpdate() {
		//TODO - update necessary frames for the next player's turn
	}
	
	public void endGame() {}
	
	/*
	 * end GUI functional methods
	 */


}