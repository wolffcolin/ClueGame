/*
 
Class: Board
Description: Represents the clue board as a whole. Can calculate the targets from a certain cell given a path length
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

package clueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;

import java.util.Scanner;
import java.util.List;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Graphics;
import java.awt.GridLayout;

public class Board extends JPanel {
    private Set<BoardCell> targets;
    private Set<BoardCell> visited;
    private BoardCell[][] grid;

    private int numColumns;
    private int numRows;

    private String layoutConfigFile;
    private String setupConfigFile;

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();

    private Map<Character, Room> roomMap = new HashMap<>();

    private static Board theInstance = new Board();

    private Solution theAnswer;
    
    private JPanel boardPanel = new JPanel();

    // constructor
    private Board() {
        super();
        this.targets = new HashSet<>();
        this.visited = new HashSet<>();

        this.grid = new BoardCell[1][1];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                grid[i][j] = new BoardCell(i, j, '~');
            }
        }
        setLayout(new GridLayout(grid.length, grid[0].length));
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	
    	super.paintComponent(g);
    	
    	
    	int boardWidth = getWidth();
    	int boardHeight = getHeight();
    	
    	int numCellsWidth = grid.length;
    	int numCellsHeight = grid[0].length;
    	
    	int cellSize = Math.min(boardWidth / numCellsWidth, boardHeight / numCellsHeight);
    	
    	for (int i = 0; i < grid.length; i++) {
    		for (int j = 0; j < grid[i].length; j++) {
    			int x = j * cellSize;
    			int y = i * cellSize;
    			grid[i][j].getOffset(x, y);
    			this.add(grid[i][j]);
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
            loadLayoutConfig();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BadConfigFormatException e) {
            e.printStackTrace();
        }
        makeAdjLists();
        dealCards();
    }

    // finds adjacency for every cell on the grid
    public void makeAdjLists() {
        // calculate adjacency for each cell
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                BoardCell cell = this.grid[i][j];
                Room room; // overwrite with room as needed
                if (cell.isDoorway()) {
                    /*
                     * Door direction allows us to find what room the door is pointing into.
                     * By checking the cell in the direction, then finding that cell's room center,
                     * we can create a bidirectional adjacency between the door and the room center.
                     */
                    DoorDirection doorDir = cell.getDoorDirection();
                    if (doorDir == DoorDirection.UP && i - 1 >= 0) {
                        room = theInstance.getRoom(this.grid[i - 1][j]);
                        // bidirectional adjacency
                        cell.addAdjacency(room.getCenterCell());
                        room.getCenterCell().addAdjacency(cell);
                        // normal walkway adj
                        if (i + 1 < numRows && grid[i + 1][j].getInitial() == 'W') {
                            cell.addAdjacency(grid[i + 1][j]);
                        }
                        if (j + 1 < numColumns && grid[i][j + 1].getInitial() == 'W') {
                            cell.addAdjacency(grid[i][j + 1]);
                        }
                        if (j - 1 >= 0 && grid[i][j - 1].getInitial() == 'W') {
                            cell.addAdjacency(grid[i][j - 1]);
                        }
                    } else if (doorDir == DoorDirection.DOWN && i + 1 < numRows) {
                        room = theInstance.getRoom(this.grid[i + 1][j]);
                        // bidirectional adjacency
                        cell.addAdjacency(room.getCenterCell());
                        room.getCenterCell().addAdjacency(cell);
                        // normal walkway adj
                        if (i - 1 >= 0 && grid[i - 1][j].getInitial() == 'W') {
                            cell.addAdjacency(grid[i - 1][j]);
                        }
                        if (j + 1 < numColumns && grid[i][j + 1].getInitial() == 'W') {
                            cell.addAdjacency(grid[i][j + 1]);
                        }
                        if (j - 1 >= 0 && grid[i][j - 1].getInitial() == 'W') {
                            cell.addAdjacency(grid[i][j - 1]);
                        }
                    } else if (doorDir == DoorDirection.LEFT && j - 1 >= 0) {
                        room = theInstance.getRoom(this.grid[i][j - 1]);
                        // bidirectional adjacency
                        cell.addAdjacency(room.getCenterCell());
                        room.getCenterCell().addAdjacency(cell);
                        // normal walkway adj
                        if (i + 1 < numRows && grid[i + 1][j].getInitial() == 'W') {
                            cell.addAdjacency(grid[i + 1][j]);
                        }
                        if (i - 1 >= 0 && grid[i - 1][j].getInitial() == 'W') {
                            cell.addAdjacency(grid[i - 1][j]);
                        }
                        if (j + 1 < numColumns && grid[i][j + 1].getInitial() == 'W') {
                            cell.addAdjacency(grid[i][j + 1]);
                        }
                    } else if (doorDir == DoorDirection.RIGHT && j + 1 < numColumns) {
                        room = theInstance.getRoom(this.grid[i][j + 1]);
                        // bidirectional adjacency
                        cell.addAdjacency(room.getCenterCell());
                        room.getCenterCell().addAdjacency(cell);
                        // normal walkway adj
                        if (j - 1 >= 0 && grid[i][j - 1].getInitial() == 'W') {
                            cell.addAdjacency(grid[i][j - 1]);
                        }
                        if (i + 1 < numRows && grid[i + 1][j].getInitial() == 'W') {
                            cell.addAdjacency(grid[i + 1][j]);
                        }
                        if (i - 1 >= 0 && grid[i - 1][j].getInitial() == 'W') {
                            cell.addAdjacency(grid[i - 1][j]);
                        }
                    }
                } else if (cell.isRoomCenter()) {
                    room = theInstance.getRoom(cell);
                    if (room.doesPassageExist()) {
                        Room passRoom = room.getPassageRoom();
                        // bidirectional adjacency
                        cell.addAdjacency(passRoom.getCenterCell());
                        passRoom.getCenterCell().addAdjacency(cell);
                    }
                } else if (cell.getInitial() == 'W') {
                    if (i + 1 < numRows && grid[i + 1][j].getInitial() == 'W') {
                        cell.addAdjacency(grid[i + 1][j]);
                    }
                    if (i - 1 >= 0 && grid[i - 1][j].getInitial() == 'W') {
                        cell.addAdjacency(grid[i - 1][j]);
                    }
                    if (j + 1 < numColumns && grid[i][j + 1].getInitial() == 'W') {
                        cell.addAdjacency(grid[i][j + 1]);
                    }
                    if (j - 1 >= 0 && grid[i][j - 1].getInitial() == 'W') {
                        cell.addAdjacency(grid[i][j - 1]);
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

            if (line.startsWith("//") || line.isEmpty()) {
                // skips this line
            } else if (line.startsWith("Room")) {
                String[] lineSplit = line.split(",");
                if (lineSplit.length == 3) {
                    String roomName = lineSplit[1].trim();
                    String roomSymbolStr = lineSplit[2].trim();
                    Character roomSymbol = roomSymbolStr.charAt(0); // convert the string to Character

                    Room room = new Room(roomName, roomSymbol); // create room
                    roomMap.put(roomSymbol, room);

                    Card roomCard = new Card(roomName, CardType.ROOM); // create room card
                    cards.add(roomCard);
                }
            } else if (line.startsWith("Space")) {
                String[] lineSplit = line.split(",");
                if (lineSplit.length == 3) {
                    String roomName = lineSplit[1].trim();
                    String roomSymbolStr = lineSplit[2].trim();
                    Character roomSymbol = roomSymbolStr.charAt(0); // convert the string to Character

                    Room room = new Room(roomName, roomSymbol); // create room card
                    roomMap.put(roomSymbol, room);
                }
            } else if (line.startsWith("Player")) {
                String[] lineSplit = line.split(",");
                if (lineSplit.length == 4) {
                    String playerName = lineSplit[1].trim();
                    String playerColor = lineSplit[2].trim();
                    String isHumanString = lineSplit[3].trim();
                    boolean isHuman = false;
                    if (isHumanString.equals("true")) {
                        isHuman = true;
                    } else if (isHumanString.equals("false")) {
                        isHuman = false;
                    }

                    Color color = getColorString(playerColor);
                    Player player; // create player
                    if (isHuman == true) {
                        player = new HumanPlayer(playerName, color);
                    } else {
                        player = new ComputerPlayer(playerName, color);
                    }

                    players.add(player);

                    Card playerCard = new Card(playerName, CardType.PERSON); // create person card
                    cards.add(playerCard);
                }
            } else if (line.startsWith("Weapon")) {
                String[] lineSplit = line.split(",");
                if (lineSplit.length == 2) {
                    String weaponName = lineSplit[1].trim();

                    Card weapon = new Card(weaponName, CardType.WEAPON); // create weapon card
                    cards.add(weapon);
                }
            }

            else {
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

    // deals cards to players after selecting a random room, weapon, and person as
    // the solution
    public void dealCards() {
        ArrayList<Card> weapons = new ArrayList<>();
        ArrayList<Card> people = new ArrayList<>();
        ArrayList<Card> rooms = new ArrayList<>();

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getCardType().equals(CardType.WEAPON)) {
                weapons.add(cards.get(i));
            } else if (cards.get(i).getCardType().equals(CardType.ROOM)) {
                rooms.add(cards.get(i));
            } else if (cards.get(i).getCardType().equals(CardType.PERSON)) {
                people.add(cards.get(i));
            }
        }

        Random random = new Random();
        int randIndexWeapons = random.nextInt(weapons.size());

        int randIndexPeople = random.nextInt(people.size());

        int randIndexRooms = random.nextInt(rooms.size());

        // set solution as a private instance called theAnswer
        theAnswer = new Solution(rooms.get(randIndexRooms), people.get(randIndexPeople), weapons.get(randIndexWeapons));

        // remove solution from the deck
        weapons.remove(randIndexWeapons);
        people.remove(randIndexPeople);
        rooms.remove(randIndexRooms);

        ArrayList<Card> newDeck = new ArrayList<>();
        newDeck.addAll(weapons);
        newDeck.addAll(rooms);
        newDeck.addAll(people);

        Collections.shuffle(newDeck);

        // deal cards out to players after the shuffle
        int topCard = 0;
        while (!newDeck.isEmpty()) {
            for (int i = 0; i < players.size(); i++) {
                players.get(i).updateHand(newDeck.get(topCard));
                newDeck.remove(topCard);
            }
        }

    }

    // checks the the solution offered in the accusation is equal to the answer, if
    // not it returns null
    public boolean checkAccusation(Solution potentialSolution) {
        if (theAnswer.equals(potentialSolution)) {
            return true;
        } else
            return false;
    }

    // handles when a suggestion is raised, if there are no cards that can dispute
    // the suggestion it returns null
    public Card handleSuggestionn(Solution suggestion, Player suggester) {
        Card dispute;
        for (Player player : players) {
            if (player.disproveSuggestion(suggestion) != null && suggester != player) {
                dispute = player.disproveSuggestion(suggestion);
                return dispute;
            }
        }
        return null;
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
        findTargets(startCell, pathLength, true);
    }

    // search algorithm to find all targets on path
    private void findTargets(BoardCell cell, int pathLength, boolean startingCell) {
        visited.add(cell);

        // base case
        if (pathLength == 0 || (cell.isRoomCenter() && !startingCell)) {
            targets.add(cell);
            visited.remove(cell);
            return;
        }

        // recursive case
        for (BoardCell adj : cell.getAdjList()) {
            if (!visited.contains(adj) && (!adj.getOccupied() || adj.isRoomCenter())) {
                findTargets(adj, pathLength - 1, false);
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

    public static void clearInstance() {
        theInstance = new Board();
    }

    public int getPlayerCount() {
        return players.size();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getWeaponCount() {
        int count = 0;
        // looping for only the cards with type WEAPON
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getCardType().equals(CardType.WEAPON)) {
                count++;
            }
        }
        return count;
    }

    public int getCardDeckSize() {
        return cards.size();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Solution getTheAnswer() {
        return theAnswer;
    }

    // should only be used in testing
    public void rigTheAnswer(Solution riggedSolution) {
        theAnswer = riggedSolution;
    }

    public Card roomCard(int row, int column) {
        BoardCell cellCheck = grid[row][column];
        char cellType = cellCheck.getInitial();
        if (cellType != 'W' || cellType != 'X') {
            Room currRoom = roomMap.get(cellType);
            String name = currRoom.getName();
            for (Card tempCard : cards) {
                if (tempCard.getCardType().equals(CardType.ROOM) && tempCard.toString() == name) {
                    return tempCard;
                }
            }
        }
        return null;
    }

    public ArrayList<Card> allCardsOfType(CardType type) {
        ArrayList<Card> cardsOfType = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getCardType().equals(type)) {
                cardsOfType.add(cards.get(i));
            }
        }

        return cardsOfType;
    }

    public Player getHumanPlayer() {
        for (Player player : players) {
            if (player.isAHuman()) {
                return player;
            }
        }
        return null;
    }

    public Color getColorString(String col) {
        Color color = null;
        switch (col.toLowerCase()) {
            case "black":
                color = Color.BLACK;
                break;
            case "blue":
                color = Color.BLUE;
                break;
            case "cyan":
                color = Color.CYAN;
                break;
            case "darkgray":
                color = Color.DARK_GRAY;
                break;
            case "gray":
                color = Color.GRAY;
                break;
            case "green":
                color = Color.GREEN;
                break;
            case "yellow":
                color = Color.YELLOW;
                break;
            case "lightgray":
                color = Color.LIGHT_GRAY;
                break;
            case "magneta":
                color = Color.MAGENTA;
                break;
            case "orange":
                color = Color.ORANGE;
                break;
            case "pink":
                color = Color.PINK;
                break;
            case "red":
                color = Color.RED;
                break;
            case "white":
                color = Color.WHITE;
                break;
        }
        return color;
    }
}
