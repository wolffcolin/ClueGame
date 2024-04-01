package clueGame;

/*
Class: Solution
Description: Solution class that stores the solution cards to end the game.
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

public class Solution {
	// solution data
	private Card room;
	private Card player;
	private Card weapon;

	// constructor
	public Solution(Card room, Card player, Card weapon) {
		this.room = room;
		this.player = player;
		this.weapon = weapon;
	}

	public boolean equals(Solution potentialSolution) {
		Card[] potentialCards = potentialSolution.theAnswerCards();
		if (room == potentialCards[0] && player == potentialCards[1] && weapon == potentialCards[2]) {
			return true;
		} else
			return false;
	}

	// returns array of cards that is the answer
	public Card[] theAnswerCards() {
		Card[] answer = { room, player, weapon };
		return answer;
	}
}