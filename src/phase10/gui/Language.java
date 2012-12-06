/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10.gui;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import phase10.exceptions.Phase10LanguageException;

/**
 * This class handles the multi-language support for Phase10 game GUI
 * 
 * @author Evan Forbes
 *
 */
public class Language {

	public static final String DEFAULT_LANG = "lang/en.txt";

	private String name;
	private HashMap<String, String> entries;

	/**
	 * Sets up the default language (English)
	 */
	Language() {
		setLanguage(DEFAULT_LANG);
	}
	/**
	 * sets the language to the specified file
	 * 
	 * @param fileName
	 *            the file (usually something like "lang/en.txt"
	 * @return true if it was successful, false if there was a problem
	 */
	void setLanguage(String fileName) {
		try {
			Scanner file = new Scanner(new File(fileName), "UTF-8");
			name = file.next();
			entries = new HashMap<String, String>();

			while (file.hasNext()) {
				String id = file.next();
				file.next();
				String entry = file.nextLine();
				
				System.out.println(id+" "+entry);

				entries.put(id, entry.substring(1));
			}

			file.close();
		} catch (Exception e) {
			throw new Phase10LanguageException("Error setting language: " + e.getMessage());
		}
	}

	/**
	 * Gets the String entry from the language file
	 * 
	 * @param id
	 *            the id of the entry
	 * @return the entry
	 */
	String getEntry(String id) {
		try{
			return entries.get(id);
		}
		catch(Exception e){
			throw new Phase10LanguageException("Error getting entry: "+id+": "+e.getMessage());
		}
	}

	/**
	 * 
	 * @return the name of the language as specified in the file: "English"
	 */
	String getName() {
		return name;
	}
	
}
