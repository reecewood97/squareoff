package GameLogic;

import java.awt.geom.Point2D;

public class Weapon extends PhysObject {
	
	private boolean inUse;
	
	public Weapon(Point2D.Double pos, double xvel, double yvel, boolean inUse) {
		super(true, pos, 10, 10, false);
		this.setName("ExplodeOnImpact");
		this.setXvel(xvel);
		this.setYvel(yvel);
		this.inUse = inUse;
	}
	
	public Weapon(Weapon other) {
		super(other);
		this.setName("ExplodeOnImpact");
		this.setXvel(other.getXvel());
		this.setYvel(other.getYvel());
		this.inUse = other.getInUse();
	}
	
	
	@Override
	public void update() {
		if(inUse) {
		setPos(new Point2D.Double(this.getPos().getX()+this.getXvel(), this.getPos().getY()+this.getYvel()));
		setYvel(getYvel()-getGrav());
		
		//TODO
		
		System.out.println("Wep at: " + getPos().getX() + ", " + getPos().getY()); 
		}
	}
}
