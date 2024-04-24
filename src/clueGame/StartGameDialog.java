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
	
	public StartGameDialog(JFrame frame, String message) {
		super(frame, "Welcome to Clue", true);
		this.message = message;
		initialize();
	}
	
	private void initialize() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        int padding = 10; // Padding in pixels
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(padding, padding, padding, padding));
        
        JLabel messageLabel = new JLabel(message);
        getContentPane().add(messageLabel, BorderLayout.NORTH);
        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        
        buttonPanel.add(new JLabel("Would you like to keep score?"), BorderLayout.NORTH);
        
        JPanel yesNoPanel = new JPanel();
        yesNoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        JButton yesButton = new JButton("Yes");
        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                keepScore = true; 
                ClueGame.setKeepScore(keepScore);
                dispose(); 
            }
        });
        yesNoPanel.add(yesButton);

        JButton noButton = new JButton("No");
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                keepScore = false;
                ClueGame.setKeepScore(keepScore);
                dispose(); 
            }
        });
        yesNoPanel.add(noButton);
        
        buttonPanel.add(yesNoPanel, BorderLayout.SOUTH);
        
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(yesButton);
        pack();
	}
	
}
