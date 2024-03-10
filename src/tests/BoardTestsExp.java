/*
 
Class: BoardTestsExp
Description: JUnit tests for adjacency and target calculation for the clue board
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;
import junit.framework.Assert;

class BoardTestsExp {

	TestBoard board;

	@BeforeEach
	void setup() {

		board = new TestBoard();

	}

	// tests that cells that should be marked adjacent are marked
	@Test
	void testAdjacency() {

		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));

		TestBoardCell cell1 = board.getCell(0, 3);
		Set<TestBoardCell> testList1 = cell1.getAdjList();
		assertTrue(testList1.contains(board.getCell(0, 2)));
		assertTrue(testList1.contains(board.getCell(1, 3)));

		TestBoardCell cell2 = board.getCell(1, 1);
		Set<TestBoardCell> testList2 = cell2.getAdjList();
		assertTrue(testList2.contains(board.getCell(0, 1)));
		assertTrue(testList2.contains(board.getCell(1, 0)));
		assertTrue(testList2.contains(board.getCell(2, 1)));
		assertTrue(testList2.contains(board.getCell(1, 2)));

		TestBoardCell cell3 = board.getCell(2, 2);
		Set<TestBoardCell> testList3 = cell3.getAdjList();
		assertTrue(testList3.contains(board.getCell(2, 1)));
		assertTrue(testList3.contains(board.getCell(1, 2)));
		assertTrue(testList3.contains(board.getCell(3, 2)));
		assertTrue(testList3.contains(board.getCell(2, 3)));

		TestBoardCell cell4 = board.getCell(0, 3);
		Set<TestBoardCell> testList4 = cell4.getAdjList();
		assertTrue(testList4.contains(board.getCell(0, 2)));
		assertTrue(testList4.contains(board.getCell(1, 3)));
	}

	// tests that cells that should be marked as targets are when a cell is marked
	// as a room
	@Test
	void TestTargetsRoom() {

		board.getCell(0, 1).setRoom(true);
		TestBoardCell cell = board.getCell(1, 3);

		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();

		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		board.getCell(0, 1).setRoom(false);

		board.getCell(2, 2).setRoom(true);
		TestBoardCell cell1 = board.getCell(3, 3);

		board.calcTargets(cell1, 3);
		Set<TestBoardCell> targets1 = board.getTargets();
		assertEquals(6, targets1.size());
		assertTrue(targets1.contains(board.getCell(3, 0)));
		assertTrue(targets1.contains(board.getCell(3, 2)));
		assertTrue(targets1.contains(board.getCell(2, 1)));
		assertTrue(targets1.contains(board.getCell(2, 3)));
		assertTrue(targets1.contains(board.getCell(1, 2)));
		assertTrue(targets1.contains(board.getCell(0, 3)));

	}

	// test that cells that should be marked as targets are when a cell is marked as
	// occupied
	@Test
	void TestTargetsOccupied() {

		TestBoardCell cell = board.getCell(0, 0);
		board.getCell(3, 3).setOccupied(true);

		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();

		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(3, 0)));

		board.getCell(3, 3).setOccupied(false);

		TestBoardCell cell1 = board.getCell(1, 1);
		board.getCell(2, 2).setOccupied(true);

		board.calcTargets(cell1, 3);
		Set<TestBoardCell> targets1 = board.getTargets();

		assertEquals(8, targets.size());
		assertTrue(targets1.contains(board.getCell(0, 1)));
		assertTrue(targets1.contains(board.getCell(0, 3)));
		assertTrue(targets1.contains(board.getCell(1, 0)));
		assertTrue(targets1.contains(board.getCell(1, 2)));
		assertTrue(targets1.contains(board.getCell(2, 1)));
		assertTrue(targets1.contains(board.getCell(2, 3)));
		assertTrue(targets1.contains(board.getCell(3, 0)));
		assertTrue(targets1.contains(board.getCell(3, 2)));

	}

	// test that cells that should be marked as targets are when a cell is marked as
	// occupied and as a room
	@Test
	void TestTargetsMixed() {

		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setRoom(true);

		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));

		board.getCell(0, 2).setOccupied(false);
		board.getCell(1, 2).setRoom(false);
		board.getCell(3, 1).setOccupied(true);
		board.getCell(3, 3).setRoom(true);

		TestBoardCell cell1 = board.getCell(0, 0);
		board.calcTargets(cell1, 3);
		Set<TestBoardCell> targets1 = board.getTargets();
		assertEquals(6, targets1.size());
		assertTrue(targets1.contains(board.getCell(1, 0)));
		assertTrue(targets1.contains(board.getCell(0, 1)));
		assertTrue(targets1.contains(board.getCell(2, 1)));
		assertTrue(targets1.contains(board.getCell(3, 0)));
		assertTrue(targets1.contains(board.getCell(1, 2)));
		assertTrue(targets1.contains(board.getCell(0, 3)));
	}

	// test that cells that should be marked as targets are when no cells are marked
	// as occupied or as a room
	@Test
	void testTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(3, 0)));

		TestBoardCell cell1 = board.getCell(0, 3);
		board.calcTargets(cell1, 2);
		Set<TestBoardCell> targets1 = board.getTargets();
		assertEquals(3, targets1.size());
		assertTrue(targets1.contains(board.getCell(0, 1)));
		assertTrue(targets1.contains(board.getCell(1, 2)));
		assertTrue(targets1.contains(board.getCell(2, 3)));

		TestBoardCell cell2 = board.getCell(3, 2);
		board.calcTargets(cell2, 1);
		Set<TestBoardCell> targets2 = board.getTargets();
		assertEquals(3, targets2.size());
		assertTrue(targets2.contains(board.getCell(3, 1)));
		assertTrue(targets2.contains(board.getCell(2, 2)));
		assertTrue(targets2.contains(board.getCell(3, 3)));

		TestBoardCell cell3 = board.getCell(2, 3);
		board.calcTargets(cell3, 4);
		Set<TestBoardCell> targets3 = board.getTargets();
		assertEquals(7, targets3.size());
		assertTrue(targets3.contains(board.getCell(0, 1)));
		assertTrue(targets3.contains(board.getCell(0, 3)));
		assertTrue(targets3.contains(board.getCell(1, 0)));
		assertTrue(targets3.contains(board.getCell(1, 2)));
		assertTrue(targets3.contains(board.getCell(2, 1)));
		assertTrue(targets3.contains(board.getCell(3, 0)));
		assertTrue(targets3.contains(board.getCell(3, 2)));

		TestBoardCell cell4 = board.getCell(3, 3);
		board.calcTargets(cell4, 5);
		Set<TestBoardCell> targets4 = board.getTargets();
		assertEquals(8, targets4.size());
		assertTrue(targets4.contains(board.getCell(0, 1)));
		assertTrue(targets4.contains(board.getCell(0, 3)));
		assertTrue(targets4.contains(board.getCell(1, 0)));
		assertTrue(targets4.contains(board.getCell(1, 2)));
		assertTrue(targets4.contains(board.getCell(2, 1)));
		assertTrue(targets4.contains(board.getCell(2, 3)));
		assertTrue(targets4.contains(board.getCell(3, 0)));
		assertTrue(targets4.contains(board.getCell(3, 2)));

		TestBoardCell cell5 = board.getCell(3, 0);
		board.calcTargets(cell5, 2);
		Set<TestBoardCell> targets5 = board.getTargets();
		assertEquals(3, targets5.size());
		assertTrue(targets5.contains(board.getCell(1, 0)));
		assertTrue(targets5.contains(board.getCell(2, 1)));
		assertTrue(targets5.contains(board.getCell(3, 2)));

		TestBoardCell cell6 = board.getCell(1, 1);
		board.calcTargets(cell6, 4);
		Set<TestBoardCell> targets6 = board.getTargets();
		assertEquals(7, targets6.size());
		assertTrue(targets6.contains(board.getCell(0, 0)));
		assertTrue(targets6.contains(board.getCell(0, 2)));
		assertTrue(targets6.contains(board.getCell(1, 3)));
		assertTrue(targets6.contains(board.getCell(2, 0)));
		assertTrue(targets6.contains(board.getCell(2, 2)));
		assertTrue(targets6.contains(board.getCell(3, 1)));
		assertTrue(targets6.contains(board.getCell(3, 3)));

		TestBoardCell cell7 = board.getCell(2, 1);
		board.calcTargets(cell7, 3);
		Set<TestBoardCell> targets7 = board.getTargets();
		assertEquals(8, targets7.size());
		assertTrue(targets7.contains(board.getCell(0, 0)));
		assertTrue(targets7.contains(board.getCell(0, 2)));
		assertTrue(targets7.contains(board.getCell(1, 1)));
		assertTrue(targets7.contains(board.getCell(1, 3)));
		assertTrue(targets7.contains(board.getCell(2, 0)));
		assertTrue(targets7.contains(board.getCell(2, 2)));
		assertTrue(targets7.contains(board.getCell(3, 1)));
		assertTrue(targets7.contains(board.getCell(3, 3)));
	}

}
