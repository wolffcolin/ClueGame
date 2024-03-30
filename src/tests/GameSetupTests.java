package tests;

/*
Class: GameSetupTests
Description: tests our game setup with our players and cards
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Card;

public class GameSetupTests {

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
    public void testNumPlayers() {
        assertEquals(6, board.getPlayerCount());

        ArrayList<Player> players = board.getPlayers();

        int humanCount = 0;
        int botCount = 0;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) instanceof HumanPlayer) {
                humanCount++;
            } else if (players.get(i) instanceof ComputerPlayer) {
                botCount++;
            }
        }

        assertEquals(1, humanCount);
        assertEquals(5, botCount);
    }

    @Test
    public void testNumWeapons() {
        assertEquals(6, board.getWeaponCount());
    }

    @Test
    public void testNumCards() {
        assertEquals(21, board.getCardDeckSize());
    }

    @Test
    public void testCardsInDeck() {
        ArrayList<Card> cards = board.getCards();
        int personCount = 0;
        int weaponCount = 0;
        int roomCount = 0;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getCardType().equals(CardType.WEAPON)) {
                weaponCount++;
            } else if (cards.get(i).getCardType().equals(CardType.ROOM)) {
                roomCount++;
            } else if (cards.get(i).getCardType().equals(CardType.PERSON)) {
                personCount++;
            }
        }

        assertEquals(6, personCount);
        assertEquals(6, weaponCount);
        assertEquals(9, roomCount);
    }

    @Test
    public void testDealtCards() {
        ArrayList<Player> players = board.getPlayers();
        ArrayList<Card> cards = board.getCards();

        Player tempPlayer = players.get(0);
        float expectedRatio = tempPlayer.getHand().size() / cards.size();

        for (Player player : players) {
            float ratio = player.getHand().size() / cards.size();
            assertEquals(expectedRatio, ratio, 0.2);
        }
    }

    @Test
    public void testHandDuplicates() {
        ArrayList<Player> players = board.getPlayers();
        Set<Card> dealtCards = new HashSet<>();

        for (Player player : players) {
            ArrayList<Card> hand = player.getHand();
            for (Card card : hand) {
                dealtCards.add(card);
            }
        }

        assertEquals(board.getCardDeckSize() - 3, dealtCards.size());
    }
}
