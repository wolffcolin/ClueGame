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
import experiment.BoardCell;
import junit.framework.Assert;

class BoardTestsExp {

	TestBoard board;

	@BeforeEach
	void setup() {

		board = new TestBoard();

	}

	// tests that cells that should be marked adjacnet are marked
	@SuppressWarnings("deprecation")
	@Test
	void testAdjacency() {

		BoardCell cell = board.getCell(0, 0);
		Set<BoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));

		BoardCell cell1 = board.getCell(0, 3);
		Set<BoardCell> testList1 = cell1.getAdjList();
		Assert.assertTrue(testList1.contains(board.getCell(0, 2)));
		Assert.assertTrue(testList1.contains(board.getCell(1, 3)));

		BoardCell cell2 = board.getCell(1, 1);
		Set<BoardCell> testList2 = cell2.getAdjList();
		Assert.assertTrue(testList2.contains(board.getCell(0, 1)));
		Assert.assertTrue(testList2.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList2.contains(board.getCell(2, 1)));
		Assert.assertTrue(testList2.contains(board.getCell(1, 2)));

		BoardCell cell3 = board.getCell(2, 2);
		Set<BoardCell> testList3 = cell3.getAdjList();
		Assert.assertTrue(testList3.contains(board.getCell(2, 1)));
		Assert.assertTrue(testList3.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList3.contains(board.getCell(3, 2)));
		Assert.assertTrue(testList3.contains(board.getCell(2, 3)));

		BoardCell cell4 = board.getCell(0, 3);
		Set<BoardCell> testList4 = cell4.getAdjList();
		Assert.assertTrue(testList4.contains(board.getCell(0, 2)));
		Assert.assertTrue(testList4.contains(board.getCell(1, 3)));
	}

	// tests that cells that should be marked as targets are when a cell is marked
	// as a room
	@Test
	void TestTargetsRoom() {

		board.getCell(0, 1).setRoom(true);
		BoardCell cell = board.getCell(1, 3);

		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();

		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
		board.getCell(0, 1).setRoom(false);

		board.getCell(2, 2).setRoom(true);
		BoardCell cell1 = board.getCell(3, 3);

		board.calcTargets(cell1, 3);
		Set<BoardCell> targets1 = board.getTargets();
		Assert.assertEquals(6, targets1.size());
		Assert.assertTrue(targets1.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets1.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets1.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets1.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets1.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets1.contains(board.getCell(0, 3)));

	}

	// test that cells that should be marked as targets are when a cell is marked as
	// occupied
	@Test
	void TestTargetsOccupied() {

		BoardCell cell = board.getCell(0, 0);
		board.getCell(3, 3).setOccupied(true);

		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();

		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));

		board.getCell(3, 3).setOccupied(false);

		BoardCell cell1 = board.getCell(1, 1);
		board.getCell(2, 2).setOccupied(true);

		board.calcTargets(cell1, 3);
		Set<BoardCell> targets1 = board.getTargets();

		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets1.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets1.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets1.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets1.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets1.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets1.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets1.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets1.contains(board.getCell(3, 2)));

	}

	// test that cells that should be marked as targets are when a cell is marked as
	// occupied and as a room
	@Test
	void TestTargetsMixed() {

		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setRoom(true);

		BoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));

		board.getCell(0, 2).setOccupied(false);
		board.getCell(1, 2).setRoom(false);
		board.getCell(3, 1).setOccupied(true);
		board.getCell(3, 3).setRoom(true);

		BoardCell cell1 = board.getCell(0, 0);
		board.calcTargets(cell1, 3);
		Set<BoardCell> targets1 = board.getTargets();
		Assert.assertEquals(6, targets1.size());
		Assert.assertTrue(targets1.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets1.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets1.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets1.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets1.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets1.contains(board.getCell(0, 3)));
	}

	// test that cells that should be marked as targets are when no cells are marked
	// as occupied or as a room
	@SuppressWarnings("deprecation")
	@Test
	void testTargetsNormal() {
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));

		BoardCell cell1 = board.getCell(0, 3);
		board.calcTargets(cell1, 2);
		Set<BoardCell> targets1 = board.getTargets();
		Assert.assertEquals(3, targets1.size());
		Assert.assertTrue(targets1.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets1.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets1.contains(board.getCell(2, 3)));

		BoardCell cell2 = board.getCell(3, 2);
		board.calcTargets(cell2, 1);
		Set<BoardCell> targets2 = board.getTargets();
		Assert.assertEquals(3, targets2.size());
		Assert.assertTrue(targets2.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets2.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets2.contains(board.getCell(3, 3)));

		BoardCell cell3 = board.getCell(2, 3);
		board.calcTargets(cell3, 4);
		Set<BoardCell> targets3 = board.getTargets();
		Assert.assertEquals(7, targets3.size());
		Assert.assertTrue(targets3.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets3.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets3.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets3.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets3.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets3.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets3.contains(board.getCell(3, 2)));

		BoardCell cell4 = board.getCell(3, 3);
		board.calcTargets(cell4, 5);
		Set<BoardCell> targets4 = board.getTargets();
		Assert.assertEquals(8, targets4.size());
		Assert.assertTrue(targets4.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets4.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets4.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets4.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets4.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets4.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets4.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets4.contains(board.getCell(3, 2)));

		BoardCell cell5 = board.getCell(3, 0);
		board.calcTargets(cell5, 2);
		Set<BoardCell> targets5 = board.getTargets();
		Assert.assertEquals(3, targets5.size());
		Assert.assertTrue(targets5.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets5.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets5.contains(board.getCell(3, 2)));

		BoardCell cell6 = board.getCell(1, 1);
		board.calcTargets(cell6, 4);
		Set<BoardCell> targets6 = board.getTargets();
		Assert.assertEquals(7, targets6.size());
		Assert.assertTrue(targets6.contains(board.getCell(0, 0)));
		Assert.assertTrue(targets6.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets6.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets6.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets6.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets6.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets6.contains(board.getCell(3, 3)));

		BoardCell cell7 = board.getCell(2, 1);
		board.calcTargets(cell7, 3);
		Set<BoardCell> targets7 = board.getTargets();
		Assert.assertEquals(8, targets7.size());
		Assert.assertTrue(targets7.contains(board.getCell(0, 0)));
		Assert.assertTrue(targets7.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets7.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets7.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets7.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets7.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets7.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets7.contains(board.getCell(3, 3)));
	}

}
