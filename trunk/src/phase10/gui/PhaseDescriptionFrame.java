package phase10.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
	private String okayButtonLabel;
	GuiManager gManage;
	Language gLang;
	private JTable table;
	
	/**
	 * Create the frame.
	 */
	public PhaseDescriptionFrame(GuiManager gm) {
		setResizable(false);
		
		gManage = gm;
		gLang = gManage.getGameLang();
		
		//TODO change constant String values to language variables
		initLanguage(gManage.getGameLang());
		
		int currentPhase = gManage.mainManager.getGame().getCurrentPlayer().getPhase();
		
		title = gLang.getEntry("PD_FRAME_TITLE") + " " + currentPhase;
		okayButtonLabel = "Okay";
		
		setTitle(title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 471, 488);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel phaseLabel = new JLabel("Phase Descriptions");
		phaseLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		phaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		phaseLabel.setBounds(83, 11, 254, 52);
		contentPane.add(phaseLabel);
		
		JButton btnOkay = new JButton(okayButtonLabel);
		btnOkay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//next two commands cause the window to close
				setVisible(false);
				dispose();
			}
		});
		btnOkay.setBounds(171, 402, 89, 23);
		contentPane.add(btnOkay);
		
		//Initialize table contents
		String[][] phaseDescriptionTable = new String[10][gm.mainManager.getGame().getNumberOfPlayers() + 2];
		for(int r = 0; r < 10; r++) {
			phaseDescriptionTable[r][0] = Integer.toString(r + 1);
			phaseDescriptionTable[r][1] = setPhaseDescriptionString(r+1);
			for(int c = 2; c < phaseDescriptionTable[r].length; c++) {
				if(gm.mainManager.getGame().getPlayer(c-2).getPhase() == r+1){
					phaseDescriptionTable[r][c] = "X";
				}
				else {
					phaseDescriptionTable[r][c] = "";
				}
			}
		}
		
		//end table contents
		
		
		//initialize table header
		String[] tableHead = new String[gm.mainManager.getGame().getNumberOfPlayers() + 2];
		tableHead[0] = "Phase #";
		tableHead[1] = "Phase Description";
		
		for(int i = 0; i < gm.mainManager.getGame().getNumberOfPlayers(); i++) {
			tableHead[i+2] = gm.mainManager.getGame().getPlayer(i).getName();
		}
		//end table header
		
		table = new JTable(phaseDescriptionTable, tableHead);
		table.setModel(new DefaultTableModel(
			phaseDescriptionTable, tableHead
		));
		table.getColumnModel().getColumn(1).setPreferredWidth(180);
		table.getColumnModel().getColumn(1).setMinWidth(150);
		table.setBounds(10, 128, 445, 220);
		contentPane.add(table);
		contentPane.add(table.getTableHeader());
	}
	private void initLanguage(Language langSetter) {
		
		//TODO GET LANGUAGE FILE STRAIGHTENED UP THEN WORK THIS OUT!!!
		title = langSetter.getEntry("PD_FRAME_TITLE");
		langSetter.getEntry("CURRENT_PHASE");
		okayButtonLabel = langSetter.getEntry("OKAY");
		langSetter.getEntry("PHASE_1_STRING");
	}

	private String setPhaseDescriptionString(int phaseNum) {
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
