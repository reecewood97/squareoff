package GameLogic;

import java.awt.geom.Point2D;

public class Missile extends PhysObject {
	
	private boolean inUse;
	
	/**
	 * Creates a new missile object with the specified attributes
	 * @param pos The position of the missile
	 * @param xvel The X velocity of the missile
	 * @param yvel The Y velocity of the missile
	 * @param inUse Whether the missile is in use
	 */
	public Missile(Point2D.Double pos, double xvel, double yvel, boolean inUse) {
		super(false, pos, 10, 10, false);
		this.setName("WeaponMissile");
		this.setXvel(xvel);
		this.setYvel(yvel);
		this.inUse = inUse;
	}
	
	/**
	 * Creates a shallow copy of a missile
	 * @param other The missile to be copied
	 */
	public Missile(Missile other) {
		super(other);
		this.setName("WeaponMissile");
		this.setXvel(other.getXvel());
		this.setYvel(other.getYvel());
		this.inUse = other.getInUse();
	}
	
	@Override
	public void update() {
		if(inUse) {
		setYvel(getYvel()-getGrav());
		setPos(new Point2D.Double(this.getPos().getX()+this.getXvel(), this.getPos().getY()+this.getYvel()));
		}
	}
}