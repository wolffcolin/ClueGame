/*
 
Class: BoardCell
Description: Represents singular cell on board.
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

package clueGame;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;

public class BoardCell extends JPanel {
    private int row;
    private int col;
    private char initial;
    private Set<BoardCell> adjList;
    private Color color; 
    private DoorDirection doorDirection;
    private boolean roomLabel;
    private boolean roomCenter;
    private char secretPassage;
    
    private int x;
    private int y;

    private boolean occupied;

    // constructor when there is no second character
    public BoardCell(int setRow, int setColumn, char roomType) {
        this.row = setRow;
        this.col = setColumn;
        this.initial = roomType;
        this.adjList = new HashSet<BoardCell>();

        this.roomLabel = false;
        this.roomCenter = false;
        this.doorDirection = DoorDirection.NONE;
    }

    // constructor when there is a second character
    public BoardCell(int setRow, int setColumn, char roomType, char secondType) {
        this.row = setRow;
        this.col = setColumn;
        this.initial = roomType;
        this.adjList = new HashSet<BoardCell>();

        /*
         * checking what the second character type is
         * character should be tested in board for validity before calling the
         * constructor
         */
        if (secondType == '#') {
            this.roomLabel = true;
            this.doorDirection = DoorDirection.NONE;
        } else if (secondType == '*') {
            this.roomCenter = true;
            this.doorDirection = DoorDirection.NONE;
        } else if (secondType == '^') {
            this.doorDirection = DoorDirection.UP;
        } else if (secondType == '<') {
            this.doorDirection = DoorDirection.LEFT;
        } else if (secondType == '>') {
            this.doorDirection = DoorDirection.RIGHT;
        } else if (secondType == 'v') {
            this.doorDirection = DoorDirection.DOWN;
        } else { // must be a secrete passage way
            this.secretPassage = secondType;
            this.doorDirection = DoorDirection.NONE;
        }
    }
    
    public void getOffset(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	draw(g, x, y);
    }
    
    public void draw(Graphics g, int xGiven, int yGiven) {
   
    	this.setColor();
    	
    	int xPos = row + x;
    	int yPos = col + y;
    	
    	g.setColor(this.color);
    	g.fillRect(xPos, yPos, xGiven, yGiven);
    	g.setColor(color.BLACK);
    	g.drawRect(xPos, yPos, xGiven, yGiven);
    	
    }

    // adds cell to adjacency list
    public void addAdjacency(BoardCell cell) {
        adjList.add(cell);
    }

    // returns char of room
    public char getInitial() {
        return this.initial;
    }

    // returns if doorway
    public boolean isDoorway() {
        if (this.doorDirection == DoorDirection.NONE)
            return false;
        else
            return true;
    }

    // returns list of adjacent cells
    public Set<BoardCell> getAdjList() {
        return this.adjList;
    }

    // returns row position
    public int getRow() {
        return this.row;
    }

    // returns column position
    public int getCol() {
        return this.col;
    }

    // returns bool for occupied or not
    public boolean getOccupied() {
        return this.occupied;
    }

    // returns enum of DoorDirection
    public DoorDirection getDoorDirection() {
        return this.doorDirection;
    }

    // returns bool of if the cell is roomLabel
    public boolean isLabel() {
        return this.roomLabel;
    }

    // returns bool of if the cell is roomCenter
    public boolean isRoomCenter() {
        return this.roomCenter;
    }

    // returns the room code char of where the secrete passage goes to
    public char getSecretPassage() {
        return this.secretPassage;
    }

    public void setOccupied(boolean b) {
        this.occupied = b;
    }
    
    public boolean equals(BoardCell otherCell) {
    	if (otherCell.getRow() == this.row && otherCell.getCol() == this.col) {
    		return true;
    	}
    	return false;
    }
    
    public void setColor() {
    	if (this.initial == 'W') {
    		this.color = Color.YELLOW;
    	} else if (this.initial == 'X') {
    		this.color = Color.BLACK;
    	} else {
    		this.color = Color.GRAY;
    	}
    }
}