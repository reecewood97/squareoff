package GameLogic;

import java.awt.geom.Point2D;

public class ExplodeOnImpact extends PhysObject {

	
	public ExplodeOnImpact(Point2D.Double pos, double xvel, double yvel, boolean inUse) {
		super(true, pos, 10, 10, false);
		this.setName("WeaponExplodeOnImpact");
		this.setXvel(xvel);
		this.setYvel(yvel);
		this.setInUse(inUse);
	}
	
	public ExplodeOnImpact(ExplodeOnImpact other) {
		//Creates a shallow copy of a weapon
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
