package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

class ComputerAITest {
	
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
	void testCreateSuggestion() {
		//test if in room
		//test if suggestion in hand
		//test if suggestion's room is the room currently occupied
		//test to make sure suggestion not in seen
		
		ArrayList<Player> players = board.getPlayers();
		int index = 0;
		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).isAHuman()) {
				index = i;
				i = players.size();
			}
		}
		
		ComputerPlayer bot = (ComputerPlayer) players.get(index);
		
		//teleport to dinosaur exhibit
		bot.teleport(11, 13);
		
		Solution suggestion = bot.createSuggestion();
		Card[] cards = suggestion.theAnswer();	
		
		Card room = cards[0];
		Card player = cards[1];
		Card weapon = cards[2];
		
		Card currentRoom = board.roomCard(11, 13);
		
		//test if room in suggestion is current room
		assertEquals(currentRoom, room);
		
		ArrayList<Card> allWeapons = board.allCardsOfType(CardType.WEAPON);
		ArrayList<Card> allPlayers = board.allCardsOfType(CardType.PERSON);
		ArrayList<Card> allRooms = board.allCardsOfType(CardType.ROOM);
		
		
	}
	
	@Test
	void testSelectTargets() {
		
	}

}
