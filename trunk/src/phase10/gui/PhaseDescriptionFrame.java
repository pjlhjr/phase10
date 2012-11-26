package phase10.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;

import phase10.Phase10;
import phase10.Player;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PhaseDescriptionFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/*
	 * All the Strings that will be displayed in this window.
	 */
	private String title;
	private String currentPhaseString;
	private String okayButtonLabel;
	private String phaseDescriptionString;
	
	GuiManager gManage;
	Language gLang;
	private JTextArea phaseDescription;
	
	/**
	 * Create the frame.
	 */
	public PhaseDescriptionFrame(int currentPhase, GuiManager gm) {
		
		gManage = gm;
		gLang = gManage.getGameLang();
		
		//TODO change constant String values to language variables
		initLanguage(gManage.getGameLang());
		
		title = gLang.getEntry("PD_FRAME_TITLE") + " " + currentPhase;
		currentPhaseString = gLang.getEntry("PHASE") + " " + currentPhase + ":";
		phaseDescriptionString = this.setPhaseDescriptionString(currentPhase);
		okayButtonLabel = "Okay";
		
		setTitle(title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 432, 264);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel phaseLabel = new JLabel(currentPhaseString);
		phaseLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		phaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		phaseLabel.setBounds(143, 11, 130, 52);
		contentPane.add(phaseLabel);
		
		JButton btnOkay = new JButton(okayButtonLabel);
		btnOkay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//next two commands cause the window to close
				setVisible(false);
				dispose();
			}
		});
		btnOkay.setBounds(163, 180, 89, 23);
		contentPane.add(btnOkay);
		
		phaseDescription = new JTextArea();
		phaseDescription.setFont(new Font("Microsoft YaHei", Font.ITALIC, 19));
		phaseDescription.setLineWrap(true);
		phaseDescription.setWrapStyleWord(true);
		phaseDescription.setEditable(false);
		phaseDescription.setText(phaseDescriptionString);
		phaseDescription.setBounds(108, 84, 230, 66);
		contentPane.add(phaseDescription);
	}
	
	void updateFrame(Player currentPlayer) {
		
		phaseDescription.setText(setPhaseDescriptionString(currentPlayer.getPhase()));
		
		
	}

	private void initLanguage(Language langSetter) {
		
		//TODO GET LANGUAGE FILE STRAIGHTENED UP THEN WORK THIS OUT!!!
		title = langSetter.getEntry("PD_FRAME_TITLE");
		currentPhaseString = langSetter.getEntry("CURRENT_PHASE");
		okayButtonLabel = langSetter.getEntry("OKAY");
		phaseDescriptionString = langSetter.getEntry("PHASE_1_STRING");
	}

	private String setPhaseDescriptionString(int phaseNum) {
		//TODO edit to add compatibility for other languages
		switch (phaseNum) {
		case 1:
			return gLang.getEntry("PHASE_1_STRING");
		case 2:
			return gLang.getEntry("PHASE_2_STRING");
		case 3:
			return gLang.getEntry("PHASE_3_STRING");
		case 4:
			return gLang.getEntry("PHASE_4_STRING");
		case 5:
			return gLang.getEntry("PHASE_5_STRING");
		case 6:
			return gLang.getEntry("PHASE_6_STRING");
		case 7:
			return gLang.getEntry("PHASE_7_STRING");
		case 8:
			return gLang.getEntry("PHASE_8_STRING");
		case 9:
			return gLang.getEntry("PHASE_9_STRING");
		case 10:
			return gLang.getEntry("PHASE_10_STRING");
		default:
			System.out.println("Error! Phase number is out of bounds!");
			return "Internal error occured! Phase number is out of bounds!";
		}
	}
}
