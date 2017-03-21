package GameLogic;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class TargetLine extends PhysObject {
	
	/**
	 * Creates a new targetLine
	 */
	public TargetLine(){
		super(false, new Point2D.Double(10,10), 10, 10, false);
		this.setInUse(false);
		this.setName("TargetLine");
		this.setInUse(false);
	}
	
	/**
	 * Creates a shallow copy of the targetLine
	 * @param other The targetLine to be copied
	 */
	public TargetLine(TargetLine other){
		super(false, other.getPos(), other.getHeight(), other.getWidth(), other.getSolid());
		this.setName(other.getName());
		
	}
}
