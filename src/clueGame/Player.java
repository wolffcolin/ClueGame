package clueGame;

import java.awt.Color;
import java.awt.Graphics;

/*
Class: Player
Description: Abstract player class that is extended by the ComputerPlayer and HumanPlayer class
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {

	// data about player
	private String name;
	private Color color;
	private int row;
	private int column;
	private boolean isHuman;

	// stores the player's hand
	private ArrayList<Card> hand = new ArrayList<>();

	private Set<Card> seenCards = new HashSet<>();

	public Player(String name, Color color, boolean isHuman) {
		this.name = name;
		this.color = color;
		this.isHuman = isHuman;
	}

	// adds card to hand
	public void updateHand(Card card) {
		card.setColor(color);
		hand.add(card);
	}

	public void updateSeen(Card seenCard) {
		seenCards.add(seenCard);
	}

	// checks the hand of the player to see if they can disprove the suggestion, if
	// so it returns the matching card
	public Card disproveSuggestion(Solution suggestion) {
		Card[] suggestionCards = suggestion.theAnswerCards();
		ArrayList<Card> matchingCards = new ArrayList<>();
		for (Card card : hand) {
			for (int i = 0; i < 3; i++) {
				if (card == suggestionCards[i]) {
					matchingCards.add(card);
				}
			}
		}
		// select a random matching card
		if (matchingCards.isEmpty()) {
			return null;
		} else {
			Collections.shuffle(matchingCards);
			return matchingCards.get(0);
		}
	}

	// draws the player as a circle in the cetner on their position cell
	public void draw(Graphics g, int size) {
		int radius = size / 2;
		int x = column * size;
		int y = row * size;

		g.setColor(color);
		g.fillOval(x, y, 2 * radius, 2 * radius);
	}

	public void teleport(int row, int column) {
		this.row = row;
		this.column = column;
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

	public Set<Card> getSeen() {
		return seenCards;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return column;
	}

	public String getName() {
		return name;
	}

}
