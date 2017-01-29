package GameLogic;

public class Move {
	//This class should represent a move.
	//Direction should be one of: "Left", "Right", or "None"
	
	private int playerColour;
	private int squareID;
	private String direction;
	private boolean jump;
	
	public Move(int playerColour, int squareID, String direction, boolean jump) {
		this.playerColour = playerColour;
		this.squareID = squareID;
		this.direction = direction;
		this.jump = jump;
	}
	
	public int getPlayerColour() {
		return playerColour;
	}
	
	public int getSquareID() {
		return squareID;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public boolean getJump() {
		return jump;
	}

}
