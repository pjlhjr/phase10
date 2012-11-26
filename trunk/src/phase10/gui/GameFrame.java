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
import phase10.card.Card;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;


public class GameFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


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
					aGame.addPlayer(new Player(aGame));
					aGame.addPlayer(new Player(aGame));
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

	private JButton hcardButton1 = new JButton("");
	private JButton hcardButton2 = new JButton("");
	private JButton hcardButton4 = new JButton("");
	private JButton hcardButton3 = new JButton("");
	private JButton hcardButton5 = new JButton("");
	private JButton hcardButton6 = new JButton("");
	private JButton hcardButton7 = new JButton("");
	private JButton hcardButton8 = new JButton("");
	private JButton hcardButton9 = new JButton("");
	private JButton hcardButton10 = new JButton("");
	private JButton hcardButton11 = new JButton("");

	//end components

	/**
	 * Creates the GameFrame at the constructor
	 */
	public GameFrame(final GuiManager gManage) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameFrame.class.getResource("/images/GameIcon.png")));

		Phase10 currentGame = gManage.mainManager.getGame(); //added for simplicity of access
		final GuiManager guiManage = gManage; //quick fix for ActionListener in Phase Description button


		setTitle("CurrentPlayer - Phase 10");
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
		btnScoreboard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gManage.displayScoreFrame();
			}
		});
		btnScoreboard.setBounds(25, 291, 118, 23);
		infoPanel.add(btnScoreboard);

		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SaveFileFrame saverWindow = new SaveFileFrame(gManage);
				saverWindow.setVisible(true);

			}
		});
		btnSave.setBounds(22, 347, 124, 23);
		infoPanel.add(btnSave);

		JButton btnExit = new JButton("Exit");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		btnExit.setBounds(5, 392, 154, 25);
		infoPanel.add(btnExit);

		deckPanel.setBounds(982, 533, 169, 107);
		getContentPane().add(deckPanel);
		deckPanel.setLayout(new GridLayout(0, 2, 0, 0));

		JButton deckButton = new JButton("");
		deckButton.setIcon(new ImageIcon(GameFrame.class.getResource("/images/cardImages/card back.png")));
		deckPanel.add(deckButton);

		JButton discardButton = new JButton("");
		deckPanel.add(discardButton);

		handPanel.setBounds(0, 533, 976, 107);
		getContentPane().add(handPanel);
		handPanel.setLayout(new GridLayout(0, 11, 0, 0));

		//begin buttons for player's hand

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

		handButtons.add(hcardButton2);
		handPanel.add(hcardButton2);

		handButtons.add(hcardButton3);
		handPanel.add(hcardButton3);

		handButtons.add(hcardButton4);
		handPanel.add(hcardButton4);

		handButtons.add(hcardButton5);
		handPanel.add(hcardButton5);

		handButtons.add(hcardButton6);
		handPanel.add(hcardButton6);

		handButtons.add(hcardButton7);
		handPanel.add(hcardButton7);

		handButtons.add(hcardButton8);
		handPanel.add(hcardButton8);

		handButtons.add(hcardButton9);
		handPanel.add(hcardButton9);

		handButtons.add(hcardButton10);
		handPanel.add(hcardButton10);

		handButtons.add(hcardButton11);
		handPanel.add(hcardButton11);
		hcardButton11.setVisible(false); //Initially set to false. Will be true when user picks up a card

		//end buttons for player's hand

		playersPanel.setBounds(10, 11, 962, 416);
		getContentPane().add(playersPanel);
		playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.X_AXIS));

		//TODO error?
		ArrayList<opponentPanel> oppPanels = new ArrayList<opponentPanel>();
		try {
			for(int x = 0; x < currentGame.getNumberOfPlayers() - 1; x++) {
				oppPanels.add(new opponentPanel(currentGame.getPlayer(x)));
				playersPanel.add(oppPanels.get(x));
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
	/*
	 * end button listeners
	 */

	/*
	 * begin functional methods
	 */

	/**
	 * Will read information from a Card object and return the path to the correct
	 * image for that Card.
	 * 
	 * @param aCard the card object
	 * @return the filename to the image file of the specified card
	 */
	private String getCardFile(Card aCard) {

		String filename = "/images/cardImages/";

		if(aCard.getColor() == Color.RED)
			filename += "Red";
		else if(aCard.getColor() == Color.BLUE)
			filename += "Blue";
		else if(aCard.getColor() == Color.YELLOW)
			filename += "Yellow";
		else if(aCard.getColor() == Color.GREEN)
			filename += "Green";
		else
			filename = "Error!";

		if(aCard.getValue() == 13)
			filename += "Wild";
		else if(aCard.getValue() == 14)
			filename += "Skip";
		else
			filename += aCard.getValue();

		filename += ".png";

		return filename;
	}


	public void updateFrame(Player current) {
		Hand currentHand = current.getHand();

		/*
		 * begin update of card images
		 */
		hcardButton1.setIcon(new ImageIcon(GameFrame.class.getResource(
				getCardFile(currentHand.getCard(1))
				)));
		hcardButton2.setIcon(new ImageIcon(GameFrame.class.getResource(
				getCardFile(currentHand.getCard(2))
				)));
		hcardButton3.setIcon(new ImageIcon(GameFrame.class.getResource(
				getCardFile(currentHand.getCard(3))
				)));
		hcardButton4.setIcon(new ImageIcon(GameFrame.class.getResource(
				getCardFile(currentHand.getCard(4))
				)));
		hcardButton5.setIcon(new ImageIcon(GameFrame.class.getResource(
				getCardFile(currentHand.getCard(5))
				)));
		hcardButton6.setIcon(new ImageIcon(GameFrame.class.getResource(
				getCardFile(currentHand.getCard(6))
				)));
		hcardButton7.setIcon(new ImageIcon(GameFrame.class.getResource(
				getCardFile(currentHand.getCard(7))
				)));
		hcardButton8.setIcon(new ImageIcon(GameFrame.class.getResource(
				getCardFile(currentHand.getCard(8))
				)));
		hcardButton9.setIcon(new ImageIcon(GameFrame.class.getResource(
				getCardFile(currentHand.getCard(9))
				)));
		hcardButton10.setIcon(new ImageIcon(GameFrame.class.getResource(
				getCardFile(currentHand.getCard(10))
				)));
		/*
		 * end update of card images
		 */


		//begin update of infoPanel
		//infoPanel
		//end update of infoPanel

	}
}
