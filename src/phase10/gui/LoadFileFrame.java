package phase10.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

/**
 * 
 * A JDialog box that allows a player to load a game by inputing the 
 * name of a previously saved game into the JTextField.
 * 
 * @author Matthew Hruz
 *
 */
public class LoadFileFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField filenameField;
	private String filename;
	private GuiManager gManage;
	private Language gameLang;

	/**
	 * Retrieves the filename entered in the JTextField
	 * 
	 * @return the filename in the JTextField
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Creates the dialog box.
	 */
	public LoadFileFrame(GuiManager guiM) {
		gameLang = guiM.getGameLang();
		
		setTitle(gameLang.getEntry("LOAD_GAME"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(SaveFileFrame.class.getResource("/images/GameIcon.png")));

		gManage = guiM;

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			filenameField = new JTextField();
			filenameField.setBounds(142, 204, 287, 20);
			contentPanel.add(filenameField);
			filenameField.setColumns(10);
		}
		{
			JTextPane promptField = new JTextPane();
			promptField.setBounds(5, 204, 122, 20);
			promptField.setEditable(false);
			promptField.setText(gameLang.getEntry("ENTER_A_FILENAME"));
			contentPanel.add(promptField);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(gameLang.getEntry("OKAY"));
				okButton.addActionListener(new OKListener());
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton(gameLang.getEntry("CANCEL"));
				cancelButton.addActionListener(new CancelListener());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	/**
	 * ActionListener class for the OK button
	 * 
	 * @author Matthew Hruz
	 *
	 */
	private class OKListener implements ActionListener {

		/**
		 * called when the OK button is clicked. When the button is clicked, the
		 * ActionListener tries to load the file entered into the JTextField. If
		 * it fails to do so, an error message will be displayed and the user can 
		 * go back and try again.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			filename = filenameField.getText();
			boolean flag = gManage.mainManager.loadGame(filename);

			if(flag == false) 
			{
				dispose();
				LoadFileFrame tryAgain = new LoadFileFrame(gManage);
				tryAgain.setVisible(true);
				MessageFrame invalidFile = new MessageFrame(gameLang.getEntry("INVALID_FILE_MESSAGE"), gameLang.getEntry("INVALID_FILENAME"), gameLang);
				invalidFile.setVisible(true);
			}
			else {
				gManage.initGameWindow();
				dispose();
			}
		
			
		}
		
	}
	
	/**
	 * ActionListener for the cancel button.
	 * 
	 * @author Matthew Hruz
	 *
	 */
	private class CancelListener implements ActionListener {

		/**
		 * Called when the cancel button is clicked. ActionListener will dispose the current instance
		 * of LoadFileFrame and will bring the user back to WelcomeFrame.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			gManage.initGui();
			dispose();
		}
		
	}
}
