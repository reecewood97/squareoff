package GameLogic;

import java.awt.geom.Point2D;

public class TimedGrenade extends PhysObject {
	
	private int framesLeft;

	/**
	 * Creates a new timed grenade with the specified attributes
	 * @param pos The position of the bottom left corner of the framing rectangle
	 * @param xvel The X velocity of the grenade
	 * @param yvel The Y velocity of the grenade
	 * @param inUse Whether the grenade is in use
	 */
	public TimedGrenade(Point2D.Double pos, double xvel, double yvel, boolean inUse) {
		super(true, pos, 10, 10, false);
		this.setName("WeaponTimedGrenade");
		this.setXvel(xvel);
		this.setYvel(yvel);
		this.setInUse(inUse);
		this.framesLeft = 100;
	}
	
	/**
	 * Creates a shallow copy of a timed grenade
	 * @param other The grenade to be copied
	 */
	public TimedGrenade(TimedGrenade other) {
		//Creates a shallow copy of a weapon
		super(other);
		this.setName("WeaponTimedGrenade");
		this.setXvel(other.getXvel());
		this.setYvel(other.getYvel());
		this.setInUse(other.getInUse());
		this.framesLeft = other.getFrames();
	}
	
	@Override
	public void update() {
		if(getInUse()){
			setYvel(getYvel()-getGrav());
			setPos(new Point2D.Double(getPos().getX()+getXvel(), getPos().getY()+getYvel()));
			framesLeft = framesLeft-1;
		}
	}
		
	
	@Override
	public int getFrames() {
		return framesLeft;
	}

}
