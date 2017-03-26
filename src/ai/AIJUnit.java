package ai;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import GameLogic.Board;
import junit.framework.TestCase;

/**
 * @author JeffLeung
 *
 */
public class AIJUnit extends TestCase{
	
	private EasyAI easyai;
	private NormalAI normalai;
	private DifficultAI diffai;
	private Board board;
	private String easyname;
	private String normalname;
	private String diffname;
	private int easyID;
	private int normalID;
	private int diffID;
	
	private static final double maxVelocity = 100;
	
	public void setUp() {
		board = new Board("Battleground");
		easyname = "easyai";
		normalname = "normalai";
		diffname = "diffai";
		easyID = 1;
		normalID = 2;
		diffID = 3;
		easyai = new EasyAI(easyID, 0, easyID, board, easyname);
		normalai = new NormalAI(normalID, 0, normalID, board, normalname);
		diffai = new DifficultAI(diffID, 0, diffID, board, diffname);
		easyai.changeAIPos();
		normalai.changeAIPos();
		diffai.changeAIPos();
	}
	
	public void tearDown() {
		easyai = null;
		normalai = null;
		diffai = null;
		board = null;
	}
	
	public void test() {
		// test determineResult
		// check whether the resulted angle is >0 and <=90
		// check whether the velocity is under the max velocity
		easyai.determineResult();
		assertTrue(easyai.getAngle() <= 90);
		assertTrue(easyai.getAngle() > 0);
		assertTrue(easyai.getVelocity() <= maxVelocity);
		assertTrue(easyai.getVelocity() >= -1 * maxVelocity);

		normalai.determineResult();
		assertTrue(normalai.getAngle() <= 90);
		assertTrue(normalai.getAngle() > 0);
		assertTrue(normalai.getVelocity() <= maxVelocity);
		assertTrue(normalai.getVelocity() >= -1 * maxVelocity);
		
		diffai.determineResult();
		assertTrue(diffai.getAngle() <= 90);
		assertTrue(diffai.getAngle() > 0);
		assertTrue(diffai.getVelocity() <= maxVelocity);
		assertTrue(diffai.getVelocity() >= -1 * maxVelocity);
		
		// test dontKillMyself
		// put two points in it and test if it is correct
		assertTrue(easyai.dontKillMyself(100, 150, 160, 150));
		assertTrue(easyai.dontKillMyself(100, 150, 100, 150));
		assertFalse(easyai.dontKillMyself(100, 150, 200, 150));
		assertFalse(easyai.dontKillMyself(100, 150, 110, 100));
		assertTrue(easyai.dontKillMyself(100, 150, 40, 150));
		assertTrue(easyai.dontKillMyself(100, 150, 80, 150));
		assertTrue(easyai.dontKillMyself(100, 150, 120, 150));
		
		// test getFinalDestination
		// test whether the ais get a target destination which is not equal to its own position
		Point2D.Double easyt = easyai.getFinalDestination();
		Point2D.Double normalt = normalai.getFinalDestination();
		Point2D.Double difft = diffai.getFinalDestination();
		easyai.changeAIPos();
		normalai.changeAIPos();
		diffai.changeAIPos();
		assertTrue(easyt != easyai.getAIPos());
		assertTrue(normalt != normalai.getAIPos());
		assertTrue(difft != diffai.getAIPos());
	}
}
