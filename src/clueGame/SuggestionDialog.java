/*
 
Class: SuggestionDialog
Description: Dialog for when suggestion needs to be made
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

package clueGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SuggestionDialog extends JDialog {
	private Board board = Board.getInstance();
	private static Solution chosenSuggestion;

	//constructor
	public SuggestionDialog(JFrame frame, String currentRoom, String[] people, String[] weapons) {
		super(frame, "Make a Suggestion", true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 200);
		setLayout(new GridLayout(4, 2, 10, 10));

		//label for current room + combo box
		add(new JLabel("Current Room:"));
		JLabel room = new JLabel(currentRoom);
		add(room);

		//label for person + combo box
		add(new JLabel("Person:"));
		JComboBox<String> person = new JComboBox<>(people);
		add(person);

		//label for weapon + combo box
		add(new JLabel("Weapon:"));
		JComboBox<String> weapon = new JComboBox<>(weapons);
		add(weapon);

		//submit button sets chosen suggestion to what was selected
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Card roomCard = new Card((String) room.getText(), CardType.ROOM);
				Card playerCard = new Card((String) person.getSelectedItem(), CardType.PERSON);
				Card weaponCard = new Card((String) weapon.getSelectedItem(), CardType.WEAPON);

				chosenSuggestion = new Solution(roomCard, playerCard, weaponCard);

				dispose();
				dispose();
			}
		});
		add(submit);

		//cancels suggestion
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public Solution getSuggestion() {
		return chosenSuggestion;
	}
}
