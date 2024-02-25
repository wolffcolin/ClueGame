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

public class BoardTestsExp {
	
	TestBoard board;
	
	@BeforeEach
	public void setup() {
		
		board = new TestBoard();
		
	}
	
	/**
	 * Tests for adjacencies between cells
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testAdjacency() {
		
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		
		TestBoardCell cell1 = board.getCell(0, 4);
		Set<TestBoardCell> testList1 = cell1.getAdjList();
		Assert.assertTrue(testList1.contains(board.getCell(0, 5)));
		Assert.assertTrue(testList1.contains(board.getCell(1, 4)));
		
		TestBoardCell cell2 = board.getCell(3, 11);
		Set<TestBoardCell> testList2 = cell2.getAdjList();
		Assert.assertTrue(testList2.contains(board.getCell(3, 10)));
		Assert.assertTrue(testList2.contains(board.getCell(4, 11)));
		Assert.assertTrue(testList2.contains(board.getCell(3, 12)));
		
		TestBoardCell cell3 = board.getCell(13, 4);
		Set<TestBoardCell> testList3 = cell3.getAdjList();
		Assert.assertTrue(testList3.contains(board.getCell(12, 4)));
		Assert.assertTrue(testList3.contains(board.getCell(13, 5)));
		
		TestBoardCell cell4 = board.getCell(13, 11);
		Set<TestBoardCell> testList4 = cell4.getAdjList();
		Assert.assertTrue(testList4.contains(board.getCell(13, 10)));
		Assert.assertTrue(testList4.contains(board.getCell(14, 11)));
	}
	
	/**
	 * Tests for targets when a room involved
	 */
	@Test
	public void TestTargetsRoom() {
		
		board.getCell(0, 1).setRoom(true);
		TestBoardCell cell = board.getCell(1, 3);
		
		board.calcTargets(cell, 6);
		Set<TestBoardCell> targets = board.getTargets();

		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		
		board.getCell(0, 1).setRoom(false);
		board.getCell(2, 2).setRoom(true);
		TestBoardCell cell1 = board.getCell(3, 3);
		board.calcTargets(cell, 6);
		Set<TestBoardCell> targets1 = board.getTargets();
		Assert.assertTrue(targets1.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets1.contains(board.getCell(3, 2)));
		
	}
	
	/**
	 * Tests for targets when interfering occupied space is involved
	 */
	@Test
	public void TestTargetsOccupied() {
		
		TestBoardCell cell = board.getCell(0, 0);
		board.getCell(3, 3).setOccupied(true);
		
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(!targets.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets.contains(board.getCell(7, 10)));
		
		TestBoardCell cell1 = board.getCell(1, 1);
		board.getCell(2, 2).setOccupied(true);
		
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets1 = board.getTargets();
		
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(!targets1.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets1.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets1.contains(board.getCell(1, 0)));
		
	}
	
	/**
	 * tests targets that includes rooms and multiple cells.
	 */
	@Test
	public void TestTargetsMixed() {
		
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setRoom(true);
		
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
		
		
		board.getCell(0, 2).setOccupied(false);
		board.getCell(1, 2).setRoom(false);
		board.getCell(4, 1).setOccupied(true);
		board.getCell(3, 4).setRoom(true);
		
		TestBoardCell cell1 = board.getCell(0, 0);
		board.calcTargets(cell1, 3);
		Set<TestBoardCell> targets1 = board.getTargets();
		Assert.assertEquals(3, targets1.size());
		Assert.assertTrue(targets1.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets1.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets1.contains(board.getCell(2, 2)));
	}

	/**
	 * test for targets on empty boards
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		
		TestBoardCell cell1 = board.getCell(0, 3);
		board.calcTargets(cell1, 2);
		Set<TestBoardCell> targets1 = board.getTargets();
		Assert.assertEquals(6, targets1.size());
		Assert.assertTrue(targets1.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets1.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets1.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets1.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets1.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets1.contains(board.getCell(1, 1)));
		
		TestBoardCell cell2 = board.getCell(3, 2);
		board.calcTargets(cell2, 1);
		Set<TestBoardCell> targets2 = board.getTargets();
		Assert.assertEquals(6, targets2.size());
		Assert.assertTrue(targets2.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets2.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets2.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets2.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets2.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets2.contains(board.getCell(1, 2)));
		
		TestBoardCell cell3 = board.getCell(2, 3);
		board.calcTargets(cell3, 4);
		Set<TestBoardCell> targets3 = board.getTargets();
		Assert.assertEquals(6, targets3.size());
		Assert.assertTrue(targets3.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets3.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets3.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets3.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets3.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets3.contains(board.getCell(3, 3)));
		
		TestBoardCell cell4 = board.getCell(3, 3);
		board.calcTargets(cell4, 5);
		Set<TestBoardCell> targets4 = board.getTargets();
		Assert.assertEquals(6, targets4.size());
		Assert.assertTrue(targets4.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets4.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets4.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets4.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets4.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets4.contains(board.getCell(1, 0)));
		
		TestBoardCell cell5 = board.getCell(3, 0);
		board.calcTargets(cell5, 6);
		Set<TestBoardCell> targets5 = board.getTargets();
		Assert.assertEquals(6, targets5.size());
		Assert.assertTrue(targets5.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets5.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets5.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets5.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets5.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets5.contains(board.getCell(3, 1)));
		
		TestBoardCell cell6 = board.getCell(4, 9);
		board.calcTargets(cell6, 4);
		Set<TestBoardCell> targets6 = board.getTargets();
		Assert.assertEquals(6, targets6.size());
		Assert.assertTrue(targets6.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets6.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets6.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets6.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets6.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets6.contains(board.getCell(2, 1)));
		
		TestBoardCell cell7 = board.getCell(4, 9);
		board.calcTargets(cell7, 3);
		Set<TestBoardCell> targets7 = board.getTargets();
		Assert.assertEquals(6, targets7.size());
		Assert.assertTrue(targets7.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets7.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets7.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets7.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets7.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets7.contains(board.getCell(0, 2)));
	}
	
}
