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

	// constructor for when we only know the name and symbol
	public Room(String setName, Character setSymbol) {
		this.name = setName;
		this.symbol = setSymbol;
	}

	// returns name
	public String getName() {
		return name;
	}

	// returns cell from label
	public BoardCell getLabelCell() {
		return labelCell;
	}

	// returns center cell
	public BoardCell getCenterCell() {
		return centerCell;
	}

	// set label cell
	public void setLabelCell(BoardCell cell) {
		this.labelCell = cell;
	}

	// set center cell
	public void setCenterCell(BoardCell cell) {
		this.centerCell = cell;
	}
}
