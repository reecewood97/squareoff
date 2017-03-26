package GameLogic;

//import java.lang.Comparable;

public class Collision{// implements Comparable<Collision>{
	
	private PhysObject thing;
	private PhysObject block;
	private int lspos;

	/**
	 * Creates a new collision object
	 * @param thing The thing that collided
	 * @param lspos The position in the objects list where that thing is
	 * @param block The block that collided
	 */
	public Collision(PhysObject thing,int lspos, PhysObject block) {
		this.thing = thing;
		this.lspos = lspos;
		this.block = block;
	}
	
	/**
	 * @return The moving object taking part in the collision
	 */
	public PhysObject getThing(){
		return thing;
	}
	
	/**
	 * @return The block taking part in the collision
	 */
	public PhysObject getBlock(){
		return block;
	}
	
	public int lspos(){
		return lspos;
	}
	
	@Override
	public boolean equals(Object anObject){
		Collision collision = (Collision)anObject;
		return(this.lspos==collision.lspos() && this.thing.equals(collision.getThing()));
	}

	/*@Override
	public int compareTo(Collision o) {
		if (thing.getPos().getY()<o.getThing().getPos().getX()){
			return -1;
		}
		if (thing.getPos().getY()==o.getThing().getPos().getX()){
			return 0;
		}
		if (thing.getPos().getY()>o.getThing().getPos().getX()){
			return 1;
		}
		return 0;
	}*/

}
