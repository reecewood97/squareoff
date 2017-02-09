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
	
	public Square(int playerID,int squareID, int colour, Point2D.Double pos) {
		super(true, pos, 10, 10);
		this.playerID = playerID;
		this.colour = colour;
		this.squareID = squareID;
		this.point = pos;
		this.alive = true;
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
