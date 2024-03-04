/*
 
Class: Board
Description: Represents the clue board as a whole. Can calculate the targets from a certain cell given a path length
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Board {
    private Set<BoardCell> targets;
    private Set<BoardCell> visited;
    private BoardCell[][] grid;

    private int numColumns = 16;
    private int numRows = 16;

    private String layoutConfigFile;
    private String setupConfigFile;

    private Map<Character, Room> roomMap;

    private static Board theInstance = new Board();

    // constructor
    private Board() {
        super();

        this.targets = new HashSet();
        this.visited = new HashSet();
        this.grid = new BoardCell[numRows][numColumns];

        // populate with cells
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                this.grid[i][j] = new BoardCell(i, j, 'W');
            }
        }

        // calculate adjacency for each cell
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                BoardCell cell = this.grid[i][j];
                if (i + 1 < numRows) {
                    cell.addAdjacency(grid[i + 1][j]);
                }
                if (i - 1 >= 0) {
                    cell.addAdjacency(grid[i - 1][j]);
                }
                if (j + 1 < numColumns) {
                    cell.addAdjacency(grid[i][j + 1]);
                }
                if (j - 1 >= 0) {
                    cell.addAdjacency(grid[i][j - 1]);

                }
            }
        }
    }

    // returns the instance of board
    public static Board getInstance() {
        return theInstance;
    }

    // initialize instance
    public void initialize() {

    }

    // load setup file
    public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
        setupConfigFile = "ClueSetup306.txt";
        try {
            File setupConfig = new File(setupConfigFile);
            Scanner reader = new Scanner(setupConfig);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                if (line.startsWith("//")) {
                    continue;
                } else if (line.startsWith("Room")) {
                    String[] lineSplit = line.split(",");
                    if (lineSplit.length == 3) {
                        String roomName = lineSplit[1].trim();
                        String roomSymbolStr = lineSplit[2].trim();
                        Character roomSymbol = roomSymbolStr.charAt(0); // convert the string to Character

                        Room room = new Room(roomName, roomSymbol);
                        roomMap.put(roomSymbol, room);
                    }
                } else if (line.startsWith("Space")) {
                    String[] lineSplit = line.split(",");
                    if (lineSplit.length == 3) {
                        String roomName = lineSplit[1].trim();
                        String roomSymbolStr = lineSplit[2].trim();
                        Character roomSymbol = roomSymbolStr.charAt(0); // convert the string to Character

                        Room room = new Room(roomName, roomSymbol);
                        roomMap.put(roomSymbol, room);
                    }
                } else {
                    reader.close();
                    throw new BadConfigFormatException("Bad Setup Config layout");
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (BadConfigFormatException e) {
            throw e;
        }
    }

    // load layout file
    public void loadLayoutConfig() {

    }

    // sets file
    public void setConfigFiles(String board, String symbols) {

    }

    // calculates the targets for a starting cell
    public void calcTargets(BoardCell startCell, int pathLength) {
        visited.clear();
        targets.clear();
        findTargets(startCell, pathLength);
    }

    // search algorithm to find all targets on path
    private void findTargets(BoardCell cell, int pathLength) {
        visited.add(cell);

        // base case
        if (pathLength == 0) {
            targets.add(cell);
            visited.remove(cell);
            return;
        }

        // recursive case
        for (BoardCell adj : cell.getAdjList()) {
            if (!visited.contains(adj) && !adj.getOccupied()) {
                findTargets(adj, pathLength - 1);
            }
        }

        // backtrack
        visited.remove(cell);
    }

    // returns the cell at given point
    public BoardCell getCell(int row, int col) {
        return grid[row][col];
    }

    // returns target list
    public Set<BoardCell> getTargets() {
        return targets;
    }

    public Room getRoom(BoardCell cell) {
        // TODO Auto-generated method stub
        Room room = new Room("empty", null, null);
        return room;
    }

    public Room getRoom(char c) {
        // TODO Auto-generated method stub
        Room room = new Room("empty", null, null);
        return room;
    }

    public int getNumRows() {
        // TODO Auto-generated method stub
        return numRows;
    }

    public int getNumColumns() {
        // TODO Auto-generated method stub
        return numColumns;
    }
}
