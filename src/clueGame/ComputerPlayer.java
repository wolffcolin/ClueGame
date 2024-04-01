package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/*
Class: ComputerPlayer
Description: computer player class that is extends Player
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

public class ComputerPlayer extends Player {

	// constructor
	public ComputerPlayer(String name, String color) {
		super(name, color, false);
	}
	
	public Solution createSuggestion() {
		Board board = Board.getInstance();
		
		Card currentRoom = board.roomCard(this.getRow(), this.getCol());
		
		Set<Card> seen = this.getSeen();
		
		ArrayList<Card> allWeapons = board.allCardsOfType(CardType.WEAPON);
		ArrayList<Card> allPlayers = board.allCardsOfType(CardType.PERSON);
		ArrayList<Card> allRooms = board.allCardsOfType(CardType.ROOM);
		
		ArrayList<Card> weaponsNotSeen = new ArrayList<>();
		for (int i = 0; i < allWeapons.size(); i++) {
			if (!seen.contains(allWeapons.get(i))) {
				weaponsNotSeen.add(allWeapons.get(i));
			}
		}
		
		ArrayList<Card> playersNotSeen = new ArrayList<>();
		for (int i = 0; i < allPlayers.size(); i++) {
			if (!seen.contains(allPlayers.get(i))) {
				playersNotSeen.add(allPlayers.get(i));
			}
		}
		
		Random random = new Random();
		
		int weaponIndex = random.nextInt(weaponsNotSeen.size());
		
		Card weaponPick = weaponsNotSeen.get(weaponIndex);
		
		int playerIndex = random.nextInt(playersNotSeen.size());
		
		Card playerPick = playersNotSeen.get(playerIndex);
		
		Solution potentialSolution = new Solution(currentRoom, playerPick, weaponPick);
		
		return potentialSolution;
	}
	
	public BoardCell selectTarget(ArrayList<BoardCell> targets) {
		
		Set<Card> seen = this.getSeen();
		
		Board board = Board.getInstance();
		
		BoardCell returnCell;
		
		ArrayList<BoardCell> rooms = new ArrayList<>();
		
		ArrayList<BoardCell> unseenRooms = new ArrayList<>();
		
		for (int i = 0; i < targets.size(); i++) {
			if (targets.get(i).getInitial() != 'W' && targets.get(i).getInitial() != 'X') {
				rooms.add(targets.get(i));
			}
		}
		
		for (int i = 0; i < rooms.size(); i++) {
			BoardCell currCell = rooms.get(i);
			Card currRoom = board.roomCard(currCell.getRow(), currCell.getCol());
			if (!seen.contains(currRoom)) {
				unseenRooms.add(currCell);
			}
		}

		
		Random random = new Random();
		
		if (!unseenRooms.isEmpty()) {
			int index = random.nextInt(unseenRooms.size());
			returnCell = unseenRooms.get(index);
		} else {
			int index = random.nextInt(targets.size());
			returnCell = targets.get(index);
		}
		
		return returnCell;
		
	}

}
