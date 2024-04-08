package clueGame;

import javax.swing.JFrame;
import java.awt.Graphics;

public class ClueGame extends JFrame {

	private static Board board = Board.getInstance();
	private static Graphics g;

	public ClueGame() {
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}

	public static void main(String[] args) {
		ClueGame frame = new ClueGame(); // create the panel
		g = board.getGraphics();
		board.paintComponent(g);
		frame.add(board);
		frame.pack();
		frame.setContentPane(board); // put the panel in the frame
		frame.setSize(600, 600); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setTitle("Clue Game Board");
		frame.setVisible(true); // make it visible
	}

}
