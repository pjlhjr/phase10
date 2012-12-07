package phase10.gui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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

/**
 * 
 * SettingsFrame is a JFrame object that allows a user to set up a new game. SettingsFrame allows a user to
 * create a game with 1 to 4 players and to specify if the players are computer players or AI players. If they
 * are AI players, SettingsFrame will also allow the user to specify the difficulty as either "EASY", "MEDIUM",
 * or "HARD". The user may also select from a variety of languages.
 * 
 * @author Matthew Hruz
 *
 */
public class SettingsFrame extends JFrame {

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
	private JLabel nameLabel;
	private JLabel languageLabel;
	private JButton btnAddPlayer;
	private JButton beginButton;
	private JRadioButton humanRadio1;
	private JRadioButton humanRadio2;
	private JRadioButton humanRadio3;
	private JLabel opponentLabel_1;
	private JLabel opponentLabel_2;
	private JLabel opponentLabel_3;
	private JTextArea txtrWelcomeToPhase;

	/**
	 * constructs the SettingFrame.
	 * 
	 * @param a reference to the game's GuiManager
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SettingsFrame(final GuiManager gManage) {
		setResizable(false);

		lang = gManage.getGameLang();

		setIconImage(Toolkit.getDefaultToolkit().getImage(SettingsFrame.class.getResource("/images/GameIcon.png")));
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

		nameLabel = new JLabel(lang.getEntry("YOUR_NAME"));
		nameLabel.setBounds(27, 64, 92, 14);
		contentPane.add(nameLabel);

		languageLabel = new JLabel(lang.getEntry("LANGUAGE"));
		languageLabel.setBounds(314, 64, 90, 14);
		contentPane.add(languageLabel);

		final JComboBox languageBox = new JComboBox();
		languageBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(languageBox.getSelectedItem().equals("English")) {
					gManage.getGameLang().setLanguage("lang/en.txt");
					updateLabelsForLanguage();
				}
				else if(languageBox.getSelectedItem().equals("Español")) {
					gManage.getGameLang().setLanguage("lang/es.txt");
					updateLabelsForLanguage();
				}
				else if(languageBox.getSelectedItem().equals("Deutsch")) {
					gManage.getGameLang().setLanguage("lang/de.txt");
					updateLabelsForLanguage();
				}	
			}
		});
		languageBox.setModel(new DefaultComboBoxModel(new String[] {"English", "Español", "Deutsch"}));
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

		opponentLabel_1 = new JLabel(lang.getEntry("OPPONENT") + " 1:");
		opponentLabel_1.setBounds(27, 132, 92, 14);
		contentPane.add(opponentLabel_1);

		opponentLabel_2 = new JLabel(lang.getEntry("OPPONENT") + " 2:");
		opponentLabel_2.setVisible(false);
		opponentLabel_2.setBounds(27, 185, 92, 14);
		contentPane.add(opponentLabel_2);

		opponentLabel_3 = new JLabel(lang.getEntry("OPPONENT") + " 3:");
		opponentLabel_3.setVisible(false);
		opponentLabel_3.setBounds(27, 247, 92, 14);
		contentPane.add(opponentLabel_3);

		txtrWelcomeToPhase = new JTextArea();
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
		humanRadio1 = new JRadioButton(lang.getEntry("HUMAN"));
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
		humanRadio2 = new JRadioButton(lang.getEntry("HUMAN"));
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
		humanRadio3 = new JRadioButton(lang.getEntry("HUMAN"));
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
					invisibleSetter(opponentLabel_2, true);
					invisibleSetter(comboBox_2, true);
				}
				else if(opponentField_3.isVisible() == false) {
					invisibleSetter(opponentField_3,true);
					invisibleSetter(humanRadio3, true);
					invisibleSetter(computerRadio3, true);
					invisibleSetter(opponentLabel_3, true);
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
					invisibleSetter(opponentLabel_3, false);
					invisibleSetter(comboBox_3, false);
					invisibleSetter(btnAddPlayer, true);
				}
				else if(opponentField_2.isVisible() == true) {
					invisibleSetter(opponentField_2, false);
					invisibleSetter(humanRadio2, false);
					invisibleSetter(computerRadio2, false);
					invisibleSetter(opponentLabel_2, false);
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
		beginButton.addActionListener(new BeginListener(this, gManage));
	}

	/**
	 * returns the selected difficulty from a menu as a difficulty value.
	 * EASY = 25
	 * MEDIUM = 50
	 * HARD = 75
	 * 
	 * @param menu the specific menu component that the value will be extracted from
	 * @return a value indicating the difficulty of the AI player.
	 */
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

	/**
	 * returns the main user's name from the first name field
	 * @return the user's name
	 */
	public String getUserName() {
		return nameField.getText();
	}

	/**
	 * returns the first opponent's name from the second name field
	 * @return the first opponent's name
	 */
	public String getOpponent_1() {
		return opponentField_1.getText();
	}
	
	/**
	 * returns the second opponnent's name from the third name field
	 * @return the second opponent's name
	 */
	public String getOpponent_2() {
		return opponentField_2.getText();
	}

	/**
	 * returns the third opponent's name from the fourth name field
	 * @return the third opponent's name
	 */
	public String getOpponent_3() {
		return opponentField_3.getText();
	}

	/**
	 * sets a specified component as visible or invisible
	 * @param comp the component
	 * @param isVisible if true the component will be set as visible. If false the component will be set as invisible
	 */
	public void invisibleSetter(Component comp, boolean isVisible) {
		comp.setVisible(isVisible);
	}
	
	/**
	 * updates all text-displaying components of SettingsFrame to the latest language
	 */
	private void updateLabelsForLanguage() {

		setTitle(lang.getEntry("SETTINGS_FRAME_TITLE"));

		txtrWelcomeToPhase.setText(lang.getEntry("SETTINGS_FRAME_MESSAGE"));
		nameLabel.setText(lang.getEntry("YOUR_NAME"));
		languageLabel.setText(lang.getEntry("LANGUAGE"));
		btnRemovePlayer.setText(lang.getEntry("REMOVE_LAST_OPPONENT"));
		btnAddPlayer.setText(lang.getEntry("ADD_PLAYER"));
		beginButton.setText(lang.getEntry("BEGIN"));
		humanRadio1.setText(lang.getEntry("HUMAN"));
		humanRadio2.setText(lang.getEntry("HUMAN"));
		humanRadio3.setText(lang.getEntry("HUMAN"));
		computerRadio1.setText(lang.getEntry("COMPUTER"));
		computerRadio2.setText(lang.getEntry("COMPUTER"));
		computerRadio3.setText(lang.getEntry("COMPUTER"));


		comboBox_1.removeAllItems();
		comboBox_1.addItem(lang.getEntry("EASY"));
		comboBox_1.addItem(lang.getEntry("MEDIUM"));
		comboBox_1.addItem(lang.getEntry("HARD"));

		comboBox_2.removeAllItems();
		comboBox_2.addItem(lang.getEntry("EASY"));
		comboBox_2.addItem(lang.getEntry("MEDIUM"));
		comboBox_2.addItem(lang.getEntry("HARD"));

		comboBox_3.removeAllItems();
		comboBox_3.addItem(lang.getEntry("EASY"));
		comboBox_3.addItem(lang.getEntry("MEDIUM"));
		comboBox_3.addItem(lang.getEntry("HARD"));

		nameLabel.setText(lang.getEntry("YOUR_NAME"));
		opponentLabel_1.setText(lang.getEntry("OPPONENT") + " 1:");
		opponentLabel_2.setText(lang.getEntry("OPPONENT") + " 2:");
		opponentLabel_3.setText(lang.getEntry("OPPONENT") + " 3:");
	}
	
	/**
	 * ActionListener for the "Begin" button.
	 * 
	 * @author Matthew Hruz
	 *
	 */
	private class BeginListener implements ActionListener {

		SettingsFrame settingsFrm;
		GuiManager gManage;

		/**
		 * Constructor for BeginListener
		 * 
		 * @param settingsFrm a SettingsFrame object
		 * @param gManage a reference to the GuiManager
		 */
		public BeginListener(SettingsFrame settingsFrm, GuiManager gManage) {
			super();
			this.gManage = gManage;
			this.settingsFrm = settingsFrm;
		}

		/**
		 * Clicking the begin button will call this method. This method is in charge of validating all of the input and
		 * sending the data entered in settings frame to the GuiManager and the current game so that a game can begin. If
		 * everything is valid, a new game will begin and a GameFrame will appear after this button is clicked.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {

			//main user
			if(settingsFrm.getUserName().isEmpty()) {
				MessageFrame noName = new MessageFrame(lang.getEntry("NO_NAME_MESSAGE"), lang.getEntry("PHASE_10"), lang);
				noName.setVisible(true);
				return;
			}
			else if(settingsFrm.getOpponent_1().isEmpty()) {
				MessageFrame noName1 = new MessageFrame(lang.getEntry("NO_NAME_1_MESSAGE"), lang.getEntry("PHASE_10"), lang);
				noName1.setVisible(true);
				return;
			}
			else if(settingsFrm.opponentField_2.isVisible()) {
				if(settingsFrm.getOpponent_2().isEmpty()) {
					MessageFrame noName2 = new MessageFrame(lang.getEntry("NO_NAME_2_MESSAGE"), lang.getEntry("PHASE_10"), lang);
					noName2.setVisible(true);
					return;
				}
			}
			else if(settingsFrm.opponentField_3.isVisible()) {
				if(settingsFrm.getOpponent_3().isEmpty()) {
					MessageFrame noName3 = new MessageFrame(lang.getEntry("NO_NAME_3_MESSAGE"), lang.getEntry("PHASE_10"), lang);
					noName3.setVisible(true);
					return;
				}
			}

			//main user
			gManage.mainManager.getGame().addPlayer(new Player(gManage.mainManager.getGame(),settingsFrm.getUserName()));

			//opponent 1
			if(computerRadio1.isSelected()) {
				gManage.mainManager.getGame().addPlayer(new AIPlayer(gManage.mainManager.getGame(), getDifficulty(comboBox_1), settingsFrm.getOpponent_1()));
			}
			else {
				gManage.mainManager.getGame().addPlayer(new Player(gManage.mainManager.getGame(), settingsFrm.getOpponent_1()));
			}

			//opponent 2
			if(!settingsFrm.getOpponent_2().isEmpty()) {
				if(computerRadio2.isSelected()) {
					gManage.mainManager.getGame().addPlayer(new AIPlayer(gManage.mainManager.getGame(), getDifficulty(comboBox_2), settingsFrm.getOpponent_2()));
				}
				else
					gManage.mainManager.getGame().addPlayer(new Player(gManage.mainManager.getGame(), settingsFrm.getOpponent_2()));
			}
			//opponent 3
			if(!settingsFrm.getOpponent_3().isEmpty()) {
				if(computerRadio3.isSelected()) {
					gManage.mainManager.getGame().addPlayer(new AIPlayer(gManage.mainManager.getGame(), getDifficulty(comboBox_3), settingsFrm.getOpponent_3()));
				}
				else{
					gManage.mainManager.getGame().addPlayer(new Player(gManage.mainManager.getGame(), settingsFrm.getOpponent_3()));
				}
			}

			gManage.mainManager.getGame().startGame();
			gManage.initGameWindow();
			gManage.displayGameFrame(); //displays the next window: the game window

			settingsFrm.dispose();
		}

	}
}
