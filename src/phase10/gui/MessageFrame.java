package phase10.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MessageFrame extends JFrame {

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
					MessageFrame frame = new MessageFrame("This is a test!", "test frame");
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
	public MessageFrame(String message, String title) {
		
		setTitle(title);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		JButton okayButton = new JButton("Okay");
		okayButton.setBounds(158, 209, 107, 35);
		contentPane.add(okayButton);
		okayButton.addMouseListener(new OkayClicked(this));

	}
	
	private class OkayClicked implements MouseListener {
		
		JFrame msgFrame;
		

		public OkayClicked(JFrame msgFrame) {
			super();
			this.msgFrame = msgFrame;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			msgFrame.dispose();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}

	}
}
