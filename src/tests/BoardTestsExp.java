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

	@SuppressWarnings("deprecation")
	@Test
	void testAdjacency() {
		
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
	
	@Test
	void TestTargetsRoom() {
		
		TestBoardCell cell = board.getCell(1, 7);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 5)));
		Assert.assertTrue(testList.contains(board.getCell(3, 7)));
		
		TestBoardCell cell1 = board.getCell(13, 7);
		Set<TestBoardCell> testList1 = cell1.getAdjList();
		Assert.assertTrue(testList1.contains(board.getCell(11, 7)));
		Assert.assertTrue(testList1.contains(board.getCell(12, 9)));
		
	}
	
	@Test
	void TestTargetsOccupied() {
		
		TestBoardCell cell = board.getCell(6, 10);
		board.getCell(5, 10).setOccupied();
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(!testList.contains(board.getCell(5, 10)));
		Assert.assertTrue(testList.contains(board.getCell(7, 10)));
		
		TestBoardCell cell1 = board.getCell(9, 4);
		board.getCell(9, 5).setOccupied();
		Set<TestBoardCell> testList1 = cell1.getAdjList();
		Assert.assertTrue(!testList1.contains(board.getCell(9, 5)));
		Assert.assertTrue(!testList.contains(board.getCell(5, 10)));
		Assert.assertTrue(testList.contains(board.getCell(10, 4)));
		
	}
	
	@Test
	void TestTargetsMixed() {
		
		
		
	}

	
	@SuppressWarnings("deprecation")
	@Test
	void testTargetsNormal() {
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
		board.calcTargets(cell1, 3);
		Set<TestBoardCell> targets1 = board.getTargets();
		Assert.assertEquals(6, targets1.size());
		Assert.assertTrue(targets1.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets1.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets1.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets1.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets1.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets1.contains(board.getCell(1, 1)));
		
		TestBoardCell cell2 = board.getCell(3, 2);
		board.calcTargets(cell2, 3);
		Set<TestBoardCell> targets2 = board.getTargets();
		Assert.assertEquals(6, targets2.size());
		Assert.assertTrue(targets2.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets2.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets2.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets2.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets2.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets2.contains(board.getCell(1, 2)));
		
		TestBoardCell cell3 = board.getCell(2, 3);
		board.calcTargets(cell3, 3);
		Set<TestBoardCell> targets3 = board.getTargets();
		Assert.assertEquals(6, targets3.size());
		Assert.assertTrue(targets3.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets3.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets3.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets3.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets3.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets3.contains(board.getCell(3, 3)));
		
		TestBoardCell cell4 = board.getCell(3, 3);
		board.calcTargets(cell4, 3);
		Set<TestBoardCell> targets4 = board.getTargets();
		Assert.assertEquals(6, targets4.size());
		Assert.assertTrue(targets4.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets4.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets4.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets4.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets4.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets4.contains(board.getCell(1, 0)));
		
		TestBoardCell cell5 = board.getCell(3, 0);
		board.calcTargets(cell5, 3);
		Set<TestBoardCell> targets5 = board.getTargets();
		Assert.assertEquals(6, targets5.size());
		Assert.assertTrue(targets5.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets5.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets5.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets5.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets5.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets5.contains(board.getCell(3, 1)));
		
		TestBoardCell cell6 = board.getCell(4, 9);
		board.calcTargets(cell6, 3);
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
