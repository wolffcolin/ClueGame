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

class FileInitTests {

	public static final int NUM_ROWS = 15;
	public static final int NUM_COLUMNS = 15;
	
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");

		board.initialize();
		
	}
	
	@Test
	public void testRoomLabels() {
		assertEquals("Dinosaur Exhibit", board.getRoom('D').getName() );
		assertEquals("Bathroom", board.getRoom('B').getName() );
		assertEquals("Mummy Exhibit", board.getRoom('M').getName() );
		assertEquals("Rock Exhibit", board.getRoom('R').getName() );
		assertEquals("Greek Exhibit", board.getRoom('Q').getName() );
		assertEquals("Entrance", board.getRoom('E').getName() );
		assertEquals("Food Court", board.getRoom('C').getName() );
		assertEquals("Gift Shop", board.getRoom('G').getName() );
	}
	
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
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
		cell = board.getCell(13, 7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		// Test that walkways are not doors
		cell = board.getCell(3, 4);
		assertFalse(cell.isDoorway());
	}
	
	@Test
	public void testNumberDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(1, numDoors);
	}
	
	@Test
	public void testRooms() {
		
	}

}
