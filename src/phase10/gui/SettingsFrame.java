package phase10.gui;


import java.awt.EventQueue;

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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import phase10.GameManager;
import phase10.Player;
import java.awt.event.MouseAdapter;
import java.awt.Toolkit;

public class SettingsFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField opponentField_1;
	private JTextField opponentField_2;
	private JTextField opponentField_3;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingsFrame frame = new SettingsFrame(new GuiManager(new GameManager()));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SettingsFrame(GuiManager gManage) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SettingsFrame.class.getResource("/images/GameIcon.png")));
		Language lang = gManage.getGameLang();
		
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

		JLabel name1Label = new JLabel(lang.getEntry("YOUR_NAME"));
		name1Label.setBounds(27, 64, 92, 14);
		contentPane.add(name1Label);

		JLabel languageLabel = new JLabel(lang.getEntry("LANGUAGE"));
		languageLabel.setBounds(314, 64, 90, 14);
		contentPane.add(languageLabel);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"English", "Español", "Français", "Deutsch"}));
		comboBox.setBounds(438, 58, 92, 20);
		contentPane.add(comboBox);

		JButton btnAddPlayer = new JButton(lang.getEntry("ADD_PLAYER"));
		btnAddPlayer.setBounds(143, 291, 109, 23);
		contentPane.add(btnAddPlayer);
		
		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(438, 129, 79, 20);
		contentPane.add(comboBox_1);
		comboBox_1.addItem(lang.getEntry("EASY"));
		comboBox_1.addItem(lang.getEntry("MEDIUM"));
		comboBox_1.addItem(lang.getEntry("HARD"));

		final JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(438, 186, 79, 20);
		contentPane.add(comboBox_2);
		comboBox_2.addItem(lang.getEntry("EASY"));
		comboBox_2.addItem(lang.getEntry("MEDIUM"));
		comboBox_2.addItem(lang.getEntry("HARD"));

		final JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(438, 244, 79, 20);
		contentPane.add(comboBox_3);
		comboBox_3.addItem(lang.getEntry("EASY"));
		comboBox_3.addItem(lang.getEntry("MEDIUM"));
		comboBox_3.addItem(lang.getEntry("HARD"));

		opponentField_1 = new JTextField();
		opponentField_1.setBounds(143, 129, 128, 20);
		contentPane.add(opponentField_1);
		opponentField_1.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(27, 104, 503, 2);
		contentPane.add(separator);

		opponentField_2 = new JTextField();
		opponentField_2.setBounds(143, 186, 128, 20);
		contentPane.add(opponentField_2);
		opponentField_2.setColumns(10);

		opponentField_3 = new JTextField();
		opponentField_3.setBounds(143, 244, 128, 20);
		contentPane.add(opponentField_3);
		opponentField_3.setColumns(10);

		JLabel lblOpponent = new JLabel(lang.getEntry("OPPONENT") + ": 1");
		lblOpponent.setBounds(27, 132, 92, 14);
		contentPane.add(lblOpponent);

		JLabel lblOpponent_1 = new JLabel(lang.getEntry("OPPONENT") + ": 2");
		lblOpponent_1.setBounds(27, 185, 92, 14);
		contentPane.add(lblOpponent_1);

		JLabel lblOpponent_2 = new JLabel(lang.getEntry("OPPONENT") + ": 3");
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
		JRadioButton radioButton = new JRadioButton(lang.getEntry("HUMAN"));
		radioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				invisibleSetter(comboBox_2 ,false);
			}
		});
		radioButton.setBounds(295, 178, 109, 23);
		opp1Group.add(radioButton);
		contentPane.add(radioButton);

		JRadioButton radioButton_1 = new JRadioButton(lang.getEntry("COMPUTER"));
		radioButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				invisibleSetter(comboBox_2, true);
			}
		});
		radioButton_1.setSelected(true);
		radioButton_1.setBounds(295, 198, 109, 23);
		opp1Group.add(radioButton_1);
		contentPane.add(radioButton_1);

		//opponent 2 set
		ButtonGroup opp2Group = new ButtonGroup();
		JRadioButton radioButton_2 = new JRadioButton(lang.getEntry("HUMAN"));
		radioButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				invisibleSetter(comboBox_1, false);
			}
		});
		//		private final Action action = new HumanRadioAction(null, null);
		//		radioButton_2.setAction(action);
		radioButton_2.setBounds(295, 113, 109, 23);
		opp2Group.add(radioButton_2);
		contentPane.add(radioButton_2);

		JRadioButton radioButton_3 = new JRadioButton(lang.getEntry("COMPUTER"));
		radioButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				invisibleSetter(comboBox_1 ,true);
			}
		});
		radioButton_3.setSelected(true);
		radioButton_3.setBounds(295, 133, 109, 23);
		opp2Group.add(radioButton_3);
		contentPane.add(radioButton_3);

		//opponent 3 set
		ButtonGroup opp3Group = new ButtonGroup();
		JRadioButton radioButton_4 = new JRadioButton(lang.getEntry("HUMAN"));
		radioButton_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				invisibleSetter(comboBox_3, false);
			}
		});
		radioButton_4.setBounds(295, 238, 109, 23);
		opp3Group.add(radioButton_4);
		contentPane.add(radioButton_4);

		JRadioButton radioButton_5 = new JRadioButton(lang.getEntry("COMPUTER"));
		radioButton_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				invisibleSetter(comboBox_3, true);
			}
		});
		radioButton_5.setSelected(true);
		radioButton_5.setBounds(295, 258, 109, 23);
		opp3Group.add(radioButton_5);
		contentPane.add(radioButton_5);
		
		/*
		 * END RADIO BUTTONS
		 */


		JButton beginButton = new JButton(lang.getEntry("BEGIN"));
		beginButton.setBounds(227, 333, 109, 42);
		contentPane.add(beginButton);
		beginButton.addMouseListener(new BeginListener(this, gManage));

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

			if(settingsFrm.getUserName() != "")
				gManage.mainManager.getGame().addPlayer(new Player(settingsFrm.getUserName())); //TODO Null pointer exception here
			if(settingsFrm.getOpponent_1() != "")
				gManage.mainManager.getGame().addPlayer(new Player(settingsFrm.getOpponent_1())); //TODO Null pointer exception here
			if(settingsFrm.getOpponent_1() != "")
				gManage.mainManager.getGame().addPlayer(new Player(settingsFrm.getOpponent_2())); //TODO Null pointer exception here
			if(settingsFrm.getOpponent_1() != "")
				gManage.mainManager.getGame().addPlayer(new Player(settingsFrm.getOpponent_2())); //TODO Null pointer exception here
			
			/*
			 * test declarations
			 */
//			System.out.println(settingsFrm.getUserName());
//			System.out.println(settingsFrm.getOpponent_1());
//			System.out.println(settingsFrm.getOpponent_2());
//			System.out.println(settingsFrm.getOpponent_3());

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
