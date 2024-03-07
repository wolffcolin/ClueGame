/*
 
Class: Board
Description: Represents the clue board as a whole. Can calculate the targets from a certain cell given a path length
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;

public class Board {
    private Set<BoardCell> targets;
    private Set<BoardCell> visited;
    private BoardCell[][] grid;

    private int numColumns;
    private int numRows;

    private String layoutConfigFile;
    private String setupConfigFile;

    private Map<Character, Room> roomMap = new HashMap<Character, Room>();

    private static Board theInstance = new Board();

    // constructor
    private Board() {
        super();
        this.targets = new HashSet();
        this.visited = new HashSet();

        this.grid = new BoardCell[1][1];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                grid[i][j] = new BoardCell(i, j, '~');
            }
        }
    }

    // returns the instance of board
    public static Board getInstance() {
        return theInstance;
    }

    // initialize instance
    public void initialize() {
        try {
            loadSetupConfig();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BadConfigFormatException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try {
            loadLayoutConfig();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BadConfigFormatException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // calculate adjacency for each cell
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                BoardCell cell = this.grid[i][j];
                if (cell.isDoorway()) {
                    DoorDirection doorDir = cell.getDoorDirection();
                    if (doorDir == DoorDirection.UP && i - 1 >= 0) {
                        Room room = theInstance.getRoom(this.grid[i - 1][j]);
                        // bidirectional adjacency
                        cell.addAdjacency(room.getCenterCell());
                        room.getCenterCell().addAdjacency(cell);
                        // normal walkway adj
                        if (i + 1 < numRows) {
                            if (grid[i + 1][j].getInitial() == 'W') {
                                cell.addAdjacency(grid[i + 1][j]);
                            }
                        }
                        if (j + 1 < numColumns) {
                            if (grid[i][j + 1].getInitial() == 'W') {
                                cell.addAdjacency(grid[i][j + 1]);
                            }
                        }
                        if (j - 1 >= 0) {
                            if (grid[i][j - 1].getInitial() == 'W') {
                                cell.addAdjacency(grid[i][j - 1]);
                            }
                        }
                    } else if (doorDir == DoorDirection.DOWN && i + 1 < numRows) {
                        Room room = theInstance.getRoom(this.grid[i + 1][j]);
                        // bidirectional adjacency
                        cell.addAdjacency(room.getCenterCell());
                        room.getCenterCell().addAdjacency(cell);
                        // normal walkway adj
                        if (i - 1 >= 0) {
                            if (grid[i - 1][j].getInitial() == 'W') {
                                cell.addAdjacency(grid[i - 1][j]);
                            }
                        }
                        if (j + 1 < numColumns) {
                            if (grid[i][j + 1].getInitial() == 'W') {
                                cell.addAdjacency(grid[i][j + 1]);
                            }
                        }
                        if (j - 1 >= 0) {
                            if (grid[i][j - 1].getInitial() == 'W') {
                                cell.addAdjacency(grid[i][j - 1]);
                            }
                        }
                    } else if (doorDir == DoorDirection.LEFT && j - 1 >= 0) {
                        Room room = theInstance.getRoom(this.grid[i][j - 1]);
                        // bidirectional adjacency
                        cell.addAdjacency(room.getCenterCell());
                        room.getCenterCell().addAdjacency(cell);
                        // normal walkway adj
                        if (i + 1 < numRows) {
                            if (grid[i + 1][j].getInitial() == 'W') {
                                cell.addAdjacency(grid[i + 1][j]);
                            }
                        }
                        if (i - 1 >= 0) {
                            if (grid[i - 1][j].getInitial() == 'W') {
                                cell.addAdjacency(grid[i - 1][j]);
                            }
                        }
                        if (j + 1 < numColumns) {
                            if (grid[i][j + 1].getInitial() == 'W') {
                                cell.addAdjacency(grid[i][j + 1]);
                            }
                        }
                    } else if (doorDir == DoorDirection.RIGHT && j + 1 < numColumns) {
                        Room room = theInstance.getRoom(this.grid[i][j + 1]);
                        // bidirectional adjacency
                        cell.addAdjacency(room.getCenterCell());
                        room.getCenterCell().addAdjacency(cell);
                        // normal walkway adj
                        if (j - 1 >= 0) {
                            if (grid[i][j - 1].getInitial() == 'W') {
                                cell.addAdjacency(grid[i][j - 1]);
                            }
                        }
                        if (i + 1 < numRows) {
                            if (grid[i + 1][j].getInitial() == 'W') {
                                cell.addAdjacency(grid[i + 1][j]);
                            }
                        }
                        if (i - 1 >= 0) {
                            if (grid[i - 1][j].getInitial() == 'W') {
                                cell.addAdjacency(grid[i - 1][j]);
                            }
                        }
                    }
                } else if (cell.isRoomCenter()) {
                    Room room = theInstance.getRoom(cell);
                    if (room.doesPassageExist()) {
                        Room passRoom = room.getPassageRoom();
                        // bidirectional adjacency
                        cell.addAdjacency(passRoom.getCenterCell());
                        passRoom.getCenterCell().addAdjacency(cell);
                    }
                } else if (cell.getInitial() == 'W') {
                    if (i + 1 < numRows) {
                        if (grid[i + 1][j].getInitial() == 'W') {
                            cell.addAdjacency(grid[i + 1][j]);
                        }
                    }
                    if (i - 1 >= 0) {
                        if (grid[i - 1][j].getInitial() == 'W') {
                            cell.addAdjacency(grid[i - 1][j]);
                        }
                    }
                    if (j + 1 < numColumns) {
                        if (grid[i][j + 1].getInitial() == 'W') {
                            cell.addAdjacency(grid[i][j + 1]);
                        }
                    }
                    if (j - 1 >= 0) {
                        if (grid[i][j - 1].getInitial() == 'W') {
                            cell.addAdjacency(grid[i][j - 1]);
                        }
                    }
                }
            }
        }
    }

    // load setup file
    public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
        File setupConfig = new File(setupConfigFile);
        Scanner reader = new Scanner(setupConfig);

        while (reader.hasNextLine()) {
            String line = reader.nextLine();

            if (line.startsWith("//")) {
                continue;
            } else if (line.isEmpty()) {
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
                throw new BadConfigFormatException("Bad Setup Config format");
            }
        }
        reader.close();
    }

    // load layout file
    public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException {
        File layoutConfig = new File(layoutConfigFile);
        Scanner reader = new Scanner(layoutConfig);
        // 2D ArrayList to temporarily hold file contents
        ArrayList<ArrayList<String>> layoutList = new ArrayList<>();
        ArrayList<Integer> colNums = new ArrayList<>(); // list of the number of columns in each row
        List<Character> specialChars = List.of('#', '*', '<', '>', 'v', '^');

        /*
         * read each row in and add to layoutList to get row and column lengths and
         * check for correct data
         */
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] lineSplit = line.split(",");
            colNums.add(lineSplit.length); // so we can check column lengths are all the same
            // add every column to the row and add row to 2d list
            ArrayList<String> row = new ArrayList<>();
            for (String split : lineSplit) {
                row.add(split);
            }
            layoutList.add(row);
        }
        reader.close();

        // checking number of columns per row is same for all rows
        Integer temp = colNums.get(0);
        for (Integer colNum : colNums) {
            if (colNum != temp) {
                throw new BadConfigFormatException("Bad Layout Config, mismatching number of columns in each row");
            }
        }

        // set row and column sizes for board
        this.numColumns = layoutList.get(0).size();
        this.numRows = layoutList.size();

        // init grid and start building the grid with BoardCells
        this.grid = new BoardCell[numRows][numColumns];

        /*
         * take every cell in layoutList and check for validity. Then construct cell and
         * add to grid
         */
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                String cellStr = layoutList.get(i).get(j);
                if (cellStr.length() == 2) {
                    /*
                     * checking to make sure room symbols are matching what is expected from setup
                     * config
                     */
                    if (!roomMap.containsKey(cellStr.charAt(0)))
                        throw new BadConfigFormatException("Bad Layout Config, Room symbols do not match Setup Config");
                    if (!specialChars.contains(cellStr.charAt(1)) && !roomMap.containsKey(cellStr.charAt(0)))
                        throw new BadConfigFormatException("Bad Layout Config, Room symbols do not match Setup Config");
                    // all ok now, so create BoardCell and add to grid
                    BoardCell cell = new BoardCell(i, j, cellStr.charAt(0), cellStr.charAt(1));
                    this.grid[i][j] = cell;

                    // setting the room center and label cell if needed
                    if (cellStr.charAt(1) == '#') {
                        Room room = this.getRoom(cellStr.charAt(0));
                        room.setLabelCell(cell);
                    } else if (cellStr.charAt(1) == '*') {
                        Room room = this.getRoom(cellStr.charAt(0));
                        room.setCenterCell(cell);
                    } else if (!specialChars.contains(cellStr.charAt(1)) && roomMap.containsKey(cellStr.charAt(1))) {
                        Room room = this.getRoom(cellStr.charAt(0));
                        Room passRoom = this.getRoom(cellStr.charAt(1));
                        room.setSecretePassage(passRoom);
                    }
                } else if (cellStr.length() == 1) {
                    /*
                     * checking to make sure room symbols are matching what is expected from setup
                     * config
                     */
                    if (!roomMap.containsKey(cellStr.charAt(0)))
                        throw new BadConfigFormatException("Bad Layout Config, Room symbols do not match Setup Config");
                    // all ok now, so create BoardCell and add to grid
                    BoardCell cell = new BoardCell(i, j, cellStr.charAt(0));
                    this.grid[i][j] = cell;
                } else
                    throw new BadConfigFormatException("Bad Layout Config, Room symbols are not expected size");
            }
        }
    }

    // sets file
    public void setConfigFiles(String board, String symbols) {
        this.layoutConfigFile = "data/" + board;
        this.setupConfigFile = "data/" + symbols;
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
        	if (cell.isRoomCenter()) {
        		if (visited.size() == 1) {
        			visited.remove(cell);
        			return;
        		}
        		return;
        	}
            visited.remove(cell);
            return;
        }
        

        // recursive case
        for (BoardCell adj : cell.getAdjList()) {
            if (!visited.contains(adj) && (!adj.getOccupied() || adj.isRoomCenter())) {
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

    // returns the room when given a cell
    public Room getRoom(BoardCell cell) {
        char roomChar = cell.getInitial();
        return roomMap.get(roomChar);
    }

    // returns the room when given the char of the room
    public Room getRoom(char c) {
        return roomMap.get(c);
    }

    // returns number of rows
    public int getNumRows() {
        return numRows;
    }

    // returns number of columns
    public int getNumColumns() {
        return numColumns;
    }

    public Set<BoardCell> getAdjList(int row, int col) {

        return getInstance().getCell(row, col).getAdjList();
    }
}
