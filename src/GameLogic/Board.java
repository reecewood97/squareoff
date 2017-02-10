package gameLogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.Math;

public class Board {
	
	private int player;
	private int squareID;
	private ArrayList<PhysObject> objects;
	private boolean freeState;
	private ArrayBlockingQueue<ArrayList<PhysObject>> q;
	private int winner;
	
	public Board(){
		this.player = 0;
		this.squareID = 0;
		this.objects = new ArrayList<PhysObject>();
		this.freeState = false;
		this.q = new ArrayBlockingQueue<ArrayList<PhysObject>>(100); //This handles the moves that need to be sent to clients.
		this.winner = -1;
		//this.q = new ArrayBlockingQueue<String>(100); //This handles the moves that need to be sent to clients.
		
		//BOARD IS 800 ACROSS BY 450 UP STARTING FROM BOTTOM LEFT AS (0, 0)
		//Initialise the placements of the 4 teams.
		Point2D.Double redpos = new Point2D.Double(200, 380);
		PhysObject red = new Square(1 ,0, 0, redpos);
		Point2D.Double blupos = new Point2D.Double(300, 380);
		PhysObject blu = new Square(2 ,0, 0, blupos);
		Point2D.Double yelpos = new Point2D.Double(400, 380);
		PhysObject yel = new Square(3 ,0, 0, yelpos);
		Point2D.Double grnpos = new Point2D.Double(500, 380);
		PhysObject grn = new Square(4 ,0, 0, grnpos);
		objects.add(red);
		objects.add(blu);
		objects.add(yel);
		objects.add(grn);
				
		//Draw blocks at bottom of map
		for(int i = 150; i < 1450; i+=40) {
			PhysObject block = new TerrainBlock(1, 1, 1,new Point2D.Double(i,350), true);
			objects.add(block);
		}
		
		Point2D.Double weaponpos = new Point2D.Double(100, 100);
		PhysObject weapon = new Weapon(weaponpos);
		objects.add(weapon);
	}
	
	public void setWinner(int player){
		
		this.winner = player;
		
	}
	
	public int getWinner(){
		return winner;
	}
	
	public void notifyQuit(){
		
	}
	
	public void setActivePlayer(int newPlayer, int newID) {
		this.player = newPlayer;
		this.squareID = newID;
	}
	
	public PhysObject getActivePlayer() {
		int x = player + squareID;
		return objects.get(x);
	}
	
	public ArrayList<PhysObject> getWeapons(){
		ArrayList<PhysObject> weapons = new ArrayList<PhysObject>();
		for(PhysObject obj : objects){
			
			if (obj.getName().equals("ExplodeOnImpact")){
				
				weapons.add(obj);
			}
		}
		
		return weapons;
	}
	
	
	public ArrayList<PhysObject> getBlocks(){
		
		ArrayList<PhysObject> blocks = new ArrayList<PhysObject>();
		for(PhysObject obj : objects){
			
			if (obj.getName().equals("TerrainBlock")){
				
				blocks.add(obj);
			}
		}
		
		return blocks;
	}
	
	public ArrayList<PhysObject> getSquares(){
		
		ArrayList<PhysObject> squares = new ArrayList<PhysObject>();
		for(PhysObject obj : objects){
			
			if (obj.getName().equals("Square")){
				
				squares.add(obj);
			}
		}
		
		return squares;
	}
	private double wallDistL(Square guy) {
		Iterator<PhysObject> it = getBlocks().iterator();
		while(it.hasNext()) {
			PhysObject nextblock = it.next();
			if(wallDistLOne(guy, nextblock)<2) {
				return wallDistLOne(guy, nextblock);
			}
		}
		return 10; //Out of range
	}
	
	private double wallDistLOne(Square guy, PhysObject block) {
		double guyleft = guy.getPos().getX();
		double guydown = guy.getPos().getY();
		double guyup = guy.getPos().getY()+guy.getHeight();
		double blockright = block.getPos().getX()+block.getWidth();
		double blockdown = block.getPos().getY();
		double blockup = block.getPos().getY()+block.getHeight();
		
		if((blockdown<guyup && guyup<blockup) || 
				(blockdown<guydown && guydown<blockup)) {
			//Here it is possible to add a bit to slow down the block so it doesnt go through the block
			if(Math.abs(blockright-guyleft)<2){
				return Math.abs(blockright-guyleft);
			}
			else {
				return 10; //Out of range
			}
		}
		else {
			return 10; //Out of range
		}
	}
	
	private double wallDistR(Square guy) {
		Iterator<PhysObject> it = getBlocks().iterator();
		while(it.hasNext()) {
			PhysObject nextblock = it.next();
			if(wallDistROne(guy, nextblock)<2) {
				return wallDistROne(guy, nextblock);
			}
		}
		return 10; //Out of range
	}
	
	private double wallDistROne(Square guy, PhysObject block) {
		double guyright = guy.getPos().getX()+guy.getWidth();
		double guyup = guy.getPos().getY()+guy.getHeight();
		double guydown = guy.getPos().getY();
		double blockleft = block.getPos().getX();
		double blockup = block.getPos().getY()+block.getHeight();
		double blockdown = block.getPos().getY();
		
		if((blockdown<guyup && guyup<blockup) || 
				(blockdown<guydown && guydown<blockup)) {
			if(Math.abs(blockleft-guyright)<2){
				return Math.abs(blockleft-guyright);
			}
			else {
				return 10; //Out of range
			}
		}
		else {
			return 10; //Out of range
		}
	}
	
	private PhysObject onFloor(Square guy) {
		Iterator<PhysObject> it = getBlocks().iterator();
		while(it.hasNext()) {
			PhysObject nextblock = it.next();
			if(onFloorOne(guy, nextblock)) {
				return nextblock;
			}
		}
		return null;
	}
	
	private boolean onFloorOne(Square guy, PhysObject block) {
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
	
	private boolean collides(PhysObject obj1, PhysObject obj2) {
		if(obj1.getSolid()==obj2.getSolid()){//Or if either is not visible TODO
			return false;
		}
		else {
			if(obj1.getName().equals("TerrainBlock")) {
				if(obj2.getName().equals("Weapon")){
					Ellipse2D.Double circle = new Ellipse2D.Double
							(obj2.getPos().getX(), obj2.getPos().getY(), obj2.getWidth(), obj2.getHeight());
					return circle.intersects
							(obj1.getPos().getX(), obj1.getPos().getY(), obj1.getWidth(), obj1.getHeight());
				}
				else{
					Rectangle2D.Double rect = new Rectangle2D.Double
							(obj2.getPos().getX(), obj2.getPos().getY(), obj2.getWidth(), obj2.getHeight());
					return rect.intersects
							(obj1.getPos().getX(), obj1.getPos().getY(), obj1.getWidth(), obj1.getHeight());
				}
			}
			else {
				if(obj1.getName().equals("Weapon")){
					Ellipse2D.Double circle = new Ellipse2D.Double
							(obj1.getPos().getX(), obj1.getPos().getY(), obj1.getWidth(), obj1.getHeight());
					return circle.intersects
							(obj2.getPos().getX(), obj2.getPos().getY(), obj2.getWidth(), obj2.getHeight());
				}
				else {
					Rectangle2D.Double rect = new Rectangle2D.Double
							(obj1.getPos().getX(), obj1.getPos().getY(), obj1.getWidth(), obj1.getHeight());
					return rect.intersects
							(obj2.getPos().getX(), obj2.getPos().getY(), obj2.getWidth(), obj2.getHeight());
				}
			}
		}
	}
	
	private void resolveCollision(PhysObject thing,int lspos, PhysObject block) {
		if(thing.getName().equals("Weapon")){
			//thing.setVisible(false);
			//block.damage(1);
		}
		else {
			thing = objects.get(lspos);
			if(true) {
				//one for x
			}
			if(true) {
				//one for y
			}
		}
	}
	
	private void freeSim() {
		//This is going to be relatively quite slow. Perhaps it can be improved later.
		ArrayList<PhysObject> objs = new ArrayList<PhysObject>(objects);
		for (PhysObject obj : objs) {
			obj.update();
		}
		for (int i = 0; i < objs.size(); i++) {
			for (int j = i+1; j < objs.size(); j++) {
				if(collides(objs.get(i),objs.get(j))){
					if(objs.get(j).getName().equals("TerrainBlock")) {
						resolveCollision(objs.get(i),i,objs.get(j));
					}
					else {
						resolveCollision(objs.get(j),j,objs.get(i));
					}
				}
			}
		}
		return;
	}
	
	public void updateFrame(Move move) {
		if(freeState) { // If the engine is in free-physics mode then the move is irrelevant,
			freeSim();  // just simulate another frame.
		}
		else if (move.getWeaponMove()) {
			move = (WeaponMove)move;
			//TODO
			freeState = true;
		}
		else {
			Square activePlayer = (Square)getActivePlayer();
			PhysObject floor = onFloor(activePlayer);
			if (floor!=null) { //if the player is standing on a block
				activePlayer.setYvel(0);
				activePlayer.setPos(new Point2D.Double
				  (activePlayer.getPos().getX(), floor.getPos().getY()+floor.getHeight()));
					if(move.getJump()) {
					activePlayer.setYvel(20);
				}
					//System.out.println("test");
				switch(move.getDirection()) {
					case "Left" : if (wallDistL(activePlayer)<2){
						activePlayer.setPos
						(new Point2D.Double(activePlayer.getPos().getX()-wallDistL(activePlayer),
						activePlayer.getPos().getY()));
					} else {
						activePlayer.setPos
						(new Point2D.Double(activePlayer.getPos().getX()-2,activePlayer.getPos().getY()));
					}
					case "Right": if (wallDistR(activePlayer)<2){
						activePlayer.setPos
						(new Point2D.Double(activePlayer.getPos().getX()+wallDistL(activePlayer),
						activePlayer.getPos().getY()));
					} else {
						activePlayer.setPos
						(new Point2D.Double(activePlayer.getPos().getX()+2,activePlayer.getPos().getY()));
					}
					case "None" : //do nothing
					default     : System.out.println("Physics engine has detected an invalid move string.");
				}
			}
			else {
				switch(move.getDirection()) {
				case "Left" : if (wallDistL(activePlayer)<2){
					activePlayer.setPos
					(new Point2D.Double(activePlayer.getPos().getX()-wallDistL(activePlayer),
					activePlayer.getPos().getY()));
				} else {
					activePlayer.setPos
					(new Point2D.Double(activePlayer.getPos().getX()-2,activePlayer.getPos().getY()));
				}
				case "Right": if (wallDistR(activePlayer)<2){
					activePlayer.setPos
					(new Point2D.Double(activePlayer.getPos().getX()+wallDistL(activePlayer),
					activePlayer.getPos().getY()));
				} else {
					activePlayer.setPos
					(new Point2D.Double(activePlayer.getPos().getX()+2,activePlayer.getPos().getY()));
				}
				case "None" : //do nothing
				default     : System.out.println("Physics engine has detected an invalid move string.");
				}
				activePlayer.setPos(new Point2D.Double(activePlayer.getPos().getX(), 
						activePlayer.getPos().getY()+activePlayer.getYvel()));
				activePlayer.setYvel(activePlayer.getYvel()-activePlayer.getGrav());
			}
		}
	}
	
	
	/**
	 * Used on the client-side, receiving an update string from the server.
	 * @param update The update string.
	 */
	//This method wont even be needed anymore
	public void update(Object update) {
//		System.out.println(update);
//		String[] updateA = update.split(" ");
//		Square active = (Square)objects.get(Integer.parseInt(updateA[0]));
//		Point2D.Double xy = new Point2D.Double(Double.parseDouble(updateA[1]),Double.parseDouble(updateA[2]));
//		active.setPoint(xy);
//		objects.remove(Integer.parseInt(updateA[0]));
//		objects.add(Integer.parseInt(updateA[0]), active);
	}
	
	/**
	 * Used on the server-side, receiving an update string that is from the inputs of the player.
	 * @param inputs
	 */
	//public void input(Move input){
	public void input(String input) {
		if(input.contains("Pressed")){
			Square active = (Square)getActivePlayer();
			String inputKey = input.substring(8,9);
			//System.out.println(inputKey);
			String ret = null;
			Move mv;
			
			switch(inputKey){
			case "W" : //jump?
				break;
			case "A" : mv = new Move(active.getColour(),active.getSquareID(),"Left",false);
						//System.out.println("Hey left sorta works");
						updateFrame(mv);
						q.add(objects);
			case "S" : //duck?
				break;
			case "D" : mv = new Move(active.getColour(),active.getSquareID(),"Right",false);
						updateFrame(mv);
						q.add(objects);
				break;
			}
		}
	}
	
	/**
	 * Used on the server-side to send an update to all the client for how their boards should look.
	 * @return The update sent.
	 * @throws InterruptedException 
	 */
	public ArrayList<PhysObject> getUpdate() throws InterruptedException {
		return q.take();
	}
	
	public ArrayList<PhysObject> getObjects(){
		return objects;
	}
	
	public void  setObjects(ArrayList<PhysObject> obj){
		this.objects = obj;
	}
}
