package phase10.gui;

import phase10.*;

public class GuiManager {

	private SettingsFrame settingsWindow;
	private GameFrame gameWindow;
	private ScoreFrame scoreWindow;
	private PhaseDescriptionFrame pdWindow;
	private SkipInputFrame siWindow;
	//	public GameManager mainManager;

	
	//use main method only for testing
	public static void main(String[] args) {
		GuiManager guiMgr = new GuiManager();
		guiMgr.displaySettingsFrame();
		guiMgr.displayScoreFrame();
		guiMgr.displayGameFrame();
	}

	//	public GuiManager(GameManager m) {
	//		mainManager = m; //passes a reference from the game manager into the GUI manager
	//	}


	//constructor
	public GuiManager() {
		super();
		// TODO Auto-generated constructor stub
		settingsWindow = new SettingsFrame();
		gameWindow = new GameFrame();
		scoreWindow = new ScoreFrame();
		pdWindow = new PhaseDescriptionFrame();
		siWindow = new SkipInputFrame();
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
	
	
	public void initGame() {
		displaySettingsFrame();
		initLanguage();
	}
	
	private void initLanguage() {
		
	}


}