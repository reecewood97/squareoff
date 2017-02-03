package GameLogic;

import java.awt.geom.Point2D;

public class Square extends PhysObject {
	//This class is to represent player characters.
	//Colour represents team.
	
	private int playerID;
	private int colour;
	private Point2D.Double pos;
	
	public Square(int playerID, int colour, Point2D.Double pos) {
		super(true, pos, 10, 10);
		this.playerID = playerID;
		this.colour = colour;
		this.pos = pos;
	}
	
	public int getPlayerID() {
		return playerID;
	}
	
	public int getColour() {
		return colour;
	}
	
	public Point2D.Double getPos(){
		return pos;
	}

}
