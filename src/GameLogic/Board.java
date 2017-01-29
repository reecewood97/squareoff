package GameLogic;

import java.util.ArrayList;

public class Board {
	
	private int player;
	private int squareID;
	private ArrayList<PhysObject> objects;
	private ArrayList<PhysObject> changed;
	private ArrayList<Square> squares;
	private boolean freeState;
	
	public Board(){
		this.player = 0;
		this.squareID = 0;
		this.objects = new ArrayList<PhysObject>();
		this.changed = new ArrayList<PhysObject>();
		this.squares = new ArrayList<Square>();
		this.freeState = false;
	}
	
	public void setActivePlayer(int newPlayer, int newID) {
		this.player = newPlayer;
		this.squareID = newID;
	}
	
	public PhysObject getActivePlayer() {
		return objects.get(1); //TODO
	}
	
	private void freeSim() {
		//TODO 
	}
	
	public void updateFrame(Move move) {
		if(freeState) { // If the engine is in free-physics mode then the move is irrelevant,
			freeSim();  // just simulate another frame.
		}
		else {
			PhysObject activePlayer = getActivePlayer();
			if (true /*TODO active player on floor*/) {
				if(move.getJump()) {
					//set up veloctiy
				}
				switch(move.getDirection()) {
					case "Left" : //move left
					case "Right": //move right
					case "None" : //do nothing
					default     : System.out.println("Physics engine has detected an invalid move direction.");
				}
			}
			else {
				switch(move.getDirection()) {
					case "Left" : //velocity more left
					case "Right": //velocity more right
					case "None" : //do nothing
					default     : System.out.println("Physics engine has detected an invalid move direction.");
				}
				//change yvel to account for gravity
			}
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
