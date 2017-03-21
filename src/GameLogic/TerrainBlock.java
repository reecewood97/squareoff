package GameLogic;

import java.awt.geom.Point2D;

public class TerrainBlock extends PhysObject {
	
	private static final long serialVersionUID = 1L;
	private int health;
	private int type;
	private boolean visible;
	private Point2D.Double pos;
	
	/**
	 * Creates a new TerrainBlock with the specified attributes
	 * @param health The health of the block
	 * @param type The type of the block
	 * @param pos The position of the bottom left corner of the block
	 * @param visible True if the block is in use
	 */
	public TerrainBlock(int health, int type, Point2D.Double pos, boolean visible){
		super(false, pos, 30, 40, true);
		this.health = health;
		this.type = type;
		this.visible = visible;
		this.pos = pos;
		this.setName("TerrainBlock");
	}
	
	/**
	 * Creates a shallow copy of a TerrainBlock
	 * @param other The block to be copied
	 */
	public TerrainBlock(TerrainBlock other){
		super(other);
		this.health = other.getHealth();
		this.type = other.getType();
		this.visible = other.isVisible();
		this.setInUse(other.getInUse());
		this.pos = other.getPos();
		this.setName("TerrainBlock");
	}
	
	public int getHealth(){	
		return health;
	}

	/**
	 * Does damage to a block
	 * @param damage The amount of damage dealt
	 */
	public void damage(int damage) {
		this.health = this.health-damage;
		if(health<1) {
			this.visible = false;
			this.setInUse(false);
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
	
	@Override
	public boolean getInUse() {
		return visible;
	}
	
	public void setHealth(int newHealth) {
		this.health = newHealth;
		if(newHealth < 1) {
			visible = false;
			this.setInUse(false);
		}
	}
}