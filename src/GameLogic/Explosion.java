package GameLogic;

import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class Explosion extends PhysObject{
	
	private int size;
	private boolean inUse;
	
	public Explosion(Point2D.Double pos){
		
		super(true, pos, 10, 10, false);
		this.setName("ExplodeOnImpact");
		inUse = false;
		this.size = 1;
	}
	
	public void setSize(int expsize){
		
		this.size = expsize;
		
	}
	
	public int getSize(){
		
		return this.size;
	}
	
	public void setInUse(boolean u){
		
		this.inUse = u;
		
		
	}
	
	public boolean getInUse(){
		
		return this.inUse;
	}

}
