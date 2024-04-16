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

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class BoardCell {
    private int row;
    private int col;
    private char initial;
    private Set<BoardCell> adjList;
    private Color color;
    private DoorDirection doorDirection;
    private boolean roomLabel;
    private boolean roomCenter;
    private char secretPassage;

    private int x;
    private int y;

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

    // draws the cell and colors it based off if it is a room or walkway/unused spot
    public void draw(Graphics g, int size) {
        int y = row * size;
        int x = col * size;

        this.setColor();

        g.setColor(this.color);
        g.fillRect(x, y, size, size);

        // if and only if they are walways then they get borders drawn in black
        if (this.initial == 'X' || this.initial == 'W') {
            g.setColor(color.BLACK);
            g.drawRect(x, y, size, size);
        }

        JPanel cellPanel = new JPanel();
        cellPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cellClicked();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }
        });

    }

    // draws the room name labels on the label cell
    public void drawLabel(Graphics g, int size) {
        if (roomLabel) {
            int y = row * size;
            int x = col * size;

            Board board = Board.getInstance();
            String roomName = board.getRoom(initial).getName();

            g.setColor(Color.BLACK);
            g.setFont(new Font("SansSerif", Font.BOLD, 12));
            FontMetrics fm = g.getFontMetrics();

            int textWidth = fm.stringWidth(roomName);
            int textHeight = fm.getHeight();

            // positioning the text on the center of the room
            g.drawString(roomName, x + (size - textWidth) / 2, y + ((size - textHeight)) / 2 + fm.getAscent());
        }
    }

    // draws the door onto the board with the directions indicated
    public void drawDoor(Graphics g, int size) {
        int y = row * size;
        int x = col * size;

        g.setColor(Color.BLUE);

        if (doorDirection == DoorDirection.DOWN) {
            g.fillRect(x, y + size, size, 5);
        } else if (doorDirection == DoorDirection.LEFT) {
            g.fillRect(x - 5, y, 5, size);
        } else if (doorDirection == DoorDirection.RIGHT) {
            g.fillRect(x + size, y, 5, size);
        } else if (doorDirection == DoorDirection.UP) {
            g.fillRect(x, y - 5, size, 5);
        }
    }

    public void cellClicked() {
        Board board = Board.getInstance();
        Player humanPlayer = board.getHumanPlayer();
        int humanIndex = board.getHumanPlayerIndex();
        int currIndex = board.getCurrentPlayerIndex();

        if (currIndex == humanIndex) {
            humanPlayer.teleport(row, col);
        } else {
            JOptionPane.showMessageDialog(null, "You cannot move as it is not your turn", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
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

    public boolean equals(BoardCell otherCell) {
        if (otherCell.getRow() == this.row && otherCell.getCol() == this.col) {
            return true;
        }
        return false;
    }

    public void setColor() {
        if (this.initial == 'W') {
            this.color = Color.YELLOW;
        } else if (this.initial == 'X') {
            this.color = Color.BLACK;
        } else {
            this.color = Color.GRAY;
        }
    }
}