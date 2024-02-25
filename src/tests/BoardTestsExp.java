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

	
}
