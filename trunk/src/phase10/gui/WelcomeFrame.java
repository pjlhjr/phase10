package phase10.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

/**
 * WelcomeFrame is a JFrame object that is displayed when Phase 10 first initializes GUI and at the end of the game if the
 * user would like to play another game. WelcomeFrame displays a "Welcome to Phase 10!" welcome banner image and gives the
 * user 3 options: New Game, Load Game, and Cancel.
 * 
 * @author Matthew Hruz
 *
 */
public class WelcomeFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Constructs and initializes the welcome frame.
	 */
	public WelcomeFrame(final GuiManager gManage) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(WelcomeFrame.class.getResource("/images/GameIcon.png")));
		setTitle("Welcome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 491, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(null);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gManage.mainManager.newGame();
				gManage.displaySettingsFrame();
				dispose();
			}
		});
		btnNewGame.setBounds(28, 224, 120, 52);
		contentPane.add(btnNewGame);
		
		JButton btnLoadGame = new JButton("Load Game");
		btnLoadGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoadFileFrame loader = new LoadFileFrame(gManage);
				loader.setVisible(true);
				dispose();
			}
		});
		btnLoadGame.setBounds(176, 224, 120, 52);
		contentPane.add(btnLoadGame);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(324, 224, 120, 52);
		contentPane.add(btnCancel);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setIcon(new ImageIcon(WelcomeFrame.class.getResource("/images/WelcomeBanner.png")));
		lblNewLabel.setBounds(32, 27, 426, 143);
		contentPane.add(lblNewLabel);
	}
}
