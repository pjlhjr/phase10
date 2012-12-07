package phase10.gui;


import phase10.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import java.awt.Point;

public class ScoreFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public ScoreFrame(GuiManager gManage) {
		
		//Create a variable to access the game's language
		Language gameLang = gManage.getGameLang();
		
		setLocation(new Point(200, 200));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ScoreFrame.class.getResource("/images/GameIcon.png")));
		setResizable(false);
		setTitle(gameLang.getEntry("SCORES_AFTER_ROUND") + " " + gManage.mainManager.getGame().getRoundNumber());
		setBounds(100, 100, 458, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		int numPlayers = gManage.mainManager.getGame().getNumberOfPlayers();
		
		Player sortedPlayers[] = new Player[numPlayers];
		for(int i = 0; i < numPlayers; i++) {
			sortedPlayers[i] = gManage.mainManager.getGame().getPlayer(i);
		}
		
		sortedPlayers = sortPlayers(sortedPlayers);
		Object[][] playersArray = new Object[sortedPlayers.length][4];
		for(int i = 0; i < sortedPlayers.length; i++){
			playersArray[i][0] = i+1;
			playersArray[i][1] =  sortedPlayers[i].getName();
			playersArray[i][2] = sortedPlayers[i].getPhase();
			playersArray[i][3] = sortedPlayers[i].getScore();
		}
		
		table = new JTable();
		table.setRowHeight(48);
		table.setShowHorizontalLines(true);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
			playersArray,
			new String[] {
				gameLang.getEntry("PLACE"), gameLang.getEntry("NAME"), gameLang.getEntry("CURRENT_PHASE"), gameLang.getEntry("SCORE")
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Integer.class, Integer.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(85);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.setBounds(36, 54, 370, 195);
		contentPane.add(table);
		
		JLabel lblPlace = new JLabel(gameLang.getEntry("PLACE"));
		lblPlace.setBounds(53, 29, 64, 14);
		contentPane.add(lblPlace);
		
		JLabel lblName = new JLabel(gameLang.getEntry("NAME"));
		lblName.setBounds(127, 29, 83, 14);
		contentPane.add(lblName);
		
		JLabel lblCurrentPhase = new JLabel(gameLang.getEntry("CURRENT_PHASE"));
		lblCurrentPhase.setBounds(220, 29, 88, 14);
		contentPane.add(lblCurrentPhase);
		
		JLabel lblScore = new JLabel(gameLang.getEntry("SCORE"));
		lblScore.setBounds(318, 29, 61, 14);
		contentPane.add(lblScore);
		
		JButton btnOkay = new JButton(gameLang.getEntry("OKAY"));
		btnOkay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		btnOkay.setBounds(174, 300, 89, 23);
		contentPane.add(btnOkay);
	}
	
	private Player[] sortPlayers(Player[] thePlayers) {

		for(int j = thePlayers.length-1; j > 0; j--){
			int max = thePlayers[0].getScore();
			Player pMax = thePlayers[0];
			int maxIndex = 0;
			for(int i = 1; i <= j; i++) {
				if(thePlayers[i].getScore() > max) {
					max = thePlayers[i].getScore();
					pMax = thePlayers[i];
					maxIndex = i;
				}
			}
			thePlayers[maxIndex] = thePlayers[j];
			thePlayers[j] = pMax;
		}
		return thePlayers;
	}
}
