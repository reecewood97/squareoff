package GameLogic;

import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class Explosion extends PhysObject{
	
	private double size;
	
	public Explosion(Point2D.Double pos, double size){ //POSITION IS MIDDLE OF EXPLOSION
		super(false, pos, (int)(2*size/5), (int)(2*size/5), false);
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
