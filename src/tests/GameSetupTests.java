package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;

public class GameSetupTests {

    private static Board board;

    @BeforeAll
    public static void setup() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");

        board.initialize();
    }

    @Test
    public void TestNumPlayers() {
        assertEquals(6, board.getPlayerCount());
    }

    @Test
    public void TestNumWeapons() {
        assertEquals(6, board.getWeaponCount());
    }

    @Test
    public void TestNumCards() {
        assertEquals(21, board.getCardDeckSize());
    }

}
