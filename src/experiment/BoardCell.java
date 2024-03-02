/*
 
Class: TestBoardCell
Description: Represents an individual cell on the clue board, can get adjacent cells return
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

package experiment;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	
	private int row;
	private int col;
	
	private Boolean isRoom;
	private Boolean isOccupied;
	
	Set<BoardCell> adjList;

	//constructor
	public BoardCell(int setRow, int setColumn) {
		
		this.row = setRow;
		
		this.col = setColumn;
		
		this.isRoom = false;
		this.isOccupied = false;
		
		this.adjList = new HashSet<BoardCell>();
		
	}
	
	//adds cell to adjacency list
	public void addAdjacency(BoardCell cell) {
		
		adjList.add(cell);
		
	}
	
	//returns list of adjacent cells
	public Set<BoardCell> getAdjList() {
		
		return adjList;
		
	}
	
	//sets cell to room
	public void setRoom(boolean isRoomBool) {
		
		this.isRoom = isRoomBool;
		
	}
	
	//returns if cell is a room
	public boolean isRoom() {
	
		return isRoom;
		
	}
	
	//sets room to occupied
	public void setOccupied(boolean isOccupBool) {
		
		this.isOccupied = isOccupBool;
		
	}
	
	//gets of a room is occupied
	public boolean getOccupied() {
		
		return this.isOccupied;
		
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
