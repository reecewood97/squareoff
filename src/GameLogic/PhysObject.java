package GameLogic;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

public class PhysObject implements Serializable{
	// This class is meant to be extended by each other physics object
	// i.e there should not exist an Object that is simply a PhysObject
	
	private boolean gravity;
	private double grav;
	private Point2D.Double pos;
	private double xvel;
	private double yvel;
	private ArrayList<String> attributes;
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
		this.gravity = other.getGravity();
		this.grav = other.getGrav();
		this.pos = other.getPos();
		this.xvel = other.getXvel();
		this.yvel = other.getYvel();
		this.attributes = other.getAttributes();
		this.width = other.getWidth();
		this.height = other.getHeight();
		this.solid = other.getSolid();
		this.inUse = other.getInUse();
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
		this.attributes = new ArrayList<String>();
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
	
	
	public boolean getGravity() {
		return gravity;
	}
	
	public double getGrav() {
		if(getGravity()){
			return grav;
		}
		return 0;
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
	
	public String getName(){
		return name;
	}
	
	public boolean getSolid() {
		return solid;
	}
	
	public void setInUse(boolean inUse){
		this.inUse = inUse;
	}
	
	public void setGravity(boolean gravity) {
		this.gravity = gravity;
	}
	
	public void setGrav(double grav) {
		this.grav = grav;
	}
	
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
	
	public void addAttribute(String att) {
		attributes.add(att);
	}
	
	public void setHeight(int newHeight) {
		this.height = newHeight;
	}
	
	public void setWidth(int newWidth) {
		this.width = newWidth;
	}
	
	public void setName(String newname){
		this.name = newname;
		
	}
	
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
