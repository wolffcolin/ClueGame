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
        Card dinosaur = new Card("dinosaur", CardType.ROOM);
        deck.add(dinosaur);
        Card security = new Card("Security", CardType.PERSON);
        deck.add(security);
        Card kitchenKnife = new Card("kitchen knife", CardType.WEAPON);
        deck.add(kitchenKnife);
        Card bathroom = new Card("bathroom", CardType.ROOM);
        deck.add(bathroom);
        Card child = new Card("child", CardType.PERSON);
        deck.add(child);
        Card butter = new Card("butter", CardType.WEAPON);
        deck.add(butter);
        Card mummy = new Card("mummy", CardType.ROOM);
        deck.add(mummy);
        Card janitor = new Card("janitor", CardType.PERSON);
        deck.add(janitor);
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
        ArrayList<Player> players = board.getPlayers();
        Player player1 = players.get(0);
        ArrayList<Card> hand1 = player1.getHand();

        Solution theSolution = board.getTheAnswer();
        Card[] solutionCards = theSolution.theAnswerCards();

        Solution oneMatch = new Solution(hand1.get(0), solutionCards[1], solutionCards[2]);
        Solution noMatches = new Solution(solutionCards[0], solutionCards[1], solutionCards[2]);
        Solution threeMatches = new Solution(hand1.get(0), hand1.get(1), hand1.get(2));

        assertEquals(hand1.get(0), player1.disproveSuggestion(oneMatch));

        assertNull(player1.disproveSuggestion(noMatches));

        int card0 = 0;
        int card1 = 0;
        int card2 = 0;
        for (int i = 0; i < 100; i++) {
            if (player1.disproveSuggestion(threeMatches) == hand1.get(0)) {
                card0++;
            } else if (player1.disproveSuggestion(threeMatches) == hand1.get(1)) {
                card1++;
            } else if (player1.disproveSuggestion(threeMatches) == hand1.get(2)) {
                card2++;
            }
        }
        assertTrue(card0 > 1);
        assertTrue(card1 > 1);
        assertTrue(card2 > 1);
    }
}
