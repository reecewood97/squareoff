package GameLogic;

import java.awt.geom.Point2D;

public class TimedGrenade extends PhysObject { //This object should be painted circular
	
	private int framesLeft;

	public TimedGrenade(TimedGrenade other) {
		//Creates a shallow copy of a weapon
		super(other);
		this.setName("WeaponTimedGrenade");
		this.setXvel(other.getXvel());
		this.setYvel(other.getYvel());
		this.setInUse(other.getInUse());
		this.framesLeft = other.getFrames();
	}

	public TimedGrenade(Point2D.Double pos, double xvel, double yvel, boolean inUse) {
		super(true, pos, 10, 10, false);
		this.setName("WeaponTimedGrenade");
		this.setXvel(xvel);
		this.setYvel(yvel);
		this.setInUse(inUse);
		this.framesLeft = 100;
	}
	
	@Override
	public void update() {
		if(getInUse()){
			setYvel(getYvel()-getGrav());
			setPos(new Point2D.Double(getPos().getX()+getXvel(), getPos().getY()+getYvel()));
			framesLeft = framesLeft-1;
		}
	}
		
	//Undoes the update for when a collision is detected
	/*@Override
	public void undoUpdate() {
		setPos(new Point2D.Double(getPos().getX()-getXvel(), getPos().getY()-getYvel()));
		setYvel(getYvel()+getGrav());
	}*/
	
	@Override
	public int getFrames() {
		return framesLeft;
	}

}
