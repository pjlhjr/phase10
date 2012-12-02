package phase10.gui;


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
import phase10.exceptions.Phase10Exception;

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


	Player current;

	ArrayList<Card> selectedCards = new ArrayList<Card>();
	private boolean isDiscarding = false;

	//begin components
	private JPanel infoPanel = new JPanel();
	private JPanel deckPanel = new JPanel();
	private JPanel handPanel = new JPanel();
	private JPanel playersPanel = new JPanel();
	private JPanel yourPhasesPanel = new JPanel();

	private JButton[] handButtons = new JButton[11];


	private JButton discardButton;


	private JLabel lblPlayername;


	private JTextArea phaseNumber;


	private JButton deckButton;


	private JButton btnNewPhase;


	private ArrayList<opponentPanel> oppPanels;


	private GuiManager gManage;

	//end components

	/**
	 * Creates the GameFrame at the constructor
	 */
	public GameFrame(final GuiManager gManage) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameFrame.class.getResource("/images/GameIcon.png")));

		this.gManage = gManage;

		gManage.mainManager.getGame().getRound().startRound();

		current = gManage.mainManager.getGame().getCurrentPlayer();

		Phase10 currentGame = gManage.mainManager.getGame(); //added for simplicity of access
		final GuiManager guiManage = gManage; //quick fix for ActionListener in Phase Description button


		setTitle(current.getName() + " - Phase 10");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1150, 668);
		getContentPane().setLayout(null);

		infoPanel.setBounds(982, 0, 169, 534);
		getContentPane().add(infoPanel);
		infoPanel.setLayout(null);

		lblPlayername = new JLabel(current.getName());
		lblPlayername.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPlayername.setBounds(10, 37, 149, 38);
		infoPanel.add(lblPlayername);

		JLabel lblPhase = new JLabel("Phase");
		lblPhase.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPhase.setBounds(58, 91, 53, 25);
		infoPanel.add(lblPhase);


		phaseNumber = new JTextArea();
		phaseNumber.setRows(1);
		phaseNumber.setColumns(1);
		phaseNumber.setFont(new Font("Century Gothic", Font.BOLD, 36));

		try {
			phaseNumber.setText(Integer.toString(currentGame.getCurrentPlayer().getPhase()));
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
				dispose();
			}
		});
		btnExit.setBounds(5, 392, 154, 25);
		infoPanel.add(btnExit);

		handPanel.setBounds(0, 533, 976, 107);
		getContentPane().add(handPanel);
		handPanel.setLayout(new GridLayout(0, 11, 0, 0));


		for(int i = 0; i < handButtons.length; i++) {
			handButtons[i] = new JButton();

			//add an action listener
			handButtons[i].addActionListener(new HandActionListener(i));
			handPanel.add(handButtons[i]);
		}
		

		//begin deck panel

		deckPanel.setBounds(982, 533, 169, 107);
		getContentPane().add(deckPanel);
		deckPanel.setLayout(new GridLayout(0, 2, 0, 0));

		deckButton = new JButton("");
		deckButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		deckButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(isDiscarding == false) {
					gManage.mainManager.getGame().getRound().drawFromDeck();
					updateFrame(gManage.mainManager.getGame());
					deckButton.setEnabled(false);
					discardButton.setEnabled(false);
					btnNewPhase.setEnabled(true);
					discardButton.setIcon(null);
					discardButton.setText("discard selected card");
					isDiscarding = true;
				}

			}
		});
		deckButton.setIcon(new ImageIcon(GameFrame.class.getResource("/images/cardImages/card back.png")));
		deckPanel.add(deckButton);

		discardButton = new JButton("");
		discardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if(isDiscarding == false) {
						boolean isValidCard = gManage.mainManager.getGame().getRound().drawFromDiscard();
						if(isValidCard) {
							updateFrame(gManage.mainManager.getGame());
							deckButton.setEnabled(false);
							discardButton.setEnabled(false);
							btnNewPhase.setEnabled(true);
							discardButton.setIcon(null);
							discardButton.setText("discard selected card");
							isDiscarding = true;
							System.out.println("isDiscarding has been set to true");
						}
						else {
							MessageFrame skipPickup = new MessageFrame("You cannot pick up a Skip card from the discard pile", "Invalid Move");
							skipPickup.setVisible(true);
						}
				}
				else {
					gManage.mainManager.getGame().getRound().discard(selectedCards.get(0));
					updateFrame(gManage.mainManager.getGame());
					deckButton.setEnabled(true);
					discardButton.setEnabled(true);	
					isDiscarding = false;
					System.out.println("isDiscarding has been set to false");
				}
			}
		});
		deckPanel.add(discardButton);

		//end deck panel

		playersPanel.setBounds(10, 11, 962, 416);
		getContentPane().add(playersPanel);
		playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.X_AXIS));

		oppPanels = new ArrayList<opponentPanel>();
		try {
			for(int x = 0; x < currentGame.getNumberOfPlayers() - 1; x++) {
				oppPanels.add(new opponentPanel(currentGame.getPlayer(x), gManage.mainManager.getGame(), this));
				playersPanel.add(oppPanels.get(x));
			}
		} catch (NullPointerException e) {
			System.out.println("null pointer exception generated when trying to display opponent Panels");
			playersPanel.add(new JLabel("Null"));
		}

		yourPhasesPanel.setBounds(0, 427, 972, 107);
		getContentPane().add(yourPhasesPanel);
		yourPhasesPanel.setLayout(null);

		btnNewPhase = new JButton("Add a Phase!");
		btnNewPhase.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				PhaseGroup newPhase = new PhaseGroup(gManage.mainManager.getGame());

				for(Card c : selectedCards)
					newPhase.addCard(c);

				boolean isValidGroup = gManage.mainManager.getGame().getCurrentPlayer().addPhaseGroups(newPhase);
				if(!isValidGroup) {
					//MessageFrame notAGoodPhase = new MessageFrame("The phase you are trying to add is not valid for your phase (phase " + gManage.mainManager.getCurrentPlayer().getPhase() + ".", "Invalid move");    
				}
			}
		});
		btnNewPhase.setEnabled(false);
		btnNewPhase.setBounds(413, 42, 146, 23);
		yourPhasesPanel.add(btnNewPhase);

		JButton button = new JButton("");
		button.setVisible(false);
		button.setPreferredSize(new Dimension(100, 23));
		button.setMaximumSize(new Dimension(114, 40));
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setBounds(10, 0, 88, 107);
		yourPhasesPanel.add(button);

		JButton button_1 = new JButton("");
		button_1.setVisible(false);
		button_1.setPreferredSize(new Dimension(100, 23));
		button_1.setMaximumSize(new Dimension(114, 40));
		button_1.setMargin(new Insets(0, 0, 0, 0));
		button_1.setHorizontalTextPosition(SwingConstants.CENTER);
		button_1.setBounds(179, 0, 88, 107);
		yourPhasesPanel.add(button_1);

		JButton button_2 = new JButton("");
		button_2.setVisible(false);
		button_2.setPreferredSize(new Dimension(100, 23));
		button_2.setMaximumSize(new Dimension(114, 40));
		button_2.setMargin(new Insets(0, 0, 0, 0));
		button_2.setHorizontalTextPosition(SwingConstants.CENTER);
		button_2.setBounds(707, 0, 88, 107);
		yourPhasesPanel.add(button_2);

		JButton button_3 = new JButton("");
		button_3.setVisible(false);
		button_3.setPreferredSize(new Dimension(100, 23));
		button_3.setMaximumSize(new Dimension(114, 40));
		button_3.setMargin(new Insets(0, 0, 0, 0));
		button_3.setHorizontalTextPosition(SwingConstants.CENTER);
		button_3.setBounds(884, 0, 88, 107);
		yourPhasesPanel.add(button_3);

		JButton btnNewButton = new JButton("add to phase");
		btnNewButton.setVisible(false);
		btnNewButton.setBounds(277, 27, 117, 52);
		yourPhasesPanel.add(btnNewButton);

		JButton button_4 = new JButton("add to phase");
		button_4.setVisible(false);
		button_4.setBounds(580, 27, 117, 52);
		yourPhasesPanel.add(button_4);

		JLabel lblTo = new JLabel("to");
		lblTo.setVisible(false);
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTo.setBounds(108, 25, 61, 53);
		yourPhasesPanel.add(lblTo);

		JLabel label = new JLabel("to");
		label.setVisible(false);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 25));
		label.setBounds(805, 27, 61, 53);
		yourPhasesPanel.add(label);

		//updateFrame(gManage.mainManager.getGame()); TODO
		updateCardImages();
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
	//	{
	//	for(opponentPanel x : oppPanels)
	//		x.update();
	//	}
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

		if(aCard.getValue() == 13)
			filename += "Wild";
		else if(aCard.getValue() == 14)
			filename += "Skip";
		else
			filename += aCard.getValue();

		filename += ".png";

		return filename;
	}

	private String getSelectedCardFile(Card aCard) {
		String filename = getCardFile(aCard);
		filename = filename.substring(0, filename.length() - 4);
		filename += "Selected.png";

		return filename;
	}


	public void updateFrame(Phase10 currentGame) {
		current = currentGame.getCurrentPlayer();
		//begin update of opponent panels
		for(opponentPanel x : oppPanels) {
			//x.update(); TODO
		}
		//end update of opponent panels

		/*
		 * begin update of cards
		 */

		for(int i = 0; i < handButtons.length; i++)
			handButtons[i].setSelected(false);
		
		selectedCards.clear();

		updateCardImages();

		if(currentGame.getRound().getTopOfDiscardStack() == null) {
			discardButton.setIcon(new ImageIcon(GameFrame.class.getResource("/images/cardImages/NoCardsLeft.png")));

		}
		else {
			discardButton.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentGame.getRound().getTopOfDiscardStack())
					)));
			discardButton.setEnabled(true);
		}

		deckButton.setEnabled(true);

		/*
		 * end update of cards
		 */


		//begin update of infoPanel

		lblPlayername.setText(current.getName());
		phaseNumber.setText(Integer.toString(current.getPhase()));

		//end update of infoPanel

	}


	/**
	 * Updates each of the card's selected and unselected images to the current configuration
	 * of the current player's hand
	 */
	private void updateCardImages() {
		
		Hand currentHand = current.getHand();
		//begin hand button update
		int i = 0;
		for(; i < currentHand.getNumberOfCards(); i++) {
			
			handButtons[i].setVisible(true);
			
			handButtons[i].setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentHand.getCard(i))
					)));
			
			handButtons[i].setSelectedIcon(new ImageIcon(GameFrame.class.getResource(
					getSelectedCardFile(currentHand.getCard(i))
					)));
		}
		
		for(; i < handButtons.length; i++) {
			handButtons[i].setVisible(false);
		}
		//end hand button update

		if(gManage.mainManager.getGame().getRound().getTopOfDiscardStack() == null){
			discardButton.setIcon(new ImageIcon(GameFrame.class.getResource("/images/cardImages/NoCardsLeft.png")));
		}
		else {	
			discardButton.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(gManage.mainManager.getGame().getRound().getTopOfDiscardStack())
					)));
		}
	}

	private class HandActionListener implements ActionListener {

		int i;

		public HandActionListener(int index) {
			i = index;
		}

		public void actionPerformed(ActionEvent arg0) {
			if(handButtons[i].isSelected()) {
				selectedCards.remove(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(i));
				handButtons[i].setSelected(false);
			}
			else {
				selectedCards.add(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(i));
				handButtons[i].setSelected(true);
			}
			if(selectedCards.size() == 1) {
				handButtons[i].setEnabled(true);
			}
			else
				handButtons[i].setEnabled(false);
		}
	}
}
