package GameLogic;

import java.awt.geom.Point2D;

public class Square extends PhysObject {
	//This class is to represent player characters.
	//Colour represents team.
	
	private int ID;
	private int colour;
	private Point2D.Double pos;
	private int player;
	
	public Square(int ID, int colour, Point2D.Double pos, int player) {
		super(true, pos, 10, 10);
		this.ID = ID;
		this.colour = colour;
		this.pos = pos;
		this.player = player;
		//this.addAttribute("player"); Maybe this would be useful, maybe not
	}
	
	public int getID() {
		return ID;
	}
	
	public int getColour() {
		return colour;
	}
	
	public Point2D.Double getPos(){
		return pos;
	}
	
	public int getPlayer(){
		return player;
	}

}
