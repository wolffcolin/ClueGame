package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

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
		Assert.assertEquals(17, numDoors);
	}
	
	@Test
	public void testRooms() {
		
	}

}
