package clueGame;

import javax.swing.JFrame;

public class ClueGame extends JFrame {

	public ClueGame() {
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		ClueGame panel = new ClueGame(); // create the panel
		JFrame frame = new JFrame(); // create the frame
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(300, 300); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setTitle("Clue Game Board");
		frame.setVisible(true); // make it visible
	}

}
