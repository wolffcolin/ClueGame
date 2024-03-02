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

    public BoardCell(int setRow, int setColumn, char type) {
        this.row = setRow;

        this.col = setColumn;

        this.initial = type;

        this.roomLabel = false;
        this.roomCenter = false;

        this.doorDirection = DoorDirection.NONE;

        this.adjList = new HashSet<BoardCell>();
    }

    public void addAdjacency(BoardCell cell) {

        adjList.add(cell);

    }

    public boolean isDoorway() {
        // TODO Auto-generated method stub
        return false;
    }

    // returns list of adjacent cells
    public Set<BoardCell> getAdjList() {

        return this.adjList;

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

    public boolean getOccupied() {
        // TODO Auto-generated method stub
        return occupied;
    }

    public char getRoomChar() {
        return this.initial;
    }

    public Object getDoorDirection() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isLabel() {
        return false;
    }

    public boolean isRoomCenter() {
        return false;
    }

}
