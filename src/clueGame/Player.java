package clueGame;


public abstract class Player {
	
	private String name;
	private String color;
	private int row;
	private int column;
	private boolean isHuman;
	
	public Player(String name, String color, boolean isHuman) {
		this.name = name;
		this.color = color;
		this.isHuman = isHuman;
	}
	
	public void updateHand(Card card) {
		
	}
	
}
