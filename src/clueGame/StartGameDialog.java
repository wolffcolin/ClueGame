/*
Class: StartGameDialog
Description: Dialog box before game starts
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

package clueGame;

import javax.swing.JCheckBox;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartGameDialog extends JDialog {
	private boolean keepScore = false;
	private String message;
	
	//constructor
	public StartGameDialog(JFrame frame, String message) {
		super(frame, "Welcome to Clue", true);
		this.message = message;
		initialize();
	}
	
	//intialize values
	private void initialize() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        //provide padding around elements
        int padding = 10; 
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(padding, padding, padding, padding));
        
        //label for message
        JLabel messageLabel = new JLabel(message);
        getContentPane().add(messageLabel, BorderLayout.NORTH);
        
        //button panel for prompt
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        
        //prompt added
        buttonPanel.add(new JLabel("Would you like to keep score?"), BorderLayout.NORTH);
        
        //panel for actual buttons
        JPanel yesNoPanel = new JPanel();
        yesNoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        //yes button
        JButton yesButton = new JButton("Yes");
        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                keepScore = true; 
                ClueGame.setKeepScore(keepScore);
                dispose(); 
            }
        });
        yesNoPanel.add(yesButton);

        //no button
        JButton noButton = new JButton("No");
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                keepScore = false;
                ClueGame.setKeepScore(keepScore);
                dispose(); 
            }
        });
        
        //add other elements
        yesNoPanel.add(noButton);
        
        buttonPanel.add(yesNoPanel, BorderLayout.SOUTH);
        
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(yesButton);
        pack();
	}
	
}
