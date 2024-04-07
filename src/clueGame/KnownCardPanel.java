package clueGame;

import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.*;
import java.awt.*;

public class KnownCardPanel extends JPanel {

	public KnownCardPanel(Player player) {
		
		ArrayList<Card> seen = new ArrayList<>(player.getSeen());
		ArrayList<Card> hand = player.getHand();
		
		ArrayList<Card> peopleSeen = new ArrayList<>();
		ArrayList<Card> peopleHand = new ArrayList<>();
		ArrayList<Card> roomSeen = new ArrayList<>();
		ArrayList<Card> roomHand = new ArrayList<>();
		ArrayList<Card> weaponSeen = new ArrayList<>();
		ArrayList<Card> weaponHand = new ArrayList<>();
		
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getCardType() == CardType.PERSON) {
				peopleHand.add(hand.get(i));
			} else if (hand.get(i).getCardType() == CardType.WEAPON) {
				weaponHand.add(hand.get(i));
			} else if (hand.get(i).getCardType() == CardType.ROOM) {
				roomHand.add(hand.get(i));
			}
		}
		
		for (int i = 0; i < seen.size(); i++) {
			if (seen.get(i).getCardType() == CardType.PERSON) {
				peopleSeen.add(seen.get(i));
			} else if (seen.get(i).getCardType() == CardType.WEAPON) {
				weaponSeen.add(seen.get(i));
			} else if (seen.get(i).getCardType() == CardType.ROOM) {
				roomSeen.add(seen.get(i));
			}
		}
		
		System.out.println("Weapon Hand:" + weaponHand.toString());
		System.out.println("People Hand: " + peopleHand.toString());
		System.out.println("Room Hand: " + roomHand.toString());
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(createSectionPanel("People", peopleSeen, peopleHand));
		add(createSectionPanel("Rooms", roomSeen, roomHand));
		add(createSectionPanel("Weapons", weaponSeen, weaponHand));
		
	}
	
	private JPanel createSectionPanel(String title, ArrayList<Card> seen, ArrayList<Card> hand) {
		JPanel sectionPanel = new JPanel(new BorderLayout());
		
		Border titleBorder = BorderFactory.createTitledBorder(title);
		Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		sectionPanel.setBorder(BorderFactory.createCompoundBorder(paddingBorder, titleBorder));
		
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
		innerPanel.add(createFieldPanel("In Hand", hand));
		
		innerPanel.add(createFieldPanel("Seen", seen));
		
		sectionPanel.add(innerPanel, BorderLayout.NORTH);
		
		return sectionPanel;
		
	}
	
	private JPanel createFieldPanel(String fieldName, ArrayList<Card> cards) {
		
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
		fieldPanel.add(new JLabel(fieldName + ":"));
		
		for (Card card : cards) {
			JTextField textField = new JTextField(card.toString(), 20);
			textField.setEditable(false);
			textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));
			fieldPanel.add(textField);
		}
		
		return fieldPanel;
		
	}
	
    public static void main(String[] args) throws FileNotFoundException, BadConfigFormatException {
    	
    	Board board = Board.getInstance();
    	board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
    	board.initialize();
    	
    	Player humanPlayer = board.getHumanPlayer();
    	
    	System.out.println(humanPlayer.getHand().toString());
    	System.out.println(humanPlayer.getSeen().toString());
    	
        KnownCardPanel panel = new KnownCardPanel(humanPlayer); // create the panel
        JFrame frame = new JFrame(); // create the frame
        frame.setContentPane(panel); // put the panel in the frame
        frame.setSize(180, 750); // size the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
        frame.setTitle("Control Panel");
        frame.setVisible(true); // make it visible
    }
    
}
