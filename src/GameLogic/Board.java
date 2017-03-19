package GameLogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import Audio.Audio;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
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
	private int time = 0;
	private TurnServant servant = new TurnServant(this);
	private boolean playing = false;
	//Miscellaneous
	private ArrayBlockingQueue<ArrayList<PhysObject>> q;
	private String[] players = new String[4];
	private int numberOfPlayers = 0;
	private Audio audio = new Audio();
	private double XtravelDist = 4;
	private boolean turnChangedFlag = true;
	//Debug
	private final boolean debug = false;
	private final boolean debugL = false;

	/*public static void main(String[] args) { //For testing purposes only
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
	}*/
	
	public Board(String map){
		this.objects = new ArrayList<PhysObject>();
		this.explosions = new ArrayList<PhysObject>();
		this.freeState = false;
		this.q = new ArrayBlockingQueue<ArrayList<PhysObject>>(10); //This handles the moves that need to be sent to clients.
		this.winner = -1;
		this.map = map;
		this.turn = new TurnMaster(this);
		//this.q = new ArrayBlockingQueue<String>(100); //This handles the moves that need to be sent to clients.
		
		//BOARD IS 800 ACROSS BY 450 UP STARTING FROM BOTTOM LEFT AS (0, 0)
		//Initialise the placements of the 4 teams.
		Point2D.Double redpos = new Point2D.Double(100, 180);
		PhysObject red = new Square(1 ,0, 0, redpos);
		((Square)red).setActivePlayer(true);
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
		
		Point2D.Double weaponpos = new Point2D.Double(150, 200);
		PhysObject weapon = new ExplodeOnImpact(weaponpos, 0, 0, false);
		objects.add(weapon);
		
		Point2D.Double explosionpos = new Point2D.Double(150,150);
		PhysObject explosion = new Explosion(explosionpos);
		explosion.setInUse(false);
		explosions.add(explosion);
		
		PhysObject targetline = new TargetLine();
		targetline.setInUse(false);
		objects.add(targetline);
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
		activePlayer = (Square)getSquares().get(x);
		for(PhysObject phys : getSquares()) {
			Square square = (Square)phys;
			square.setActivePlayer(false);
		}
		activePlayer.setActivePlayer(true);
		objects.remove(x);
		objects.add(x, activePlayer);
	}
	
	public PhysObject getActivePlayer() {
		return activePlayer;
	}
	
	public boolean getPlaying() {
		return this.playing;
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
	
	public ArrayList<PhysObject> getTargetLine(){
		ArrayList<PhysObject> target = new ArrayList<PhysObject>();
		for(PhysObject obj : objects){
			if (obj.getName().startsWith("Target")){
				target.add(obj);
			}
		}
		return target;
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
		
	
	public ArrayList<PhysObject> getMissile(){
		ArrayList<PhysObject> weapons = new ArrayList<PhysObject>();
		for(PhysObject obj : objects){
			if (obj.getName().endsWith("Missile")){
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
		
		ArrayList<PhysObject> exp = new ArrayList<PhysObject>();
		for(PhysObject obj : objects){
			
			if (obj.getName().equals("Explosion")){
				
				exp.add(obj);
			}
		}
		
		return exp;
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
		if(!block.getInUse()) {return 100;}
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
		if(!block.getInUse()) {return 100;}
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
		if(!block.getInUse()) {return false;}
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
		if(!block.getInUse()) {return false;}
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
		if((obj1.getSolid()==obj2.getSolid()) || (!obj1.getInUse()) || (!obj2.getInUse())){
			return false;
		}
		if(obj1.getName().equals("TerrainBlock")) {
			if(obj2.getName().endsWith("ExplodeOnImpact")/* || obj2.getName().endsWith("TimedGrenade")*/){ //All circular objects
				Ellipse2D.Double circle = new Ellipse2D.Double
						(obj2.getPos().getX(), obj2.getPos().getY()+obj2.getHeight(), obj2.getWidth(), obj2.getHeight());
				if(circle.intersects
						(obj1.getPos().getX(), obj1.getPos().getY()/*+obj1.getHeight()*/, obj1.getWidth(), obj1.getHeight())){
					System.out.println("Circular object collision detected between wep at "+obj2.getPos()+"with height "
							+obj2.getHeight()+"and width "+obj2.getWidth()+" and block at "
							+obj1.getPos()+" with height "+obj1.getHeight()+" and width "+obj1.getWidth());
					return true;
				} else {return false;}
			} else {
				return obj1.rectIntersect(obj2);
				/*Rectangle2D.Double rect = new Rectangle2D.Double
						(obj2.getPos().getX(), obj2.getPos().getY()+obj2.getHeight(), obj2.getWidth(), obj2.getHeight());
				return rect.intersects
						(obj1.getPos().getX(), obj1.getPos().getY()+obj1.getHeight(), obj1.getWidth(), obj1.getHeight());*/
			}
		} else {
			if(obj1.getName().endsWith("ExplodeOnImpact")/* || obj1.getName().endsWith("TimedGrenade")*/){ //All circular objects
				Ellipse2D.Double circle = new Ellipse2D.Double
						(obj1.getPos().getX(), obj1.getPos().getY()+obj1.getHeight(), obj1.getWidth(), obj1.getHeight());
				if(circle.intersects
						(obj2.getPos().getX(), obj2.getPos().getY()/*+obj2.getHeight()*/, obj2.getWidth(), obj2.getHeight())){
					System.out.println("Circular object collision detected between wep at "+obj1.getPos()+"with height "
							+obj1.getHeight()+"and width "+obj1.getWidth()+" and block at "
							+obj2.getPos()+" with height "+obj2.getHeight()+" and width "+obj2.getWidth());
					return true;
				} else {return false;}
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
		
		
		System.out.println("CREATING EXPLOSION***************************");
		
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
		for(int hah=0;hah<things.size();hah++){
			if(things.get(hah).getName().equals("Explosion")){
				things.remove(hah);
			}
		}
		things.add(new Explosion(new Point2D.Double(x, y)));
		
		/*System.out.println("here ***************************");
		
		Explosion exp = new Explosion(new Point2D.Double(x, y));
		System.out.println("INUSE?" + exp.getInUse());
		
		System.out.println("GETTING FROM ARRAYLIST" + explosions.get(0).getInUse());*/
	}
	
	//If two objects are colliding, this method will be called to resolve the collision
	private void resolveCollision(ArrayList<PhysObject> things, PhysObject thing, PhysObject block) {
		if(thing.getName().endsWith("ExplodeOnImpact") || thing.getName().endsWith("Missile")) {
			if(debug) System.out.println("Resolving " + thing.getName() + "collision between thing at: " + thing.getPos()
			+ ", and block at: " + block.getPos());
			thing.setInUse(false);
			createExplosion(things, thing.getPos().getX()+(thing.getWidth()/2),
					thing.getPos().getY()+(thing.getHeight()/2), 150, 50, 1);
		}
		else if(thing.getName().endsWith("TimedGrenadeDONT USE")){ //Collisions for circular objects
			thing.undoUpdate();
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
			thing.undoUpdate();
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
			switch(objects.get(i).getName()) {
			case "TerrainBlock": objs.add(new TerrainBlock((TerrainBlock)objects.get(i))); break;
			case "Square": objs.add(new Square((Square)objects.get(i))); break;
			case "WeaponExplodeOnImpact": objs.add(new ExplodeOnImpact((ExplodeOnImpact)objects.get(i))); break;
			case "WeaponTimedGrenade": objs.add(new TimedGrenade((TimedGrenade)objects.get(i))); break;
			case "WeaponMissile": objs.add(new Missile((Missile)objects.get(i))); break;
			case "TargetLine": objs.add(new TargetLine((TargetLine)objects.get(i))); break;
			case "Explosion": objs.add(new Explosion((Explosion)objects.get(i))); break;
			default: System.out.println("error copying arraylists in freeSim: " + objects.get(i).getName()); break;
			}
		}
		
		for (PhysObject obj : objs) {
			obj.update();
		}
		
		//Explode objects on a timer if they have run out
		for(PhysObject obj: objs){
			switch(obj.getName()){
			case "WeaponTimedGrenade": 
				TimedGrenade grenade = (TimedGrenade) obj;
				if((grenade.getFrames()<=0) && (grenade.getInUse()==true)){
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
		for(Collision collision: list){
			resolveCollision(objs, collision.getThing(), collision.getBlock());
		}
		
		for(PhysObject object: objs){
			if(object.getInUse()){
				if((object.getPos().getY() < 100) || (object.getPos().getX()<(-40)) || (object.getPos().getX()>850)){
					
					if(object.getInUse()){
						
						object.setInUse(false);
						//audio.splash();
						if (checkForWinner() != -1){
							if(debug)System.out.println("winner?");
							int won = findPlayer();
							setWinner(won);
							turn.endItAll();
						}
					}
				}
			}
		}
		
		ArrayList<PhysObject> explos = new ArrayList<PhysObject>();
		for(PhysObject one: objs){
			if(one.getName().equals("Explosion")){
				explos.add(one);
			}
		}
		for(PhysObject exp : explos){
			
			Explosion explo = (Explosion) exp;
			
			
			if(explo.getInUse()){
				
				if(explo.getSize()>20){
					
					audio.explosion();
				}
				
				if(explo.getSize() > 60){
						
					explo.setInUse(false);
				}
				else{
						
					explo.setSize((int)explo.getSize()+6);
				}
						
			}
			
		}
		
		boolean same = true;
		for(int i = 0;i<objects.size();i++){
			if (!objs.get(i).equals(objects.get(i)) || 
					(objs.get(i).getName().equals("WeaponTimedGrenade") && objs.get(i).getInUse()) ||
					(objs.get(i).getName().equals("Explosion") && objs.get(i).getInUse())) {
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
	public synchronized void updateFrame(Move move) {
		if(freeState) { // If the engine is in free-physics mode then the move is irrelevant,
			freeSim();  // just simulate another frame.
		}
		else if (move.getWeaponMove()) {
			WeaponMove wepMove = (WeaponMove)move;
			System.out.println("Weapon spawning at: " + wepMove.getPos());
			PhysObject wep = null;
			//switch(wepMove.wepType()){
			switch(weaponType){
			case "ExplodeOnImpact": wep = new ExplodeOnImpact(
					wepMove.getPos(), wepMove.getXvel(), wepMove.getYvel(), true); break;
			//case "ExplodeOnImpact":  wep = new TimedGrenade(

			//		wepMove.getPos(), wepMove.getXvel(), wepMove.getYvel(), true); break;
			case "TimedGrenade": wep = new TimedGrenade(
					wepMove.getPos(), wepMove.getXvel(), wepMove.getYvel(), true); break;
			case "Missile": wep = new Missile(
					wepMove.getPos(), wepMove.getXvel(), wepMove.getYvel(), true); break;
			case "LongJump": wep = getWeapons().get(0); activePlayer.setYvel(wepMove.getYvel());
					activePlayer.setXvel(wepMove.getXvel()); break;
			default: System.out.println("Weapon move parsing error"); break;
			}
			freeState = true;
			for(int i=0;i<objects.size();i++){
				if(objects.get(i).getName().startsWith("Weapon")){
					objects.remove(i);
				}
			}
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
				if (checkForWinner() != -1){
					if(debug)System.out.println("winner?");
					int won = findPlayer();
					setWinner(won);
					turn.endItAll();
				}
				incrementTurn();
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
		
		
		if (getExplosion().size()>0){
			
			
				//if(!input.contains("setExp")){
					
					//if(!input.contains("setUse")){
						
						if(!input.contains("None")){
							
						
							if (getExplosion().get(0).getInUse()){
								
							
								return;
							}
						}
					//}
				//}
			
		}
		
		Square active = (Square)getActivePlayer();
		
		if (input.contains("AItakesashotx86")){
			weaponsopen = true;
			String[] AIatk = input.split(",");
			Double xVel = Double.parseDouble(AIatk[0]);
			Double yVel = Double.parseDouble(AIatk[1]);
			//if (AIatk[2].equals(players[player])) {
				WeaponMove wmv = new WeaponMove(weaponType,new Point2D.Double(active.getPos().getX(), active.getPos().getY()+25),xVel,yVel);
				updateFrame(wmv);
				if (q.size() > 0)
					q.remove();
				q.add(objects);
			//}
			//else return;
		}
		
		if(input.length() >= 7 && input.substring(0, 7).equals(("Pressed"))) {
			if(!(input.substring(11, input.length()).equals(players[player]))){
				return;
			}
			String input1 = input.substring(8, 9);
			String input2 = input.substring(9, 10);
		
			//System.out.println(inputKey);
			if(input1.equals(" ")) input1 = "None";
			else if(input1.equals("A")) input1 = "Left";
			else if(input1.equals("D")) input1 = "Right";
			active.setFacing(input1);
			Move mv = new Move(active.getColour(),active.getSquareID(), input1, input2.equals("W"));
			updateFrame(mv);
			if (q.size() > 0)
				q.remove();
			q.add(objects);


		}
		else if(input.contains("Clicked")){
			//System.out.println(input);
			
			String[] inputArray = new String[3];
			inputArray = input.split(" ");
			
			//System.out.println(inputArray[2]);
			
			if( !(inputArray[3].equals(players[player]))  ){
				return;
			}
			//if(!(input.substring(36, input.length()).equals(players[player]))){
				//return;
			//}
			if(weaponsopen){
				
				for( PhysObject obj : objects){
					
					if(obj.getName().contains("Target")){
						
						obj.setInUse(false);
					}
				}
				
				int xs = input.indexOf('x');
				int xe = input.indexOf(',');
				//String xc = input.substring(xs+2, xe);
				String xc = input.split(" ")[1];
				int ye = input.indexOf(']');
				//String yc = input.substring(xe+3, ye);
				String yc = input.split(" ")[2];
				System.out.println("xc = " + xc + "and yc = " + yc);
				
				Double x = Double.parseDouble(xc);
				Double y = Double.parseDouble(yc);
				Double x2 = active.getPos().getX();
				Double y2 = active.getPos().getY();
				
				System.out.println("ActivePlayer is at: " + active.getPos());
				System.out.println("Mouse press is at: " + x + ", " + y);
				
				Double factor; //Need to check if the value is behind the player.
				if (x < x2){
					factor = -1.0;
				}else{
					factor = 1.0;
				}
				
				Double yfactor;
				if(y < y2){
					yfactor = -1.0;
				} else{
					yfactor = 1.0;
				}
				
				//Use some basic geometry to better work out how a shot is fired
				WeaponMove wmv;
				Double xVel = (Math.abs(x2-x)/800)*30;
				Double yVel = (Math.abs(y2-y)/450)*30;
				

				wmv = new WeaponMove(weaponType,new Point2D.Double(active.getPos().getX()+10, active.getPos().getY()+10),xVel*factor,yVel*yfactor);
				//wmv = new WeaponMove(weaponType,new Point2D.Double(active.getPos().getX(), active.getPos().getY()+25),5,10);
				System.out.println("wep xvel is: " + xVel);
				System.out.println("wep yvel is: " + yVel);
				
				getTargetLine().get(0).setInUse(false);
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
			String[] wepA = input.split(",");
			//System.out.println(wepA[2] +"contents of array");
			if (!(wepA[2].equals(players[player]))){
				return;
			}
			if(debugL) System.out.println("Now a weapon in use");
			this.weaponType = wepA[1];
			weaponsopen = true;

			
		}
		else if (input.contains("setExp")){
			
			for(PhysObject exp : this.getExplosion()){
				((Explosion) exp).setSize(Integer.parseInt(input.substring(7)));
				
			}
				
		}
		else if (input.contains("setUse")){
			
			
			System.out.println("hello*******");
			System.out.println("size of this.getexp() " + this.getExplosion().size());
			
			
			
			for(PhysObject exp : this.getExplosion()){
				
				exp.setInUse(Boolean.parseBoolean(input.substring(7)));
				
			}
			
		}
		else if(input.contains("setTar")){
			
			//System.out.println("SET TAR SET TAR!");
			
			for(PhysObject obj : objects){
				
				if ( obj.getName().contains("Target")){
					
					
					//System.out.println("HELLO: " + input.substring(7));
					
					obj.setInUse(Boolean.parseBoolean(input.substring(7)));
					
					//System.out.println("HELLO2: " + obj.getInUse());
				}
			}
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
		
		this.playing = true;
		
		this.turn = new TurnMaster(this);
		turn.start();
		
		for (int i = 0; i < 4; i++){
			if (players[i] == null){
				players[i] = "";
			}
		}
	}
	/**
	 * Increments the active player
	 */
	public void incrementTurn(){
		weaponsopen = false;
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
		else {

		}
		setTurnFlag(true);
		servant.end();
		turn.resetTimer();
	}
	
	public void addName(String name){
		players[numberOfPlayers] = name;
		numberOfPlayers++;
	}
	
	/**
	 * Checks the arraylist of Squares to see if any two living squares have different players
	 * @return True if all living squares are played by the same player, false otherwise.
	 */
	public int checkForWinner(){
		ArrayList<PhysObject> chickenDinner = getSquares();
		int winner = -1;
		
		for(int i=0; i< chickenDinner.size()-1;i++){
			Square first = ((Square)chickenDinner.get(i));
			Square second = ((Square)chickenDinner.get(i+1));
			
			if((first.getAlive() && second.getAlive()))
			
				winner = first.getPlayerID();
				
				if(first.getPlayerID() != second.getPlayerID()){
					return -1;
				}
				else{
					
					winner = first.getPlayerID();
					
				}
		}
		
		return winner;
		
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
	
	/**
	 * Removes a name from the array of players (way more difficult than necessary, because someone used an array instead of an arraylist!).
	 * @param name The name to be removed.
	 */
	public void removeName(String name) {
		for(int i = 0; i < numberOfPlayers; i++) {
			if(players[i].equals(name)) {
				if(i < numberOfPlayers - 1) {
					String temp = players[i];
					players[i] = players[i + 1];
					players[i + 1] = temp;
				}
				else {
					players[numberOfPlayers - 1] = "";
					numberOfPlayers--;
				}
			}
		}
	}
	
	/**
	 * Sets the array of players.
	 * @param players The new array.
	 */
	public void setPlayers(String[] players) {
		this.players = players;
	}
	
	/**
	 * Returns and array of the players.
	 * @return The players.
	 */
	public String[] getPlayers() {
		return players;
	}
	
	public void startLocalTimer(){
		//System.out.println("Restarted the timer");
		this.servant = new TurnServant(this);
		servant.start();
		
//		if (player != 3){
//			player = player+1;
//		}else{
//			player = 0;
//		}
//		setActivePlayer(player,squareID);
	}

	public void setTime(int time){
		this.time = time;
	}
	public int getTime(){
		return time;
	}
	public void setTurnFlag(boolean set){
		this.turnChangedFlag = set;
	}
	public boolean getTurnFlag(){
		return turnChangedFlag;
	}
}
