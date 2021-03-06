package GameLogic;

/**
 * The move class represents all a square can do in the course of normal play, including movement and firing weapons.
 *
 */
public class Move {
	
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
	/**
	 * If it is a weapon move, set that we have a weapon selected
	 * @param wep True if we have a weapon selected.
	 */
	public void setWeapon(boolean wep) {
		this.weapon = wep;
	}
	/**
	 * Get the colour of the square
	 * @return Colour of the square
	 */
	public int getPlayerColour() {
		return playerColour;
	}
	/**
	 * @return The ID of the square making a move.
	 */
	public int getSquareID() {
		return squareID;
	}
	/**
	 * @return If we are moving left or right with the square.
	 */
	public String getDirection() {
		return direction;
	}
	/**
	 * @return If it is a jump move or not
	 */
	public boolean getJump() {
		return jump;
	}
	/**
	 * @return If they are using a weapon or not
	 */
	public boolean getWeaponMove() {
		return weapon;
	}

}
