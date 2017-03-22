package GameLogic;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class provides testing for;
 * Non-physics parts of the board class
 * Move Class
 * Square class
 * TerrainBlock class
 * TurnMaster class
 * TurnServant class
 */
public class GameLogicJUnit {
	private Board board;
	private Move move;
	private Square square;
	private TerrainBlock block;
	private TurnMaster master;
	private TurnServant servant;

	@Before
	public void setUp(){
		this.board = new Board();
		this.move = new Move(0,0,"None",false);
		this.square = new Square(0,0,0, new Point2D.Double(0.0,0.0));
		this.block = new TerrainBlock(1,0,new Point2D.Double(0.0,0.0),false);
		this.master = new TurnMaster(board);
		this.servant = new TurnServant(board);
	}
	
	@After
	public void tearDown(){
		master.interrupt();
		servant.interrupt();
	}
	
	@Test
	public void test(){
		
		
	}
}
