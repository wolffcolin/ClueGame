package experiment;

import java.util.*;

public class TestBoard {
    private ArrayList targets;

    public TestBoard() {
        super();
    }

    public void calcTargets(TestBoardCell startCell, int pathLength) {
    }

    public TestBoardCell getCell(int row, int col) {
        TestBoardCell cell = new TestBoardCell();
        return cell;
    }

    public Set<TestBoardCell> getTargets() {
        Set<TestBoardCell> set = new HashSet<TestBoardCell>();
        return set;
    }
}
