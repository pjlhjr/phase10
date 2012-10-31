package phase10.gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import phase10.Player;
public class opponentPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public opponentPanel(Player opponent) {
		
		//begin panel setup
		setLayout(new BorderLayout(0, 0));
		
		JPanel oppInfoPanel = new JPanel();
		oppInfoPanel.setPreferredSize(new Dimension(10, 70));
		oppInfoPanel.setSize(new Dimension(0, 25));
		add(oppInfoPanel, BorderLayout.NORTH);
		oppInfoPanel.setLayout(new BorderLayout(0, 0));
		
		JTextField namePane = new JTextField();
		namePane.setHorizontalAlignment(SwingConstants.CENTER);
		namePane.setEditable(false);
		namePane.setPreferredSize(new Dimension(6, 30));
		namePane.setText(opponent.getName());
		oppInfoPanel.add(namePane, BorderLayout.NORTH);
		
		JTextField txtPhase = new JTextField();
		txtPhase.setEditable(false);
		txtPhase.setPreferredSize(new Dimension(100, 20));
		txtPhase.setText("Phase: " + opponent.getPhase());
		oppInfoPanel.add(txtPhase, BorderLayout.WEST);
		
		JTextField numCardsPane = new JTextField();
		numCardsPane.setEditable(false);
		numCardsPane.setPreferredSize(new Dimension(13, 10));
		numCardsPane.setText("Cards in hand: " + opponent.getHand().getNumberOfCards());
		oppInfoPanel.add(numCardsPane, BorderLayout.CENTER);
		
		JTextField txtpnScore = new JTextField();
		txtpnScore.setEditable(false);
		txtpnScore.setPreferredSize(new Dimension(100, 20));
		txtpnScore.setText("Score: " + opponent.getScore());
		oppInfoPanel.add(txtpnScore, BorderLayout.EAST);
		//end panel setup
	}
}
