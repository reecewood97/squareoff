package GameLogic;

import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class Explosion extends PhysObject{
	
	private double size;
	
	/**
	 * Creates a new explosion object
	 * @param pos The co-ordinates of the middle of the explosion
	 */
	public Explosion(Point2D.Double pos){ //POSITION IS MIDDLE OF EXPLOSION
		super(false, pos, 1, 1, false);
		this.setName("Explosion");
		this.size = 1;
	}
	
	/**
	 * Creates a shallow copy of an explosion object
	 * @param other The explosion to be copied
	 */
	public Explosion(Explosion other) {
		super(false, other.getPos(), 1, 1, false);
		this.setName("Explosion");
		this.size = other.getSize();
		this.setInUse(other.getInUse());
	}
	
	/**
	 * Sets the size of the explosion, disappears after >60
	 * @param expsize The new size of the explosion
	 */
	public void setSize(int expsize){
		this.size = expsize;
	}
	
	/**
	 * @return The current size of the explosion
	 */
	public double getSize(){
		return this.size;
	}
}
