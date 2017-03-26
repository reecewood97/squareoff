package GameLogic;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class provides testing for; Non-physics parts of the board class Move
 * Class Square class TerrainBlock class TurnMaster class TurnServant class
 */
public class GameLogicJUnit {
	private Board board;
	private Move move;
	private Square square;
	private TerrainBlock block;
	private TurnMaster master;
	private TurnServant servant;

	@Before
	public void setUp() {
		this.board = new Board("Battleground");
		this.square = new Square(0, 0, 0, new Point2D.Double(0.0, 0.0));//
		this.master = new TurnMaster(board);//
		this.servant = new TurnServant(board);//
	}

	@After
	public void tearDown() {
		master.interrupt();
		servant.interrupt();
	}

	@Test
	public void test() {
		// Start simple and work on the TurnServant
		// Start the thread and check the first tick
		servant.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
		}
		//assertTrue(board.getTime() == 20);

		// Test again 2 seconds later
		try {
			Thread.sleep(2000);
			//assertTrue(board.getTime() == 18);
		} catch (InterruptedException e) {
		}

		// Make sure the thread can be correctly stopped
		servant.interrupt();
		board.setTime(0);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
		}
		//assertTrue(board.getTime() == 0);

		
		//TurnMaster tests
		// Start the thread and check the first tick
		master.start();
		try {
			Thread.sleep(45);
		} catch (InterruptedException e1) {
		}
		//assertTrue(board.getTime() == 1);
		
		//Check again a few ticks later
		try {
			Thread.sleep(85);
		} catch (InterruptedException e1) {
		}
		//assertTrue(board.getTime() == 3);
		
		//Make sure the reset function works
		master.resetTimer();
		try {
			Thread.sleep(45);
		} catch (InterruptedException e1) {
		}
		//assertTrue(board.getTime() == 1);
		
		//Make sure the timer stops fiddling once it's been stopped
		master.interrupt();
		try {
			Thread.sleep(45);
		} catch (InterruptedException e1) {
		}
		//assertTrue(board.getTime() == 2);

		
		
		//Test for TerrainBlock, wont bother including trivial get and sets directly.
		//Make a new, very standard block.
		this.block = new TerrainBlock(1, 0, new Point2D.Double(0.0, 0.0), true);
		//Start by testing the shallow copy stuff - Also servers to test getters
		TerrainBlock test = new TerrainBlock(block);
		assertEquals(block.getHealth(),test.getHealth());
		assertEquals(block.getType(),test.getType());
		assertEquals(block.isVisible(),test.isVisible());
		assertEquals(block.getInUse(),test.getInUse());
		assertEquals(block.getPos(),test.getPos());
		assertEquals(block.getName(),test.getName());
		
		//Now test dealing damage to a block
		block.damage(2);
		assertTrue(block.isVisible() == false);
		assertTrue(block.getInUse() == false);
		this.block = new TerrainBlock(5, 0, new Point2D.Double(0.0, 0.0), true);
		block.damage(2);
		assertTrue(block.isVisible() == true);
		assertTrue(block.getInUse() == true);
		this.block = new TerrainBlock(2, 0, new Point2D.Double(0.0, 0.0), true);
		block.setHealth(0);
		assertTrue(block.isVisible() == false);
		assertTrue(block.getInUse() == false);
		
		
		
		//We can't directly create moves and add them to the board so we need to just use the input method.
		//Set the board up for testing by acquiring the active player and adding myself as the first player.
		Square active = (Square) board.getActivePlayer();
		Point2D.Double current = active.getPos();
		board.addName("sam");
		//Test left moves
		String inputString = "Pressed " + "A" + " " + " sam";
		for (int i =0; i < 5; i++)
			board.input(inputString);	
		
		assertNotEquals(current, active.getPos());
		
		Point2D.Double leftShifted = new Point2D.Double(current.getX() -20,current.getY());
		assertEquals(leftShifted,active.getPos());
		//Test right moves
		inputString = "Pressed " + "D" + " " + " sam";
		for (int i =0; i < 5; i++)
			board.input(inputString);
		assertEquals(current,active.getPos());
		
		//Test jump moves
		inputString = "Pressed " + " " + "W" + " sam";
		board.input(inputString);
		assertTrue(active.getPos().getY()>current.getY());
		//Test that none moves deal with players in the air
		for (int i = 0; i < 3; i++)
			board.input(inputString);
		current = active.getPos();
		for (int i = 0; i < 10; i++)
			board.input("None");
		assertTrue(current.getY()>active.getPos().getY());
		
		//Test to make sure the wrong player can't take their turn
		board.addName("AI 1");
		board.addName("Fran");
		current = active.getPoint();
		inputString = "Pressed " + " " + "W" + " Fran";
		for (int i = 0; i < 10; i++)
			board.input(inputString);
		assertEquals(current, active.getPoint());
		
		//Test to make sure the turn increment works
		Square pastActive = (Square) board.getActivePlayer();
		board.incrementTurn();
		assertNotEquals(pastActive, board.getActivePlayer());
		
		//Test if AI attacks are treated correctly, as long as this doesn't error we know it's valid, due to strange layout
		int objectLst = board.getObjects().size();
		inputString = "5.0,5.0,AItakesashotx86 ";
		board.input(inputString);
		
		//Change the active player to "Fran" so we can test player firing
		board.incrementTurn();
		int x = 50;
		int y = 100;
		//We form a weapon firing String, though it wont work yet as no weapon has been selected
		inputString  = "Clicked " + x + " " + y + " " + "Fran";
		ArrayList<PhysObject> OG = board.getObjects();
		board.input(inputString);
		assertEquals(OG,board.getObjects());
		
		//Select a weapon now
		inputString = "setWep," + "ExplodeOnImpact" + "," + "Fran";
		board.input(inputString);
		inputString  = "Clicked " + x + " " + y + " " + "Fran";
		board.input(inputString);
		assertEquals(OG, board.getObjects());
		
		board.addName("David");
		assertTrue(board.checkForWinner(board.getObjects()) == -1);
		for (int i = 0; i<3; i++){
			active = (Square) board.getActivePlayer();
			active.setDead();
			board.incrementTurn();
		}
		
		assertTrue(board.checkForWinner(board.getObjects())==2);
		
		board.removeName("sam");
		assertFalse(board.getPlayerArray().contains("sam"));
		
		board.removeNameSimple("Fran");
		assertFalse(board.getPlayerArray().contains("Fran"));
		
		assertEquals(board.nonAIPlayers(), 3);
		
		board.startGame();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
		assertTrue(board.getTime() > 0);
		
	
	}
}
