package GameLogic;

import java.awt.geom.Point2D;

public class ExplodeOnImpact extends PhysObject {
	
	private boolean inUse;
	
	public ExplodeOnImpact(Point2D.Double pos, double xvel, double yvel, boolean inUse) {
		super(true, pos, 10, 10, false);
		this.setName("WeaponExplodeOnImpact");
		this.setXvel(xvel);
		this.setYvel(yvel);
		this.inUse = inUse;
	}
	
	public ExplodeOnImpact(ExplodeOnImpact other) {
		//Creates a shallow copy of a weapon
		super(other);
		this.setName("WeaponExplodeOnImpact");
		this.setXvel(other.getXvel());
		this.setYvel(other.getYvel());
		this.inUse = other.getInUse();
	}
	
	
	@Override
	public void update() {
		if(inUse) {
		setPos(new Point2D.Double(this.getPos().getX()+this.getXvel(), this.getPos().getY()+this.getYvel()));
		setYvel(getYvel()-getGrav());
		}
	}
}
