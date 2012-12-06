package phase10.gui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import phase10.Player;
import phase10.ai.AIPlayer;

import java.awt.event.MouseAdapter;
import java.awt.Toolkit;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class SettingsFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	protected JTextField opponentField_1;
	protected JTextField opponentField_2;
	protected JTextField opponentField_3;
	private JButton btnRemovePlayer;
	private JRadioButton computerRadio1;
	private JRadioButton computerRadio2;
	private JRadioButton computerRadio3;
	private Language lang;
	private JComboBox<String> comboBox_1;
	private JComboBox<String> comboBox_2;
	private JComboBox<String> comboBox_3;
	private JLabel name1Label;
	private JLabel languageLabel;
	private JButton btnAddPlayer;
	private JButton beginButton;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingsFrame frame = new SettingsFrame(new GuiManager(new GameManager()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SettingsFrame(final GuiManager gManage) {
		
		lang = gManage.getGameLang();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(SettingsFrame.class.getResource("/images/GameIcon.png")));
		setResizable(false);
		setTitle(lang.getEntry("SETTINGS_FRAME_TITLE"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 570, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		nameField = new JTextField();
		nameField.setBounds(143, 61, 128, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);

		name1Label = new JLabel(lang.getEntry("YOUR_NAME"));
		name1Label.setBounds(27, 64, 92, 14);
		contentPane.add(name1Label);

		languageLabel = new JLabel(lang.getEntry("LANGUAGE"));
		languageLabel.setBounds(314, 64, 90, 14);
		contentPane.add(languageLabel);

		final JComboBox languageBox = new JComboBox();
		languageBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				System.out.println("Item state has changed!");
				if(languageBox.getSelectedItem().equals("English")) {
					gManage.setGameLang(new Language());
					updateLabelsForLanguage();
				}
				
				
				/*
				 * TODO Other language files may be added here: else if's
				 */
				
			}
		});
		languageBox.setModel(new DefaultComboBoxModel(new String[] {"English"/*, "Español", "Français", "Deutsch"*/}));
		languageBox.setBounds(438, 58, 92, 20);
		contentPane.add(languageBox);


		comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(438, 129, 79, 20);
		contentPane.add(comboBox_1);
		comboBox_1.addItem(lang.getEntry("EASY"));
		comboBox_1.addItem(lang.getEntry("MEDIUM"));
		comboBox_1.addItem(lang.getEntry("HARD"));

		comboBox_2 = new JComboBox<String>();
		comboBox_2.setVisible(false);
		comboBox_2.setBounds(438, 186, 79, 20);
		contentPane.add(comboBox_2);
		comboBox_2.addItem(lang.getEntry("EASY"));
		comboBox_2.addItem(lang.getEntry("MEDIUM"));
		comboBox_2.addItem(lang.getEntry("HARD"));

		comboBox_3 = new JComboBox<String>();
		comboBox_3.setVisible(false);
		comboBox_3.setBounds(438, 244, 79, 20);
		contentPane.add(comboBox_3);
		comboBox_3.addItem(lang.getEntry("EASY"));
		comboBox_3.addItem(lang.getEntry("MEDIUM"));
		comboBox_3.addItem(lang.getEntry("HARD"));

		JSeparator separator = new JSeparator();
		separator.setBounds(27, 104, 503, 2);
		contentPane.add(separator);

		opponentField_1 = new JTextField();
		opponentField_1.setBounds(143, 129, 128, 20);
		contentPane.add(opponentField_1);
		opponentField_1.setColumns(10);

		opponentField_2 = new JTextField();
		opponentField_2.setVisible(false);
		opponentField_2.setBounds(143, 186, 128, 20);
		contentPane.add(opponentField_2);
		opponentField_2.setColumns(10);

		opponentField_3 = new JTextField();
		opponentField_3.setVisible(false);
		opponentField_3.setBounds(143, 244, 128, 20);
		contentPane.add(opponentField_3);
		opponentField_3.setColumns(10);

		final JLabel lblOpponent = new JLabel(lang.getEntry("OPPONENT") + " 1:");
		lblOpponent.setBounds(27, 132, 92, 14);
		contentPane.add(lblOpponent);

		final JLabel lblOpponent_1 = new JLabel(lang.getEntry("OPPONENT") + " 2:");
		lblOpponent_1.setVisible(false);
		lblOpponent_1.setBounds(27, 185, 92, 14);
		contentPane.add(lblOpponent_1);

		final JLabel lblOpponent_2 = new JLabel(lang.getEntry("OPPONENT") + " 3:");
		lblOpponent_2.setVisible(false);
		lblOpponent_2.setBounds(27, 247, 92, 14);
		contentPane.add(lblOpponent_2);

		JTextArea txtrWelcomeToPhase = new JTextArea();
		txtrWelcomeToPhase.setEditable(false);
		txtrWelcomeToPhase.setWrapStyleWord(true);
		txtrWelcomeToPhase.setText(lang.getEntry("SETTINGS_FRAME_MESSAGE"));
		txtrWelcomeToPhase.setBounds(60, 11, 436, 39);
		contentPane.add(txtrWelcomeToPhase);

		/*
		 * BEGIN RADIO BUTTONS
		 */

		//opponent 1 set
		ButtonGroup opp1Group = new ButtonGroup();
		final JRadioButton humanRadio1 = new JRadioButton(lang.getEntry("HUMAN"));
		humanRadio1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				invisibleSetter(comboBox_1, false);
			}
		});
		humanRadio1.setBounds(295, 113, 109, 23);
		//radioButton_2.setVisible(false);
		opp1Group.add(humanRadio1);
		contentPane.add(humanRadio1);

		computerRadio1 = new JRadioButton(lang.getEntry("COMPUTER"));
		computerRadio1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				invisibleSetter(comboBox_1 ,true);
			}
		});
		computerRadio1.setSelected(true);
		computerRadio1.setBounds(295, 133, 109, 23);
		opp1Group.add(computerRadio1);
		contentPane.add(computerRadio1);

		//opponent 2 set
		ButtonGroup opp2Group = new ButtonGroup();
		final JRadioButton humanRadio2 = new JRadioButton(lang.getEntry("HUMAN"));
		humanRadio2.setVisible(false);
		humanRadio2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				invisibleSetter(comboBox_2 ,false);
			}
		});
		humanRadio2.setBounds(295, 178, 109, 23);
		opp2Group.add(humanRadio2);
		contentPane.add(humanRadio2);

		computerRadio2 = new JRadioButton(lang.getEntry("COMPUTER"));
		computerRadio2.setVisible(false);
		computerRadio2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				invisibleSetter(comboBox_2, true);
			}
		});
		computerRadio2.setSelected(true);
		computerRadio2.setBounds(295, 198, 109, 23);
		opp2Group.add(computerRadio2);
		contentPane.add(computerRadio2);

		//opponent 3 set
		ButtonGroup opp3Group = new ButtonGroup();
		final JRadioButton humanRadio3 = new JRadioButton(lang.getEntry("HUMAN"));
		humanRadio3.setVisible(false);
		humanRadio3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				invisibleSetter(comboBox_3, false);
			}
		});
		humanRadio3.setBounds(295, 238, 109, 23);
		opp3Group.add(humanRadio3);
		contentPane.add(humanRadio3);

		computerRadio3 = new JRadioButton(lang.getEntry("COMPUTER"));
		computerRadio3.setVisible(false);
		computerRadio3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				invisibleSetter(comboBox_3, true);
			}
		});
		computerRadio3.setSelected(true);
		computerRadio3.setBounds(295, 258, 109, 23);
		opp3Group.add(computerRadio3);
		contentPane.add(computerRadio3);

		/*
		 * END RADIO BUTTONS
		 */


		beginButton = new JButton(lang.getEntry("BEGIN"));
		beginButton.setBounds(227, 333, 109, 42);
		contentPane.add(beginButton);

		btnAddPlayer = new JButton(lang.getEntry("ADD_PLAYER"));
		btnAddPlayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(opponentField_2.isVisible() == false) {
					invisibleSetter(btnRemovePlayer,true);
					invisibleSetter(opponentField_2,true);
					invisibleSetter(humanRadio2, true);
					invisibleSetter(computerRadio2, true);
					invisibleSetter(lblOpponent_1, true);
					invisibleSetter(comboBox_2, true);
				}
				else if(opponentField_3.isVisible() == false) {
					invisibleSetter(opponentField_3,true);
					invisibleSetter(humanRadio3, true);
					invisibleSetter(computerRadio3, true);
					invisibleSetter(lblOpponent_2, true);
					invisibleSetter(comboBox_3, true);
					invisibleSetter(btnAddPlayer, false);
				}
				else {
					System.out.println("There was an error!");
				}
			}
		});
		btnAddPlayer.setBounds(27, 288, 123, 23);
		contentPane.add(btnAddPlayer);

		btnRemovePlayer = new JButton(lang.getEntry("REMOVE_LAST_OPPONENT"));
		btnRemovePlayer.setVisible(false);
		btnRemovePlayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(opponentField_3.isVisible() == true) {
					invisibleSetter(opponentField_3,false);
					invisibleSetter(humanRadio3, false);
					invisibleSetter(computerRadio3, false);
					invisibleSetter(lblOpponent_2, false);
					invisibleSetter(comboBox_3, false);
					invisibleSetter(btnAddPlayer, true);
				}
				else if(opponentField_2.isVisible() == true) {
					invisibleSetter(opponentField_2, false);
					invisibleSetter(humanRadio2, false);
					invisibleSetter(computerRadio2, false);
					invisibleSetter(lblOpponent_1, false);
					invisibleSetter(comboBox_2, false);
					invisibleSetter(btnRemovePlayer, false);
				}
				else {
					System.out.println("An internal error occured while trying to remove players!");
				}
			}
		});
		btnRemovePlayer.setBounds(181, 288, 191, 23);
		contentPane.add(btnRemovePlayer);
		beginButton.addMouseListener(new BeginListener(this, gManage));
	}

	int getDifficulty(JComboBox<String> menu) {
		if(menu.getSelectedItem() == lang.getEntry("EASY")) {
			return 25;
		}
		else if(menu.getSelectedItem() == lang.getEntry("MEDIUM")) {
			return 50;
		}
		else if(menu.getSelectedItem() == lang.getEntry("HARD")) {
			return 75;
		}
		else {
			System.out.println("There was an error in converting the selection of the difficulty to the difficulty value. Setting difficulty to medium");
			return 50;
		}	
	}

	public String getUserName() {
		return nameField.getText();
	}

	public String getOpponent_1() {
		return opponentField_1.getText();
	}

	public String getOpponent_2() {
		return opponentField_2.getText();
	}

	public String getOpponent_3() {
		return opponentField_3.getText();
	}

	public void invisibleSetter(Component comp, boolean isVisible) {
		comp.setVisible(isVisible);
	}
	
	private void updateLabelsForLanguage() {
		
		//TODO complete this method
		
		setTitle(lang.getEntry("SETTINGS_FRAME_TITLE"));
		name1Label.setText(lang.getEntry("YOUR_NAME"));
		languageLabel.setText(lang.getEntry("LANGUAGE"));
		btnRemovePlayer.setText(lang.getEntry("REMOVE_LAST_OPPONENT"));
		btnAddPlayer.setText(lang.getEntry("ADD_PLAYER"));
		beginButton.setText(lang.getEntry("BEGIN"));
	}

	private class BeginListener implements MouseListener {

		SettingsFrame settingsFrm;
		GuiManager gManage;

		public BeginListener(SettingsFrame settingsFrm, GuiManager gManage) {
			super();
			this.gManage = gManage;
			this.settingsFrm = settingsFrm;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
			//main user
			if(settingsFrm.getUserName().isEmpty()) {
				MessageFrame noName = new MessageFrame(lang.getEntry("NO_NAME_MESSAGE"), lang.getEntry("PHASE_10"), lang);
				noName.setVisible(true);
				return;
			}
			else {
				gManage.mainManager.getGame().addPlayer(new Player(gManage.mainManager.getGame(),settingsFrm.getUserName()));
			}
			
			//opponent 1
			if(settingsFrm.getOpponent_1().isEmpty()) {
				MessageFrame noName1 = new MessageFrame(lang.getEntry("NO_NAME_1_MESSAGE"), lang.getEntry("PHASE_10"), lang);
				noName1.setVisible(true);
				return;
			}
			else {
				if(computerRadio1.isSelected()) {
					gManage.mainManager.getGame().addPlayer(new AIPlayer(gManage.mainManager.getGame(), getDifficulty(comboBox_1), settingsFrm.getOpponent_1()));
				}
				else
					gManage.mainManager.getGame().addPlayer(new Player(gManage.mainManager.getGame(), settingsFrm.getOpponent_1()));
			}
			
			//opponent 2
			if(settingsFrm.opponentField_2.isVisible()) {
				if(settingsFrm.getOpponent_2().isEmpty()) {
					MessageFrame noName2 = new MessageFrame(lang.getEntry("NO_NAME_2_MESSAGE"), lang.getEntry("PHASE_10"), lang);
					noName2.setVisible(true);
					return;
				}
				else {
					if(computerRadio1.isSelected()) {
						gManage.mainManager.getGame().addPlayer(new AIPlayer(gManage.mainManager.getGame(), getDifficulty(comboBox_2), settingsFrm.getOpponent_2()));
					}
					else
						gManage.mainManager.getGame().addPlayer(new Player(gManage.mainManager.getGame(), settingsFrm.getOpponent_2()));
				}
			}
			
			//opponent 3
			if(settingsFrm.opponentField_3.isVisible()) {
				if(settingsFrm.getOpponent_3().isEmpty()) {
					MessageFrame noName3 = new MessageFrame(lang.getEntry("NO_NAME_3_MESSAGE"), lang.getEntry("PHASE_10"), lang);
					noName3.setVisible(true);
					return;
				}
				else {
					if(computerRadio1.isSelected()) {
						gManage.mainManager.getGame().addPlayer(new AIPlayer(gManage.mainManager.getGame(), getDifficulty(comboBox_3), settingsFrm.getOpponent_3()));
					}
					else
						gManage.mainManager.getGame().addPlayer(new Player(gManage.mainManager.getGame(), settingsFrm.getOpponent_3()));
				}
			}

			gManage.mainManager.getGame().startGame();
			//gManage.mainManager.getGame().getRound().startRound();
			gManage.initGameWindow();
			gManage.displayGameFrame(); //displays the next window: the game window
			
			

			settingsFrm.dispose();
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

	}
}
