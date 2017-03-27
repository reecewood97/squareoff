package GameLogic;

import java.awt.geom.Point2D;
import java.io.Serializable;
//import java.util.ArrayList;

public class PhysObject implements Serializable{
	// This class is meant to be extended by each other physics object
	// i.e there should not exist an Object that is simply a PhysObject
	
	private boolean gravity;
	private double grav;
	private Point2D.Double pos;
	private double xvel;
	private double yvel;
	private int width;
	private int height;
	private String name;
	private boolean solid;
	private boolean inUse;
	
	/**
	 * Creates a shallow copy of a physObject
	 * @param other The physObject to be copied
	 */
	public PhysObject(PhysObject other) {
		setGravity(other.getGravity());
		setGrav(other.getGrav());
		setPos(other.getPos());
		setXvel(other.getXvel());
		setYvel(other.getYvel());
		setWidth(other.getWidth());
		setHeight(other.getHeight());
		setSolid(other.getSolid());
		setInUse(other.getInUse());
	}
	
	/**
	 * Creates a new physObject with the specified attributes
	 * @param gravity Whether this object is subject to gravity
	 * @param pos The position of this object(bottom left origin)
	 * @param height The height of the object
	 * @param width The width of the object
	 * @param solid Whether the object allows things to pass through it
	 */
	public PhysObject(boolean gravity, Point2D.Double pos, int height, int width, boolean solid) {
		this.gravity = gravity;
		this.grav = 0.5;
		this.pos = pos;
		this.xvel = 0;
		this.yvel = 0;
		//this.attributes = new ArrayList<String>();
		this.width = width;
		this.height = height;
		this.solid = solid;
		this.name = "";
		this.inUse = true;
	}
	
	public int getFrames() {
		return 1;
	}
	
	/**
	 * @return true if this object is in use, false otherwise
	 */
	public boolean getInUse() {
		return inUse;
	}
	
	/**
	 * Updates one frame for this object
	 */
	public void update() {
		if(getInUse()){
			setYvel(getYvel()-getGrav());
			setPos(new Point2D.Double(pos.getX()+xvel, pos.getY()+yvel));
		}
	}
	
	/**
	 * Undoes the update for when a collision is detected
	 */
	public void undoUpdate() {
		setPos(new Point2D.Double(pos.getX()-xvel, pos.getY()-yvel));
		setYvel(getYvel()+getGrav());
	}
	
	/**
	 * @return whether or not this object is affected by gravity
	 */
	public boolean getGravity() {
		return gravity;
	}
	
	/**
	 * @return The gravity acting on this object
	 */
	public double getGrav() {
		if(getGravity()){
			return grav;
		}
		return 0;
	}
	
	/**
	 * @return The position of the object
	 */
	public Point2D.Double getPos() {
		return pos;
	}
	
	/**
	 * @return The X velocity of the object (Positive if right, negative if left)
	 */
	public double getXvel() {
		return xvel;
	}
	
	/**
	 * @return The Y velocity of the object (Positive if up, negative if down)
	 */
	public double getYvel() {
		return yvel;
	}
	
	/**
	 * @return The height of this object
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @return The width of this object
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @return What type of object this is
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return Whether or not other objects can pass through this one
	 */
	public boolean getSolid() {
		return solid;
	}
	
	/**
	 * Sets the object in use or not in use
	 * @param inUse Whether this object should be in use
	 */
	public void setInUse(boolean inUse){
		this.inUse = inUse;
	}
	
	/**
	 * Sets whether the object should be affected by gravity
	 * @param gravity Whether gravity should affect this object
	 */
	public void setGravity(boolean gravity) {
		this.gravity = gravity;
	}
	
	/**
	 * Sets the amount of gravity acting on this object
	 * @param grav The amount of gravity acting on this object
	 */
	public void setGrav(double grav) {
		this.grav = grav;
	}
	
	/**
	 * Sets the position of the object
	 * @param pos The position of the object
	 */
	public void setPos(Point2D.Double pos) {
		this.pos = pos;
	}
	
	/**
	 * Sets the x-velocity of the object(won't go over 10)
	 * @param vel The new velocity
	 */
	public void setXvel(double vel) {
		if(vel > 20){
			this.xvel = 20;
		} else if (vel < (-20)){
			this.xvel = (-20);
		} else {
			this.xvel = vel;
		}
	}
	
	/**
	 * Sets the y-velocity of the object(won't go over 10)
	 * @param vel The new velocity
	 */
	public void setYvel(double vel) {
		if(vel > 20){
			this.yvel = 20;
		} else if (vel < (-20)){
			this.yvel = (-20);
		} else {
			this.yvel = vel;
		}
	}
	
	/**
	 * Sets the height of the object
	 * @param newHeight The new height of the object
	 */
	public void setHeight(int newHeight) {
		this.height = newHeight;
	}
	
	/**
	 * Sets the width of the object
	 * @param newWidth The new width of the object
	 */
	public void setWidth(int newWidth) {
		this.width = newWidth;
	}
	
	/**
	 * Sets the name of the object
	 * @param newname The new name of the object
	 */
	public void setName(String newname){
		this.name = newname;
		
	}
	
	/**
	 * Sets whether other objects can pass through this one
	 * @param s Whether other objects should be able to pass through this one
	 */
	public void setSolid(boolean s) {
		this.solid = s;
	}
	
	@Override
	public boolean equals(Object anObject) {
		PhysObject p = (PhysObject)anObject;
		if(pos.equals(p.getPos()) && this.getInUse()==p.getInUse() && this.getName().equals(p.getName())
				&& getYvel()==p.getYvel()) {
			return true;
		}
		return false;
	}
	
}
