package phase10.gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import phase10.Player;
import java.awt.GridBagLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridLayout;
import javax.swing.JButton;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.Font;
public class opponentPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton addToPhase_1;

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
		txtPhase.setHorizontalAlignment(SwingConstants.CENTER);
		txtPhase.setEditable(false);
		txtPhase.setPreferredSize(new Dimension(100, 20));
		txtPhase.setText("Phase: " + opponent.getPhase());
		oppInfoPanel.add(txtPhase, BorderLayout.WEST);
		
		JTextField numCardsPane = new JTextField();
		numCardsPane.setHorizontalAlignment(SwingConstants.CENTER);
		numCardsPane.setEditable(false);
		numCardsPane.setPreferredSize(new Dimension(13, 10));
		numCardsPane.setText("Cards in hand: " + opponent.getHand().getNumberOfCards());
		oppInfoPanel.add(numCardsPane, BorderLayout.CENTER);
		
		JTextField txtpnScore = new JTextField();
		txtpnScore.setHorizontalAlignment(SwingConstants.CENTER);
		txtpnScore.setEditable(false);
		txtpnScore.setPreferredSize(new Dimension(100, 20));
		txtpnScore.setText("Score: " + opponent.getScore());
		oppInfoPanel.add(txtpnScore, BorderLayout.EAST);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		
		addToPhase_1 = new JButton("Add to Phase");
		addToPhase_1.setVisible(false);
		
		JButton button_1 = new JButton("");
		button_1.setVisible(false);
		
		JLabel lblTo = new JLabel("to");
		lblTo.setVisible(false);
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnAddToPhase = new JButton("");
		btnAddToPhase.setVisible(false);
		
		JButton button = new JButton("");
		button.setVisible(false);
		
		JLabel label = new JLabel("to");
		label.setVisible(false);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton button_3 = new JButton("");
		button_3.setVisible(false);
		
		JButton button_4 = new JButton("Add to Phase");
		button_4.setVisible(false);
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
								.addComponent(btnAddToPhase, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
								.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
						.addComponent(addToPhase_1, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(10)
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
								.addComponent(button, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
							.addGap(2))
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
							.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
							.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
					.addGap(25))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblTo, GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnAddToPhase, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
								.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
							.addGap(64))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(125)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(addToPhase_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))))
					.addGap(114))
		);
		panel.setLayout(gl_panel);
		//end panel setup
	}
}
