package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

/*
Class: ComputerAITests
Description: tests AI suggestion creation and target selection
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

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
		
		//finds a player in the player list that isnt human to be a test subject
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
		
		//gets the suggestion from the bot and separates the cards out
		Solution suggestion = bot.createSuggestion();
		Card[] cards = suggestion.theAnswerCards();	
		
		Card room = cards[0];
		Card player = cards[1];
		Card weapon = cards[2];
		
		//sets current room to arbitrary starting point, and gets card
		Card currentRoom = board.roomCard(11, 13);
		
		//gets names of room to compare
		String currentRoomName = currentRoom.toString();
		String roomName = room.toString();
		
		//test if room in suggestion is current room
		assertEquals(currentRoomName, roomName);
		
		//gets all possible cards, separated into types
		ArrayList<Card> allWeapons = board.allCardsOfType(CardType.WEAPON);
		ArrayList<Card> allPlayers = board.allCardsOfType(CardType.PERSON);
		ArrayList<Card> allRooms = board.allCardsOfType(CardType.ROOM);
		
		//bot has seen all but 1 weapon
		for (int i = 0; i < (allWeapons.size() - 1); i++) {
			bot.updateSeen(allWeapons.get(i));
		}
		
		//bot has seen all but 1 player
		for (int i = 0; i < (allPlayers.size() - 1); i++) {
			bot.updateSeen(allPlayers.get(i));
		}
		
		//create suggestion
		Solution suggestion2 = bot.createSuggestion();
		Card[] cards2 = suggestion2.theAnswerCards();
		
		//bot has seen all but 1 weapon
		for (int i = 0; i < allWeapons.size()-1; i++) {
			bot.updateSeen(allWeapons.get(i));
		}
		
		//bot has seen all but 1 player
		for (int i = 0; i < allPlayers.size()-1; i++) {
			bot.updateSeen(allPlayers.get(i));
		}
		
		Card room2 = cards2[0];
		Card player2 = cards2[1];
		Card weapon2 = cards2[2];
		
		//checks if only 1 weapon not seen, its selected
		assertEquals(allWeapons.get(allWeapons.size() - 1), weapon2);
		assertEquals(allPlayers.get(allPlayers.size()-1), player2);
		
		//creates set of weapons that havent been seen
		Set<Card> weaponsNotSeen = new HashSet<>();
		for (int i = 0; i < allWeapons.size(); i++) {
			if (!bot.getSeen().contains(allWeapons.get(i))) {
				weaponsNotSeen.add(allWeapons.get(i));
			}
		}
		
		//creates set of players that havent been seenn
		Set<Card> playersNotSeen = new HashSet<>();
		for (int i = 0; i < allPlayers.size(); i++) {
			if (!bot.getSeen().contains(allPlayers.get(i))) {
				playersNotSeen.add(allPlayers.get(i));
			}
		}
		
		//checks case where multiple weapons not seen
		assertTrue(weaponsNotSeen.contains(weapon2));
		assertTrue(playersNotSeen.contains(player2));
		
	}
	
	@Test
	void testSelectTargets() {
		
		//picks player that isnt human to use as test subject
		ArrayList<Player> players = board.getPlayers();
		int index = 0;
		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).isAHuman()) {
				index = i;
				i = players.size();
			}
		}
		
		ComputerPlayer bot = (ComputerPlayer) players.get(index);
		

		
		//BoardCell cell2 = new BoardCell();
		BoardCell cell1 = new BoardCell(3, 9, 'W');
		BoardCell cell2 = new BoardCell(1, 12, 'M');
		BoardCell cell3 = new BoardCell(4, 10, 'W');
		
		//first test target list, checking situation where 1 room not seen in list
		ArrayList<BoardCell> targets1 = new ArrayList<>();
		targets1.add(cell1);
		targets1.add(cell2);
		targets1.add(cell3);
		
		BoardCell chosenTarget1 = bot.selectTarget(targets1);
		
		//if a target is an unseen room, it should be selected
		assertTrue(chosenTarget1.equals(cell2));
		
		bot.clearSeen();
		
		BoardCell cell4 = new BoardCell(1, 9, 'W');
		BoardCell cell5 = new BoardCell(3, 11, 'W');
		BoardCell cell6 = new BoardCell(4, 8, 'W');
		
		//second test, checking situation where no rooms are in list
		ArrayList<BoardCell> targets2 = new ArrayList<>();
		targets2.add(cell4);
		targets2.add(cell5);
		targets2.add(cell6);
		
		int[] selectionRates = {0,0,0};
		
		//tracks selection rates of each target to ensure randomness
		for (int i = 0; i < 100; i++) {
			BoardCell chosenTarget2 = bot.selectTarget(targets2);
			for (int j = 0; j < targets2.size(); j++) {
				if (chosenTarget2.equals(targets2.get(j))) {
					selectionRates[j]++;
				}
			}
		}
		
		//to ensure randomness each cell should have been chosen at least once
		for (int i = 0; i < 3; i++) {
			assertTrue(selectionRates[i] > 0);
		}
		
		bot.clearSeen();
		
		BoardCell cell7 = new BoardCell(1, 9, 'W');
		BoardCell cell8 = new BoardCell(2, 10, 'W');
		BoardCell cell9 = new BoardCell(1, 12, 'M');
		
		//checking situation where room is in target list, but has been seen
		ArrayList<BoardCell> targets3 = new ArrayList<>();
		targets3.add(cell7);
		targets3.add(cell8);
		targets3.add(cell9);
		
		//adds room to seen list
		Card room = board.roomCard(cell9.getRow(), cell9.getCol());
		bot.updateSeen(room);
		
		int[] selectionRates2 = {0,0,0};
		
		//counts times each cell has been chosen
		for (int i = 0; i < 100; i++) {
			BoardCell chosenTarget3 = bot.selectTarget(targets3);
			for (int j = 0; j < targets3.size(); j++) {
				if (chosenTarget3.equals(targets3.get(j))) {
					selectionRates2[j]++;
				}
			}
		}
		
		//checks for randomness, each cell should have been chosen at least once
		for (int i = 0; i < 3; i++) {
			assertTrue(selectionRates2[i] > 0);
		}
		
	}

}
