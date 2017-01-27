package GameLogic;

import java.awt.geom.Point2D;

public class PhysObject {
	
	private boolean gravity;
	private boolean changed;
	private Point2D.Double pos;
	
	public Point2D.Double getPos() {
		return pos;
	}
	
	public boolean getGrav() {
		return gravity;
	}
	
	public boolean getChanged() {
		return changed;
	}
	
	public void setPos(Point2D.Double pos) {
		this.pos = pos;
	}
	
}
