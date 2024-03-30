package clueGame;

/*
Class: PLayer
Description: Abstract player class that is extended by the ComputerPlayer and HumanPlayer class
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

import java.util.ArrayList;

public abstract class Player {

	// data about player
	private String name;
	private String color;
	private int row;
	private int column;
	private boolean isHuman;

	// stores the player's hand
	private ArrayList<Card> hand = new ArrayList<>();

	public Player(String name, String color, boolean isHuman) {
		this.name = name;
		this.color = color;
		this.isHuman = isHuman;
	}

	// adds card to hand
	public void updateHand(Card card) {
		hand.add(card);
	}

	// returns hand
	public ArrayList<Card> getHand() {
		return hand;
	}

}
