package GameLogic;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;

import Audio.Audio;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.lang.Math;

public class Board {
	private int player;
	private int squareID;
	private int winner = -1;
	private boolean freeState;
	private final boolean debug = true;
	private boolean weaponsopen = false;
	private ArrayList<PhysObject> objects;
	private ArrayBlockingQueue<ArrayList<PhysObject>> q;
	private String[] players = new String[4];
	private int numberOfPlayers = 0;
	private Audio audio = new Audio();
	private static Square activePlayer;
	private TurnMaster turn;
	private double XtravelDist = 4;
	private boolean targetline;
	private ArrayList<PhysObject> explosions;
	Weapon wep;
	

	public static void main(String[] args) {
		Board board = new Board();
		Scanner scanner = new Scanner(System.in);
		while(true){
			String input = scanner.nextLine();
			if (input.equals("l")){
				board.updateFrame(new Move(0,0,"Left",false));
			}
			if (input.equals("r")){
				board.updateFrame(new Move(0,0,"Right",false));
			}
			if (input.equals("u")){
				board.updateFrame(new Move(0,0,"None",true));
			}
			if (input.equals("w")){
				board.updateFrame(new WeaponMove("ExplodeOnImpact",
						new Point2D.Double(activePlayer.getPos().getX(), activePlayer.getPos().getY()+5),0,0));
			}
		}
	}
	
	public Board(){
		this.objects = new ArrayList<PhysObject>();
		this.freeState = false;
		this.q = new ArrayBlockingQueue<ArrayList<PhysObject>>(10); //This handles the moves that need to be sent to clients.
		this.winner = -1;
		this.targetline = false;
		//this.q = new ArrayBlockingQueue<String>(100); //This handles the moves that need to be sent to clients.
		
		//BOARD IS 800 ACROSS BY 450 UP STARTING FROM BOTTOM LEFT AS (0, 0)
		//Initialise the placements of the 4 teams.
		Point2D.Double redpos = new Point2D.Double(100, 180);
		PhysObject red = new Square(1 ,0, 0, redpos);
		Point2D.Double blupos = new Point2D.Double(300, 180);
		PhysObject blu = new Square(2 ,0, 0, blupos);
		Point2D.Double yelpos = new Point2D.Double(400, 180);
		PhysObject yel = new Square(3 ,0, 0, yelpos);
		Point2D.Double grnpos = new Point2D.Double(500, 180);
		PhysObject grn = new Square(4 ,0, 0, grnpos);
		objects.add(red);
		objects.add(blu);
		objects.add(yel);
		objects.add(grn);
				
		//Draw blocks at bottom of map
		objects.add(new TerrainBlock(1,1,1,new Point2D.Double(240,180), true));
		
		for(int i = 100; i < 700; i+=40) {
			PhysObject block = new TerrainBlock(1, 1, 1,new Point2D.Double(i,150), true);
			objects.add(block);
		}
		
		for(int i = 100; i < 700; i+=120){
			
			PhysObject block = new TerrainBlock(1, 1, 1,new Point2D.Double(i,225), true);
			objects.add(block);
			
		}
		
		for(int i = 150; i < 700; i+=160){
			
			PhysObject block = new TerrainBlock(1, 1, 1,new Point2D.Double(i,300), true);
			objects.add(block);
			
		}
		
		for(int i = 50; i < 700; i+=200){
			
			PhysObject block = new TerrainBlock(1, 1, 1,new Point2D.Double(i,375), true);
			objects.add(block);
			
		}
		
		this.player = 0;
		this.squareID = 0;
		int x = player + squareID;
		activePlayer = (Square)objects.get(x);
		explosions = new ArrayList<PhysObject>();
		
		/*Point2D.Double weaponpos = new Point2D.Double(30, 30);
		PhysObject weapon = new Weapon(weaponpos);
		objects.add(weapon);
		
		Point2D.Double explosionpos = new Point2D.Double(40,40);
		PhysObject explosion = new Explosion(explosionpos);
		objects.add(explosion);*/
	}
	
	public void setFreeState(boolean free) {
		freeState = free;
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
		int x = player + squareID;
		activePlayer = (Square)objects.get(x);
	}
	
	public void setTargetLine(boolean b){
		this.targetline = b;
	}
	
	public boolean getTargetLine(){
		return this.targetline;
	}
	
	public PhysObject getActivePlayer() {
		return activePlayer;
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
	
	public ArrayList<PhysObject> getExplosion(){
		
		return new ArrayList<PhysObject>(explosions);
		
	}
	
	private double wallDistL(Square guy) {
		Iterator<PhysObject> it = getBlocks().iterator();
		while(it.hasNext()) {
			PhysObject nextblock = it.next();
			if(wallDistLOne(guy, nextblock)<=XtravelDist) {
				return wallDistLOne(guy, nextblock);
			}
		}
		return 100; //Out of range
	}
	
	private double wallDistLOne(Square guy, PhysObject block) {
		double guyleft = guy.getPos().getX();
		double guydown = guy.getPos().getY();
		double guyup = guy.getPos().getY()+guy.getHeight();
		double blockright = block.getPos().getX()+block.getWidth();
		double blockdown = block.getPos().getY();
		double blockup = block.getPos().getY()+block.getHeight();
		
		if((blockdown<guyup && guyup<blockup) || 
				(blockdown<guydown && guydown<blockup) ||
				(guyup == blockup) || (guydown == blockdown)) {
			if(Math.abs(blockright-guyleft)<=XtravelDist){
				return Math.abs(blockright-guyleft);
			}
			else {
				return 100; //Out of range
			}
		}
		else {
			return 100; //Out of range
		}
	}
	
	private double wallDistR(Square guy) {
		Iterator<PhysObject> it = getBlocks().iterator();
		while(it.hasNext()) {
			PhysObject nextblock = it.next();
			if(wallDistROne(guy, nextblock)<=XtravelDist) {
				return wallDistROne(guy, nextblock);
			}
		}
		return 100; //Out of range
	}
	
	private double wallDistROne(Square guy, PhysObject block) {
		double guyright = guy.getPos().getX()+guy.getWidth();
		double guyup = guy.getPos().getY()+guy.getHeight();
		double guydown = guy.getPos().getY();
		double blockleft = block.getPos().getX();
		double blockup = block.getPos().getY()+block.getHeight();
		double blockdown = block.getPos().getY();
		
		if((blockdown<guyup && guyup<blockup) || 
				(blockdown<guydown && guydown<blockup) ||
				(guyup == blockup) || (guydown == blockdown)) {
			if(Math.abs(blockleft-guyright)<=XtravelDist){
				return Math.abs(blockleft-guyright);
			}
			else {
				return 100; //Out of range
			}
		}
		else {
			return 100; //Out of range
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
		double blockup = block.getPos().getY()+block.getHeight();
		
		if((blockleft<=guyleft && guyleft<=blockright) || 
				(blockleft<=guyright && guyright<=blockright)) {
			if((-1)*Math.abs(guydown-blockup)>=guy.getYvel()){
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
	
	private PhysObject onCeiling(Square guy) {
		Iterator<PhysObject> it = getBlocks().iterator();
		while(it.hasNext()) {
			PhysObject nextblock = it.next();
			if(onCeilingOne(guy, nextblock)) {
				return nextblock;
			}
		}
		return null; //Out of range
	}
	
	private boolean onCeilingOne(Square guy, PhysObject block) {
		double guyright = guy.getPos().getX()+guy.getWidth();
		double guyleft = guy.getPos().getX();
		double guyup = guy.getPos().getY()+guy.getHeight();
		double blockleft = block.getPos().getX();
		double blockright = block.getPos().getX()+block.getWidth();
		double blockdown = block.getPos().getY();
		
		if((blockleft<guyleft && guyleft<blockright) || 
				(blockleft<guyright && guyright<blockright)) {
			if((Math.abs(blockdown-guyup))<activePlayer.getYvel()){
				return true;
			}
			else {
				return false; //Out of range
			}
		}
		else {
			return false; //Out of range
		}
	}
	
	private boolean collides(PhysObject obj1, PhysObject obj2) {
		if(obj1.getSolid()==obj2.getSolid() || !obj1.getInUse() || !obj2.getInUse()){
			return false;
		}
		if(obj1.getName().equals("TerrainBlock")) {
			if(obj2.getName().equals("ExplodeOnImpact")){
				if(debug) System.out.println("Weapon collision detected");
				Ellipse2D.Double circle = new Ellipse2D.Double
						(obj2.getPos().getX()+obj2.getHeight(), obj2.getPos().getY()+obj2.getHeight(), obj2.getWidth(), obj2.getHeight());
				return circle.intersects
						(obj1.getPos().getX()+obj1.getHeight(), obj1.getPos().getY()+obj1.getHeight(), obj1.getWidth(), obj1.getHeight());
			} else {
				Rectangle2D.Double rect = new Rectangle2D.Double
						(obj2.getPos().getX()+obj2.getHeight(), obj2.getPos().getY()+obj2.getHeight(), obj2.getWidth(), obj2.getHeight());
				return rect.intersects
						(obj1.getPos().getX()+obj1.getHeight(), obj1.getPos().getY()+obj1.getHeight(), obj1.getWidth(), obj1.getHeight());
			}
		} else {
			if(obj1.getName().equals("ExplodeOnImpact")){
				if(debug) System.out.println("Weapon collision detected");
				Ellipse2D.Double circle = new Ellipse2D.Double
						(obj1.getPos().getX()+obj1.getHeight(), obj1.getPos().getY()+obj1.getHeight(), obj1.getWidth(), obj1.getHeight());
				return circle.intersects
						(obj2.getPos().getX()+obj2.getHeight(), obj2.getPos().getY()+obj2.getHeight(), obj2.getWidth(), obj2.getHeight());
			} else {
				Rectangle2D.Double rect = new Rectangle2D.Double
						(obj1.getPos().getX()+obj1.getHeight(), obj1.getPos().getY()+obj1.getHeight(), obj1.getWidth(), obj1.getHeight());
				return rect.intersects
						(obj2.getPos().getX()+obj2.getHeight(), obj2.getPos().getY()+obj2.getHeight(), obj2.getWidth(), obj2.getHeight());
			}
		}
	}
	
	private void resolveCollision(PhysObject thing,int lspos, PhysObject block) {
		if(thing.getName().equals("ExplodeOnImpact")){
			if(debug) System.out.println("Resolving weapon collision");
			thing.setInUse(false);
			Weapon wep = (Weapon)thing;
			wep.setInUse(false);
			wep.setPos(new Point2D.Double(30, 30));
			TerrainBlock castedblock = (TerrainBlock)block;
			castedblock.damage(1);
			explosions.add(new Explosion(thing.getPos()));
			//TODO later implement different weapon types, and this doesn't work
		}
		else {
			thing = objects.get(lspos);
				if(thing.getPos().getX()+thing.getWidth()<=block.getPos().getX()) { //on the left
					thing.setXvel((-0.3)*thing.getXvel());
				}
				if(thing.getPos().getX()>=block.getPos().getX()+block.getWidth()) { //on the right
					thing.setXvel((-0.3)*thing.getXvel());
				}
				if(thing.getPos().getY()>=block.getPos().getY()+block.getHeight()) { //on top
					if(debug) System.out.println(thing.getYvel());
					if(Math.abs(thing.getXvel())<=2.5){
						if(debug)System.out.println("Sticky X");
						thing.setXvel(0);
					}
					else {
						thing.setXvel(0.6*thing.getXvel());
					}
					if(thing.getYvel()>=(-2.5)) {
						if(debug)System.out.println("Sticky Y");
						thing.setYvel(0);
						thing.setPos(new Point2D.Double(thing.getPos().getX(),block.getPos().getY()+block.getHeight()));
					}
					else {
						thing.setYvel((-0.3)*thing.getYvel());
					}
			}
				if(thing.getPos().getY()+thing.getHeight()<=block.getPos().getY()) { //below
					thing.setYvel((-0.3)*thing.getYvel());
			}
		}
	}
	
	private void freeSim() {
		//This is going to be relatively quite slow. Perhaps it can be improved later.
		ArrayList<PhysObject> objs = new ArrayList<PhysObject>(objects);
		for (PhysObject obj : objs) {
			if(obj.getName().equals("Square")) {if(debug)System.out.println(obj.getPos().getY());}
			obj.update();
			if(obj.getName().equals("Square")) {if(debug)System.out.println(obj.getPos().getY());}
		}
		ConcurrentSkipListSet<Collision> set = new ConcurrentSkipListSet<Collision>();
		for (int i = 0; i < objs.size(); i++) {
			for (int j = i+1; j < objs.size(); j++) {
				if(collides(objs.get(i),objs.get(j))){
					if(objs.get(j).getName().equals("TerrainBlock")) {
						set.add(new Collision(objs.get(i), objs.indexOf(objs.get(i)), objs.get(j)));
					}
					else {
						set.add(new Collision(objs.get(j), objs.indexOf(objs.get(j)), objs.get(i)));
					}
				}
			}
		}
		for(Collision collision: set){
			collision.getThing().undoUpdate();
			resolveCollision(collision.getThing(), collision.lspos(), collision.getBlock());
		}
		boolean same = true;
		if(debug) System.out.println(objects.size());
		if(debug) System.out.println(objs.size());
		for(int i = 0;i<objects.size();i++){
			if (!objs.get(i).equals(objects.get(i))) {
				same = false;
			}
		}
		if(same){
			if(debug)System.out.println("FreeState exited due to no movement");
			freeState=false;
			turn.resetTimer();
			incrementTurn();
		}
		objects = objs;
	}
	
	public void updateFrame(Move move) {
		if(freeState) { // If the engine is in free-physics mode then the move is irrelevant,
			freeSim();  // just simulate another frame.
		}
		else if (move.getWeaponMove()) {
			WeaponMove wepMove = (WeaponMove)move;
			//get weapon from arraylist
			//TODO implement other weapon types
			Weapon wep = new Weapon(wepMove.getPos(), wepMove.getXvel(), wepMove.getYvel());
			freeState = true;
			objects.add(wep);
		}
		else { //Not in freeState, change active player depending on move
			PhysObject floor = onFloor(activePlayer);
			if (floor!=null) { //if the player is standing on a block
				activePlayer.setYvel(0);
				activePlayer.setPos(new Point2D.Double
				  (activePlayer.getPos().getX(), floor.getPos().getY()+floor.getHeight()));
				if(move.getJump()) {
					activePlayer.setYvel(20);
				}
				if(move.getDirection().equals("Left")){
					if (wallDistL(activePlayer)<XtravelDist){
						activePlayer.setPos
						(new Point2D.Double(activePlayer.getPos().getX()-wallDistL(activePlayer),
						activePlayer.getPos().getY()));
					} else {
						activePlayer.setPos
						(new Point2D.Double(activePlayer.getPos().getX()-XtravelDist,activePlayer.getPos().getY()));
					}
				} else if(move.getDirection().equals("Right")){
					if (wallDistR(activePlayer)<XtravelDist){
						activePlayer.setPos
						(new Point2D.Double(activePlayer.getPos().getX()+wallDistR(activePlayer),
						activePlayer.getPos().getY()));
					}
					else {
						activePlayer.setPos(new Point2D.Double(activePlayer.getPos().getX()+XtravelDist,
						activePlayer.getPos().getY()));
					}
				} else if(move.getDirection().equals("None")){
					//Don't move the square
				}
			} else { //Player not standing on a block
				if(move.getDirection().equals("Left")){
					activePlayer.setXvel((-1)*XtravelDist);
					if (wallDistL(activePlayer)<XtravelDist){
						activePlayer.setPos
						(new Point2D.Double(activePlayer.getPos().getX()-wallDistL(activePlayer),
						activePlayer.getPos().getY()));
					} else {
						activePlayer.setPos
						(new Point2D.Double(activePlayer.getPos().getX()-XtravelDist,activePlayer.getPos().getY()));
					}
				} else if(move.getDirection().equals("Right")){
					activePlayer.setXvel(XtravelDist);
					if (wallDistR(activePlayer)<XtravelDist){
						activePlayer.setPos
						(new Point2D.Double(activePlayer.getPos().getX()+wallDistR(activePlayer),
						activePlayer.getPos().getY()));
					} else {
						activePlayer.setPos
						(new Point2D.Double(activePlayer.getPos().getX()+XtravelDist,activePlayer.getPos().getY()));
					}
				} else if(move.getDirection().equals("None")) {
					activePlayer.setXvel(0);
					//Don't move the square
				}
			}
			
			PhysObject ceiling = onCeiling(activePlayer);
			if(ceiling!=null){ //If they are hitting their head
				activePlayer.setPos(new Point2D.Double(activePlayer.getPos().getX(),
				ceiling.getPos().getY()-activePlayer.getHeight()));
				activePlayer.setYvel(0);
			} else {
				activePlayer.setPos(new Point2D.Double(activePlayer.getPos().getX(),
				activePlayer.getPos().getY()+activePlayer.getYvel()));
			}
			
			activePlayer.setYvel(activePlayer.getYvel()-activePlayer.getGrav());
			
			if((activePlayer.getPos().getY() < 100) && activePlayer.getAlive()){
				
				activePlayer.setDead();
				audio.splash();
				if (checkForWinner()){
					if(debug)System.out.println("winner?");
					int won = findPlayer();
					setWinner(won);
					turn.endItAll();
				}
				incrementTurn();
				turn.resetTimer();
				
			}
		}
		//if (debug) System.out.println(getActivePlayer().getPos().getX()+", "+getActivePlayer().getPos().getY());
		//if (debug) System.out.println(player);
		int x = player+squareID;
		objects.add(x,activePlayer);
		objects.remove(x+1);
	}
	
	/**
	 * Used on the server-side, receiving an update string that is from the inputs of the player.
	 * @param inputs the formatted string from hangerOn.
	 */
	//public void input(Move input){
	public void input(String input) {
		Square active = (Square)getActivePlayer();
		
		if(input.contains("Pressed")){
			if(input.contains(players[player])){
				//if(debug) System.out.println("I'm from the current player!");
				}
			
			String inputKey = input.substring(8,9);
			//System.out.println(inputKey);
			String ret = null;
			Move mv;
			
			switch(inputKey){
			case "W" : mv = new Move(active.getColour(),active.getSquareID(),"None",true);
							//System.out.println("Hey left sorta works");
							updateFrame(mv);
							if (q.size() > 0)
								q.remove();
							q.add(objects);
							
				break;
			case "A" : mv = new Move(active.getColour(),active.getSquareID(),"Left",false);
						//System.out.println("Hey left sorta works");
						updateFrame(mv);
						if (q.size() > 0)
							q.remove();
						q.add(objects);
						active.setFacing("Left");
			case "S" : //duck?
				break;
			case "D" : mv = new Move(active.getColour(),active.getSquareID(),"Right",false);
						updateFrame(mv);
						if (q.size() > 0)
							q.remove();
						q.add(objects);
						active.setFacing("Right");
				break;
			default : if (input.contains("Space")){
							mv = new Move(active.getColour(),active.getSquareID(),"None",false);
							mv.setWeapon(true);
				}
			}
		}
		else if(input.contains("clicked")){
			if(wep.getInUse()){
				WeaponMove wmv;
				int xs = input.indexOf('x');
				int xe = input.indexOf(',');
				String xc = input.substring(xs+2, xe);
				int ye = input.indexOf(']');
				String yc = input.substring(xe+3, ye);
				
				Double x = Double.parseDouble(xc);
				Double y =800- Double.parseDouble(yc);
				Point2D.Double target = new Point2D.Double(x, y);
				//System.out.println(origin);
				
				wmv = new WeaponMove("None",active.getPoint(),0,0);
				//updateFrame(wmv);
				if (q.size() > 0)
					q.remove();
				q.add(objects);
			}
			else{
				//Create a weapon error check for the server
			}
		}
		else if (input.contains("setWep")){
			this.wep = new Weapon(active.getPoint(), 0, 0);
			wep.setInUse(true);
		}
		else
		{
			Move mv = new Move(active.getColour(),active.getSquareID(),"None",false);
			updateFrame(mv);
			q.offer(objects);
		};
	}
	
	/**
	 * Used on the server-side to send an update to all the client for how their boards should look.
	 * @return The update sent.
	 * @throws InterruptedException 
	 */
	public ArrayList<PhysObject> getUpdate() throws InterruptedException {
		return q.take();
	}
	
	/**
	 * Returns the PhysObject arraylist that tracks all moving parts in the game
	 * @return the list of objects that are affected by physics.
	 */
	public ArrayList<PhysObject> getObjects(){
		return objects;
	}
	
	/**
	 * Update the list of objects affected by physics, used on the client version of the boards so they know what changes the server has made.
	 * @param obj the updated list of objects
	 */
	public void  setObjects(ArrayList<PhysObject> obj){
		this.objects = obj;
	}
	
	public boolean getWeaponsOpen(){
		
		return weaponsopen;
	}
	
	public void setWeaponsOpen(boolean open){
		
		weaponsopen = open;
	}
	
	/**
	 * Called when the game actually begins, starts the turn timer and fiddles with the player list.
	 */
	public void startGame(){
		this.turn = new TurnMaster(this);
		turn.start();
		
		for (int i = 0; i < 4; i++){
			if (players[i] == null){
				players[i] = "meep";
			}
		}
	}
	/**
	 * Increments the active player
	 */
	public void incrementTurn(){
		if (player != 3){
			player = player+1;
		}else{
			player = 0;
			//squareID = squareID+1;
		}
		setActivePlayer(player,squareID);
		//System.out.println(player);
		if (!(activePlayer.getAlive())){
			incrementTurn();
		}
	}
	
	public void addName(String name){
		players[numberOfPlayers] = name;
		numberOfPlayers++;
	}
	
	/**
	 * Checks the arraylist of Squares to see if any two living squares have different players
	 * @return True if all living squares are played by the same player, false otherwise.
	 */
	private boolean checkForWinner(){
		ArrayList<PhysObject> chickenDinner = getSquares();
		
		for(int i=0; i< chickenDinner.size()-1;i++){
			Square first = ((Square)chickenDinner.get(i));
			Square second = ((Square)chickenDinner.get(i+1));
			if((first.getAlive() && second.getAlive()))
				if(first.getPlayerID() != second.getPlayerID())
					return false;
		}
		return true;
		
	}
	
	private int findPlayer()
	{
		ArrayList<PhysObject> chickenDinner = getSquares();
		for(int i=0; i< chickenDinner.size();i++){
			Square first = ((Square)chickenDinner.get(i));
			if (first.getAlive())
				return ((Square)chickenDinner.get(0)).getPlayerID();
		}
		return -1;
	}
}
