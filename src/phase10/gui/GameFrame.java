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


	private final ButtonGroup handButtons = new ButtonGroup();


	Player current;

	ArrayList<Card> selectedCards = new ArrayList<Card>();
	private boolean isDiscarding;

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

		//begin buttons for player's hand

		hcardButton1.setHorizontalTextPosition(SwingConstants.CENTER);
		hcardButton1.setPreferredSize(new Dimension(100, 23));
		hcardButton1.setMargin(new Insets(0, 0, 0, 0));
		hcardButton1.setMaximumSize(new Dimension(114, 40));
		hcardButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(hcardButton1.isSelected()) {
					selectedCards.remove(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(0));
					hcardButton1.setSelected(false);
				}
				else {
					selectedCards.add(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(0));
					hcardButton1.setSelected(true);
				}
				if(selectedCards.size() == 1) {
					discardButton.setEnabled(true);
				}
				else
					discardButton.setEnabled(false);
			}
		});

		handButtons.add(hcardButton1);
		handPanel.add(hcardButton1);

		handButtons.add(hcardButton2);
		hcardButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(hcardButton2.isSelected()) {
					selectedCards.remove(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(1));
					hcardButton2.setSelected(false);
				}
				else {
					selectedCards.add(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(1));
					hcardButton2.setSelected(true);
				}
				if(selectedCards.size() == 1) {
					discardButton.setEnabled(true);
				}
				else
					discardButton.setEnabled(false);
			}
		});
		handPanel.add(hcardButton2);

		handButtons.add(hcardButton3);
		hcardButton3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(hcardButton3.isSelected()) {
					selectedCards.remove(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(2));
					hcardButton3.setSelected(false);
				}
				else {
					selectedCards.add(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(2));
					hcardButton3.setSelected(true);
				}
				if(selectedCards.size() == 1) {
					discardButton.setEnabled(true);
				}
				else
					discardButton.setEnabled(false);
			}
		});
		handPanel.add(hcardButton3);

		handButtons.add(hcardButton4);
		hcardButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(hcardButton4.isSelected()) {
					selectedCards.remove(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(3));
					hcardButton4.setSelected(false);
				}
				else {
					selectedCards.add(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(3));
					hcardButton4.setSelected(true);
				}
				if(selectedCards.size() == 1) {
					discardButton.setEnabled(true);
				}
				else
					discardButton.setEnabled(false);
			}
		});
		handPanel.add(hcardButton4);

		handButtons.add(hcardButton5);
		hcardButton5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(hcardButton5.isSelected()) {
					selectedCards.remove(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(4));
					hcardButton5.setSelected(false);
				}
				else {
					selectedCards.add(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(4));
					hcardButton5.setSelected(true);
				}
				if(selectedCards.size() == 1) {
					discardButton.setEnabled(true);
				}
				else
					discardButton.setEnabled(false);
			}
		});
		handPanel.add(hcardButton5);

		handButtons.add(hcardButton6);
		hcardButton6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(hcardButton6.isSelected()) {
					selectedCards.remove(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(5));
					hcardButton6.setSelected(false);
				}
				else {
					selectedCards.add(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(5));
					hcardButton6.setSelected(true);
				}
				if(selectedCards.size() == 1) {
					discardButton.setEnabled(true);
				}
				else
					discardButton.setEnabled(false);
			}
		});
		handPanel.add(hcardButton6);

		handButtons.add(hcardButton7);
		hcardButton7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(hcardButton7.isSelected()) {
					selectedCards.remove(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(6));
					hcardButton7.setSelected(false);
				}
				else {
					selectedCards.add(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(6));
					hcardButton7.setSelected(true);
				}
				if(selectedCards.size() == 1) {
					discardButton.setEnabled(true);
				}
				else
					discardButton.setEnabled(false);
			}
		});
		handPanel.add(hcardButton7);

		handButtons.add(hcardButton8);
		hcardButton8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(hcardButton8.isSelected()) {
					selectedCards.remove(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(7));
					hcardButton8.setSelected(false);
				}
				else {
					selectedCards.add(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(7));
					hcardButton8.setSelected(true);
				}
				if(selectedCards.size() == 1) {
					discardButton.setEnabled(true);
				}
				else
					discardButton.setEnabled(false);
			}
		});
		handPanel.add(hcardButton8);

		handButtons.add(hcardButton9);
		hcardButton9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(hcardButton9.isSelected()) {
					selectedCards.remove(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(8));
					hcardButton9.setSelected(false);
				}
				else {
					selectedCards.add(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(8));
					hcardButton9.setSelected(true);
				}
				if(selectedCards.size() == 1) {
					discardButton.setEnabled(true);
				}
				else
					discardButton.setEnabled(false);
			}
		});
		handPanel.add(hcardButton9);

		handButtons.add(hcardButton10);
		hcardButton10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(hcardButton10.isSelected()) {
					selectedCards.remove(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(9));
					hcardButton10.setSelected(false);
				}
				else {
					selectedCards.add(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(9));
					hcardButton10.setSelected(true);
				}
				if(selectedCards.size() == 1) {
					discardButton.setEnabled(true);
				}
				else
					discardButton.setEnabled(false);
			}
		});
		handPanel.add(hcardButton10);

		handButtons.add(hcardButton11);
		hcardButton11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(hcardButton11.isSelected()) {
					selectedCards.remove(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(10));
					hcardButton11.setSelected(false);
				}
				else {
					selectedCards.add(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(10));
					hcardButton11.setSelected(true);
				}
				if(selectedCards.size() == 1) {
					discardButton.setEnabled(true);
				}
				else
					discardButton.setEnabled(false);
			}
		});
		handPanel.add(hcardButton11);

		hcardButton11.setVisible(false); //Initially set to false. Will be true when user picks up a card on the first turn

		//end buttons for player's hand

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
				gManage.mainManager.getGame().getRound().drawFromDeck();
				hcardButton11.setIcon(new ImageIcon(GameFrame.class.getResource(
						getCardFile(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(10)))));
				hcardButton11.setVisible(true);
				updateFrame(gManage.mainManager.getGame());
				deckButton.setEnabled(false);
				discardButton.setEnabled(false);
				btnNewPhase.setEnabled(true);
				discardButton.setIcon(null);
				discardButton.setText("discard selected card");

			}
		});
		deckButton.setIcon(new ImageIcon(GameFrame.class.getResource("/images/cardImages/card back.png")));
		deckPanel.add(deckButton);

		isDiscarding = false;

		discardButton = new JButton("");
		discardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if(isDiscarding == false) {
					try {
						gManage.mainManager.getGame().getRound().drawFromDiscard();
						hcardButton11.setIcon(new ImageIcon(GameFrame.class.getResource(
								getCardFile(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(10)))));
						hcardButton11.setVisible(true);
						updateFrame(gManage.mainManager.getGame());
						deckButton.setEnabled(false);
						discardButton.setEnabled(false);
						btnNewPhase.setEnabled(true);
						discardButton.setIcon(null);
						discardButton.setText("discard selected card");
						isDiscarding = true;
					} catch (Phase10Exception e1) {
						//TODO catch an exception. Is this really an exception?
						MessageFrame skipPickup = new MessageFrame("You cannot pick up a Skip card from the discard pile", "Invalid Move");
						skipPickup.setVisible(true);

						e1.printStackTrace();
					}

				}
				else {
					gManage.mainManager.getGame().getRound().discard(selectedCards.get(0));
					updateFrame(gManage.mainManager.getGame());
					deckButton.setEnabled(true);
					discardButton.setEnabled(true);	
					isDiscarding = false;
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

		//begin update of opponent panels
		for(opponentPanel x : oppPanels) {
			x.update();
		}
		//end update of opponent panels

		/*
		 * begin update of cards
		 */

		hcardButton1.setSelected(false);
		hcardButton2.setSelected(false);
		hcardButton3.setSelected(false);
		hcardButton4.setSelected(false);
		hcardButton5.setSelected(false);
		hcardButton6.setSelected(false);
		hcardButton7.setSelected(false);
		hcardButton8.setSelected(false);
		hcardButton9.setSelected(false);
		hcardButton10.setSelected(false);
		hcardButton11.setSelected(false);

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


	private void updateCardImages() {
		Hand currentHand = current.getHand();
		//begin selected and unselected image update
		switch(currentHand.getNumberOfCards()) {
		case 11:
			hcardButton11.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentHand.getCard(10))
					)));

			hcardButton11.setSelectedIcon(new ImageIcon(GameFrame.class.getResource(
					getSelectedCardFile(currentHand.getCard(10))
					)));
		case 10:
			hcardButton10.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentHand.getCard(9))
					)));

			hcardButton10.setSelectedIcon(new ImageIcon(GameFrame.class.getResource(
					getSelectedCardFile(currentHand.getCard(9))
					)));

		case 9:
			hcardButton9.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentHand.getCard(8)))
					));

			hcardButton9.setSelectedIcon(new ImageIcon(GameFrame.class.getResource(
					getSelectedCardFile(currentHand.getCard(8)))
					));

		case 8:
			hcardButton8.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentHand.getCard(7))
					)));

			hcardButton8.setSelectedIcon(new ImageIcon(GameFrame.class.getResource(
					getSelectedCardFile(currentHand.getCard(7))
					)));

		case 7:
			hcardButton7.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentHand.getCard(6))
					)));

			hcardButton7.setSelectedIcon(new ImageIcon(GameFrame.class.getResource(
					getSelectedCardFile(currentHand.getCard(6))
					)));	

		case 6:
			hcardButton6.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentHand.getCard(5))
					)));

			hcardButton6.setSelectedIcon(new ImageIcon(GameFrame.class.getResource(
					getSelectedCardFile(currentHand.getCard(5))
					)));

		case 5:
			hcardButton5.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentHand.getCard(4))
					)));

			hcardButton5.setSelectedIcon(new ImageIcon(GameFrame.class.getResource(
					getSelectedCardFile(currentHand.getCard(4))
					)));

		case 4:
			hcardButton4.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentHand.getCard(3))
					)));

			hcardButton4.setSelectedIcon(new ImageIcon(GameFrame.class.getResource(
					getSelectedCardFile(currentHand.getCard(3))
					)));

		case 3:
			hcardButton3.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentHand.getCard(2))
					)));

			hcardButton3.setSelectedIcon(new ImageIcon(GameFrame.class.getResource(
					getSelectedCardFile(currentHand.getCard(2))
					)));

		case 2:
			hcardButton2.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentHand.getCard(1))
					)));

			hcardButton2.setSelectedIcon(new ImageIcon(GameFrame.class.getResource(
					getSelectedCardFile(currentHand.getCard(1))
					)));

		case 1:
			hcardButton1.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentHand.getCard(0))
					)));

			hcardButton1.setSelectedIcon(new ImageIcon(GameFrame.class.getResource(
					getSelectedCardFile(currentHand.getCard(0))
					)));
		}
		//end selected and unselected image update

		//begin making all other buttons invisible
		switch(10 - currentHand.getNumberOfCards()) {
		case 10:
			hcardButton1.setVisible(false);
		case 9:
			hcardButton2.setVisible(false);
		case 8:
			hcardButton3.setVisible(false);
		case 7:
			hcardButton4.setVisible(false);
		case 6:
			hcardButton5.setVisible(false);
		case 5:
			hcardButton6.setVisible(false);
		case 4:
			hcardButton7.setVisible(false);
		case 3:
			hcardButton8.setVisible(false);
		case 2:
			hcardButton9.setVisible(false);
		case 1:
			hcardButton10.setVisible(false);
		case 0:
			break;
		}
		//end making all other buttons invisible

		if(gManage.mainManager.getGame().getRound().getTopOfDiscardStack() == null){
			discardButton.setIcon(new ImageIcon(GameFrame.class.getResource("/images/cardImages/NoCardsLeft.png")));
		}
		else {	
			discardButton.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(gManage.mainManager.getGame().getRound().getTopOfDiscardStack())
					)));
		}
	}
}
