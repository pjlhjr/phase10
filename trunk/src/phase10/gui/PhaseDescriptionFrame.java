package phase10.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PhaseDescriptionFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PhaseDescriptionFrame frame = new PhaseDescriptionFrame(5);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * All the Strings that will be displayed in this window.
	 */
	private String title;
	private String currentPhaseString;
	private String okayButtonLabel;
	private String phaseDescriptionString;
	
	/**
	 * Create the frame.
	 */
	public PhaseDescriptionFrame(int currentPhase) {
		
		//TODO change constant String values to language variables
		initLanguage();
		
		title = "Phase " + currentPhase + " Description";
		currentPhaseString = "Phase " + currentPhase + ":";
		phaseDescriptionString = this.setPhaseDescriptionString(currentPhase);
		okayButtonLabel = "Okay";
		
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JTextArea phaseDescription = new JTextArea();
		phaseDescription.setFont(new Font("Microsoft YaHei", Font.ITALIC, 19));
		phaseDescription.setLineWrap(true);
		phaseDescription.setWrapStyleWord(true);
		phaseDescription.setEditable(false);
		phaseDescription.setText(phaseDescriptionString);
		phaseDescription.setBounds(108, 84, 230, 66);
		contentPane.add(phaseDescription);
	}

	private void initLanguage() {
		
		//TODO GET LANGUAGE FILE STRAIGHTENED UP THEN WORK THIS OUT!!!
		Language langSetter = new Language();
		title = langSetter.getEntry(61);
		currentPhaseString = langSetter.getEntry(62);
		okayButtonLabel = langSetter.getEntry(id);
		phaseDescriptionString = langSetter.getEntry(id);
	}

	private String setPhaseDescriptionString(int phaseNum) {
		//TODO edit to add compatibility for other languages
		switch (phaseNum) {
		case 1:
			return "2 sets of 3";
		case 2:
			return "1 set of 3 and 1 run of 4";
		case 3:
			return "1 set of 4 and 1 run of 4";
		case 4:
			return "1 run of 7";
		case 5:
			return "1 run of 8";
		case 6:
			return "1 run of 9";
		case 7:
			return "2 sets of 4";
		case 8:
			return "7 cards of 1 color";
		case 9:
			return "1 set of 5 and 1 set of 2";
		case 10:
			return "1 set of 5 and 1 set of 3";
		default:
			System.out.println("Error! Phase number is out of bounds!");
			return "Internal error occured! Phase number is out of bounds!";
		}
	}
}
