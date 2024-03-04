package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

class BoardAdjTargetTest {
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load config files
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms() {
		// Mummy Exhibit
		Set<BoardCell> testList = board.getCell(1, 12).getAdjList();
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(1, 10)));
		assertTrue(testList.contains(board.getCell(14, 0)));

		// Fossil Exhibit
		testList = board.getCell(7, 1).getAdjList();
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(9, 2)));
		assertTrue(testList.contains(board.getCell(6, 4)));
		assertTrue(testList.contains(board.getCell(7, 4)));

		// Rock Exhibit
		testList = board.getCell(13, 13).getAdjList();
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(13, 11)));
		assertTrue(testList.contains(board.getCell(1, 1)));
	}

	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor() {
		Set<BoardCell> testList = board.getCell(9, 2).getAdjList();
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(7, 1)));
		assertTrue(testList.contains(board.getCell(9, 1)));
		assertTrue(testList.contains(board.getCell(9, 3)));
		assertTrue(testList.contains(board.getCell(10, 2)));

		testList = board.getCell(10, 2).getAdjList();
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(13, 1)));
		assertTrue(testList.contains(board.getCell(9, 2)));
		assertTrue(testList.contains(board.getCell(10, 1)));
		assertTrue(testList.contains(board.getCell(10, 3)));

		testList = board.getCell(13, 11).getAdjList();
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(13, 13)));
		assertTrue(testList.contains(board.getCell(13, 10)));
		assertTrue(testList.contains(board.getCell(12, 11)));
	}

	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways() {
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getCell(24, 14).getAdjList();
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(23, 14)));

		// Test near a door but not adjacent
		testList = board.getCell(18, 4).getAdjList();
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(18, 3)));
		assertTrue(testList.contains(board.getCell(17, 4)));
		assertTrue(testList.contains(board.getCell(18, 5)));

		// Test adjacent to walkways
		testList = board.getCell(19, 6).getAdjList();
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(19, 5)));
		assertTrue(testList.contains(board.getCell(19, 7)));
		assertTrue(testList.contains(board.getCell(18, 6)));
		assertTrue(testList.contains(board.getCell(20, 6)));

		// Test next to closet
		testList = board.getCell(9, 14).getAdjList();
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(9, 15)));
		assertTrue(testList.contains(board.getCell(8, 14)));
		assertTrue(testList.contains(board.getCell(10, 14)));

	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInFossilExhibit() {
		// test a roll of 1
		board.calcTargets(board.getCell(7, 1), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(9, 2)));
		assertTrue(targets.contains(board.getCell(6, 4)));

		// test a roll of 3
		board.calcTargets(board.getCell(7, 1), 3);
		targets = board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(5, 5)));
		assertTrue(targets.contains(board.getCell(7, 5)));
		assertTrue(targets.contains(board.getCell(8, 4)));
		assertTrue(targets.contains(board.getCell(9, 4)));
		assertTrue(targets.contains(board.getCell(10, 1)));
		assertTrue(targets.contains(board.getCell(10, 3)));
		assertTrue(targets.contains(board.getCell(9, 4)));

		// test a roll of 4
		board.calcTargets(board.getCell(7, 1), 4);
		targets = board.getTargets();
		assertEquals(14, targets.size());
		assertTrue(targets.contains(board.getCell(3, 4)));
		assertTrue(targets.contains(board.getCell(4, 3)));
		assertTrue(targets.contains(board.getCell(5, 6)));
		assertTrue(targets.contains(board.getCell(4, 5)));
		assertTrue(targets.contains(board.getCell(8, 5)));
		assertTrue(targets.contains(board.getCell(9, 4)));
		assertTrue(targets.contains(board.getCell(7, 4)));
		assertTrue(targets.contains(board.getCell(5, 4)));

		assertTrue(targets.contains(board.getCell(12, 4)));
		assertTrue(targets.contains(board.getCell(10, 2)));
		assertTrue(targets.contains(board.getCell(9, 5)));
		assertTrue(targets.contains(board.getCell(10, 4)));
		assertTrue(targets.contains(board.getCell(12, 4)));
		assertTrue(targets.contains(board.getCell(8, 4)));

	}

	@Test
	public void testTargetsInGreekExhibit() {
		// test a roll of 1
		board.calcTargets(board.getCell(13, 1), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(10, 2)));
		assertTrue(targets.contains(board.getCell(12, 4)));
		assertTrue(targets.contains(board.getCell(1, 12)));

		// test a roll of 3
		board.calcTargets(board.getCell(13, 1), 3);
		targets = board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(7, 1)));
		assertTrue(targets.contains(board.getCell(9, 3)));
		assertTrue(targets.contains(board.getCell(1, 9)));
		assertTrue(targets.contains(board.getCell(13, 5)));

		// test a roll of 4
		board.calcTargets(board.getCell(13, 1), 4);
		targets = board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(9, 2)));
		assertTrue(targets.contains(board.getCell(10, 5)));
		assertTrue(targets.contains(board.getCell(2, 9)));
		assertTrue(targets.contains(board.getCell(3, 10)));
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(8, 17), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(7, 17)));
		assertTrue(targets.contains(board.getCell(8, 18)));

		// test a roll of 3
		board.calcTargets(board.getCell(8, 17), 3);
		targets = board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(7, 17)));
		assertTrue(targets.contains(board.getCell(7, 19)));
		assertTrue(targets.contains(board.getCell(9, 15)));

		// test a roll of 4
		board.calcTargets(board.getCell(8, 17), 4);
		targets = board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(10, 15)));
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(5, 16)));
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(11, 2), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(11, 1)));
		assertTrue(targets.contains(board.getCell(11, 3)));

		// test a roll of 3
		board.calcTargets(board.getCell(11, 2), 3);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(8, 2)));
		assertTrue(targets.contains(board.getCell(11, 5)));

		// test a roll of 4
		board.calcTargets(board.getCell(11, 2), 4);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(8, 2)));
		assertTrue(targets.contains(board.getCell(11, 6)));
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(13, 7), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(13, 6)));
		assertTrue(targets.contains(board.getCell(12, 7)));

		// test a roll of 3
		board.calcTargets(board.getCell(13, 7), 3);
		targets = board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(15, 6)));
		assertTrue(targets.contains(board.getCell(14, 7)));
		assertTrue(targets.contains(board.getCell(11, 8)));

		// test a roll of 4
		board.calcTargets(board.getCell(13, 7), 4);
		targets = board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(15, 9)));
		assertTrue(targets.contains(board.getCell(11, 5)));
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(5, 7).setOccupied(true);
		board.calcTargets(board.getCell(5, 9), 3);
		board.getCell(5, 7).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(3, 10)));
		assertTrue(targets.contains(board.getCell(6, 11)));
		assertTrue(targets.contains(board.getCell(5, 13)));
		assertFalse(targets.contains(board.getCell(15, 7)));
		assertFalse(targets.contains(board.getCell(17, 7)));

		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(12, 20).setOccupied(true);
		board.getCell(8, 18).setOccupied(true);
		board.calcTargets(board.getCell(8, 17), 1);
		board.getCell(12, 20).setOccupied(false);
		board.getCell(8, 18).setOccupied(false);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(7, 17)));
		assertTrue(targets.contains(board.getCell(8, 16)));
		assertTrue(targets.contains(board.getCell(12, 20)));

		// check leaving a room with a blocked doorway
		board.getCell(12, 15).setOccupied(true);
		board.calcTargets(board.getCell(12, 20), 3);
		board.getCell(12, 15).setOccupied(false);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(8, 19)));
		assertTrue(targets.contains(board.getCell(8, 15)));

	}

}
