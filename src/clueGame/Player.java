package clueGame;

import java.util.ArrayList;

public abstract class Player {
	
	private String name;
	private String color;
	private int row;
	private int column;
	private boolean isHuman;
	
    private ArrayList<Card> hand = new ArrayList<>();
	
	public Player(String name, String color, boolean isHuman) {
		this.name = name;
		this.color = color;
		this.isHuman = isHuman;
	}
	
	public void updateHand(Card card) {
		hand.add(card);
	}
	
}
