package GameLogic;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import Audio.Audio;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.lang.Math;

//Ideas for weapons in the future; some can be pickup-only, some can be basic.
//The missile idea sounds cool but very hard to implement :(
//1. Air-strike; Click to send airplane across top of screen, click again to release multiple bombs.
//2. TNT; Choose any block. After one full cycle of turns it will explode.
//3. Grenade with a timer instead of exploding on impact.
//4. Anti-grav bomb; bomb that falls upwards instead of downwards.
//5. Dont use any weapon and instead fling your square across the map.


//Formula for adding more weapons. All these things need to be implemented.
//Weapons are no longer all going to extend Weapon, sorry for the confusion.
//1. Create new class. Modify all necessary methods from PhysObject which would be different.
//2. Create new get method for this type of weapon.
//3. Modify collision methods to detect the defining string of the weapon
//4. update freeSim arrayList copy cases
//5. update updateFrame weaponMove cases
//

public class Board {
	//Keep track of the current player
	private int player;
	private int squareID;
	private static Square activePlayer;
	//Keep track of state of the board
	private String map;
	private ArrayList<PhysObject> objects;
	private ArrayList<PhysObject> explosions;
	private int winner = -1;
	private boolean freeState;
	private TurnMaster turn;
	private boolean weaponsopen = false;
	private String weaponType;
	//Miscellaneous
	private ArrayBlockingQueue<ArrayList<PhysObject>> q;
	private String[] players = new String[4];
	private int numberOfPlayers = 0;
	private Audio audio = new Audio();
	private double XtravelDist = 4;
	private boolean targetline;
	//Debug
	private final boolean debug = false;
	private final boolean debugL = false;

	public static void main(String[] args) { //For testing purposes only
		Board board = new Board("map1");
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
	
	public Board(String map){
		this.objects = new ArrayList<PhysObject>();
		explosions = new ArrayList<PhysObject>();
		this.freeState = false;
		this.q = new ArrayBlockingQueue<ArrayList<PhysObject>>(10); //This handles the moves that need to be sent to clients.
		this.winner = -1;
		this.targetline = false;
		this.map = map;
		this.turn = new TurnMaster(this);
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
		
		//Which map are we playing on? Initialise the correct one.
		if(this.map.equals("map1")){
			//Draw blocks at bottom of map
			objects.add(new TerrainBlock(1,1,new Point2D.Double(240,180), true));
			
			for(int i = 100; i < 700; i+=40) {
				PhysObject block = new TerrainBlock(1, 1,new Point2D.Double(i,150), true);
				objects.add(block);
			}
			
			for(int i = 100; i < 700; i+=120){
				
				PhysObject block = new TerrainBlock(2, 2,new Point2D.Double(i,225), true);
				objects.add(block);
				
			}
			
			for(int i = 150; i < 700; i+=160){
				
				PhysObject block = new TerrainBlock(1,2,new Point2D.Double(i,300), true);
				objects.add(block);
				
			}
			
			for(int i = 50; i < 700; i+=200){
				
				PhysObject block = new TerrainBlock(1,1,new Point2D.Double(i,375), true);
				objects.add(block);
				
			}
		}
		else{
			
			//Draw blocks at bottom of map
			objects.add(new TerrainBlock(1,1,new Point2D.Double(240,180), true));
			
			for(int i = 100; i < 700; i+=40) {
				PhysObject block = new TerrainBlock(2, 2,new Point2D.Double(i,150), true);
				objects.add(block);
			}
			
			for(int i = 100; i < 700; i+=120){
				
				PhysObject block = new TerrainBlock(1, 2,new Point2D.Double(i,200), true);
				objects.add(block);
				
			}
			
			for(int i = 150; i < 700; i+=160){
				
				PhysObject block = new TerrainBlock(1,1,new Point2D.Double(i,250), true);
				objects.add(block);
				
			}
			
			for(int i = 50; i < 700; i+=200){
				
				PhysObject block = new TerrainBlock(1,1,new Point2D.Double(i,475), true);
				objects.add(block);
				
			}
			
			
		}
		
		this.player = 0;
		this.squareID = 0;
		activePlayer = (Square)objects.get(0);
		
		//ssshhhh dont tell anyone but im gonna add a new wep instead of changing the boolean
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
			if (obj.getName().startsWith("Weapon")){
				weapons.add(obj);
			}
		}
		return weapons;
	}
	
	public ArrayList<PhysObject> getExplodeOnImpact(){
		ArrayList<PhysObject> weapons = new ArrayList<PhysObject>();
		for(PhysObject obj : objects){
			if (obj.getName().endsWith("ExplodeOnImpact")){
				weapons.add(obj);
			}
		}
		return weapons;
	}
	
	public ArrayList<PhysObject> getTimedGrenade(){
		ArrayList<PhysObject> weapons = new ArrayList<PhysObject>();
		for(PhysObject obj : objects){
			if (obj.getName().endsWith("TimedGrenade")){
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
		
		return explosions;
		
	}
	
	//All these big chunk of functions are for figuring out how far a square is from a block
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
	
	//Are two PhysObjects currently colliding?
	private boolean collides(PhysObject obj1, PhysObject obj2) {
		if(obj1.getSolid()==obj2.getSolid() || !obj1.getInUse() || !obj2.getInUse()){
			return false;
		}
		if(obj1.getName().equals("TerrainBlock")) {
			if(obj2.getName().endsWith("ExplodeOnImpact") || obj2.getName().endsWith("TimedGrenade")){ //All circular objects
				if(debug) System.out.println("Circular object collision detected");
				Ellipse2D.Double circle = new Ellipse2D.Double
						(obj2.getPos().getX(), obj2.getPos().getY()+obj2.getHeight(), obj2.getWidth(), obj2.getHeight());
				return circle.intersects
						(obj1.getPos().getX(), obj1.getPos().getY()+obj1.getHeight(), obj1.getWidth(), obj1.getHeight());
			} else {
				return obj1.rectIntersect(obj2);
				/*Rectangle2D.Double rect = new Rectangle2D.Double
						(obj2.getPos().getX(), obj2.getPos().getY()+obj2.getHeight(), obj2.getWidth(), obj2.getHeight());
				return rect.intersects
						(obj1.getPos().getX(), obj1.getPos().getY()+obj1.getHeight(), obj1.getWidth(), obj1.getHeight());*/
			}
		} else {
			if(obj1.getName().endsWith("ExplodeOnImpact") || obj1.getName().endsWith("TimedGrenade")){ //All circular objects
				if(debug) System.out.println("Circular object collision detected");
				Ellipse2D.Double circle = new Ellipse2D.Double
						(obj1.getPos().getX(), obj1.getPos().getY()+obj1.getHeight(), obj1.getWidth(), obj1.getHeight());
				return circle.intersects
						(obj2.getPos().getX(), obj2.getPos().getY()+obj2.getHeight(), obj2.getWidth(), obj2.getHeight());
			} else {
				return obj1.rectIntersect(obj2);
				/*Rectangle2D.Double rect = new Rectangle2D.Double
						(obj1.getPos().getX(), obj1.getPos().getY()+obj1.getHeight(), obj1.getWidth(), obj1.getHeight());
				return rect.intersects
						(obj2.getPos().getX(), obj2.getPos().getY()+obj2.getHeight(), obj2.getWidth(), obj2.getHeight());*/
			}
		}
	}
	
	private void createExplosion(ArrayList<PhysObject> things, double x, double y, double power, double size, int damage){
		//for i from x to y, all squares push away, all blocks damage
		double i = (2*size/5);
		Ellipse2D.Double circle = new Ellipse2D.Double(x-(i/2), y+(i/2), 2*i, 2*i);
		for(PhysObject thing : things){
			if(thing.getName().equals("TerrainBlock")){
				if(circle.intersects(thing.getPos().getX(),
				thing.getPos().getY()+thing.getHeight(),thing.getWidth(),thing.getHeight())){
					((TerrainBlock)thing).damage(damage);
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
					square.setXvel(square.getXvel()+(power/(thing.getPos().getX()-x)));
					square.setYvel(square.getYvel()+(power/(thing.getPos().getY()-y)));
				}
			}
		}
		explosions.add(new Explosion(new Point2D.Double(x, y), size));
	}
	
	//If two objects are colliding, this method will be called to resolve the collision
	private void resolveCollision(ArrayList<PhysObject> things, PhysObject thing, PhysObject block) {
		if(thing.getName().endsWith("ExplodeOnImpact")) {
			if(debug) System.out.println("Resolving impactGrenade collision");
			thing.setInUse(false);
			createExplosion(things, thing.getPos().getX()+(thing.getWidth()/2),
					thing.getPos().getY()+(thing.getHeight()/2), 150, 50, 1);
		}
		else if(thing.getName().endsWith("TimedGrenade")){ //Collisions for circular objects
			if(thing.getPos().getX()+thing.getWidth()<=block.getPos().getX()) { //on the left
				thing.setXvel((-0.3)*thing.getXvel());
				if(thing.getXvel()==0){
					thing.update();
				}
			}
			else if(thing.getPos().getX()>=block.getPos().getX()+block.getWidth()) { //on the right
				thing.setXvel((-0.3)*thing.getXvel());
				if(thing.getXvel()==0){
					thing.update();
				}
			}
			else if(thing.getPos().getY()>=block.getPos().getY()+block.getHeight()) { //on top
				if(Math.abs(thing.getXvel())<=2){
					thing.setXvel(0);
				}
				else {
					thing.setXvel(0.9*thing.getXvel());
				}
				if(thing.getYvel()>=(-2)) {
					thing.setYvel(0);
					thing.setPos(new Point2D.Double(thing.getPos().getX(),block.getPos().getY()+block.getHeight()));
				}
				else {
					thing.setYvel((-0.3)*thing.getYvel());
				}
			}
			else if(thing.getPos().getY()+thing.getHeight()<=block.getPos().getY()) { //below
				thing.setYvel((-0.3)*thing.getYvel());
			}
			else {
				thing.setYvel((-0.4)*thing.getYvel());
				thing.setXvel((-0.4)*thing.getXvel());
			}
		}
		else { // Collisions for squares
			if(thing.getPos().getX()+thing.getWidth()<=block.getPos().getX()) { //on the left
				thing.setXvel((-0.3)*thing.getXvel());
				if(thing.getXvel()==0){
					thing.update();
				}
			}
			if(thing.getPos().getX()>=block.getPos().getX()+block.getWidth()) { //on the right
				thing.setXvel((-0.3)*thing.getXvel());
				if(thing.getXvel()==0){
					thing.update();
				}
			}
			if(thing.getPos().getY()>=block.getPos().getY()+block.getHeight()) { //on top
				if(Math.abs(thing.getXvel())<=2){
					thing.setXvel(0);
				}
				else {
					thing.setXvel(0.6*thing.getXvel());
				}
				if(thing.getYvel()>=(-2)) {
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
	
	//For when no player is in control and things are bouncing about
	//This method simulates a frame, detects collisions and resolves them.
	//If nothing has changed since the last frame, move on.
	private void freeSim() {
		//This is going to be relatively quite slow. Perhaps it can be improved later.
		ArrayList<PhysObject> objs = new ArrayList<PhysObject>();
		for(int i=0; i < objects.size();i++){
			System.out.println(objects.get(i).getName());
			switch(objects.get(i).getName()) {
			case "TerrainBlock": objs.add(new TerrainBlock((TerrainBlock)objects.get(i))); break;
			case "Square": objs.add(new Square((Square)objects.get(i))); break;
			case "WeaponExplodeOnImpact": objs.add(new ExplodeOnImpact((ExplodeOnImpact)objects.get(i))); break;
			case "WeaponTimedGrenade": objs.add(new TimedGrenade((TimedGrenade)objects.get(i))); break;
			default: System.out.println("error copying arraylists in freeSim"); break;
			}
		}
		for (PhysObject obj : objs) {
			obj.update();
		}
		
		for(PhysObject obj: objs){
			switch(obj.getName()){
			case "WeaponTimedGrenade": 
				TimedGrenade grenade = (TimedGrenade) obj;
				if(grenade.getFrames()==0){
					grenade.setInUse(false);
					createExplosion(objs, grenade.getPos().getX()+(grenade.getWidth()/2),
						grenade.getPos().getY()+(grenade.getHeight()/2), 150, 50, 1);
				}
				break;
			default: break;
			}
		}
		
		ArrayList<Collision> list = new ArrayList<Collision>();
		for (int i = 0; i < objs.size(); i++) {
			for (int j = i+1; j < objs.size(); j++) {
				if(collides(objs.get(i),objs.get(j))){
					if(objs.get(j).getName().equals("TerrainBlock")) {
						Collision collis = new Collision(objs.get(i), objs.indexOf(objs.get(i)), objs.get(j));
						if(!list.contains(collis)){
							list.add(collis);
						}
					}
					else {
						Collision collis = new Collision(objs.get(j), objs.indexOf(objs.get(j)), objs.get(i));
						if(!list.contains(collis)){
							list.add(collis);
						}
					}
				}
			}
		}
		System.out.println(list.size());
		System.out.println(getBlocks().size());
		System.out.println(objs.size());
		for(Collision collision: list){
			/*if(collision.getThing().getName().equals("Square")){
				if(((Square)collision.getThing()).getPlayerID()==1){
					System.out.println("resolving red square collision with block at: "+
						collision.getBlock().getPos().getX()+", "+collision.getBlock().getPos().getY());
				}
			}*/
			collision.getThing().undoUpdate();
			resolveCollision(objs, collision.getThing(), collision.getBlock());
		}
		
		for(PhysObject object: objs){
			if((object.getPos().getY() < 100) || (object.getPos().getX()<=0) || (object.getPos().getX()>850)){
				object.setInUse(false);
				audio.splash();
				if (checkForWinner()){
					if(debug)System.out.println("winner?");
					int won = findPlayer();
					setWinner(won);
					turn.endItAll();
				}
			}
		}
		
		boolean same = true;
		for(int i = 0;i<objects.size();i++){
			if (!objs.get(i).equals(objects.get(i))) {
				//System.out.println(objects.get(i).getYvel()+" is changing to "+objs.get(i).getYvel());
				same = false;
			}
		}
		if(same){
			if(debug)System.out.println("FreeState exited due to no movement");
			freeState=false;
			incrementTurn();
		}
		turn.resetTimer();
		objects = objs;
	}
	
	
	//Takes a move and updates one frame.
	public void updateFrame(Move move) {
		if(freeState) { // If the engine is in free-physics mode then the move is irrelevant,
			freeSim(); // just simulate another frame.
		}
		else if (move.getWeaponMove()) {
			WeaponMove wepMove = (WeaponMove)move;
			System.out.println(wepMove.getPos());
			PhysObject wep = null;
			switch(wepMove.wepType()){
			case "ExplodeOnImpact": wep = new ExplodeOnImpact(
					wepMove.getPos(), wepMove.getXvel(), wepMove.getYvel(), true); break;
			case "TimedGrenade": wep = new TimedGrenade(
					wepMove.getPos(), wepMove.getXvel(), wepMove.getYvel(), true); break;
			default: System.out.println("Weapon move parsing error"); break;
			}
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
			int x = player+squareID;
			objects.add(x,activePlayer);
			objects.remove(x+1);
		}
	}
	
	/**
	 * Used on the server-side, receiving an update string that is from the inputs of the player.
	 * @param inputs the formatted string from hangerOn.
	 */
	public void input(String input) {
		Square active = (Square)getActivePlayer();
		
		if(input.contains("Pressed")){
			if(input.contains(players[player])){
			String inputKey = input.substring(8,9);
			//System.out.println(inputKey);
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
		}
		else if(input.contains("Clicked")){
			if(weaponsopen){
				
				setTargetLine(false);
				
				int xs = input.indexOf('x');
				int xe = input.indexOf(',');
				String xc = input.substring(xs+2, xe);
				int ye = input.indexOf(']');
				String yc = input.substring(xe+3, ye);
				
				Double x = Double.parseDouble(xc);
				Double y =800- Double.parseDouble(yc);
				Point2D.Double target = new Point2D.Double(x, y);
				
				WeaponMove wmv = new WeaponMove(weaponType,
						new Point2D.Double(active.getPos().getX(), active.getPos().getY()+25),10,4);
				updateFrame(wmv);
				if (q.size() > 0)
					q.remove();
				q.add(objects);
				weaponsopen = false;
			}
			//else{
				//Create a weapon error check for the server
			//}
		}
		else if (input.contains("setWep")){
			if(debugL)
				System.out.println("Now a weapon in use");
			else{
				this.weaponType = input.substring(7);
				System.out.println(weaponType);
				weaponsopen = true;
//				for(PhysObject weapon : this.getWeapons()){
//					
//					weapon.setInUse(true);
//					weapon.setName(input.substring(8));
//					
//				}
			}
		}
		else if (input.contains("setExp")){
			
			for(PhysObject exp : this.getExplosion()){
				
				((Explosion) exp).setSize(Integer.parseInt(input.substring(8)));
				
			}
				
		}
		else if (input.contains("setUse")){
			
			for(PhysObject exp : this.getExplosion()){
				
				exp.setInUse(Boolean.parseBoolean(input.substring(8)));
				
			}
			
		}
		else if(input.contains("setTar")){
			
			setTargetLine(Boolean.parseBoolean(input.substring(8)));
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
