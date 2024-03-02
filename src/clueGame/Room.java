package clueGame;

public class Room {

	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;

	public Room(String setName, BoardCell center, BoardCell label) {
		this.name = setName;
		this.centerCell = center;
		this.labelCell = label;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public BoardCell getLabCell() {
		return null;
	}
}
