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
    private Set<TestBoardCell> targets;
    private TestBoardCell[][] grid;
    private Set<TestBoardCell> visited;

    final static int COLS = 4;
    final static int ROWS = 4;

    // constructor
    public TestBoard() {
        super();

        this.targets = new HashSet();
        this.visited = new HashSet();
        this.grid = new TestBoardCell[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                this.grid[i][j] = new TestBoardCell(i, j);
            }
        }

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                TestBoardCell cell = this.grid[i][j];
                if (i + 1 < ROWS) {
                    cell.addAdjacency(grid[i + 1][j]);
                }
                if (i - 1 >= 0) {
                    cell.addAdjacency(grid[i - 1][j]);
                }
                if (j + 1 < COLS) {
                    cell.addAdjacency(grid[i][j + 1]);
                }
                if (j - 1 >= 0) {
                    cell.addAdjacency(grid[i][j - 1]);

                }
            }
        }
    }

    // calculates the targets for a starting cell
    public void calcTargets(TestBoardCell startCell, int pathLength) {
    	visited.clear();
    	targets.clear();
    	findTargets(startCell, pathLength);
    }
    
    private void findTargets(TestBoardCell cell, int pathLength) {
    	visited.add(cell);
    	
    	if (pathLength == 0) {
    		targets.add(cell);
    		visited.remove(cell);
    		return;
    	}
    	
    	for (TestBoardCell adj : cell.getAdjList()) {
    		if (!visited.contains(adj) && !adj.getOccupied()) {
    			findTargets(adj, pathLength - 1);
    		}
    	}
    	
    	visited.remove(cell);
    }

    // returns the cell at given point
    public TestBoardCell getCell(int row, int col) {
        return grid[row][col];
    }

    // returns target list
    public Set<TestBoardCell> getTargets() {
        return targets;
    }
}
