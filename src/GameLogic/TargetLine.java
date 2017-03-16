package Graphics;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import GameLogic.PhysObject;

public class TargetLine extends PhysObject {
	
	public TargetLine(){
		super(false, new Point2D.Double(10,10), 10, 10, false);
		this.setName("TargetLine");
	}
	
	public TargetLine(TargetLine other){
		super(false, other.getPos(), other.getHeight(), other.getWidth(), other.getSolid());
		this.setName(other.getName());
	}

	@Override
	public boolean getInUse(){
		return false;
	}
}
