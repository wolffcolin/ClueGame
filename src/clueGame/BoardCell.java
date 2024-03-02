package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
    private int row;
    private int col;

    private char initial;

    private DoorDirection doorDirection;

    private boolean roomLabel;
    private boolean roomCenter;
    private char secretPassage;
    private Set<BoardCell> adjList;

    public BoardCell(int setRow, int setColumn, char type) {
        this.row = setRow;

        this.col = setColumn;

        this.initial = type;

        this.roomLabel = false;
        this.roomCenter = false;

        this.doorDirection = DoorDirection.NONE;

        this.adjList = new HashSet<BoardCell>();
    }

    public void addAdj(BoardCell cell) {

        adjList.add(cell);

    }

}
