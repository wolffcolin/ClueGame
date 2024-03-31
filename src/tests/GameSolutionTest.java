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
        board.setConfigFiles("ClueLayout.csv", "ClueSetupTesting.txt");
        board.initialize();

        // rigging the hand to known values
        Card rigRoom = new Card("riggedRoom", CardType.ROOM);
        Card rigPerson = new Card("riggedPerson", CardType.PERSON);
        Card rigWeapon = new Card("riggedWeapon", CardType.WEAPON);
        Solution riggedSolution = new Solution(rigRoom, rigPerson, rigWeapon);
        board.rigTheAnswer(riggedSolution);

        ArrayList<Card> deck = new ArrayList<>();
        Card security = new Card("Security", CardType.PERSON);
        deck.add(security);
        Card dinosaur = new Card("dinosaur", CardType.ROOM);
        deck.add(dinosaur);
        Card kitchenKnife = new Card("kitchen knife", CardType.WEAPON);
        deck.add(kitchenKnife);
        Card child = new Card("child", CardType.PERSON);
        deck.add(child);
        Card bathroom = new Card("bathroom", CardType.ROOM);
        deck.add(bathroom);
        Card butter = new Card("butter", CardType.WEAPON);
        deck.add(butter);
        Card janitor = new Card("janitor", CardType.PERSON);
        deck.add(janitor);
        Card mummy = new Card("mummy", CardType.ROOM);
        deck.add(mummy);
        Card nunchucks = new Card("nunchucks", CardType.WEAPON);
        deck.add(nunchucks);

        // set all player hands to know values
        int count = 0;
        ArrayList<Player> players = board.getPlayers();
        for (Player player : players) {
            ArrayList<Card> newHand = new ArrayList<>();
            newHand.add(deck.get(count));
            newHand.add(deck.get(count + 1));
            newHand.add(deck.get(count + 2));
            player.setHand(deck);
            count += 3;
        }

    }

    @Test
    public void testCheckAccusation() {
        // test the solution
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

    }
}
