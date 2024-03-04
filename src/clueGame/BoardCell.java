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

import experiment.TestBoardCell;

public class BoardCell {
    private int row;
    private int col;

    private char initial;

    private DoorDirection doorDirection;

    private boolean roomLabel;
    private boolean roomCenter;
    private char secretPassage;
    private Set<BoardCell> adjList;
    private boolean occupied;

    // constructor
    public BoardCell(int setRow, int setColumn, char roomType) {
        this.row = setRow;
        this.col = setColumn;

        this.initial = roomType;

        this.roomLabel = false;
        this.roomCenter = false;

        this.doorDirection = DoorDirection.NONE;

        this.adjList = new HashSet<BoardCell>();
    }

    public BoardCell(int setRow, int setColumn, char roomType, char secondType) {
        this.row = setRow;
        this.col = setColumn;
        this.initial = roomType;
        this.adjList = new HashSet<BoardCell>();

        if (secondType == '#') {
            this.roomLabel = true;
        } else if (secondType == '*') {
            this.roomCenter = true;
        } else if (secondType == '^') {
            this.doorDirection = DoorDirection.UP;
        } else if (secondType == '<') {
            this.doorDirection = DoorDirection.LEFT;
        } else if (secondType == '>') {
            this.doorDirection = DoorDirection.RIGHT;
        } else if (secondType == 'v') {
            this.doorDirection = DoorDirection.DOWN;
        } else {
            this.secretPassage = secondType;
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

    // returns row
    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public String getName() {
        return null;
    }

    public boolean getOccupied() {
        return this.occupied;
    }

    public DoorDirection getDoorDirection() {
        return this.doorDirection;
    }

    public boolean isLabel() {
        return this.roomLabel;
    }

    public boolean isRoomCenter() {
        return this.roomCenter;
    }

    public char getSecretPassage() {
        return this.secretPassage;
    }

}
