package clueGame;

/*
Class: Solution
Description: Solution class that stores the solution cards to end the game.
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

public class Solution {
	//solution data
	private Card room;
	private Card player;
	private Card weapon;
	
	//constructor
	public Solution(Card room, Card player, Card weapon) {
		this.room = room;
		this.player = player;
		this.weapon = weapon;
		
	}
	
	//returns array of cards that is the answer
	public Card[] theAnswer() {
		Card[] answer = {room, player, weapon};
		return answer;
	}
}