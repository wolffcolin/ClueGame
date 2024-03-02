/*
 
Class: BadConfigFormatException
Description: Exception for bad format of config
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

package clueGame;

public class BadConfigFormatException extends Exception {
    public BadConfigFormatException(String message) {
        super(message);
    }
}
