package GameLogic;

import java.awt.geom.Point2D;

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
	
	//What is the point of colour? I thought that was the same as the player that owned it
	public Square(int playerID,int squareID, int colour, Point2D.Double pos) {
		super(true, pos, 30, 30, false);
		this.playerID = playerID;
		this.colour = colour;
		this.squareID = squareID;
		this.point = pos;
		this.alive = true;
		this.setName("Square");
		this.facing = "At you!";
		this.activePlayer = false;
	}
	
	public Square(Square other) {
		//Creates a shallow copy of a square
		super(other);
		this.playerID = other.getPlayerID();
		this.colour = other.getColour();
		this.squareID = other.getSquareID();
		this.point = other.getPoint();
		this.alive = other.getAlive();
		this.setName("Square");
		this.facing = other.getFacing();
		this.activePlayer = other.getActivePlayer();
	}
	
	public boolean getActivePlayer(){
		return this.activePlayer;
	}
	
	public void setActivePlayer(boolean active){
		this.activePlayer = active;
	}
	
	@Override
	public boolean equals(Object anObject){
		Square square = (Square)anObject;
		return (getPos().equals(square.getPos()) && getXvel()==square.getXvel() && getYvel()==square.getYvel());
	}
	
	@Override
	public void setInUse(boolean use){
		this.alive=use;
	}
	
	public String getFacing(){
		
		return facing;
	}
	
	public void setFacing(String direction){
		
		facing = direction;
		
	}
	public int getPlayerID() {
		return playerID;
	}
	
	public int getColour() {
		return colour;
	}
	
	public int getSquareID() {
		return squareID;
	}
	
	public Point2D.Double getPoint(){
		return point;
	}
	
	public boolean getInUse() {
		return alive;
	}
	
	public void setPoint(Point2D.Double update){
		this.point = update;
	}
	
	public void setDead(){
		alive = false;
	}
	public boolean getAlive(){
		return alive;
	}

}
