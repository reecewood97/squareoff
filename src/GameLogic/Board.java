package GameLogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.geom.Point2D;
import java.lang.Math;

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
		
		//BOARD IS 800 ACROSS BY 450 UP STARTING FROM BOTTOM LEFT AS (0, 0)
		//Initialise the placements of the 4 teams.
		Point2D.Double redpos = new Point2D.Double(300, 220);
		Square red = new Square(1 , 0, redpos);
		Point2D.Double blupos = new Point2D.Double(300, 220);
		Square blu = new Square(2 , 0, blupos);
		Point2D.Double yelpos = new Point2D.Double(700, 220);
		Square yel = new Square(3 , 0, yelpos);
		Point2D.Double grnpos = new Point2D.Double(700, 220);
		Square grn = new Square(4 , 0, grnpos);
		squares.add(red);
		objects.add(red);
		squares.add(blu);
		objects.add(blu);
		squares.add(yel);
		objects.add(yel);
		squares.add(grn);
		objects.add(grn);
				
		//Draw blocks at bottom of map
		for(int i = 150; i < 1450; i+=100) {
			TerrainBlocks block = new TerrainBlocks(1, 1, 1,new Point2D.Double(i,250), true);
			blocks.add(block);
			objects.add(block);
			//TerrainBlocks block2 = new TerrainBlocks(1, 1, 1,new Point2D.Double(i,195), true);
			//objects.add(block2);
			//blocks.add(block2);
			//TerrainBlocks block3 = new TerrainBlocks(1, 1, 1,new Point2D.Double(i,190), true);
			//objects.add(block3);
			//blocks.add(block3);
			}
	}
	
	public void setActivePlayer(int newPlayer, int newID) {
		this.player = newPlayer;
		this.squareID = newID;
	}
	
	public PhysObject getActivePlayer() {
		int x = 4*player + squareID;
		return objects.get(x);
	}
	
	private TerrainBlocks onFloor(Square guy) {
		Iterator<TerrainBlocks> it = blocks.iterator();
		while(it.hasNext()) {
			TerrainBlocks nextblock = it.next();
			if(onFloorOne(guy, nextblock)) {
				return nextblock;
			}
		}
		return null;
	}
	
	private boolean onFloorOne(Square guy, TerrainBlocks block) {
		double guyleft = guy.getPos().getX();
		double guyright = guy.getPos().getX()+guy.getWidth();
		double guydown = guy.getPos().getY();
		double blockleft = block.getPos().getX();
		double blockright = block.getPos().getX()+block.getWidth();
		double blockup = block.getPos().getY();
		
		if((blockleft<guyleft && guyleft<blockright) || 
				(blockleft<guyright && guyright<blockright)) {
			//Here it is possible to add a bit to slow down the block so it doesnt go through the block
			if(Math.abs(blockup-guydown)<2){
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	private void freeSim() {
		//TODO
	}
	
	public void updateFrame(Move move) {
		changed = new ArrayList<PhysObject>();
		if(freeState) { // If the engine is in free-physics mode then the move is irrelevant,
			freeSim();  // just simulate another frame.
		}
		else {
			Square activePlayer = (Square)getActivePlayer();
			TerrainBlocks floor = onFloor(activePlayer);
			if (floor!=null) { //if the player is standing on a block
				activePlayer.setYvel(0);
				activePlayer.setPos(new Point2D.Double
				  (activePlayer.getPos().getX(), floor.getPos().getY()+floor.getHeight()));
					if(move.getJump()) {
					activePlayer.setYvel(20);
				}
				switch(move.getDirection()) {
				// TODO check wall collisions
					case "Left" : activePlayer.setPos
					(new Point2D.Double(activePlayer.getPos().getX()-2,activePlayer.getPos().getY()-2));
					case "Right": activePlayer.setPos
					(new Point2D.Double(activePlayer.getPos().getX()+2,activePlayer.getPos().getY()+2));
					case "None" : //do nothing
					default     : System.out.println("Physics engine has detected an invalid move string.");
				}
			}
			else {
				switch(move.getDirection()) {
					case "Left" : activePlayer.setXvel(activePlayer.getXvel()-2);
					case "Right": activePlayer.setXvel(activePlayer.getXvel()+2);
					case "None" : //do nothing
					default     : System.out.println("Physics engine has detected an invalid move string.");
				}
				activePlayer.setYvel(activePlayer.getYvel()-activePlayer.getGrav());
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
