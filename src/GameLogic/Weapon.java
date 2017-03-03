package GameLogic;

import java.awt.geom.Point2D;

public class Weapon extends PhysObject {
	
	private boolean inUse;
	
	public Weapon(Point2D.Double pos, double xvel, double yvel) {
		super(true, pos, 10, 10, false);
		this.setName("ExplodeOnImpact");
		this.setXvel(xvel);
		this.setYvel(yvel);
		inUse = true;
	}
	
	public boolean getInUse(){
		
		return inUse;
	}
	
	public void setInUse(Boolean bool){
		inUse = bool;
	}
	
	@Override
	public void update() {
		if(inUse) {
		setPos(new Point2D.Double(this.getPos().getX()+this.getXvel(), this.getPos().getY()+this.getYvel()));
		setYvel(getYvel()-getGrav());
		System.out.println("Wep at: " + getPos().getX() + ", " + getPos().getY()); //TODO
		}
	}
}
