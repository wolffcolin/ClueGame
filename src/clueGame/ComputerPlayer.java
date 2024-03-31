package clueGame;

/*
Class: ComputerPlayer
Description: computer player class that is extends Player
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

public class ComputerPlayer extends Player {

	// constructor
	public ComputerPlayer(String name, String color) {
		super(name, color, false);
	}
	
	public Solution createSuggestion() {
		Solution potentialSolution = new Solution(this.getHand().get(0),this.getHand().get(0),this.getHand().get(0));
		return potentialSolution;
	}
	
	public BoardCell selectTarget() {
		BoardCell targetPlaceholder = new BoardCell(0, 0, 'W');
		return targetPlaceholder;
	}

}
