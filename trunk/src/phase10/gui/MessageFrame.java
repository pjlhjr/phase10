package phase10.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

/**
 * A simple JFrame class that will display a message to the user. The message displays in a JTextField.
 * The frame also includes a JButton titled "okay". Clicking that button will dispose the frame.
 * 
 * @author Matthew Hruz
 */
public class MessageFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	/**
	 * Simple constructor for MessageFrame. Creates a simple frame using the specified
	 * parameters. The Okay button will always display "Okay", as the gameLang will
	 * be assumed to be English.
	 * 
	 * @param message the text that will be displayed to the user
	 * @param title the title of the frame
	 */
	public MessageFrame(String message, String title) {
		this(message, title, new Language());
	}

	/**
	 * Constructor for MessageFrame. Creates a simple frame using the specified parameters
	 * 
	 * @param message The text that will be displayed to the user.
	 * @param title The title of the frame
	 * @param gameLang the language the current game is using
	 */
	public MessageFrame(String message, String title, Language gameLang) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MessageFrame.class.getResource("/images/GameIcon.png")));
		
		setTitle(title);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextPane messagePane = new JTextPane();
		messagePane.setEditable(false);
		messagePane.setText(message);
		messagePane.setBounds(54, 39, 315, 125);
		contentPane.add(messagePane);

		JButton okayButton = new JButton(gameLang.getEntry("OKAY"));
		okayButton.setBounds(158, 209, 107, 35);
		contentPane.add(okayButton);
		okayButton.addActionListener(new OkayClicked());

	}
	
	/**
	 * ActionListener for the Okay button.
	 * 
	 * @author Matthew Hruz
	 *
	 */
	private class OkayClicked implements ActionListener {
		
		/**
		 * called when the Okay button is clicked. Disposes the message frame.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}

	}
}
