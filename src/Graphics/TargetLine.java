package Graphics;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import GameLogic.PhysObject;

public class TargetLine extends PhysObject {
	
	public TargetLine(){
		
		super(true, new Point2D.Double(10,10), 10, 10, false);
		this.setName("TargetLine");
	}

}
