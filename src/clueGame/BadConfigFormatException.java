/*
 
Class: BadConfigFormatException
Description: Exception for bad format of config
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

package clueGame;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

public class BadConfigFormatException extends Exception {

	//logger to track errors
	private static final Logger LOG = Logger.getLogger(BadConfigFormatException.class.getName());

	//initialize logger and catch file errors
	static {
		try {
			FileHandler fileHandler = new FileHandler("error.log", true);
			fileHandler.setFormatter(new SimpleFormatter());
			LOG.addHandler(fileHandler);
		} catch (IOException e) {
			LOG.warning("Failed to access logger handler");
		}
	}

	//constructor w/ message
	public BadConfigFormatException(String message) {
		super(message);
		LOG.log(Level.SEVERE, message);
	}

	//standard constructor
	public BadConfigFormatException() {
		super("Bad Config Format in config files encountered");
		LOG.log(Level.SEVERE, "Bad Config Format in config files encountered");
	}
}
