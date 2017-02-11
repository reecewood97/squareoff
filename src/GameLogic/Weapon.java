package GameLogic;

import java.awt.geom.Point2D;

public class Weapon extends PhysObject {
	
	private boolean inUse;
	
	public Weapon(Point2D.Double pos) {
		super();
		this.setName("ExplodeOnImpact");
		inUse = false;
	
	}
	
	public boolean getInUse(){
		
		return inUse;
	}
	
	public void setInUse(Boolean bool){
		inUse = bool;
	}
	

}
