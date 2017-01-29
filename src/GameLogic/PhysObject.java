package GameLogic;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PhysObject {
	// This class is meant to be extended by each other physics object
	
	private boolean gravity;
	private boolean changed;
	private Point2D.Double pos;
	private double xvel;
	private double yvel;
	private ArrayList<String> attributes;
	private int width;
	private int height;
	
	public PhysObject() {
		//Initiates a new physics object with no values... if you want...
		this.gravity = false;
		this.changed = false;
		this.pos = new Point2D.Double(0, 0);
		this.xvel = 0;
		this.yvel = 0;
		this.attributes = new ArrayList<String>();
		this.width = 10;
		this.height = 10;
	}
	
	public PhysObject(boolean gravity, Point2D.Double pos, int height, int width) {
		//Probably best to use this constructor
		//If you want to set changed, velocity, or attributes, you have to do it after construction
		this.gravity = gravity;
		this.changed = false;
		this.pos = pos;
		this.xvel = 0;
		this.yvel = 0;
		this.attributes = new ArrayList<String>();
		this.width = width;
		this.height = height;
	}
	
	public boolean getGrav() {
		return gravity;
	}
	
	public boolean getChanged() {
		return changed;
	}
	
	public Point2D.Double getPos() {
		return pos;
	}
	
	public double getXvel() {
		return xvel;
	}
	
	public double getYvel() {
		return yvel;
	}
	
	public ArrayList<String> getAttributes() {
		return attributes;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setGrav(boolean gravity) {
		this.gravity = gravity;
	}
	
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	public void setPos(Point2D.Double pos) {
		this.pos = pos;
		this.changed = true;
	}
	
	public void setXvel(double vel) {
		this.xvel = vel;
	}
	
	public void setYvel(double vel) {
		this.yvel = vel;
	}
	
	public void addAttribute(String att) {
		attributes.add(att);
	}
	
	public void setHeight(int newHeight) {
		this.height = newHeight;
		this.changed = true;
	}
	
	public void setWidth(int newWidth) {
		this.width = newWidth;
		this.changed = true;
	}
	
}
