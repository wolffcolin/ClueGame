package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

/*
 
Class: FileInitTests
Description: Testing the file input validity and that all points on the map are lining up
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

class FileInitTests {

	public static final int NUM_ROWS = 15;
	public static final int NUM_COLUMNS = 15;

	private static Board board;
	
	@BeforeAll
	public static void cleanInstance() {
		Board.getInstance().clearInstance();
	}

	@BeforeAll
	public static void setUp() {

		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");

		board.initialize();

	}

	// tests that room labels are matching their symbols
	@Test
	public void testRoomLabels() {
		assertEquals("Dinosaur Exhibit", board.getRoom('D').getName());
		assertEquals("Bathroom", board.getRoom('B').getName());
		assertEquals("Mummy Exhibit", board.getRoom('M').getName());
		assertEquals("Rock Exhibit", board.getRoom('R').getName());
		assertEquals("Greek Exhibit", board.getRoom('Q').getName());
		assertEquals("Entrance", board.getRoom('E').getName());
		assertEquals("Food Court", board.getRoom('C').getName());
		assertEquals("Gift Shop", board.getRoom('G').getName());
	}

	// testing that the dimensions are correct
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}

	// testing the directions of the doors are working
	@Test
	public void FourDoorDirections() {
		BoardCell cell = board.getCell(6, 4);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(3, 7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(9, 11);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(11, 7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		// Test that walkways are not doors
		cell = board.getCell(3, 4);
		assertFalse(cell.isDoorway());
	}

	// testing the number of doorways expected are present
	@Test
	public void testNumberDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		BoardCell cell = board.getCell(6, 4);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(3, 7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(1, 10);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(10, 2);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		// Test that walkways are not doors
		cell = board.getCell(12, 11);
		assertFalse(cell.isDoorway());
		Assert.assertEquals(14, numDoors);
	}

	// testing all the different types of room
	@Test
	public void testRooms() {
		BoardCell cell = board.getCell(2, 2);
		Room room = board.getRoom(cell);

		// testing a location
		assertTrue(room != null);
		assertEquals(room.getName(), "Gift Shop");
		assertFalse(cell.isLabel());
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isDoorway());

		// testing the label cell
		cell = board.getCell(1, 13);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Mummy Exhibit");
		assertTrue(cell.isLabel());
		assertTrue(room.getLabelCell() == cell);

		// testing the center cell
		cell = board.getCell(1, 12);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Mummy Exhibit");
		assertTrue(cell.isRoomCenter());
		assertTrue(room.getCenterCell() == cell);

		// testing secrete passage
		cell = board.getCell(12, 14);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Rock Exhibit");
		assertTrue(cell.getSecretPassage() == 'G');

		// testing walkways
		cell = board.getCell(3, 8);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Walkway");
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isLabel());

		// testing closet
		cell = board.getCell(6, 7);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Unused");
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isLabel());
	}

}
