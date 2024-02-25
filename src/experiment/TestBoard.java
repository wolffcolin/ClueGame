/*
 
Class: TestBoard
Description: Represents the clue board as a whole. Can calculate the targets from a certain cell given a path length
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

package experiment;

import java.util.*;

public class TestBoard {
    private ArrayList targets;

    //constructor
    public TestBoard() {
        super();
    }

    //calculates the targets for a starting cell
    public void calcTargets(TestBoardCell startCell, int pathLength) {
    }

    //returns the cell at given point
    public TestBoardCell getCell(int row, int col) {

    	TestBoardCell cell = new TestBoardCell(row, col);
        return cell;
    }

    //returns target list
    public Set<TestBoardCell> getTargets() {
        Set<TestBoardCell> set = new HashSet<TestBoardCell>();
        return set;
    }
}
