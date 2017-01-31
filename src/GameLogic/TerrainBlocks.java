package GameLogic;

import java.awt.geom.Point2D;

public class TerrainBlocks extends PhysObject {
	private int health;
	private int colour;
	private int type;
	private boolean visible;
	
	public TerrainBlocks(int health, int colour, int type, Point2D.Double pos, boolean visible){
		super(false, pos, 5, 5);
		this.health = health;
		this.colour = colour;
		this.type = type;
		this.visible = visible;
		 /*
		 * * Red = 1
		 *  * Green = 2
		 *  * Yellow = 3 
		 *  * Blue = 4
		 *  * etc
		 *  */
	 }
	public int getHealth(){	
		return health;
	}
	
	public int getColour(){
		return colour;
	} 
	
	public int getType(){
		return type;
	}
	
	public Point2D.Double getPos(){	
		return this.getPos(); 
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public void setHealth(int newHealth) {
		this.health = newHealth;
		if(newHealth < 1) {
			visible = false;
		}
	}
}