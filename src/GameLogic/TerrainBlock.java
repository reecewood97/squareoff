package GameLogic;

import java.awt.geom.Point2D;
/**
 * The class that handles data and methods for the terrain blocks.
 *
 */
public class TerrainBlock extends PhysObject {
	
	private static final long serialVersionUID = 1L;
	private int health;
	private int type;
	private boolean visible;
	private Point2D.Double pos;
	/**
	 * Constructor
	 * @param health The number of hits needed to destroy the block
	 * @param type Wood/Metal etc
	 * @param pos Position
	 * @param visible Should it be drawn or not
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
	 * Creates a shallow copy of another terrain block for physics equations.
	 * @param other
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
	
	/**
	 * Check the health of a block
	 * @return The integer health of the block
	 */
	public int getHealth(){	
		return health;
	}

	/**
	 * Deal damage to the block appropriate to the strength of the weapon that hits it
	 * @param damage The amount of damage being done to the block
	 */
	public void damage(int damage) {
		this.health = this.health-damage;
		if(health<1) {
			this.visible = false;
			this.setInUse(false);
		}
	}
	
	/**
	 * Check the type of the block
	 * @return The block type
	 */
	public int getType(){
		return type;
	}
	/**
	 * @return The position of the block
	 */
	public Point2D.Double getPos(){	
		return pos; 
	}
	
	/**
	 * @return True if the object should be drawn, false otherwise
	 */
	public boolean isVisible(){
		return visible;
	}
	
	/**
	 * @return Check if the block is in use, by seeing if it  should be drawn.
	 */
	@Override
	public boolean getInUse() {
		return visible;
	}
	
	/** 
	 * Set the health of the block, this is separate from dealing damage.
	 * @param newHealth The new health of the block
	 */
	public void setHealth(int newHealth) {
		this.health = newHealth;
		if(newHealth < 1) {
			visible = false;
			this.setInUse(false);
		}
	}
}