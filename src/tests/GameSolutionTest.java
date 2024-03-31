package tests;

import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Solution;

public class GameSolutionTest {
    private static Board board;

    @BeforeAll
    public static void cleanInstance() {
        Board.getInstance().clearInstance();
    }

    @BeforeAll
    public static void setup() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");

        board.initialize();
    }

    @Test
    public void testCheckAccusation() {
        Solution badSolution = new Solution(null, null, null);
        assertFalse(board.checkAccusation(badSolution));

        Solution goodSolution = board.getTheAnswer();
        assertTrue(board.checkAccusation(goodSolution));
    }
}
