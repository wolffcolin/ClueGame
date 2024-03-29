package clueGame;

public class Solution {
	private Card room;
	private Card player;
	private Card weapon;
	
	public Solution(Card room, Card player, Card weapon) {
		this.room = room;
		this.player = player;
		this.weapon = weapon;
		
	}
	
	public Card[] theAnswer() {
		Card[] answer = {room, player, weapon};
		return answer;
	}
}