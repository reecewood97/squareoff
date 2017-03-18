package GameLogic;

import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class Explosion extends PhysObject{
	
	private double size;
	
	public Explosion(Point2D.Double pos){ //POSITION IS MIDDLE OF EXPLOSION
		super(false, pos, 1, 1, false);
		this.setName("Explosion");
		this.size = 1;
	}
	
	public void setSize(int expsize){
		
		this.size = expsize;
		
	}
	
	public double getSize(){
		
		return this.size;
	}
	
	

}
