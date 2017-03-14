package GameLogic;

import java.awt.geom.Point2D;

public class Missile extends PhysObject {
	
	private boolean inUse;
	
	public Missile(Point2D.Double pos, double xvel, double yvel, boolean inUse) {
		super(false, pos, 10, 10, false);
		this.setName("WeaponMissile");
		this.setXvel(xvel);
		this.setYvel(yvel);
		this.inUse = inUse;
	}
	
	public Missile(Missile other) {
		//Creates a shallow copy of a weapon
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