package clueGame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.*;
import java.awt.*;

public class KnownCardPanel extends JPanel {

	public KnownCardPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(createSectionPanel("People"));
		add(createSectionPanel("Rooms"));
		add(createSectionPanel("Weapons"));
		
	}
	
	private JPanel createSectionPanel(String title) {
		JPanel sectionPanel = new JPanel(new BorderLayout());
		
		Border titleBorder = BorderFactory.createTitledBorder(title);
		Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		sectionPanel.setBorder(BorderFactory.createCompoundBorder(paddingBorder, titleBorder));
		
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
		innerPanel.add(createFieldPanel("In Hand"));
		
		innerPanel.add(createFieldPanel("Seen"));
		
		sectionPanel.add(innerPanel, BorderLayout.NORTH);
		
		return sectionPanel;
		
	}
	
	private JPanel createFieldPanel(String fieldName) {
		
		JPanel fieldPanel = new JPanel(new BorderLayout());
		fieldPanel.add(new JLabel(fieldName + ":"), BorderLayout.NORTH);
		
		JTextArea textArea = new JTextArea(4, 20);
		textArea.setText("Test");
		JScrollPane scrollPane = new JScrollPane(textArea);
		fieldPanel.add(scrollPane, BorderLayout.CENTER);
		
		return fieldPanel;
		
	}
	
    public static void main(String[] args) {
        KnownCardPanel panel = new KnownCardPanel(); // create the panel
        JFrame frame = new JFrame(); // create the frame
        frame.setContentPane(panel); // put the panel in the frame
        frame.setSize(180, 750); // size the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
        frame.setTitle("Control Panel");
        frame.setVisible(true); // make it visible
    }
}
