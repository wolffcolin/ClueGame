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

public class TestBoardCell {
	
	private int row;
	private int col;
	
	private Boolean isRoom;
	private Boolean isOccupied;
	
	Set<TestBoardCell> adjList;

	//constructor
	public TestBoardCell(int setRow, int setColumn) {
		
		this.row = setRow;
		
		this.col = setColumn;
		
		this.isRoom = false;
		this.isOccupied = false;
		
		this.adjList = new HashSet<TestBoardCell>();
		
	}
	
	//adds cell to adjacency list
	public void addAdjacency(TestBoardCell cell) {
		
		adjList.add(cell);
		
	}
	
	//returns list of adjacent cells
	public Set<TestBoardCell> getAdjList() {
		
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
	
	
}
