/*
 
Class: ClueGame
Description: JFrame for all graphics in game and main function to actually run the game
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

package clueGame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.awt.BorderLayout;

public class ClueGame extends JFrame {

	// getting instance of board and graphics
	private static Board board = Board.getInstance();
	private static Graphics g;

	private Player currentPlayer;

	// constructor
	public ClueGame() {
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}

	// main
	public static void main(String[] args) {
		// make new frame and get graphics + paint components
		ClueGame frame = new ClueGame(); // create the panel
		g = board.getGraphics();
		board.paintComponents(g);

		// get human player to generate known card panel
		Player humanPlayer = board.getHumanPlayer();

		// make instance of panels for controls and known cards
		GameControlPanel controls = new GameControlPanel();
		KnownCardPanel cards = new KnownCardPanel(humanPlayer);

		String humanPlayerName = humanPlayer.getName();

		String message = "<html><body><div style='text-align: center;'>"
				+ "You are " + humanPlayerName + ".<br>"
				+ "Can you find the solution<br>before the Computer players?"
				+ "</div></body></html>";

		JOptionPane.showMessageDialog(null, message, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);

		// add all panels to frame and set close behavior
		frame.add(board, BorderLayout.CENTER);
		frame.add(controls, BorderLayout.SOUTH);
		frame.add(cards, BorderLayout.EAST);
		frame.pack();
		frame.setSize(800, 700); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setTitle("Clue Game Board");
		frame.setVisible(true); // make it visible
	}
	


}
