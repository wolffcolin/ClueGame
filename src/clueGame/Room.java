/*
 
Class: Room
Description: Represents a single room on the clue board
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

package clueGame;

public class Room {

	private String name;
	private Character symbol;
	private BoardCell centerCell;
	private BoardCell labelCell;

	// constructor
	public Room(String setName, BoardCell center, BoardCell label) {
		this.name = setName;
		this.centerCell = center;
		this.labelCell = label;
	}

	public Room(String setName, Character setSymbol) {
		this.name = setName;
		this.symbol = setSymbol;
	}

	// returns name
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	// returns cell from label
	public BoardCell getLabelCell() {
		return null;
	}

	// returns center cell
	public BoardCell getCenterCell() {
		return null;
	}
}
