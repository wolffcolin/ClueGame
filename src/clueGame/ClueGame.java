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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.BorderLayout;

public class ClueGame extends JFrame {

	// getting instance of board and graphics
	private static Board board = Board.getInstance();
	private static Graphics g;

	private static GameControlPanel controls;
	private static KnownCardPanel cards;

	private static JFrame holderFrame;

	private static boolean keepScore;

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
		holderFrame = frame;
		g = board.getGraphics();
		board.paintComponents(g);

		// get human player to generate known card panel
		Player humanPlayer = board.getHumanPlayer();

		// make instance of panels for controls and known cards
		controls = new GameControlPanel();
		controls.getFrame(frame);
		cards = new KnownCardPanel(humanPlayer);

		board.initializePlayer();

		String humanPlayerName = humanPlayer.getName();

		String message = "<html><body><div style='text-align: center;'>"
				+ "You are " + humanPlayerName + ".<br>"
				+ "Can you find the solution<br>before the Computer players?"
				+ "</div></body></html>";

		StartGameDialog startDialog = new StartGameDialog(frame, message);
		startDialog.setVisible(true);

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

	public static void setNameAndRoll(Player player, int roll) {
		controls.setTurnName(player);
		controls.setRoll(String.valueOf(roll));
	}

	public static void setGuessAndResult(Solution guess, Card dispute, Player disputPlayer) {
		String result = null;
		if (dispute == null) {
			result = "No one can disprove the suggestion";
		}
		if (board.getCurrentPlayer().isAHuman() && dispute != null) {
			result = "Disproved by " + disputPlayer.toString() + " with " + dispute.toString();
		} else if (dispute != null) {
			result = "Disproved by " + disputPlayer.toString();
		}
		controls.setGuess(guess.toString());
		controls.setGuessResult(result);
	}

	public static Graphics getClueGraphics() {
		return g;
	}

	// handles and outputs file for game end human player win
	public static void endGameWin(Solution solution) {
		String filePath = "score.txt";

		int wins = 0;
		int losses = 0;

		if (keepScore) {
			if (Files.exists(Paths.get(filePath)) && Files.isReadable(Paths.get(filePath))) {
				try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
					String line = reader.readLine();
					if (line != null) {
						wins = Integer.parseInt(line);
						line = reader.readLine();
						if (line != null) {
							losses = Integer.parseInt(line);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			wins++;

			try (FileWriter writer = new FileWriter(filePath, false)) {
				writer.write(String.valueOf(wins) + "\n");
				writer.write(String.valueOf(losses) + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(holderFrame,
					"You've won! Your guess was: " + solution.getPerson().toString() + " in the "
							+ solution.getRoom().toString() + " with the " + solution.getWeapon().toString()
							+ " Your record is: " + wins + " wins, and " + losses + " losses!");
			System.exit(0);
		} else {
			JOptionPane.showMessageDialog(holderFrame, "You've won! Your guess was: " + solution.getPerson().toString()
					+ " in the " + solution.getRoom().toString() + " with the " + solution.getWeapon().toString());
			System.exit(0);
		}

	}

	// handles and outputs file for game end human player loss
	public static void endGameLoss(Solution solution) {
		String filePath = "score.txt";

		int wins = 0;
		int losses = 0;

		if (keepScore) {
			if (Files.exists(Paths.get(filePath)) && Files.isReadable(Paths.get(filePath))) {
				try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
					String line = reader.readLine();
					if (line != null) {
						wins = Integer.parseInt(line);
						line = reader.readLine();
						if (line != null) {
							losses = Integer.parseInt(line);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			losses++;

			try (FileWriter writer = new FileWriter(filePath, false)) {
				writer.write(String.valueOf(wins) + "\n");
				writer.write(String.valueOf(losses) + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(holderFrame,
					"You've lost. The correct answer was: " + solution.getPerson().toString() + " in the "
							+ solution.getRoom().toString() + " with the " + solution.getWeapon().toString()
							+ " Your record is: " + wins + " wins, and " + losses + " losses!");
			System.exit(0);
		} else {
			JOptionPane.showMessageDialog(holderFrame,
					"You've lost. The correct answer was: " + solution.getPerson().toString() + " in the "
							+ solution.getRoom().toString() + " with the " + solution.getWeapon().toString());
			System.exit(0);
		}

	}

	// gets the human players suggestion and handels the disputes/updates gui
	public static void manageSuggestion(String roomName, String[] people, String[] weapons) {
		SuggestionDialog suggest = new SuggestionDialog(holderFrame, roomName, people, weapons);
		suggest.setVisible(true);
		Solution suggestion = suggest.getSuggestion();
		Card dispute = board.handleSuggestion(suggestion, board.getHumanPlayer());
		// teleport player in the suggestion to the room
		for (Player player : board.getPlayers()) {
			if (player.getName().equals(suggestion.getPerson().toString())) {
				board.movePlayer(board.getCell(board.getHumanPlayer().getRow(), board.getHumanPlayer().getCol()),
						player);
			}
		}
		// if there is a dispute
		Player disputePlayer = null;
		if (dispute != null) {
			for (Player player : board.getPlayers()) {
				for (Card card : player.getHand()) {
					if (card == dispute) {
						disputePlayer = player;
					}
				}
			}
			board.getHumanPlayer().updateSeen(dispute);
			cards.rebuild(board.getHumanPlayer());
		}
		setGuessAndResult(suggestion, dispute, disputePlayer);
	}

	public static void setKeepScore(boolean toKeep) {
		keepScore = toKeep;
	}

	// handels game end when the computer wins
	public static void endGameWinComputer(String computerPlayerName, Solution solution) {
		JOptionPane.showMessageDialog(holderFrame, "Computer Player " + computerPlayerName +
				" has made a correct accusation of " + solution.getPerson().toString() + " in the "
				+ solution.getRoom().toString() + " with the " + solution.getWeapon().toString()
				+ " The game is over.");
		System.exit(0);
	}

}
