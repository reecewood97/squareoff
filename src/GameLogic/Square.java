package GameLogic;

import java.awt.geom.Point2D;
/**
 * The Square class handles all information and methods relevant to the square.
 *
 */
public class Square extends PhysObject {
	//This class is to represent player characters.
	//Colour represents team.
	
	private int playerID;
	private int colour;
	private int squareID;
	private Point2D.Double point;
	private boolean alive;
	private String facing;
	private boolean activePlayer;
	private String playerName;
	
	/**
	 * Creates a new square with the specified attributes
	 * @param name The name of the owner
	 * @param playerID The ID of the owner
	 * @param squareID The ID of the square in the owner's team
	 * @param colour The colour of the square
	 * @param pos The position of the square
	 */
	public Square(String name, int playerID,int squareID, int colour, Point2D.Double pos) {
		super(true, pos, 30, 30, false);
		this.playerName = name;
		this.playerID = playerID;
		this.colour = colour;
		this.squareID = squareID;
		this.point = pos;
		this.alive = true;
		this.setName("Square");
		this.facing = "At you!";
		this.activePlayer = false;
	}
	
	/**
	 * Creates a new square with the specified attributes an name "noName"
	 * @param playerID The ID of the owner
	 * @param squareID The ID of the square in the owner's team
	 * @param colour The colour of the square
	 * @param pos The position of the square
	 */
	public Square(int playerID, int squareID, int colour, Point2D.Double pos) {
		super(true, pos, 30, 30, false);
		this.playerName = "noName";
		this.playerID = playerID;
		this.colour = colour;
		this.squareID = squareID;
		this.point = pos;
		this.alive = true;
		this.setName("Square");
		this.facing = "At you!";
		this.activePlayer = false;
	}
	
	/**
	 * Creates a shallow copy of a square
	 * @param other The square to be copied
	 */
	public Square(Square other) {
		super(other);
		this.playerName = other.getPlayerName();
		this.playerID = other.getPlayerID();
		this.colour = other.getColour();
		this.squareID = other.getSquareID();
		this.point = other.getPoint();
		this.alive = other.getAlive();
		this.setName("Square");
		this.facing = other.getFacing();
		this.activePlayer = other.getActivePlayer();
	}
	/**
	 * Get the name of the square
	 * @return Will almost always be "Square"
	 */
	public String getPlayerName(){
		return playerName;
	}
	
	/** 
	 * Checks if this player is the active player
	 * @return True if this is the active player, false otherwise.
	 */
	public boolean getActivePlayer(){
		return this.activePlayer;
	}
	
	/**
	 * Makes this player the active player
	 * @param active true if this player is becoming the active player, false if their turn as the active player is over
	 */
	public void setActivePlayer(boolean active){
		this.activePlayer = active;
	}
	
	/**
	 * Checks if two Squares are the same by comparing their position, and their velocity.
	 */
	@Override
	public boolean equals(Object anObject){
		Square square = (Square)anObject;
		return (getPos().equals(square.getPos()) && getXvel()==square.getXvel() && getYvel()==square.getYvel());
	}
	
	@Override
	/**
	 * Change if the Square is alive or not, using a method inherited from PhyObject
	 * @param use True if the player is alive, falseif not
	 */
	public void setInUse(boolean use){
		this.alive=use;
	}
	
	/**
	 * Facing is used to draw the squares eyes
	 * @return Left, Right or Forward
	 */
	public String getFacing(){
		
		return facing;
	}
	
	/**
	 * Change the facing of the square
	 * @param direction The direction the square needs to be facing, Left Right or None
	 */
	public void setFacing(String direction){
		
		facing = direction;
		
	}
	/**
	 * Return the ID of the player controlling the square
	 * @return The player ID in charge of the square
	 */
	public int getPlayerID() {
		return playerID;
	}
	
	/** 
	 * The colour of the square in use
	 * @return Red,Blue,Green or yellow 
	 */
	public int getColour() {
		return colour;
	}
	/**
	 * The ID of the square being used, will normally be used to access it in the arraylist.
	 * @return the ID of the square.
	 */
	public int getSquareID() {
		return squareID;
	}
	/**
	 * The current position of the square
	 * @return The current position
	 */
	public Point2D.Double getPoint(){
		return point;
	}
	/**
	 * Check if the Square is alive or not
	 * @return if the square is alive or dead/ true or false
	 */
	public boolean getInUse() {
		return alive;
	}
	/**
	 * Change the name of the owner of the square
	 * @param newName The name of the client controlling the square
	 */
	public void setPlayerName(String newName) {
		this.playerName = newName;
	}
	/**
	 * Change the position of the square
	 * @param update The new position of the square
	 */
	public void setPoint(Point2D.Double update){
		this.point = update;
	}
	/**
	 * Set the square to no longer be in use i.e. dead
	 */
	public void setDead(){
		alive = false;
	}
	/**
	 * Another version of the inUse check with a more user friendly name
	 * @return If the square is alive or not.
	 */
	public boolean getAlive(){
		return alive;
	}

}
