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

public class BoardCell {
    private int row;
    private int col;
    private char initial;
    private Set<BoardCell> adjList;

    private DoorDirection doorDirection;
    private boolean roomLabel;
    private boolean roomCenter;
    private char secretPassage;

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

}