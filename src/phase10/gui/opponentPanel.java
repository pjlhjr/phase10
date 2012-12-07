package phase10.gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import phase10.Phase10;
import phase10.Player;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * OpponentPanel is a JPanel object that displays information about an opponent in Phase10. OpponentPanel displays the opponent's
 * name, current phase, the number of cards in his/her hand, and his/her score on the top of the panel. Phase groups are laid
 * down on the bottom of the panel with buttons that allow a user to add to an opponent's phase group if they are able to.
 * 
 * @author Matthew Hruz
 *
 */
public class opponentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton addToPhase_1; //a button that allows a user to add to an opponent's first phase group
	private Player opponent; //the opponent who's information is displayed in the panel
	private JTextField txtPhase; //displays an opponent's current phase
	private JTextField numCardsPane; //displays the number of cards an opponent has in their hand
	private JTextField txtpnScore; //displays the opponent's score
	private JButton phaseGroup1Begin; //displays the first card in an opponent's first phase group and allows the current player to add to the beginning of the opponent's first phase group
	private JButton phaseGroup1End; //displays the last card in an opponent's first phase group
	private JButton addToPhase_2; //button that allows a user to add cards to an opponent's first phase group
	private JTextField namePane; //displays the opponent's name
	private JLabel lblTo; //displays the word "to" in between the first and last card of an opponent's first phase. Hovering over the label will allow the user to see all the cards in the phase group
	private JLabel labelTo_2; //displays the word "to" in between the first and last card of an opponent's second phase. Hovering over the label will allow the user to see all the cards in the phase group
	private JButton phaseGroup2Begin; //displays the first card in an opponent's second phase group and allows the current player to add to the beginning of the opponent's second phase group
	private JButton phaseGroup2End; //displays the last card in an opponent's second phase group
	private GameFrame gameWindow; //reference to the GameFrame object that the panel resides in
	private Phase10 currentGame; //reference to the current game
	private Language gameLang; //the language that the current game is using

	/**
	 * Constructs a panel to display information about an opponent to the current player.
	 * 
	 * @param opponent The player who's game statistics will be displayed in the window
	 * @param currentGame The current phase10 game
	 * @param gameWindow the window in which this panel will reside
	 */
	public opponentPanel(final Player opponent, final Phase10 currentGame, final GameFrame gameWindow) {
		setMinimumSize(new Dimension(80, 10));
		this.opponent = opponent;
		this.gameWindow = gameWindow;
		this.currentGame = currentGame;

		gameLang = gameWindow.gameLang;

		//begin panel setup
		setLayout(new BorderLayout(0, 0));

		JPanel oppInfoPanel = new JPanel();
		oppInfoPanel.setPreferredSize(new Dimension(10, 70));
		oppInfoPanel.setSize(new Dimension(0, 25));
		add(oppInfoPanel, BorderLayout.NORTH);
		oppInfoPanel.setLayout(new BorderLayout(0, 0));

		namePane = new JTextField();
		namePane.setHorizontalAlignment(SwingConstants.CENTER);
		namePane.setEditable(false);
		namePane.setPreferredSize(new Dimension(6, 30));
		namePane.setText(opponent.getName());
		oppInfoPanel.add(namePane, BorderLayout.NORTH);

		txtPhase = new JTextField();
		txtPhase.setHorizontalAlignment(SwingConstants.CENTER);
		txtPhase.setEditable(false);
		txtPhase.setPreferredSize(new Dimension(100, 20));
		txtPhase.setText("Phase: " + opponent.getPhase());
		oppInfoPanel.add(txtPhase, BorderLayout.WEST);

		numCardsPane = new JTextField();
		numCardsPane.setHorizontalAlignment(SwingConstants.CENTER);
		numCardsPane.setEditable(false);
		numCardsPane.setPreferredSize(new Dimension(13, 10));
		numCardsPane.setText("Cards in hand: " + opponent.getHand().getNumberOfCards());
		oppInfoPanel.add(numCardsPane, BorderLayout.CENTER);

		txtpnScore = new JTextField();
		txtpnScore.setHorizontalAlignment(SwingConstants.CENTER);
		txtpnScore.setEditable(false);
		txtpnScore.setPreferredSize(new Dimension(100, 20));
		txtpnScore.setText("Score: " + opponent.getScore());
		oppInfoPanel.add(txtpnScore, BorderLayout.EAST);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);

		addToPhase_1 = new JButton("Add to Phase");
		addToPhase_1.addActionListener(new AddToPhaseListener(0));

		phaseGroup1Begin = new JButton("");
		phaseGroup1Begin.addActionListener(new AddToPhaseListener(0, true));

		lblTo = new JLabel(gameLang.getEntry("TO"));
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);

		phaseGroup1End = new JButton("");
		phaseGroup1End.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		phaseGroup2Begin = new JButton("");
		phaseGroup2Begin.addActionListener(new AddToPhaseListener(1, true));

		labelTo_2 = new JLabel(gameLang.getEntry("TO"));
		labelTo_2.setHorizontalAlignment(SwingConstants.CENTER);
		labelTo_2.setFont(new Font("Tahoma", Font.PLAIN, 14));

		phaseGroup2End = new JButton("");

		addToPhase_2 = new JButton("Add to Phase");
		addToPhase_2.addActionListener(new AddToPhaseListener(1));

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addGap(26)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
										.addGap(32)
										.addComponent(lblTo, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel.createSequentialGroup()
												.addGap(12)
												.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
														.addComponent(phaseGroup1End, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
														.addComponent(phaseGroup1Begin, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
														.addComponent(addToPhase_1, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
														.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
																.addGroup(gl_panel.createSequentialGroup()
																		.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																				.addGroup(gl_panel.createSequentialGroup()
																						.addGap(10)
																						.addComponent(labelTo_2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
																						.addComponent(phaseGroup2Begin, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
																						.addGap(2))
																						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
																								.addComponent(addToPhase_2, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
																								.addComponent(phaseGroup2End, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
																								.addGap(25))
				);
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(phaseGroup1Begin, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
								.addComponent(phaseGroup2Begin, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblTo, GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
										.addComponent(labelTo_2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_panel.createSequentialGroup()
														.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
																.addComponent(phaseGroup1End, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
																.addComponent(phaseGroup2End, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
																.addGap(64))
																.addGroup(gl_panel.createSequentialGroup()
																		.addGap(125)
																		.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
																				.addComponent(addToPhase_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
																				.addComponent(addToPhase_2, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))))
																				.addGap(114))
				);
		panel.setLayout(gl_panel);

		phaseAreaUpdate();
		//end panel setup
	}

/**
 * updates this instance of opponentPanel
 * 
 * @param nextOpponent the next opponent who's information will be displayed in this instance of opponentPanel
 */
	void updatePanel(Player nextOpponent) {

		opponent = nextOpponent;

		this.namePane.setText(opponent.getName());
		this.txtPhase.setText(gameLang.getEntry("PHASE") + ": " + opponent.getPhase());
		this.txtpnScore.setText(gameLang.getEntry("SCORE") + ": " + opponent.getScore());
		this.numCardsPane.setText(gameLang.getEntry("CARDS_IN_HAND") + ": " + opponent.getHand().getNumberOfCards());

		phaseAreaUpdate();
	}

	/**
	 * Returns the player represented in the panel.
	 * 
	 * @return the opponent represented in the panel.
	 */
	Player getOpponent() {
		return opponent;
	}

	/**
	 * only updates the area of the panel dedicated to the player's phase groups
	 */
	private void phaseAreaUpdate() {
		if(opponent.hasLaidDownPhase()) {
			if(opponent.getNumberOfPhaseGroups() == 1) {

				phaseGroup1Begin.setIcon(new ImageIcon(
						GameFrame.class.getResource(gameWindow.getCardFile(opponent.getPhaseGroup(0).getCard(0)))));
				phaseGroup1End.setIcon(new ImageIcon(
						GameFrame.class.getResource(gameWindow.getCardFile(opponent.getPhaseGroup(0).getCard(
								opponent.getPhaseGroup(0).getNumberOfCards()-1)))));

				phaseGroup1Begin.setVisible(true);
				lblTo.setVisible(true);
				phaseGroup1End.setVisible(true);
				addToPhase_1.setVisible(true);

				lblTo.setToolTipText(opponent.getPhaseGroup(0).toString());

				phaseGroup2Begin.setVisible(false);
				labelTo_2.setVisible(false);
				phaseGroup2End.setVisible(false);
				addToPhase_2.setVisible(false);

			}
			else if(opponent.getNumberOfPhaseGroups() == 2) {

				phaseGroup1Begin.setIcon(new ImageIcon(
						GameFrame.class.getResource(gameWindow.getCardFile(opponent.getPhaseGroup(0).getCard(0)))));
				phaseGroup1End.setIcon(new ImageIcon(
						GameFrame.class.getResource(gameWindow.getCardFile(opponent.getPhaseGroup(0).getCard(
								opponent.getPhaseGroup(0).getNumberOfCards()-1)))));

				phaseGroup2Begin.setIcon(new ImageIcon(
						GameFrame.class.getResource(gameWindow.getCardFile(opponent.getPhaseGroup(1).getCard(0)))));
				phaseGroup2End.setIcon(new ImageIcon(
						GameFrame.class.getResource(gameWindow.getCardFile(opponent.getPhaseGroup(1).getCard(
								opponent.getPhaseGroup(1).getNumberOfCards()-1)))));

				phaseGroup1Begin.setVisible(true);
				lblTo.setVisible(true);
				phaseGroup1End.setVisible(true);
				addToPhase_1.setVisible(true);

				lblTo.setToolTipText(opponent.getPhaseGroup(0).toString());
				labelTo_2.setToolTipText(opponent.getPhaseGroup(1).toString());

				phaseGroup2Begin.setVisible(true);
				labelTo_2.setVisible(true);
				phaseGroup2End.setVisible(true);
				addToPhase_2.setVisible(true);
			}
			else
				System.out.println("Error!");
		}
		else {
			phaseGroup1Begin.setVisible(false);
			lblTo.setVisible(false);
			phaseGroup1End.setVisible(false);
			addToPhase_1.setVisible(false);

			phaseGroup2Begin.setVisible(false);
			labelTo_2.setVisible(false);
			phaseGroup2End.setVisible(false);
			addToPhase_2.setVisible(false);
		}
	}
	
	/**
	 * Action listener for the "add to phase" button in opponentPanel. Allows players to add to the beginning
	 * of a phase as well as the end of a phase.
	 * 
	 * @author Matthew Hruz
	 *
	 */
	private class AddToPhaseListener implements ActionListener {

		private int phaseGroupIndex;
		private boolean isAtBeginning;

		/**
		 * Constructor for AddToPhaseListener
		 * @param phaseGroup the phase group that the cards will be added to.
		 */
		public AddToPhaseListener(int phaseGroup) {
			this.phaseGroupIndex = phaseGroup;
			this.isAtBeginning = false;
		}

		/**
		 * Constructor for AddToPhaseListener that specifies which end of a Phase Group a player 
		 * would like to add their cards to
		 * @param phaseGroup the phase group that the cards will be added to.
		 * @param atBeginning if true ActionPerformed will add cards to the beginning of a PhaseGroup. If false the cards will be added to the end of a Phase Group
		 */
		public AddToPhaseListener(int phaseGroup, boolean atBeginning) {
			this.phaseGroupIndex = phaseGroup;
			this.isAtBeginning = atBeginning;
		}

		/**
		 * called when a button with an AddToPhaseListener is clicked. Adds cards to the phase group specified
		 * in the constructor at the end of the phase group specified in the constructor.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(currentGame.getCurrentPlayer().getHasDrawnCard()) {
				for(int x = 0; x < gameWindow.selectedCards.size(); x++) {

					boolean isValid;

					if(isAtBeginning)
						isValid = opponent.getPhaseGroup(phaseGroupIndex).addCardToBeginning(gameWindow.selectedCards.get(0));
					else
						isValid = opponent.getPhaseGroup(phaseGroupIndex).addCard(gameWindow.selectedCards.get(x));

					if(!isValid) {
						MessageFrame invalidAdd = new MessageFrame(gameLang.getEntry("INVALID_ADD_MESSAGE"), gameLang.getEntry("INVALID_MOVE"), gameLang);
						invalidAdd.setVisible(true);
						break;
					}
					else {
						gameWindow.hideAndClearSelectedCards();
						gameWindow.updateFrame(currentGame);
						gameWindow.updateYourPhasesPanel();
					}
				}

			}
		}

	}
}
