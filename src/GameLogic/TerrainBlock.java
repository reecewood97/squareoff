package GameLogic;

import java.awt.geom.Point2D;

public class TerrainBlock extends PhysObject {
	private int health;
	private int colour;
	private int type;
	private boolean visible;
	private Point2D.Double pos;
	
	public TerrainBlock(int health, int colour, int type, Point2D.Double pos, boolean visible){
		super(false, pos, 30, 40, true);
		this.health = health;
		this.colour = colour;
		this.type = type;
		this.visible = visible;
		this.pos = pos;
		this.setName("TerrainBlock");
	}
	
	public TerrainBlock(TerrainBlock other){
		super(other);
		this.health = other.getHealth();
		this.colour = other.getColour();
		this.type = other.getType();
		this.visible = other.isVisible();
		this.pos = other.getPos();
		this.setName("TerrainBlock");
	}
	
	public int getHealth(){	
		return health;
	}

	public int getColour(){
		return colour;
	}
	
	public void damage(int damage) {
		this.health = this.health-damage;
		if(health<1) {
			this.visible = false;
		}
	}
	
	public int getType(){
		return type;
	}
	
	public Point2D.Double getPos(){	
		return pos; 
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public boolean getInUse() {
		return visible;
	}
	
	public void setHealth(int newHealth) {
		this.health = newHealth;
		if(newHealth < 1) {
			visible = false;
		}
	}
}