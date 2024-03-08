/*
 
Class: BoardAdjTargetTest
Description: tests various cells in various situations on a real board
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

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
		Set<BoardCell> testList = board.getAdjList(1, 12);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(1, 10)));
		assertTrue(testList.contains(board.getCell(13, 1)));

		// Fossil Exhibit
		testList = board.getAdjList(7, 1);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(9, 2)));
		assertTrue(testList.contains(board.getCell(6, 4)));

		// Rock Exhibit
		testList = board.getAdjList(13, 13);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(13, 11)));
		assertTrue(testList.contains(board.getCell(1, 1)));
	}

	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor() {
		Set<BoardCell> testList = board.getAdjList(9, 2);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(7, 1)));
		assertTrue(testList.contains(board.getCell(9, 1)));
		assertTrue(testList.contains(board.getCell(9, 3)));
		assertTrue(testList.contains(board.getCell(10, 2)));

		testList = board.getAdjList(10, 2);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(13, 1)));
		assertTrue(testList.contains(board.getCell(9, 2)));
		assertTrue(testList.contains(board.getCell(10, 1)));
		assertTrue(testList.contains(board.getCell(10, 3)));

		testList = board.getAdjList(13, 11);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(13, 13)));
		assertTrue(testList.contains(board.getCell(13, 10)));
		assertTrue(testList.contains(board.getCell(12, 11)));
	}

	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways() {
		// Test on bottom edge of board
		Set<BoardCell> testList = board.getAdjList(13, 10);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(13, 9)));
		assertTrue(testList.contains(board.getCell(13, 11)));
		assertTrue(testList.contains(board.getCell(12, 10)));

		// Test near a door but not adjacent
		testList = board.getAdjList(7, 5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(7, 4)));
		assertTrue(testList.contains(board.getCell(8, 5)));
		assertTrue(testList.contains(board.getCell(6, 5)));

		// Test adjacent to walkways
		testList = board.getAdjList(7, 10);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(7, 11)));
		assertTrue(testList.contains(board.getCell(7, 9)));
		assertTrue(testList.contains(board.getCell(8, 10)));
		assertTrue(testList.contains(board.getCell(6, 10)));

		// Test next to closet
		testList = board.getAdjList(7, 9);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(7, 10)));
		assertTrue(testList.contains(board.getCell(6, 9)));
		assertTrue(testList.contains(board.getCell(8, 9)));

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
		assertEquals(16, targets.size());
		assertTrue(targets.contains(board.getCell(3, 4)));
		assertTrue(targets.contains(board.getCell(4, 3)));
		assertTrue(targets.contains(board.getCell(5, 6)));
		assertTrue(targets.contains(board.getCell(4, 5)));
		assertTrue(targets.contains(board.getCell(8, 5)));
		assertTrue(targets.contains(board.getCell(9, 4)));
		assertTrue(targets.contains(board.getCell(7, 4)));
		assertTrue(targets.contains(board.getCell(5, 4)));
		assertTrue(targets.contains(board.getCell(10, 2)));
		assertTrue(targets.contains(board.getCell(9, 5)));
		assertTrue(targets.contains(board.getCell(10, 4)));
		assertTrue(targets.contains(board.getCell(8, 4)));

	}

	@Test
	public void testTargetsInGreekExhibit() {
		// test a roll of 1
		board.calcTargets(board.getCell(13, 1), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(10, 2)));
		assertTrue(targets.contains(board.getCell(12, 4)));
		assertTrue(targets.contains(board.getCell(1, 12)));

		// test a roll of 3
		board.calcTargets(board.getCell(13, 1), 3);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(7, 1)));
		assertTrue(targets.contains(board.getCell(9, 3)));
		assertTrue(targets.contains(board.getCell(1, 12)));
		assertTrue(targets.contains(board.getCell(13, 5)));

		// test a roll of 4
		board.calcTargets(board.getCell(13, 1), 4);
		targets = board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(9, 2)));
		assertTrue(targets.contains(board.getCell(10, 5)));
		assertTrue(targets.contains(board.getCell(10, 3)));
		assertTrue(targets.contains(board.getCell(1, 12)));
	}

	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(1, 5), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(1, 7)));
		assertTrue(targets.contains(board.getCell(1, 4)));
		assertTrue(targets.contains(board.getCell(2, 5)));

		// test a roll of 3
		board.calcTargets(board.getCell(1, 5), 3);
		targets = board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(1, 7)));
		assertTrue(targets.contains(board.getCell(4, 5)));
		assertTrue(targets.contains(board.getCell(3, 4)));
		assertTrue(targets.contains(board.getCell(0, 5)));
		assertTrue(targets.contains(board.getCell(1, 1)));

		// test a roll of 4
		board.calcTargets(board.getCell(1, 5), 4);
		targets = board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(3, 7)));
		assertTrue(targets.contains(board.getCell(0, 4)));
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(1, 7)));
		assertTrue(targets.contains(board.getCell(4, 4)));
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(7, 10), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(6, 10)));
		assertTrue(targets.contains(board.getCell(8, 10)));
		assertTrue(targets.contains(board.getCell(7, 9)));
		assertTrue(targets.contains(board.getCell(7, 11)));

		// test a roll of 3
		board.calcTargets(board.getCell(7, 13), 3);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(7, 10)));
		assertTrue(targets.contains(board.getCell(6, 13)));
		assertTrue(targets.contains(board.getCell(6, 11)));
		assertTrue(targets.contains(board.getCell(8, 11)));

		// test a roll of 4
		board.calcTargets(board.getCell(7, 13), 4);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(6, 12)));
		assertTrue(targets.contains(board.getCell(6, 10)));
		assertTrue(targets.contains(board.getCell(5, 11)));
		assertTrue(targets.contains(board.getCell(9, 11)));
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(10, 5), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(10, 6)));
		assertTrue(targets.contains(board.getCell(9, 5)));

		// test a roll of 3
		board.calcTargets(board.getCell(3, 1), 3);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 4)));
		assertTrue(targets.contains(board.getCell(4, 1)));
		assertTrue(targets.contains(board.getCell(4, 3)));

		// test a roll of 4
		board.calcTargets(board.getCell(3, 1), 4);
		targets = board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(4, 2)));
		assertTrue(targets.contains(board.getCell(3, 5)));
		assertTrue(targets.contains(board.getCell(2, 4)));
		assertTrue(targets.contains(board.getCell(4, 4)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 3 blocked 2 left
		board.getCell(5, 7).setOccupied(true);
		board.calcTargets(board.getCell(5, 9), 3);
		board.getCell(5, 7).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(3, 10)));
		assertTrue(targets.contains(board.getCell(6, 11)));
		assertTrue(targets.contains(board.getCell(8, 9)));
		assertTrue(targets.contains(board.getCell(7, 10)));
		assertFalse(targets.contains(board.getCell(5, 7)));

		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(13, 13).setOccupied(true);
		board.calcTargets(board.getCell(13, 11), 1);
		board.getCell(13, 13).setOccupied(false);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(13, 13)));

		// check leaving a room with a blocked doorway
		board.getCell(13, 11).setOccupied(true);
		board.calcTargets(board.getCell(13, 13), 1);
		board.getCell(13, 11).setOccupied(false);
		targets = board.getTargets();
		assertEquals(1, targets.size());
		assertFalse(targets.contains(board.getCell(13, 11)));

	}

}
