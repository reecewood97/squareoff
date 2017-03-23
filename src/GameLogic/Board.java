/**
 * The Board class represents the state of the game and
 * contains methods for entering moves via an input string.
 */

package GameLogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import Audio.Audio;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.Math;

//1. Missile
//2. Dont use any weapon and instead fling your square across the map.

//Formula for adding more weapons. All these things need to be implemented.
//Weapons are no longer all going to extend Weapon, sorry for the confusion.
//1. Create new class. Modify all necessary methods from PhysObject which would be different.
//2. Modify collision methods to detect the defining string of the weapon
//3. update freeSim arrayList copy cases
//4. update updateFrame weaponMove cases

public class Board {
	// Keep track of the current player
	private int player;
	private int squareID;
	private Square activePlayer;
	// Keep track of state of the board
	//private String map;
	private ArrayList<PhysObject> objects;
	private ArrayList<PhysObject> explosions;
	private int winner = -1;
	private boolean freeState;
	private TurnMaster turn;
	private boolean weaponsopen = false;
	private String weaponType = "ExplodeOnImpact";
	private int time = 0;
	private TurnServant servant = new TurnServant(this);
	private boolean playing = false;
	// Miscellaneous
	private ArrayBlockingQueue<ArrayList<PhysObject>> q;
	private String[] players = new String[4];
	private int numberOfPlayers = 0;
	private int sent = 0;
	private Audio audio = new Audio();
	private double XtravelDist = 4;
	private boolean turnChangedFlag = true;
	// Debug
	private final boolean debug = false;
	private final boolean debugL = false;

	/*
	 * public static void main(String[] args) { //For testing purposes only
	 * Board board = new Board("map1"); Scanner scanner = new
	 * Scanner(System.in); while(true){ String input = scanner.nextLine(); if
	 * (input.equals("l")){ board.updateFrame(new Move(0,0,"Left",false)); } if
	 * (input.equals("r")){ board.updateFrame(new Move(0,0,"Right",false)); } if
	 * (input.equals("u")){ board.updateFrame(new Move(0,0,"None",true)); } if
	 * (input.equals("w")){ board.updateFrame(new WeaponMove("ExplodeOnImpact",
	 * new Point2D.Double(activePlayer.getPos().getX(),
	 * activePlayer.getPos().getY()+5),0,0)); } } }
	 */

	/**
	 * Constructor makes a default board with four players
	 * 
	 * @param map
	 *            Which map is being used
	 */
	public Board(String map){

		this.objects = new ArrayList<PhysObject>();
		this.explosions = new ArrayList<PhysObject>();
		this.freeState = false;
		this.q = new ArrayBlockingQueue<ArrayList<PhysObject>>(10); 
		this.winner = -1;
		//this.map = map;
		this.turn = new TurnMaster(this);
		//this.q = new ArrayBlockingQueue<String>(100); //This handles the moves that need to be sent to clients.
	
		
		if(map.equals("map1")){
			//Initialise the placements of the 4 teams.
			Point2D.Double redpos = new Point2D.Double(100, 150);
			PhysObject red = new Square(1 ,0, 0, redpos);
			((Square)red).setActivePlayer(true);
			Point2D.Double blupos = new Point2D.Double(300, 150);
			PhysObject blu = new Square(2 ,0, 0, blupos);
			Point2D.Double yelpos = new Point2D.Double(400, 150);
			PhysObject yel = new Square(3 ,0, 0, yelpos);
			Point2D.Double grnpos = new Point2D.Double(500, 150);
			PhysObject grn = new Square(4 ,0, 0, grnpos);
			objects.add(red);
			objects.add(blu);
			objects.add(yel);
			objects.add(grn);
			
			//level1
			for(int i = 100; i < 700; i+=40) {
				PhysObject block = new TerrainBlock(2, 2,new Point2D.Double(i,120), true);
				objects.add(block);
			}
			//level2
			for(int i = 100; i < 700; i+=120){
				PhysObject block = new TerrainBlock(1, 2,new Point2D.Double(i,195), true);
				objects.add(block);
			}
			//level3
			for(int i = 150; i < 700; i+=160){
				PhysObject block = new TerrainBlock(1,1,new Point2D.Double(i,270), true);
				objects.add(block);
			}
	
	
			for (int i = 50; i < 700; i += 200) {
				PhysObject block = new TerrainBlock(2, 2, new Point2D.Double(i, 345), true);
				objects.add(block);
			}
		}
		else if(map.equals("map2")){
			
			Point2D.Double redpos = new Point2D.Double(255, 390);
			PhysObject red = new Square(1 ,0, 0, redpos);
			((Square)red).setActivePlayer(true);
			Point2D.Double blupos = new Point2D.Double(555, 390);
			PhysObject blu = new Square(2 ,0, 0, blupos);
			Point2D.Double yelpos = new Point2D.Double(415, 300);
			PhysObject yel = new Square(3 ,0, 0, yelpos);
			Point2D.Double grnpos = new Point2D.Double(415, 150);
			PhysObject grn = new Square(4 ,0, 0, grnpos);
			objects.add(red);
			objects.add(blu);
			objects.add(yel);
			objects.add(grn);
			
			//level1
			for(int i = 100; i < 700; i+=40) {
				PhysObject block = new TerrainBlock(1, 1, new Point2D.Double(i,120), true);
				objects.add(block);
			}
			
			//Bottom left bucket
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(200, 210), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(210, 180), true));
			objects.add(new TerrainBlock(2, 2, new Point2D.Double(250, 180), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(290, 180), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(300, 210), true));
			
			//Bottom right bucket
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(500, 210), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(590, 180), true));
			objects.add(new TerrainBlock(2, 2, new Point2D.Double(550, 180), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(510, 180), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(600, 210), true));
			
			//Middle left bucket
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(80, 300), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(90, 270), true));
			objects.add(new TerrainBlock(2, 2, new Point2D.Double(130, 270), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(170, 270), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(180, 300), true));
			
			//Middle middle bucket
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(360, 300), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(370, 270), true));
			objects.add(new TerrainBlock(2, 2, new Point2D.Double(410, 270), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(450, 270), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(460, 300), true));
			
			//Middle right bucket
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(610, 300), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(620, 270), true));
			objects.add(new TerrainBlock(2, 2, new Point2D.Double(660, 270), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(700, 270), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(710, 300), true));
			
			//Top left bucket
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(200, 390), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(210, 360), true));
			objects.add(new TerrainBlock(2, 2, new Point2D.Double(250, 360), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(290, 360), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(300, 390), true));
			
			//Top right bucket
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(500, 390), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(590, 360), true));
			objects.add(new TerrainBlock(2, 2, new Point2D.Double(550, 360), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(510, 360), true));
			objects.add(new TerrainBlock(1, 1, new Point2D.Double(600, 390), true));
		}/* else {
			Point2D.Double redpos = new Point2D.Double(255, 390);
			PhysObject red = new Square(1 ,0, 0, redpos);
			((Square)red).setActivePlayer(true);
			Point2D.Double blupos = new Point2D.Double(555, 390);
			PhysObject blu = new Square(2 ,0, 0, blupos);
			Point2D.Double yelpos = new Point2D.Double(415, 300);
			PhysObject yel = new Square(3 ,0, 0, yelpos);
			Point2D.Double grnpos = new Point2D.Double(415, 150);
			PhysObject grn = new Square(4 ,0, 0, grnpos);
			objects.add(red);
			objects.add(blu);
			objects.add(yel);
			objects.add(grn);
		}*/
		
		
		Point2D.Double weaponpos = new Point2D.Double(150, 200);
		PhysObject weapon = new ExplodeOnImpact(weaponpos, 0, 0, false);
		objects.add(weapon);

		Point2D.Double explosionpos = new Point2D.Double(150, 150);
		PhysObject explosion = new Explosion(explosionpos);
		explosion.setInUse(false);
		explosions.add(explosion);

		PhysObject targetline = new TargetLine();
		objects.add(targetline);

		this.player = 0;
		this.squareID = 0;
		
		activePlayer = (Square)objects.get(0);
		
	}

	public void setFreeState(boolean free) {
		freeState = free;
	}
	
	public boolean getFreeState(){
		return freeState;
	}
	
	public void setWinner(int player){
		
		this.winner = player;

	}

	public int getWinner() {
		return winner;
	}

	public void notifyQuit() {

	}

	private void setActivePlayer(int newPlayer, int newID) {

		// System.out.println("NEW PLAYER" + newPlayer +
		// "************************");

		this.player = newPlayer;
		this.squareID = newID;

		int x = player + squareID;
		activePlayer = (Square) getSquares().get(x);
		for (PhysObject phys : getSquares()) {
			((Square)phys).setActivePlayer(false);
		}
		activePlayer.setActivePlayer(true);
		
		objects.remove(x);
		objects.add(x, activePlayer);

//		for (PhysObject square : getSquares()) {
//
//			System.out.println(((Square) square).getActivePlayer());
//			
//		}
//		System.out.println();
	}

	public PhysObject getActivePlayer() {

		// System.out.println("in getactive player");
		return activePlayer;
	}

	/**
	 * Used by the clients to get the active player, they have less access than
	 * the server and hence have a more complex method.
	 * 
	 * @return the active player.
	 */
	public synchronized PhysObject getActiveBoard() {
		
		PhysObject ret = null;
		for (PhysObject square : getSquares()) {

			//System.out.println(((Square) square).getActivePlayer());
			if (((Square) square).getActivePlayer()) {

				//System.out.println("player id " + ((Square) square).getPlayerID());

				ret = square;

			}
		}
		return ret;
	}

	public boolean getPlaying() {
		return this.playing;
	}

	/**
	 * @return All weapons in the objects list
	 */
	public synchronized ArrayList<PhysObject> getWeapons() {
		ArrayList<PhysObject> weapons = new ArrayList<PhysObject>();
		for (PhysObject obj : objects) {
			if (obj.getName().startsWith("Weapon")) {
				weapons.add(obj);
			}
		}
		return weapons;
	}

	/**
	 * @return The target line from the objects list
	 */
	public synchronized ArrayList<PhysObject> getTargetLine() {
		ArrayList<PhysObject> target = new ArrayList<PhysObject>();
		for (PhysObject obj : objects) {
			if (obj.getName().startsWith("Target")) {
				target.add(obj);
			}
		}
		return target;
	}

	/**
	 * @return All impact grenades in the objects list
	 */
	public synchronized ArrayList<PhysObject> getExplodeOnImpact() {
		ArrayList<PhysObject> weapons = new ArrayList<PhysObject>();
		for (PhysObject obj : objects) {
			if (obj.getName().endsWith("ExplodeOnImpact")) {
				weapons.add(obj);
			}
		}
		return weapons;
	}

	/**
	 * @return All timed grenades in the objects list
	 */
	public synchronized ArrayList<PhysObject> getTimedGrenade() {
		ArrayList<PhysObject> weapons = new ArrayList<PhysObject>();
		for (PhysObject obj : objects) {
			if (obj.getName().endsWith("TimedGrenade")) {
				weapons.add(obj);
			}
		}
		return weapons;
	}

	/**
	 * @return All missiles in the objects list
	 */
	public synchronized ArrayList<PhysObject> getMissile() {
		ArrayList<PhysObject> weapons = new ArrayList<PhysObject>();
		for (PhysObject obj : objects) {
			if (obj.getName().endsWith("Missile")) {
				weapons.add(obj);
			}
		}
		return weapons;
	}

	/**
	 * @return All blocks in the objects list
	 */
	public synchronized ArrayList<PhysObject> getBlocks() {

		ArrayList<PhysObject> blocks = new ArrayList<PhysObject>();
		for (PhysObject obj : objects) {

			if (obj.getName().equals("TerrainBlock")) {

				blocks.add(obj);
			}
		}
		return blocks;
	}

	/**
	 * @return All squares in the objects list
	 */
	public synchronized ArrayList<PhysObject> getSquares() {

		ArrayList<PhysObject> squares = new ArrayList<PhysObject>();
		for (PhysObject obj : objects) {

			if (obj.getName().equals("Square")) {

				squares.add(obj);
			}
		}

		return squares;
	}

	/**
	 * @return All explosions in the objects list
	 */
	public synchronized ArrayList<PhysObject> getExplosion() {

		ArrayList<PhysObject> exp = new ArrayList<PhysObject>();
		for (PhysObject obj : objects) {

			if (obj.getName().equals("Explosion")) {

				exp.add(obj);
			}
		}

		return exp;
	}

	/**
	 * @return Distance to the closest block to the left of the square
	 */
	private double wallDistL(Square guy) {
		Iterator<PhysObject> it = getBlocks().iterator();
		while (it.hasNext()) {
			PhysObject nextblock = it.next();
			if (wallDistLOne(guy, nextblock) <= XtravelDist) {
				return wallDistLOne(guy, nextblock);
			}
		}
		return 100; // Out of range
	}

	private double wallDistLOne(Square guy, PhysObject block) {
		if (!block.getInUse()) {
			return 100;
		}
		double guyleft = guy.getPos().getX();
		double guydown = guy.getPos().getY();
		double guyup = guy.getPos().getY() + guy.getHeight();
		double blockright = block.getPos().getX() + block.getWidth();
		double blockdown = block.getPos().getY();
		double blockup = block.getPos().getY() + block.getHeight();

		if ((blockdown < guyup && guyup < blockup) || (blockdown < guydown && guydown < blockup) || (guyup == blockup)
				|| (guydown == blockdown)) {
			if (Math.abs(blockright - guyleft) <= XtravelDist) {
				return Math.abs(blockright - guyleft);
			} else {
				return 100; // Out of range
			}
		} else {
			return 100; // Out of range
		}
	}

	/**
	 * @return Distance to the closest block to the right of the square
	 */
	private double wallDistR(Square guy) {
		Iterator<PhysObject> it = getBlocks().iterator();
		while (it.hasNext()) {
			PhysObject nextblock = it.next();
			if (wallDistROne(guy, nextblock) <= XtravelDist) {
				return wallDistROne(guy, nextblock);
			}
		}
		return 100; // Out of range
	}

	private double wallDistROne(Square guy, PhysObject block) {
		if (!block.getInUse()) {
			return 100;
		}
		double guyright = guy.getPos().getX() + guy.getWidth();
		double guyup = guy.getPos().getY() + guy.getHeight();
		double guydown = guy.getPos().getY();
		double blockleft = block.getPos().getX();
		double blockup = block.getPos().getY() + block.getHeight();
		double blockdown = block.getPos().getY();

		if ((blockdown < guyup && guyup < blockup) || (blockdown < guydown && guydown < blockup) || (guyup == blockup)
				|| (guydown == blockdown)) {
			if (Math.abs(blockleft - guyright) <= XtravelDist) {
				return Math.abs(blockleft - guyright);
			} else {
				return 100; // Out of range
			}
		} else {
			return 100; // Out of range
		}
	}

	/**
	 * @return The closest block to the bottom of the square
	 */
	private PhysObject onFloor(Square guy) {
		Iterator<PhysObject> it = getBlocks().iterator();
		while (it.hasNext()) {
			PhysObject nextblock = it.next();
			if (onFloorOne(guy, nextblock)) {
				return nextblock;
			}
		}
		return null;
	}

	private boolean onFloorOne(Square guy, PhysObject block) {
		if (!block.getInUse()) {
			return false;
		}
		double guyleft = guy.getPos().getX();
		double guyright = guy.getPos().getX() + guy.getWidth();
		double guydown = guy.getPos().getY();
		double blockleft = block.getPos().getX();
		double blockright = block.getPos().getX() + block.getWidth();
		double blockup = block.getPos().getY() + block.getHeight();

		if ((blockleft <= guyleft && guyleft <= blockright) || (blockleft <= guyright && guyright <= blockright)) {
			if ((-1) * Math.abs(guydown - blockup) >= guy.getYvel()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * @return The closest block to the top of the square
	 */
	private PhysObject onCeiling(Square guy) {
		Iterator<PhysObject> it = getBlocks().iterator();
		while (it.hasNext()) {
			PhysObject nextblock = it.next();
			if (onCeilingOne(guy, nextblock)) {
				return nextblock;
			}
		}
		return null; // Out of range
	}

	private boolean onCeilingOne(Square guy, PhysObject block) {
		if (!block.getInUse()) {
			return false;
		}
		double guyright = guy.getPos().getX() + guy.getWidth();
		double guyleft = guy.getPos().getX();
		double guyup = guy.getPos().getY() + guy.getHeight();
		double blockleft = block.getPos().getX();
		double blockright = block.getPos().getX() + block.getWidth();
		double blockdown = block.getPos().getY();

		if ((blockleft < guyleft && guyleft < blockright) || (blockleft < guyright && guyright < blockright)) {
			if ((Math.abs(blockdown - guyup)) < activePlayer.getYvel()) {
				return true;
			} else {
				return false; // Out of range
			}
		} else {
			return false; // Out of range
		}
	}

	/**
	 * Method for checking if two objects are colliding
	 * 
	 * @param obj1
	 *            The first physObject
	 * @param obj2
	 *            The second physObject
	 * @return True if the two objects are currently colliding
	 */
	private boolean collides(PhysObject obj1, PhysObject obj2) {
		if ((obj1.getSolid() == obj2.getSolid()) || (!obj1.getInUse()) || (!obj2.getInUse())) {
			return false;
		}
		if (obj1.getName().equals("TerrainBlock")) {
			if (obj2.getName().endsWith("ExplodeOnImpact")/*|| obj2.getName().endsWith(
												 * "TimedGrenade")*/) { // All circular objects
				Ellipse2D.Double circle = new Ellipse2D.Double(obj2.getPos().getX(),
						obj2.getPos().getY() + obj2.getHeight(), obj2.getWidth(), obj2.getHeight());
				if (circle.intersects(obj1.getPos().getX(),
						obj1.getPos().getY()/* +obj1.getHeight() */, obj1.getWidth(), obj1.getHeight())) {
					System.out.println("Circular object collision detected between wep at " + obj2.getPos()
							+ "with height " + obj2.getHeight() + "and width " + obj2.getWidth() + " and block at "
							+ obj1.getPos() + " with height " + obj1.getHeight() + " and width " + obj1.getWidth());
					return true;
				} else {
					return false;
				}
			} else {
				//return obj1.rectIntersect(obj2);
				Rectangle2D.Double rect = new Rectangle2D.Double(obj2.getPos().getX(),
						obj2.getPos().getY()+ obj2.getHeight(), obj2.getWidth(), obj2.getHeight());
				return rect.intersects(obj1.getPos().getX(),
						obj1.getPos().getY() + 10, obj1.getWidth(), obj1.getHeight());
			}
		} else {
			/*if (obj1.getName().endsWith("ExplodeOnImpact")/*
												 * || obj1.getName().endsWith(
												 * "TimedGrenade")
												 ) { // All circular objects
				Ellipse2D.Double circle = new Ellipse2D.Double(obj1.getPos().getX(),
						obj1.getPos().getY() + obj1.getHeight(), obj1.getWidth(), obj1.getHeight());
				if (circle.intersects(obj2.getPos().getX(),
						obj2.getPos().getY()/* +obj2.getHeight() , obj2.getWidth(), obj2.getHeight())) {
					System.out.println("Circular object collision detected between wep at " + obj1.getPos()
							+ "with height " + obj1.getHeight() + "and width " + obj1.getWidth() + " and block at "
							+ obj2.getPos() + " with height " + obj2.getHeight() + " and width " + obj2.getWidth());
					return true;
				} else {
					return false;
				}
			} else {*/
				//return obj1.rectIntersect(obj2);
				Rectangle2D.Double rect = new Rectangle2D.Double(obj1.getPos().getX(),
						obj1.getPos().getY() + obj1.getHeight(), obj1.getWidth(), obj1.getHeight());
				return rect.intersects(obj2.getPos().getX(),
						obj2.getPos().getY() + obj2.getHeight(), obj2.getWidth(), obj2.getHeight());
			
		}
	}

	/**
	 * Creates an explosion at a given point
	 * 
	 * @param things
	 *            The list of physObjects being operated on
	 * @param x
	 *            The X co-ordinate of the middle of the explosion
	 * @param y
	 *            The Y co-ordinate of the middle of the explosion
	 * @param power
	 *            The relative force with which squares will be pushed away
	 * @param size
	 *            The area of effect of the explosion
	 * @param damage
	 *            The damage done to any blocks in range
	 */
	private void createExplosion(ArrayList<PhysObject> things, double x, double y, double power, double size,
			int damage) {

		System.out.println("CREATING EXPLOSION***************************");

		// for i from x to y, all squares push away, all blocks damage
		double i = (2 * size / 5);
		Ellipse2D.Double circle = new Ellipse2D.Double(x - (i / 2), y + (i / 2), 2 * i, 2 * i);
		for (PhysObject thing : things) {
			if (thing.getName().equals("TerrainBlock")) {
				if (circle.intersects(thing.getPos().getX(), thing.getPos().getY() + thing.getHeight(),
						thing.getWidth(), thing.getHeight())) {
					((TerrainBlock) thing).damage(damage);
				}
			}
		}
		i = size;
		circle = new Ellipse2D.Double(x-(i/2), y+(i/2), 2*i, 2*i);
		for(PhysObject thing : things){
			if(thing.getName().equals("Square")){
				if(circle.intersects(thing.getPos().getX(),
				thing.getPos().getY()+thing.getHeight(),thing.getWidth(),thing.getHeight())){
					Square square = ((Square)thing);
					square.setXvel(square.getXvel()+(power/((thing.getPos().getX()+thing.getWidth()/2)-x)));
					square.setYvel(square.getYvel()+(power/((thing.getPos().getY()+thing.getHeight()/2)-y)));
				}
			}
		}
		for (int num = 0; num < things.size(); num++) {
			if (things.get(num).getName().equals("Explosion")) {
				things.remove(num);
			}
		}
		things.add(new Explosion(new Point2D.Double(x, y)));

		/*
		 * System.out.println("here ***************************");
		 * 
		 * Explosion exp = new Explosion(new Point2D.Double(x, y));
		 * System.out.println("INUSE?" + exp.getInUse());
		 * 
		 * System.out.println("GETTING FROM ARRAYLIST" +
		 * explosions.get(0).getInUse());
		 */
	}

	/**
	 * Resolves a collision between two objects; will always be a free moving
	 * object and a block
	 * 
	 * @param things
	 *            The list of physObjects being operated on
	 * @param thing
	 *            The object that hit the block
	 * @param block
	 *            The block that was collided with
	 */
	private void resolveCollision(ArrayList<PhysObject> things, PhysObject thing, PhysObject block) {
		if (thing.getName().endsWith("ExplodeOnImpact") || thing.getName().endsWith("Missile")) {
			if (debug)
				System.out.println("Resolving " + thing.getName() + "collision between thing at: " + thing.getPos()
						+ ", and block at: " + block.getPos());
			thing.setInUse(false);
			createExplosion(things, thing.getPos().getX() + (thing.getWidth() / 2),
					thing.getPos().getY() + (thing.getHeight() / 2), 150, 50, 1);
		}/* else if (thing.getName().endsWith("TimedGrenadeDONT USE")) { // Collisions
																		// for
																		// circular
																		// objects
			thing.undoUpdate();
			if (thing.getPos().getX() + thing.getWidth() <= block.getPos().getX()) { // on
																						// the
																						// left
				thing.setXvel((-0.3) * thing.getXvel());
				if (thing.getXvel() == 0) {
					thing.update();
				}
			} else if (thing.getPos().getX() >= block.getPos().getX() + block.getWidth()) { // on
																							// the
																							// right
				thing.setXvel((-0.3) * thing.getXvel());
				if (thing.getXvel() == 0) {
					thing.update();
				}
			} else if (thing.getPos().getY() >= block.getPos().getY() + block.getHeight()) { // on
																								// top
				if (Math.abs(thing.getXvel()) <= 2) {
					thing.setXvel(0);
				} else {
					thing.setXvel(0.9 * thing.getXvel());
				}
				if (thing.getYvel() >= (-2)) {
					thing.setYvel(0);
					thing.setPos(new Point2D.Double(thing.getPos().getX(), block.getPos().getY() + block.getHeight()));
				} else {
					thing.setYvel((-0.3) * thing.getYvel());
				}
			} else if (thing.getPos().getY() + thing.getHeight() <= block.getPos().getY()) { // below
				thing.setYvel((-0.3) * thing.getYvel());
			} else {
				thing.setYvel((-0.4) * thing.getYvel());
				thing.setXvel((-0.4) * thing.getXvel());
			}
		}*/ else { // Collisions for squares
			thing.undoUpdate();
			if (thing.getPos().getX() + thing.getWidth() <= block.getPos().getX()) { // on the left
				System.out.println("Collided on the left");
				thing.setXvel((-0.3) * thing.getXvel());
				if (thing.getXvel() == 0) {
					System.out.println("xvel zero");
					thing.update();
				}
			}
			if (block.getPos().getX() + block.getWidth() <= thing.getPos().getX()) { // on the right
				System.out.println("Collided on the right");
				thing.setXvel((-0.3) * thing.getXvel());
				if (thing.getXvel() == 0) {
					System.out.println("xvel zero");
					thing.update();
				}
			}
			if (thing.getPos().getY() >= block.getPos().getY() + block.getHeight()) { // on top
				if (Math.abs(thing.getXvel()) <= 2) {
					thing.setXvel(0);
				} else {
					thing.setXvel(0.6 * thing.getXvel());
				}
				if (thing.getYvel() >= (-2)) {
					thing.setYvel(0);
					thing.setPos(new Point2D.Double(thing.getPos().getX(), block.getPos().getY() + block.getHeight()));
				} else {
					thing.setYvel((-0.3) * thing.getYvel());
				}
			}
			if (thing.getPos().getY() + thing.getHeight() <= block.getPos().getY()) { // below
				System.out.println("Collided on the bottom");
				thing.setYvel((-0.3) * thing.getYvel());
			}
		}
	}

	/**
	 * For when no player is in control and things are bouncing about. This
	 * method simulates a frame, detects collisions and resolves them. If
	 * nothing has changed since the last frame, stop free state and increment
	 * the turn.
	 */
	private void freeSim() {
		
		// This is going to be relatively quite slow. Perhaps it can be improved
		// later.
		ArrayList<PhysObject> objs = new ArrayList<PhysObject>();
		for (int i = 0; i < objects.size(); i++) {
			switch (objects.get(i).getName()) {
			case "TerrainBlock":
				objs.add(new TerrainBlock((TerrainBlock) objects.get(i)));
				break;
			case "Square":
				objs.add(new Square((Square) objects.get(i)));
				break;
			case "WeaponExplodeOnImpact":
				objs.add(new ExplodeOnImpact((ExplodeOnImpact) objects.get(i)));
				break;
			case "WeaponTimedGrenade":
				objs.add(new TimedGrenade((TimedGrenade) objects.get(i)));
				break;
			case "WeaponMissile":
				objs.add(new Missile((Missile) objects.get(i)));
				break;
			case "TargetLine":
				objs.add(new TargetLine((TargetLine) objects.get(i)));
				break;
			case "Explosion":
				objs.add(new Explosion((Explosion) objects.get(i)));
				break;
			default:
				System.out.println("error copying arraylists in freeSim: " + objects.get(i).getName());
				break;
			}
		}
		
		// Explode objects on a timer if they have run out
		TimedGrenade boomer = null;
		boolean boom = false;
		for (PhysObject obj : objs) {
			if (obj.getName().equals("WeaponTimedGrenade")) {
				TimedGrenade grenade = (TimedGrenade) obj;
				if ((grenade.getFrames() <= 0) && (grenade.getInUse() == true)) {
					boom = true;
					boomer = grenade;
				}
			}
		}
		if (boom) {
			boomer.setInUse(false);
			createExplosion(objs, boomer.getPos().getX() + (boomer.getWidth() / 2),
					boomer.getPos().getY() + (boomer.getHeight() / 2), 150, 50, 1);
		}
		
		for (PhysObject obj : objs) {
			obj.update();
		}

		ArrayList<Collision> list = new ArrayList<Collision>();
		for (int i = 0; i < objs.size(); i++) {
			for (int j = i + 1; j < objs.size(); j++) {
				if (collides(objs.get(i), objs.get(j))) {
					if (objs.get(j).getName().equals("TerrainBlock")) {
						Collision collis = new Collision(objs.get(i), objs.indexOf(objs.get(i)), objs.get(j));
						if (!list.contains(collis)) {
							list.add(collis);
						}
					} else {
						Collision collis = new Collision(objs.get(j), objs.indexOf(objs.get(j)), objs.get(i));
						if (!list.contains(collis)) {
							list.add(collis);
						}
					}
				}
			}
		}

		for (Collision collision : list) {
			resolveCollision(objs, collision.getThing(), collision.getBlock());
		}
		
		for (PhysObject object : objs) {
			if (object.getInUse()) {
				if (((object.getPos().getY() < 100) || (object.getPos().getX() < (-40))
						|| (object.getPos().getX() > 850) || (object.getPos().getY() > 1500))) {
					
					object.setInUse(false);
						
					if ((object.getName().equals("Square"))) {

						((Square) object).setDead();
						 audio.splash();
						 for (PhysObject one : objs) {
							 	if(one.getName().equals("Square"))
							 		System.out.println(one.getInUse());
							}
						 
						
						if (winner != 5) {
							//System.out.println("checking for winner");
							int won = checkForWinner(objs);
							if (won != -1) {
								if (debug)
									System.out.println("winner?");
								//int won = findPlayer();
								setWinner(won);
								turn.interrupt();
							}
						}
					}
				}
			}
		}

		ArrayList<PhysObject> explos = new ArrayList<PhysObject>();
		for (PhysObject one : objs) {
			if (one.getName().equals("Explosion")) {
				explos.add(one);
			}
		}
		for (PhysObject exp : explos) {

			Explosion explo = (Explosion) exp;

			if (explo.getInUse()) {

				if (explo.getSize() > 20) {

					audio.explosion();
				}

				if (explo.getSize() > 60) {

					explo.setInUse(false);
				} else {

					explo.setSize((int) explo.getSize() + 6);
				}

			}

		}

		boolean same = true;
		for (int i = 0; i < objects.size(); i++) {
			if (!objs.get(i).equals(objects.get(i))
					|| (objs.get(i).getName().equals("WeaponTimedGrenade") && objs.get(i).getInUse())
					|| (objs.get(i).getName().equals("Explosion") && objs.get(i).getInUse())) {
				same = false;
			}
		}

		if (same) {
			if (debug)
				System.out.println("FreeState exited due to no movement");
			freeState = false;
			//incrementTurn();
		}
		turn.resetTimer();
		objects = objs;
		if(same) {
			incrementTurn();
		}
		
	}

	/**
	 * Takes a move and updates one frame
	 * 
	 * @param move
	 *            The move to be executed, does not matter if in free state.
	 */
	private synchronized void updateFrame(Move move) {
		if (freeState) { // If the engine is in free-physics mode then the move
						 // is irrelevant,
			freeSim();   // just simulate another frame.
		} else if (move.getWeaponMove()) {
			WeaponMove wepMove = (WeaponMove) move;
			System.out.println("Weapon spawning at: " + wepMove.getPos());
			PhysObject wep = null;
			switch (weaponType) {
			case "ExplodeOnImpact":
				wep = new ExplodeOnImpact(wepMove.getPos(), wepMove.getXvel(), wepMove.getYvel(), true);
				break;
			case "TimedGrenade":
				wep = new TimedGrenade(wepMove.getPos(), wepMove.getXvel(), wepMove.getYvel(), true);
				break;
			case "Missile":
				wep = new Missile(wepMove.getPos(), wepMove.getXvel(), wepMove.getYvel(), true);
				break;
			case "LongJump":
				wep = getWeapons().get(0);
				activePlayer.setYvel(wepMove.getYvel());
				activePlayer.setXvel(wepMove.getXvel());
				break;
			default:
				System.out.println("Weapon move parsing error");
				break;
			}
			freeState = true;
			for (int i = 0; i < objects.size(); i++) {
				if (objects.get(i).getName().startsWith("Weapon")) {
					objects.remove(i);
				}
			}
			objects.add(wep);
		} else { // Not in freeState, change active player depending on move
			PhysObject floor = onFloor(activePlayer);
			if (floor != null) { // if the player is standing on a block
				activePlayer.setYvel(0);
				activePlayer.setPos(new Point2D.Double
				  (activePlayer.getPos().getX(), floor.getPos().getY()+floor.getHeight()));
				if(move.getJump()) {
					activePlayer.setYvel(10);
				}
				if (move.getDirection().equals("Left")) {
					if (wallDistL(activePlayer) < XtravelDist) {
						activePlayer.setPos(new Point2D.Double(activePlayer.getPos().getX() - wallDistL(activePlayer),
								activePlayer.getPos().getY()));
					} else {
						activePlayer.setPos(new Point2D.Double(activePlayer.getPos().getX() - XtravelDist,
								activePlayer.getPos().getY()));
					}
				} else if (move.getDirection().equals("Right")) {
					if (wallDistR(activePlayer) < XtravelDist) {
						activePlayer.setPos(new Point2D.Double(activePlayer.getPos().getX() + wallDistR(activePlayer),
								activePlayer.getPos().getY()));
					} else {
						activePlayer.setPos(new Point2D.Double(activePlayer.getPos().getX() + XtravelDist,
								activePlayer.getPos().getY()));
					}
				} else if (move.getDirection().equals("None")) {
					// Don't move the square
				}
			} else { //Player not standing on a block
				
				if(move.getDirection().equals("Left")){
					activePlayer.setXvel((-1)*XtravelDist);
					if (wallDistL(activePlayer)<XtravelDist){
						activePlayer.setPos
						(new Point2D.Double(activePlayer.getPos().getX()-wallDistL(activePlayer),
						activePlayer.getPos().getY()));
					} else {
						activePlayer.setPos(new Point2D.Double(activePlayer.getPos().getX() - XtravelDist,
								activePlayer.getPos().getY()));
					}
				} else if (move.getDirection().equals("Right")) {
					activePlayer.setXvel(XtravelDist);
					if (wallDistR(activePlayer) < XtravelDist) {
						activePlayer.setPos(new Point2D.Double(activePlayer.getPos().getX() + wallDistR(activePlayer),
								activePlayer.getPos().getY()));
					} else {
						activePlayer.setPos(new Point2D.Double(activePlayer.getPos().getX() + XtravelDist,
								activePlayer.getPos().getY()));
					}
				} else if (move.getDirection().equals("None")) {
					activePlayer.setXvel(0);
					// Don't move the square
				}
				activePlayer.setYvel(activePlayer.getYvel()-activePlayer.getGrav());
			}

			PhysObject ceiling = onCeiling(activePlayer);
			if (ceiling != null) { // If they are hitting their head
				activePlayer.setPos(new Point2D.Double(activePlayer.getPos().getX(),
						ceiling.getPos().getY() - activePlayer.getHeight()));
				activePlayer.setYvel(0);
			} else {
				activePlayer.setPos(new Point2D.Double(activePlayer.getPos().getX(),
						activePlayer.getPos().getY() + activePlayer.getYvel()));
			}
			
			//activePlayer.setYvel(activePlayer.getYvel()-activePlayer.getGrav());
			
			if((activePlayer.getPos().getY() < 100) && activePlayer.getAlive()){
				
				activePlayer.setDead();
				audio.splash();
				if (winner != 5) {
					//System.out.println("Checking for winner at 1");
					int won = checkForWinner(getSquares());
					if (won != -1) {
						if (debug)
							System.out.println("winner?");
						//int won = findPlayer();
						setWinner(won);
						turn.interrupt();
					}
				}
				incrementTurn();
			}
			int x = player + squareID;
			objects.remove(x);
			objects.add(x, activePlayer);
		}
	}

	/**
	 * Used on the server-side, receiving an update string that is from the
	 * inputs of the player.
	 * 
	 * @param inputs
	 *            the formatted string from hangerOn.
	 */
	public synchronized void input(String input) {
		if (getExplosion().size() > 0) {

			if (!input.contains("None")) {

				if (getExplosion().get(0).getInUse()) {

					return;
				}
			}
		}

		Square active = (Square) getActivePlayer();

		// Handles the AI taking shots, they are generally assumed to have
		// better behaviour than players so there aren't checks to make sure
		// it's their turn.
		if (input.contains("AItakesashotx86")) {
			weaponsopen = true;
			String[] AIatk = input.split(",");
			Double xVel = Double.parseDouble(AIatk[0]);
			Double yVel = Double.parseDouble(AIatk[1]);
			// if (AIatk[2].equals(players[player])) {
			WeaponMove wmv = new WeaponMove(weaponType,
					new Point2D.Double(active.getPos().getX(), active.getPos().getY() + 10), xVel, yVel);
			updateFrame(wmv);
			if (q.size() > 1)
				q.remove();
			q.add(objects);
			// }
			// else return;
		}
		
		//Human players are not so well treated
		if(input.length() >= 7 && input.substring(0, 7).equals(("Pressed"))) {
			if(!(input.substring(11, input.length()).equals(players[player]))){
				//System.out.println("oops");
				return;
			}
			String input1 = input.substring(8, 9);
			String input2 = input.substring(9, 10);

			// System.out.println(inputKey);
			if (input1.equals(" "))
				input1 = "None";
			else if (input1.equals("A"))
				input1 = "Left";
			else if (input1.equals("D"))
				input1 = "Right";
			active.setFacing(input1);
			Move mv = new Move(active.getColour(), active.getSquareID(), input1, input2.equals("W"));
			updateFrame(mv);
			if (q.size() > 1)
				q.remove();
			q.add(objects);

		} // The code to handle a mouse click, will only be usable when a weapon
			// is selected.
		else if (input.contains("Clicked")) {

			String[] inputArray = new String[3];
			inputArray = input.split(" ");

			if (!(inputArray[3].equals(players[player]))) {
				return;
			}
			if (weaponsopen) {

				for (PhysObject obj : objects) {

					if (obj.getName().contains("Target")) {

						obj.setInUse(false);
					}
				}

				// int xs = input.indexOf('x');
				// int xe = input.indexOf(',');
				// String xc = input.substring(xs+2, xe);
				String xc = input.split(" ")[1];
				// int ye = input.indexOf(']');
				// String yc = input.substring(xe+3, ye);
				String yc = input.split(" ")[2];
				System.out.println("xc = " + xc + "and yc = " + yc);

				Double x = Double.parseDouble(xc);
				Double y = Double.parseDouble(yc);
				Double x2 = active.getPos().getX() + (active.getWidth() / 2);
				Double y2 = active.getPos().getY() + (active.getHeight() / 2);

				System.out.println("ActivePlayer is at: " + active.getPos());
				System.out.println("Mouse press is at: " + x + ", " + y);

				Double factor; // Need to check if the value is behind the
								// player.
				if (x < x2) {
					factor = -1.0;
				} else {
					factor = 1.0;
				}

				Double yfactor;
				if (y < y2) {
					yfactor = -1.0;
				} else {
					yfactor = 1.0;
				}

				// Use some basic geometry to better work out how a shot is
				// fired
				WeaponMove wmv;
				Double xVel = (Math.abs(x2 - x) / 800) * 30;
				Double yVel = (Math.abs(y2 - y) / 450) * 30;
				
				double myx = xVel;
				if(Math.abs(myx)<2){
					if(myx>=0){
						myx = 2;
					} else {
						myx = -2;
					}
				}
				double myy = yVel;
				myy = myy*0.5;
				if(Math.abs(myy)<2){
					if(myy>=0){
						myy = 2;
					} else {
						myy = -2;
					}
				}

				wmv = new WeaponMove(weaponType,
						new Point2D.Double(active.getPos().getX() + 10, active.getPos().getY() + 10), myx * factor,
						myy * yfactor);
				// wmv = new WeaponMove(weaponType,new
				// Point2D.Double(active.getPos().getX(),
				// active.getPos().getY()+25),5,10);
				System.out.println("wep xvel is: " + xVel);
				System.out.println("wep yvel is: " + yVel);
				System.err.println("weapontype is: " + weaponType);
				getTargetLine().get(0).setInUse(false);
				updateFrame(wmv);
				if (q.size() > 0)
					q.remove();
				q.add(objects);
				weaponsopen = false;
			}
			// else{
			// Create a weapon error check for the server
			// }
		} else if (input.contains("setWep")) {
			String[] wepA = input.split(",");
			// System.out.println(wepA[2] +"contents of array");
			if (!(wepA[2].equals(players[player]))) {
				return;
			}
			if (debugL) {
				System.out.println("Now a weapon in use");
			}
			this.weaponType = wepA[1];
			weaponsopen = true;

		} else if (input.contains("setExp")) {

			for (PhysObject exp : this.getExplosion()) {
				((Explosion) exp).setSize(Integer.parseInt(input.substring(7)));

			}

		} else if (input.contains("setUse")) {

			System.out.println("hello*******");
			System.out.println("size of this.getexp() " + this.getExplosion().size());

			for (PhysObject exp : this.getExplosion()) {

				exp.setInUse(Boolean.parseBoolean(input.substring(7)));

			}

		} else if (input.contains("setTar")) {

			// System.out.println("SET TAR SET TAR!");

			for (PhysObject obj : objects) {

				if (obj.getName().contains("Target")) {

					// System.out.println("HELLO: " + input.substring(7));

					obj.setInUse(Boolean.parseBoolean(input.substring(7)));

					// System.out.println("HELLO2: " + obj.getInUse());
				}
			}
		} else {
			Move mv = new Move(active.getColour(), active.getSquareID(), "None", false);
			updateFrame(mv);
			q.offer(objects);
		}

	}

	/**
	 * Used on the server-side to send an update to all the client for how their
	 * boards should look.
	 * 
	 * @return The update sent.
	 * @throws InterruptedException
	 */
	public ArrayList<PhysObject> getUpdate() throws InterruptedException {
		return q.take();
	}

	/**
	 * Returns the PhysObject arraylist that tracks all moving parts in the game
	 * 
	 * @return the list of objects that are affected by physics.
	 */
	public ArrayList<PhysObject> getObjects() {
		return objects;
	}

	/**
	 * Update the list of objects affected by physics, used on the client
	 * version of the boards so they know what changes the server has made.
	 * 
	 * @param obj
	 *            the updated list of objects
	 */
	public void setObjects(ArrayList<PhysObject> obj) {
		this.objects = obj;
	}

	/**
	 * Check if a weapon is selected.
	 * 
	 * @return true if a weapon has been selected by the *current* player, false
	 *         otherwise.
	 */
	public boolean getWeaponsOpen() {

		return weaponsopen;
	}

	/**
	 * Set the weapons in use or not
	 * 
	 * @param open
	 *            true if the weapon has been selected, false otherwise.
	 */
	public void setWeaponsOpen(boolean open) {

		weaponsopen = open;
	}

	/**
	 * Called when the game actually begins, starts the turn timer and fiddles
	 * with the player list.
	 */
	public void startGame() {

		this.playing = true;

		this.turn = new TurnMaster(this);
		turn.start();

		for (int i = 0; i < 4; i++) {
			if (players[i] == null) {
				players[i] = "";
			}
		}
	}

	/**
	 * Increments the active player
	 */
	private void incrementTurn() {
		weaponsopen = false;
		if (player != 3) {
			player = player + 1;
		} else {
			player = 0;
			// squareID = squareID+1;
		}
		setActivePlayer(player, squareID);
		if (!(activePlayer.getAlive())) {
			incrementTurn();
		} else {

		}
		setTurnFlag(true);
		// servant.interrupt();
		turn.resetTimer();
	}

	/**
	 * Adds the name of a new client connected to the game
	 * 
	 * @param name
	 *            The name of the new client
	 */
	public void addName(String name) {
		players[numberOfPlayers] = name;
		numberOfPlayers++;
	}

	/**
	 * Checks the arraylist of Squares to see if any two living squares have
	 * different players
	 * @param arrayList 
	 * 
	 * @return True if all living squares are played by the same player, false
	 *         otherwise.
	 */
	public int checkForWinner(ArrayList<PhysObject> arrayList) {
		// System.err.println("We are checking when someone dies");
		ArrayList<PhysObject> chickenDinner = new ArrayList<PhysObject>();
		for (PhysObject thing : arrayList){
			if (thing.getName().equals("Square")){
				chickenDinner.add(thing);
			}
		}
		
		int winner = -1;

		for (int i = 0; i < chickenDinner.size(); i++) {
			Square first = ((Square) chickenDinner.get(i));
			// Square second = ((Square)chickenDinner.get(i+1));

			if (first.getAlive()) {
				if ((winner == -1) || winner == first.getPlayerID()) {
					winner = first.getPlayerID();
				} else {
					return -1;
				}
			}
		}

		System.out.println("winner is " + winner);

		if (winner == -1)
			return 5;
		else
			return winner;

	}

//	/**
//	 * Code to select a specific player
//	 * 
//	 * @return the ID of the player.
//	 */
//	private int findPlayer() {
//		ArrayList<PhysObject> chickenDinner = getSquares();
//		for (int i = 0; i < chickenDinner.size(); i++) {
//			Square first = ((Square) chickenDinner.get(i));
//			if (first.getAlive())
//				return ((Square) chickenDinner.get(0)).getPlayerID();
//		}
//		return -1;
//	}

	/**
	 * Removes a name from the array of players (way more difficult than
	 * necessary, because someone used an array instead of an arraylist!).
	 * 
	 * @param name
	 *            The name to be removed.
	 */
	public void removeName(String name) {
		for (int i = 0; i < numberOfPlayers; i++) {
			if (players[i].equals(name)) {
				if (i < numberOfPlayers - 1) {
					String temp = players[i];
					players[i] = players[i + 1];
					players[i + 1] = temp;
				} else {
					players[numberOfPlayers - 1] = "";
					numberOfPlayers--;
				}
			}
		}
	}

	/**
	 * Removes a name without swapping to conform to player order.
	 * 
	 * @param name
	 *            The name to be removed.
	 */
	public void removeNameSimple(String name) {
		for (int i = 0; i < numberOfPlayers; i++) {
			if (players[i].equals(name)) {
				players[i] = "";
				break;
			}
		}
	}

	/**
	 * Sets the name at a specific index of the player list.
	 * 
	 * @param index
	 *            The index.
	 * @param name
	 *            The new name.
	 */
	public void addName(int index, String name) {
		players[index] = name;
	}

	/**
	 * Sets the array of players.
	 * 
	 * @param players
	 *            The new array.
	 */
	public void setPlayers(String[] players) {
		this.players = players;
	}

	/**
	 * Returns and array of the players.
	 * 
	 * @return The players.
	 */
	public String[] getPlayers() {
		return players;
	}

	/**
	 * Converts the array of player names into an arrayList, as it's the
	 * prefered format for the network code
	 * 
	 * @return An arraylist version of the players array
	 */
	public ArrayList<String> getPlayerArray() {
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < players.length; i++) {
			ret.add(players[i]);
		}
		return ret;
	}

	/**
	 * Used in the local game board to allow a local timer to shown to players
	 * to give them an idea of their remaining time.
	 */
	public void startLocalTimer() {
		servant.interrupt();
		this.servant = new TurnServant(this);
		servant.start();

	}

	/**
	 * Used by the turn keeping classes to set the time on the board
	 * 
	 * @param time
	 *            the current amount of time left for the turn.
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * Get the remaining time in the turn
	 * 
	 * @return the remaining turn
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Let the local players know the current turn has been ended early and to
	 * advance their timers.
	 * 
	 * @param set
	 */
	public void setTurnFlag(boolean set) {
		this.turnChangedFlag = set;
	}

	/**
	 * Check the board to see if turns need to be incremented
	 * 
	 * @return True if the turn has ended, false otherwise.
	 */
	public boolean getTurnFlag() {
		return turnChangedFlag;
	}
	
	/**
	 * Returns the number of players who have received the turn over signal to reset their local timers.
	 * @return the number of sent signals
	 */
	public int sent(){
		return sent;
	}
	
	/**
	 * Change the number of players who have sent the reset timer signal
	 * @param x the number of people who have sent the signal
	 */
	public void setSent(int x){
		sent = x;
	}
	
	/**
	 * Checks how many players are not AI
	 * @return ret the number of real players.
	 */
	public int nonAIPlayers(){
		int ret = 0;
		for (int i = 0; i < 4; i++){
			if (!players[i].contains("AI")){
				ret ++;
			}
		}
		return ret;
	}
}
