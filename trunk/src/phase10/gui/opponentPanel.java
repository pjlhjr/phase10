package phase10.gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import phase10.Phase10;
import phase10.PhaseGroup;
import phase10.Player;
import phase10.card.Card;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class opponentPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton addToPhase_1;
	private Player opponent;
	private JTextField txtPhase;
	private JTextField numCardsPane;
	private JTextField txtpnScore;
	private JButton phaseGroup1Begin;
	private JButton addToPhase_2;
	private JTextField namePane;
	private JLabel lblTo;
	private JLabel labelTo_2;
	private JButton phaseGroup2Begin;
	private JButton phaseGroup2End;
	private JButton phaseGroup1End;
	private GameFrame gameWindow;
	private Phase10 currentGame;

	/**
	 * Create the panel.
	 */
	public opponentPanel(final Player opponent, final Phase10 currentGame, final GameFrame gameWindow) {
		this.opponent = opponent;
		this.gameWindow = gameWindow;
		this.currentGame = currentGame;
		
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
		addToPhase_1.addActionListener(new AddPhasesListener(0));
		
		phaseGroup1Begin = new JButton("");
		
		lblTo = new JLabel("to");
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		
		phaseGroup1End = new JButton("");
		phaseGroup1End.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		phaseGroup2Begin = new JButton("");
		
		labelTo_2 = new JLabel("to");
		labelTo_2.setHorizontalAlignment(SwingConstants.CENTER);
		labelTo_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		phaseGroup2End = new JButton("");
		
		addToPhase_2 = new JButton("Add to Phase");
		addToPhase_2.addActionListener(new AddPhasesListener(1));
		
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
	
	void updatePanel(Player nextOpponent) {
		
		//TODO get this to work!!!!!
	
		opponent = nextOpponent;
		
		this.namePane.setText(opponent.getName());
		this.txtPhase.setText("Phase: " + opponent.getPhase());
		this.txtpnScore.setText("Score: " + opponent.getScore());
		this.txtPhase.setText("Phase: " + opponent.getPhase());
		this.numCardsPane.setText("Cards in Hand: " + opponent.getHand().getNumberOfCards());
		
		//TODO add update for the phases area
		phaseAreaUpdate();
	}

	Player getOpponent() {
		return opponent;
	}
	
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
	
	private class AddPhasesListener implements ActionListener {

		private int phaseGroupIndex;

		public AddPhasesListener(int phaseGroup) {
			this.phaseGroupIndex = phaseGroup;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO new listener
			for(int x = 0; x < gameWindow.selectedCards.size(); x++) {
				boolean isValid = opponent.getPhaseGroup(phaseGroupIndex).addCard(gameWindow.selectedCards.get(x));
				if(!isValid) {
					MessageFrame invalidAdd = new MessageFrame("A card you are trying to add is invlaid", "Invalid move");
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
