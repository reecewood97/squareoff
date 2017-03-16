package GameLogic;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class TargetLine extends PhysObject {
	
	public TargetLine(){
		super(false, new Point2D.Double(10,10), 10, 10, false);
		this.setInUse(false);
		this.setName("TargetLine");
		this.setInUse(false);
	}
	
	public TargetLine(TargetLine other){
		super(false, other.getPos(), other.getHeight(), other.getWidth(), other.getSolid());
		this.setName(other.getName());
		
	}
}
