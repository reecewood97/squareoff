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
	//WARNING RUNNING THIS TEST CAUSES A LOUD SOUND **DO NOT WEAR HEADPHONES**
	
	private Board board;
	private ArrayList<PhysObject> objects;
	
	@Before
	public void setUp(){
		board = new Board();
		board.setMap("map1");
		String[] players = new String[4];
		for(int i = 0; i < players.length;i++){
			players[i] = ""+(i+1);
		}
		board.setPlayers(players);
		board.input("Pressed,  ,1");
		ArrayList<PhysObject> objs = new ArrayList<PhysObject>();
		for(int i=0; i < board.getObjects().size();i++){
			switch(board.getObjects().get(i).getName()) {
			case "TerrainBlock": objs.add(new TerrainBlock((TerrainBlock)board.getObjects().get(i))); break;
			case "Square": objs.add(new Square((Square)board.getObjects().get(i))); break;
			case "WeaponExplodeOnImpact": objs.add(new ExplodeOnImpact((ExplodeOnImpact)board.getObjects().get(i))); break;
			case "WeaponTimedGrenade": objs.add(new TimedGrenade((TimedGrenade)board.getObjects().get(i))); break;
			case "WeaponMissile": objs.add(new Missile((Missile)board.getObjects().get(i))); break;
			case "TargetLine": objs.add(new TargetLine((TargetLine)board.getObjects().get(i))); break;
			case "Explosion": objs.add(new Explosion((Explosion)board.getObjects().get(i))); break;
			default: System.out.println("error copying arraylists in freeSim: " + board.getObjects().get(i).getName()); break;
			}
		}
		objects = objs;
	}
	
	/**
	 * Asserts the following:
	 * 1. No objects are changed after an empty move while not in free state.
	 * 2. The active player is incremented correctly following the exit of free state.
	 * 3. No objects are changed from free state, assuming they begin stationary on the floor.
	 * 4. Wall collisions are correctly detected and resolved outside of free state.
	 * 5. Raising a block above the floor then initiating physics leaves it in the same position as it started.
	 * 6. A block's path in free state unfolds as it is meant to.
	 * 7. Throwing a grenade results in an explosion affecting blocks and squares, possibly knocking them off the map.
	 * Hence object collision detection and resolution are working as intended.
	 */
	@Test
	public void test() {
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
		for(int i = 0;i<board.getObjects().size();i++){
			if(!board.getObjects().get(i).equals(objects.get(i))){
				same = false;
			}
		}
		assertTrue(same); //Assert no objects change state from freeSim, 
							//assuming they begin stationary on the floor
		
		for(int i = 0;i<6;i++){
			board.input("Pressed,A ,2");
		}
		assertTrue(board.getActivePlayer().getPos().getX()==280);
		//Assert wall collisions are detected without being in free state
		
		board.getActivePlayer().setPos(new Point2D.Double(300, 160));
		board.setFreeState(true);
		while(board.getFreeState()){
			board.input("Pressed,  ,2");
		}
		boolean same2 = true;
		for(int i = 0;i<board.getObjects().size();i++){
			if(!board.getObjects().get(i).equals(objects.get(i))){
				same2 = false;
			}
		}
		assertTrue(same2); //Asserts raising then dropping a block does not displace it
		
		board.getSquares().get(2).setPos(new Point2D.Double(330, 220));
		board.getSquares().get(2).setXvel(-4);
		board.getSquares().get(2).setYvel(7.5);
		board.setFreeState(true);
		while(board.getFreeState()){
			board.input("Pressed,  ,3");
		}
		//Asserts the block ends up where it is meant to
		assertTrue(board.getSquares().get(2).getPos().equals(new Point2D.Double(265.60000000000025, 180)));
		
		board.setFreeState(true);
		while(board.getFreeState()){
			board.input("Pressed,  ,4");
		}
		board.input("setWep,ExplodeOnImpact,1");
		board.input("setTar true");
		board.input("Clicked 135 200 1"); //Enter a move that should kill the red player
		while(board.getFreeState()){
			board.input("Pressed    d");
			board.input("None");
		}
		assertFalse(board.getSquares().get(0).getInUse()); //Assert the red player has in fact died
	}
	
	@After
	public void tearDown(){
		board = null;
		objects = null;
	}
}
