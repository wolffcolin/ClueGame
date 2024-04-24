package clueGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SuggestionDialog extends JDialog {
	private Board board = Board.getInstance();
	private static Solution chosenSuggestion;

	public SuggestionDialog(JFrame frame, String currentRoom, String[] people, String[] weapons) {
		super(frame, "Make a Suggestion", true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 200);
		setLayout(new GridLayout(4, 2, 10, 10));

		add(new JLabel("Current Room:"));
		JLabel room = new JLabel(currentRoom);
		add(room);

		add(new JLabel("Person:"));
		JComboBox<String> person = new JComboBox<>(people);
		add(person);

		add(new JLabel("Weapon:"));
		JComboBox<String> weapon = new JComboBox<>(weapons);
		add(weapon);

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

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public static Solution getSuggestion() {
		return chosenSuggestion;
	}
}
