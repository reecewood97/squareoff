package GameLogic;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

public class PhysObject implements Serializable{
	// This class is meant to be extended by each other physics object
	
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
	
	public PhysObject() {
		//Initiates a new physics object with no values... if you want...
		this.gravity = false;
		this.grav = 2;
		this.pos = new Point2D.Double(0, 0);
		this.xvel = 0;
		this.yvel = 0;
		this.attributes = new ArrayList<String>();
		this.width = 10;
		this.height = 10;
		this.solid = true;
		this.inUse = true;
	}
	
	public PhysObject(boolean gravity, Point2D.Double pos, int height, int width, boolean solid) {
		//Probably best to use this constructor
		//If you want to set changed, velocity, or attributes, you have to do it after construction
		this.gravity = gravity;
		this.grav = 2;
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
	
	public boolean getInUse() {
		return inUse;
	}
	
	public void update() {
		setPos(new Point2D.Double(pos.getX()+xvel, pos.getY()+yvel));
		setYvel(getYvel()-getGrav());
	}
	
	public void undoUpdate() {
		setYvel(getYvel()+getGrav());
		setPos(new Point2D.Double(pos.getX()-xvel, pos.getY()-yvel));
	}
	
	public boolean getGravity() {
		return gravity;
	}
	
	public double getGrav() {
		return grav;
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
	
	public void setInUse(boolean hi){
		inUse = hi;
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
	
	public boolean equal(PhysObject p) {
		if(pos.equals(p.getPos()) && this.getInUse()==p.getInUse()) {
			return true;
		}
		else return false;
	}
	
}
