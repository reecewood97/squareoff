package GameLogic;

import java.util.ArrayList;

public class Board {
	
	private ArrayList<PhysObject> objects;
	private ArrayList<PhysObject> changed;
	private boolean freeState;
	
	public Board(){
		this.objects = new ArrayList<PhysObject>();
		this.changed = new ArrayList<PhysObject>();
		
		this.freeState = false;
	}
	
	private void freeSim() {
		//TODO
	}
	
	public void updateFrame(Move move) {
		if(freeState) { // If the engine is in free-physics mode then the move is irrelevant,
			freeSim();  // just simulate another frame.
		}
		else {
			//TODO
			//If left/right then move a certain amount
			//If jump and on floor then set y-velocity to a certain amount.
		}
	}
	
	/**
	 * Used on the client-side, receiving an update string from the server.
	 * @param update The update string.
	 */
	public void update(String update) {
		
	}
	
	/**
	 * Used on the server-side, receiving an update string that is from the inputs of the player.
	 * @param inputs
	 */
	public void input(String input) {
	}
	
	/**
	 * Used on the server-side to send an update to all the client for how their boards should look.
	 * @return The update sent.
	 */
	public ArrayList<PhysObject> getUpdate() {
		return objects;
	}
	
	public ArrayList<PhysObject> getChanged() {
		return changed;
	}
}
