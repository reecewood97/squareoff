package GameLogic;

import java.awt.geom.Point2D;

public class ExplodeOnImpact extends PhysObject {

	/**
	 * Creates a new impact grenade with the specified attributes
	 * @param pos The position of the grenade(origin from bottom left)
	 * @param xvel The velocity in the x-direction
	 * @param yvel The velocity in the y-direction
	 * @param inUse Whether the object is in use
	 */
	public ExplodeOnImpact(Point2D.Double pos, double xvel, double yvel, boolean inUse) {
		super(true, pos, 10, 10, false);
		this.setName("WeaponExplodeOnImpact");
		this.setXvel(xvel);
		this.setYvel(yvel);
		this.setInUse(inUse);
	}
	
	/**
	 * Creates a shallow copy of an impact grenade
	 * @param other The grenade to be copied
	 */
	public ExplodeOnImpact(ExplodeOnImpact other) {
		super(other);
		this.setName("WeaponExplodeOnImpact");
		this.setXvel(other.getXvel());
		this.setYvel(other.getYvel());
		this.setInUse(other.getInUse());
	}
	
	
	@Override
	public void update() {
		if(getInUse()) {
		setYvel(getYvel()-getGrav());
		setPos(new Point2D.Double(this.getPos().getX()+this.getXvel(), this.getPos().getY()+this.getYvel()));
		}
	}
}
