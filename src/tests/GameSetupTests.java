package tests;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
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

}
