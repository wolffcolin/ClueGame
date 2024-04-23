package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AccusationDialog extends JDialog {
	private Board board = Board.getInstance();
	
	public AccusationDialog(JFrame frame, ArrayList<String> peopleList, ArrayList<String> roomsList, ArrayList<String> weaponsList) {
		super(frame, "Make an Accusation", true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300,200);
		setLayout(new GridLayout(4,2,10,10));
		
		String[] rooms = roomsList.toArray(new String[0]);
		String[] people = peopleList.toArray(new String[0]);
		String[] weapons = weaponsList.toArray(new String[0]);
		
		add(new JLabel("Current Room:"));
		JComboBox<String> room = new JComboBox<>(rooms);
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
				Card roomCard = new Card((String)room.getSelectedItem(), CardType.ROOM);
				Card playerCard = new Card((String)person.getSelectedItem(), CardType.PERSON);
				Card weaponCard = new Card((String)weapon.getSelectedItem(), CardType.WEAPON);
				
				Solution accusation = new Solution(roomCard, playerCard, weaponCard);
				board.handleAccusation(accusation);
				
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
		add(cancel);
	}
}
