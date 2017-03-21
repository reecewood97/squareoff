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
	
	private Board board;
	private ArrayList<PhysObject> objects;
	private ArrayList<PhysObject> squares;
	
	@Before
	public void setUp(){
		board = new Board("map1");
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
	
	@Test
	public void test() {
		board.input("Pressed,  ,1");
		//board.input("setWep,ExplodeOnImpact,1");
		for(int i = 0;i<board.getObjects().size();i++){
			PhysObject obj = board.getObjects().get(i);
			assertTrue(obj.equals(objects.get(i)) || !obj.getName().equals("Square") || ((Square)obj).getPlayerID()==1);
		}
		board.setFreeState(true);
		board.input("Pressed,  ,1");
		board.input("Pressed,  ,1");
		board.input("Pressed,  ,2");
		boolean same = true;
		for(int i = 0;i<board.getObjects().size();i++){
			if(!board.getObjects().get(i).equals(objects.get(i))){
				same = false;
			}
		}
		assertFalse(same);
		Square square = new Square("2", 2, 0, 0, new Point2D.Double(300, 150));
		square.setYvel(-0.5);
		//System.out.println(((Square)board.getActivePlayer()).getPlayerID());
		assertTrue(board.getActivePlayer().equals(square));
	}
	
	@After
	public void tearDown(){
		board = null;
		objects = null;
		squares = null;
	}
}
