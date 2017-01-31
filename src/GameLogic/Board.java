package GameLogic;

import java.util.ArrayList;
import java.awt.geom.Point2D;

public class Board {
	
	private int player;
	private int squareID;
	private ArrayList<PhysObject> objects;
	private ArrayList<PhysObject> changed;
	private ArrayList<Square> squares;
	private ArrayList<TerrainBlocks> blocks;
	private boolean freeState;
	
	public Board(){
		this.player = 0;
		this.squareID = 0;
		this.objects = new ArrayList<PhysObject>();
		this.changed = new ArrayList<PhysObject>();
		this.squares = new ArrayList<Square>();
		this.blocks = new ArrayList<TerrainBlocks>();
		this.freeState = false;
		
		//Initialise the placements of the 4 teams.
		Point2D.Double redpos = new Point2D.Double(300, 901);
		Square red = new Square(0 , 0, redpos);
		Point2D.Double blupos = new Point2D.Double(300, 901);
		Square blu = new Square(0 , 0, blupos);
		Point2D.Double yelpos = new Point2D.Double(300, 901);
		Square yel = new Square(0 , 0, yelpos);
		Point2D.Double grnpos = new Point2D.Double(300, 901);
		Square grn = new Square(0 , 0, grnpos);
		squares.add(red);
		objects.add(red);
		squares.add(blu);
		objects.add(blu);
		squares.add(yel);
		objects.add(yel);
		squares.add(grn);
		objects.add(grn);
				
		//Draw blocks at bottom of map
		for(int i = 0; i < 1500; i+=5) {
			TerrainBlocks block = new TerrainBlocks(1, 1, 1,new Point2D.Double(i,900), true);
			objects.add(block);
			TerrainBlocks block2 = new TerrainBlocks(1, 1, 1,new Point2D.Double(i,895), true);
			objects.add(block2);
			TerrainBlocks block3 = new TerrainBlocks(1, 1, 1,new Point2D.Double(i,890), true);
			objects.add(block3);
			}
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
	
	public ArrayList<TerrainBlocks> getBlocks(){
		return blocks;
		
	}
	
	public ArrayList<Square> getSquares(){
		return squares;
	}
}
