package GameLogic;

import static org.junit.Assert.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runners.Parameterized.Parameters;
import org.junit.Test;
import junit.framework.TestCase;

public class PhysicsTest extends TestCase {
	//**WARNING** RUNNING THIS TEST CAUSES A LOUD SOUND **DO NOT WEAR HEADPHONES**
	
	private Board board;
	private ArrayList<PhysObject> objects;
	
	@Before
	public void setUp(){
		board = new Board("Battleground");
		String[] players = new String[4];
		for(int i = 0; i < players.length;i++){
			players[i] = ""+(i+1);
		}
		board.setPlayers(players);
		ArrayList<PhysObject> objs = new ArrayList<PhysObject>();
		for(int i=0; i < board.getObjects().size();i++){
			switch(board.getObjects().get(i).getName()) {
			case "TerrainBlock": objs.add(new TerrainBlock((TerrainBlock)board.getObjects().get(i))); break;
			case "Square": objs.add(new Square((Square)board.getObjects().get(i))); break;
			case "WeaponExplodeOnImpact": objs.add(new ExplodeOnImpact((ExplodeOnImpact)board.getObjects().get(i))); break;
			case "TargetLine": objs.add(new TargetLine((TargetLine)board.getObjects().get(i))); break;
			case "Explosion": objs.add(new Explosion((Explosion)board.getObjects().get(i))); break;
			case "Particle": objs.add(new Particle((Particle)board.getObjects().get(i))); break;
			}
		}
		objs.remove(1);
		objs.add(1,new Square("2",2,0,0,new Point2D.Double(300,150)));
		objects = objs;
	}
	
	/**
	 * Asserts the following:
	 * 1. The methods for returning data from the board work correctly.
	 * 2. No objects are changed after an empty move while not in free state.
	 * 3. The active player is incremented correctly following the exit of free state.
	 * 4. No objects are changed from free state, assuming they begin stationary on the floor.
	 * 5. Jumping works and wall collisions are correctly detected and resolved outside of free state.
	 * 6. Raising a block above the floor then initiating physics leaves it in the same position as it started.
	 * 7. A block's path in free state unfolds as it is meant to.
	 * 8. Throwing a bomb results in an explosion affecting blocks and squares, possibly knocking them off the map.
	 * Hence object collision detection and resolution are working as intended.
	 */
	@Test
	public void test() {
		assertTrue(board.getActivePlayer().equals(board.getActiveBoard()));
		assertTrue(board.getWeapons().get(0).equals(board.getExplodeOnImpact().get(0)));
		assertTrue(board.getMissile().isEmpty());
		assertTrue(board.getTimedGrenade().isEmpty());
		board.input("Pressed,  ,1");
		for(int i = 0;i<board.getObjects().size();i++){
			PhysObject obj = board.getObjects().get(i);
			assertTrue(obj.equals(objects.get(i))); //Assert no objects are changed after an empty move
		}
		
		board.setFreeState(true);
		while(board.getFreeState()){
			board.input("Pressed,  ,1");
		}
		assertTrue(((Square)board.getActivePlayer()).getPlayerID()==2);
		board.input("Pressed,  ,2");
		boolean same = true;
		for(int i = 0;i<board.getObjects().size()-2;i++){
			if(!board.getObjects().get(i).equals(objects.get(i))){
				same = false;
			}
		}
		assertTrue(same); //Assert no objects change state from freeSim, 
							//assuming they begin stationary on the floor
		
		board = new Board("Pyramid");
		String[] players = new String[4];
		for(int i = 0; i < players.length;i++){
			players[i] = ""+(i+1);
		}
		board.setPlayers(players);
		
		for(int i = 0; i < 3; i++){
			board.input("Pressed,A ,1");
		}
		board.input("Pressed, W,1");
		while(board.getActivePlayer().getPos().getY()<170){
			board.input("Pressed,  ,1");
		}
		board.input("Pressed,D ,1");
		board.input("Pressed,A ,1");
		while(board.getActivePlayer().getPos().getY()!=150){
			board.input("Pressed,  ,1");
		}
		assertTrue(board.getActivePlayer().getPos().getX()==166);
		//Assert jumping works and wall collisions are detected without being in free state
		
		board = new Board("Sandwich");
		board.setPlayers(players);
		
		board.getActivePlayer().setPos(new Point2D.Double(180, 170));
		board.setFreeState(true);
		while(board.getFreeState()){
			board.input("None");
		}
		assertTrue(board.getSquares().get(0).getPos().equals(new Point2D.Double(180,150)));
		//Asserts raising then dropping a block does not displace it
		
		board = new Board("Smile");
		board.setPlayers(players);
		
		board.getSquares().get(2).setXvel(-4);
		board.getSquares().get(2).setYvel(7.5);
		board.setFreeState(true);
		while(board.getFreeState()){
			board.input("None");
		}
		//Asserts the block ends up where it is meant to
		assertTrue(board.getSquares().get(0).getPos().equals(new Point2D.Double(280, 140.0)));
		
		board = new Board("No Hiding");
		board.setPlayers(players);
		
		board.input("50,-50,1,AItakesashotx86"); //Enter a move that should kill the red player
		while(board.getFreeState()){
			board.input("None");
		}
		assertFalse(board.getSquares().get(0).getInUse()); //Assert that the red player has in fact died
		
		board = new Board("X");
		board.setPlayers(players);
		
		board.input("setWep,TimedGrenade,1");
		board.input("setTar true");
		board.input("Clicked 520 170 1");
		while(board.getFreeState()){
			board.input("None");
		}
		assertTrue(board.getSquares().get(0).getPos().equals(new Point2D.Double(860, 328.5)));
		
		board.input("setWep,Missile,2");
		board.input("setTar true");
		board.input("Clicked 345 200 2");
		while(board.getFreeState()){
			board.input("None");
		}
		assertTrue(board.getSquares().get(1).getPos().equals(new Point2D.Double(-45, 352.5)));
		assertFalse(board.getSquares().get(1).getInUse());
		
		while(board.getSquares().get(2).getInUse()){
			board.input("Pressed,A ,3");
		}
		assertFalse(board.getSquares().get(2).getInUse());
	}
	
	@After
	public void tearDown(){
		board = null;
		objects = null;
	}
}
