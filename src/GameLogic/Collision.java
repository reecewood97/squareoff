package GameLogic;

public class Collision{
	
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
	
	public PhysObject getThing(){
		return thing;
	}
	
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

}
