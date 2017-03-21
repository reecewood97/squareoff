package GameLogic;

public class Move {
	//This class should represent a move.
	//Direction should be one of: "Left", "Right", or "None"
	
	private int playerColour;
	private int squareID;
	private String direction;
	private boolean jump;
	private boolean weapon;
	
	/**
	 * Represents a move to be made by a player
	 * @param playerColour The team the player is on
	 * @param squareID The ID of the square in that team
	 * @param direction The direction: should be "Left", "Right", or "None"
	 * @param jump Whether the player wants to jump
	 */
	public Move(int playerColour, int squareID, String direction, boolean jump) {
		this.playerColour = playerColour;
		this.squareID = squareID;
		this.direction = direction;
		this.jump = jump;
		this.weapon = false;
	}
	
	public void setWeapon(boolean wep) {
		this.weapon = wep;
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
	
	public boolean getWeaponMove() {
		return weapon;
	}

}
