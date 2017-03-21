package GameLogic;

/**
 * The move class represents all a square can do in the course of normal play, including movement and firing weapons.
 *
 */
public class Move {
	//This class should represent a move.
	//Direction should be one of: "Left", "Right", or "None"
	
	private int playerColour;
	private int squareID;
	private String direction;
	private boolean jump;
	private boolean weapon;
	
	/**
	 * Constructor when you make a move
	 * @param player Colour colour of the active square
	 * @param squareID The ID so it can be found in the relevant arraylist 
	 * @param direction Left or right
	 * @param jump true when jumping, false otherwise.
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
