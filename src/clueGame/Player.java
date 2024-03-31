package clueGame;

/*
Class: Player
Description: Abstract player class that is extended by the ComputerPlayer and HumanPlayer class
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {

	// data about player
	private String name;
	private String color;
	private int row;
	private int column;
	private boolean isHuman;

	// stores the player's hand
	private ArrayList<Card> hand = new ArrayList<>();

	private Set<Card> seenCards = new HashSet<>();

	public Player(String name, String color, boolean isHuman) {
		this.name = name;
		this.color = color;
		this.isHuman = isHuman;
	}

	// adds card to hand
	public void updateHand(Card card) {
		hand.add(card);
	}

	public void updateSeen(Card seenCard) {
		// seenCards.add(seenCard);
	}

	public Card disproveSuggestion(Solution suggestion) {
		return hand.get(0);
	}

	// returns hand
	public ArrayList<Card> getHand() {
		return hand;
	}

	// only to be used in testing
	public void setHand(ArrayList<Card> newHand) {
		hand = newHand;
	}

	public boolean isAHuman() {
		return isHuman;
	}

	public void teleport(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public Set<Card> getSeen() {
		return seenCards;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return column;
	}

}
