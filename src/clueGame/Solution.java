package clueGame;

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