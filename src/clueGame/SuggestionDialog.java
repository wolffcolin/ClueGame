package clueGame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SuggestionDialog extends JDialog {
	public SuggestionDialog(JFrame frame, String[] people, String[] weapons) {
		super(frame, "Make a Suggestion", true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300,200);
		setLayout(new GridLayout(4,2,10,10));
		
		add(new JLabel("Current Room:"));
		JLabel room = new JLabel("Lounge");
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
}
