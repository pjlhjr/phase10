package phase10.gui;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

public class FileLoaderFrame extends JFileChooser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		FileLoaderFrame loader = new FileLoaderFrame(new GuiManager());
//	}
	
	public FileLoaderFrame(GuiManager gManage) {
		
		//window properties
		setMultiSelectionEnabled(false);
		setSize(600, 400);
		
		//selection option
		int returnVal = showOpenDialog(getParent());
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File loadedFile = getSelectedFile();
			gManage.mainManager.loadGame(loadedFile.getName());
			System.out.println("Absolute Path: " + getSelectedFile().getAbsolutePath());
			try {
				System.out.println("Cannonical Path: " + getSelectedFile().getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(returnVal == JFileChooser.CANCEL_OPTION) {
			WelcomeFrame welcomer = new WelcomeFrame(gManage);
			welcomer.setVisible(true);
		}
	}
}
