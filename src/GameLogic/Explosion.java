package GameLogic;

import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class Explosion extends PhysObject{
	
	private int size;
	
	public Explosion(Point2D.Double pos){
		
		super(false, pos, 10, 10, false);
		this.setName("Explosion");
		this.size = 1;
	}
	
	public void setSize(int expsize){
		
		this.size = expsize;
		
	}
	
	public int getSize(){
		
		return this.size;
	}
	
	

}
