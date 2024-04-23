package clueGame;

import java.awt.Color;
import java.util.ArrayList;
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
	public ComputerPlayer(String name, Color color) {
		super(name, color, false);
	}

	// creates suggestion
	public Solution createSuggestion() {
		Board board = Board.getInstance();

		// get card of current room
		Card currentRoom = board.roomCard(this.getRow(), this.getCol());

		// get list of seen cards
		Set<Card> seen = this.getSeen();

		// get all of the cards separated by type
		ArrayList<Card> allWeapons = board.allCardsOfType(CardType.WEAPON);
		ArrayList<Card> allPlayers = board.allCardsOfType(CardType.PERSON);
		// ArrayList<Card> allRooms = board.allCardsOfType(CardType.ROOM);

		// loads all cards that havent been seen yet into list
		ArrayList<Card> weaponsNotSeen = new ArrayList<>();
		for (int i = 0; i < allWeapons.size(); i++) {
			if (!seen.contains(allWeapons.get(i))) {
				weaponsNotSeen.add(allWeapons.get(i));
			}
		}

		// loads all player cards that havent been seen yet into list
		ArrayList<Card> playersNotSeen = new ArrayList<>();
		for (int i = 0; i < allPlayers.size(); i++) {
			if (!seen.contains(allPlayers.get(i))) {
				playersNotSeen.add(allPlayers.get(i));
			}
		}

		// randomly chooses a weapon and player from the list of not seen cards
		Random random = new Random();

		int weaponIndex = random.nextInt(weaponsNotSeen.size());

		Card weaponPick = weaponsNotSeen.get(weaponIndex);

		int playerIndex = random.nextInt(playersNotSeen.size());

		Card playerPick = playersNotSeen.get(playerIndex);

		// create solution from randomly picked cards and current room
		Solution potentialSolution = new Solution(currentRoom, playerPick, weaponPick);

		return potentialSolution;
	}

	public BoardCell selectTarget(ArrayList<BoardCell> targets) {

		// get list of seen cards
		Set<Card> seen = this.getSeen();

		// get instance
		Board board = Board.getInstance();

		BoardCell returnCell;

		ArrayList<BoardCell> rooms = new ArrayList<>();

		ArrayList<BoardCell> unseenRooms = new ArrayList<>();

		// adds cells that are rooms into rooms list
		for (int i = 0; i < targets.size(); i++) {
			if (targets.get(i).getInitial() != 'W' && targets.get(i).getInitial() != 'X') {
				rooms.add(targets.get(i));
			}
		}

		// adds cells from rooms list to unseenRooms list
		for (int i = 0; i < rooms.size(); i++) {
			BoardCell currCell = rooms.get(i);
			Card currRoom = board.roomCard(currCell.getRow(), currCell.getCol());
			if (!seen.contains(currRoom)) {
				unseenRooms.add(currCell);
			}
		}

		Random random = new Random();

		// if there are rooms in unseenRooms, randomly pick from those, if not, randomly
		// pick from all cards
		if (!unseenRooms.isEmpty()) {
			int index = random.nextInt(unseenRooms.size());
			returnCell = unseenRooms.get(index);
		} else {
			int index = random.nextInt(targets.size());
			returnCell = targets.get(index);
		}

		return returnCell;

	}

	// clears seen list
	public void clearSeen() {
		this.getSeen().clear();
	}

}
