package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
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
        Solution goodSolution = board.getTheAnswer();
        assertTrue(board.checkAccusation(goodSolution));

        // make bad cards
        Card badPerson = new Card("bad", CardType.PERSON);
        Card badWeapon = new Card("bad", CardType.WEAPON);
        Card badRoom = new Card("bad", CardType.ROOM);

        // get good cards
        Card[] solutionCards = goodSolution.theAnswerCards();
        Card goodRoom = solutionCards[0];
        Card goodPerson = solutionCards[1];
        Card goodWeapon = solutionCards[2];

        // test a bad person
        Solution badPersonSolution = new Solution(goodRoom, badPerson, goodWeapon);
        assertFalse(board.checkAccusation(badPersonSolution));

        // test a bad weapon
        Solution badWeaponSolution = new Solution(goodRoom, goodPerson, badWeapon);
        assertFalse(board.checkAccusation(badWeaponSolution));

        // test a bad room
        Solution badRoomSolution = new Solution(badRoom, goodPerson, goodWeapon);
        assertFalse(board.checkAccusation(badRoomSolution));
    }

    @Test
    public void testDisproveSuggestion() {
        ArrayList<Player> players = board.getPlayers();
        Card playerCard = players.get(1).getHand().get(0);

        Solution falseSolution = new Solution(null, null, null);
    }
}
