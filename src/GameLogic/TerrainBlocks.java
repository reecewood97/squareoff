package GameLogic;

import java.awt.geom.Point2D;

public class TerrainBlocks extends PhysObject {
	private int health;
	private int colour;
	private int type;
	
	public TerrainBlocks(int health, int colour, int type, Point2D.Double pos){
		super();
		this.health = health;
	 /*
	 * * Red = 1
	 *  * Green = 2
	 *  * Yellow = 3 
	 *  * Blue = 4
	 *  * etc
	 *  */
	 this.colour = colour;
	 this.type = type;
	 this.setPos(pos);
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
}