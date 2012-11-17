package phase10.gui;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Toolkit;
import javax.swing.ButtonGroup;

import phase10.*;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;


public class GameFrame extends JFrame {
	private final ButtonGroup handButtons = new ButtonGroup();


	Player current;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameManager mainManager = new GameManager();
					mainManager.newGame();
					//TODO get test situation to work
					Phase10 aGame = mainManager.getGame();
					aGame.addPlayer(new Player());
					aGame.addPlayer(new Player());
					aGame.startGame();
					GameFrame frame = new GameFrame(new GuiManager(mainManager));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * begin text field values.
	 */
	private String playerName; //name of current player
	private String playerPhase; //phase of current player
	private String frameTitle;
	//begin button labels
	private String pdButtonLabel; //label for the phase description button
	private String sbButtonLabel; //label for the score board button
	private String saeButtonLabel; //label for the save and exit button
	private String ewsButtonLabel; //label for the exit without saving button
	/*
	 * end text field values
	 */
	
	//begin components
	private JPanel infoPanel = new JPanel();
	private JPanel deckPanel = new JPanel();
	private JPanel handPanel = new JPanel();
	private JPanel playersPanel = new JPanel();
	private JPanel yourPhasesPanel = new JPanel();
	//end components

	/**
	 * Create the frame.
	 */
	public GameFrame(GuiManager gManage) {
		
		Phase10 currentGame = gManage.mainManager.getGame(); //added for simplicity of access
		final GuiManager guiManage = gManage; //quick fix for ActionListener in Phase Description button

		
		setTitle("CurrentPlayer - Phase 10");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1150, 668);
		getContentPane().setLayout(null);

		infoPanel.setBounds(982, 0, 169, 534);
		getContentPane().add(infoPanel);
		infoPanel.setLayout(null);

		JLabel lblPlayername = new JLabel("PlayerName");
		lblPlayername.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPlayername.setBounds(10, 37, 149, 38);
		infoPanel.add(lblPlayername);

		JLabel lblPhase = new JLabel("Phase");
		lblPhase.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPhase.setBounds(58, 91, 53, 25);
		infoPanel.add(lblPhase);

		
		JTextArea phaseNumber = new JTextArea();
		phaseNumber.setRows(1);
		phaseNumber.setColumns(1);
		phaseNumber.setFont(new Font("Century Gothic", Font.BOLD, 36));

		//TODO fix null pointer exception errors.
		try {
			phaseNumber.setText(Integer.toString(currentGame.getCurrentPlayer().getPhase())); //problem targeted! null is returned at Phase10: line 52
		} catch (NullPointerException e) {
			phaseNumber.setText("null");
		}
		phaseNumber.setEditable(false);
		phaseNumber.setBounds(58, 120, 53, 53);
		infoPanel.add(phaseNumber);

		JButton btnPhaseDescription = new JButton("Phase Description");
		btnPhaseDescription.setBounds(10, 184, 149, 23);
		btnPhaseDescription.addMouseListener(new pdListener(guiManage));
		infoPanel.add(btnPhaseDescription);

		JButton btnScoreboard = new JButton("Scoreboard");
		btnScoreboard.setBounds(25, 291, 118, 23);
		infoPanel.add(btnScoreboard);

		JButton btnSaveAndExit = new JButton("Save and Exit");
		btnSaveAndExit.setBounds(22, 347, 124, 23);
		infoPanel.add(btnSaveAndExit);

		JButton btnExitWithoutSaving = new JButton("Exit without Saving");
		btnExitWithoutSaving.setBounds(5, 392, 154, 25);
		infoPanel.add(btnExitWithoutSaving);

		deckPanel.setBounds(982, 533, 169, 107);
		getContentPane().add(deckPanel);
		deckPanel.setLayout(new GridLayout(0, 2, 0, 0));

		JButton deckButton = new JButton("");
		deckButton.addMouseListener(new deckListener()); //mouse listener for the deck button
		deckPanel.add(deckButton);

		JButton discardButton = new JButton("");
		deckPanel.add(discardButton);

		handPanel.setBounds(0, 533, 976, 107);
		getContentPane().add(handPanel);
		handPanel.setLayout(new GridLayout(0, 11, 0, 0));

		JButton hcardButton1 = new JButton("");
		hcardButton1.setHorizontalTextPosition(SwingConstants.CENTER);
		hcardButton1.setPreferredSize(new Dimension(100, 23));
		hcardButton1.setMargin(new Insets(0, 0, 0, 0));
		hcardButton1.setMaximumSize(new Dimension(114, 40));
		hcardButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		handButtons.add(hcardButton1);
		handPanel.add(hcardButton1);

		JButton hcardButton2 = new JButton("");
		handButtons.add(hcardButton2);
		handPanel.add(hcardButton2);

		JButton hcardButton3 = new JButton("");
		handButtons.add(hcardButton3);
		handPanel.add(hcardButton3);

		JButton hcardButton4 = new JButton("");
		handButtons.add(hcardButton4);
		handPanel.add(hcardButton4);

		JButton hcardButton5 = new JButton("");
		handButtons.add(hcardButton5);
		handPanel.add(hcardButton5);

		JButton hcardButton6 = new JButton("");
		handButtons.add(hcardButton6);
		handPanel.add(hcardButton6);

		JButton hcardButton7 = new JButton("");
		handButtons.add(hcardButton7);
		handPanel.add(hcardButton7);

		JButton hcardButton8 = new JButton("");
		handButtons.add(hcardButton8);
		handPanel.add(hcardButton8);

		JButton hcardButton9 = new JButton("");
		handButtons.add(hcardButton9);
		handPanel.add(hcardButton9);

		JButton hcardButton10 = new JButton("");
		handButtons.add(hcardButton10);
		handPanel.add(hcardButton10);

		JButton hcardButton11 = new JButton("");
		handButtons.add(hcardButton11);
		handPanel.add(hcardButton11);

		playersPanel.setBounds(10, 11, 962, 416);
		getContentPane().add(playersPanel);
		playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.X_AXIS));
		
		ArrayList<JPanel> oppPanels = new ArrayList<JPanel>();
		try {
			for(int x = 0; x < currentGame.getNumberOfPlayers() - 1; x++) {
				oppPanels.add(new opponentPanel(currentGame.getPlayer(x)));
			}
		} catch (NullPointerException e) {
			System.out.println("null pointer exception generated when trying to display opponent Panels");
			playersPanel.add(new JLabel("Null"));
		}

		yourPhasesPanel.setBounds(0, 427, 972, 107);
		getContentPane().add(yourPhasesPanel);
	}
	
	/*
	 * begin button listeners
	 */
	private class pdListener implements MouseListener {
		private final GuiManager gm;
		
		public pdListener(GuiManager guiManage) {
			gm = guiManage;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			gm.displayPhaseDescriptionFrame();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
	private class deckListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	/*
	 * end button listeners
	 */

	/*
	 * begin functional methods
	 */


//	public void updateFrame(Player c) {
//		current = c;
//		Hand currentHand = c.getHand();
//		
//		String cardFileName;
//		String buttonName;
//		
//		for(int i = 0; i < 11; i++) {
//			cardFileName = currentHand.getCard(i).getColor();
//			cardFileName += currentHand.getCard(0).getValue();
//			
//			buttonName = "hcardButton" + currentHand.getCard(i).getValue();
//			//TODO finish the "update hand" part of this method
//			
//		}
//		
//		//begin update of infoPanel
//			//infoPanel.
//		//end update of infoPanel
//		
//	}
}
