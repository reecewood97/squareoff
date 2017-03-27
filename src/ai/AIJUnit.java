package ai;

import static org.junit.Assert.assertNotEquals;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import GameLogic.Board;
import GameLogic.Square;
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
	private Board board2;
	private String easyname;
	private String normalname;
	private String diffname;
	private int easyID;
	private int normalID;
	private int diffID;
	private EasyAI easyai2;
	
	private static final double maxVelocity = 100;
	
	public void setUp() {
		board = new Board("Battleground");
		board2 = new Board("X");
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
		easyai2 = new EasyAI(easyID, 0, easyID, board2, easyname);
		easyai2.changeAIPos();
	}
	
	public void tearDown() {
		easyai = null;
		normalai = null;
		diffai = null;
		board = null;
		easyai2 = null;
	}
	
	public void test() {
		// test determineResult
		// check whether the resulted angle is >0 and <=90
		// check whether the velocity is under the max velocity
		easyai.determineResult();
		double ea = easyai.getAngle();
		double ev = easyai.getVelocity();
		assertTrue(ea <= 90);
		assertTrue(ea > 0);
		assertTrue(ev <= maxVelocity);
		assertTrue(ev >= -1 * maxVelocity);
		
		normalai.determineResult();
		double na = normalai.getAngle();
		double nv = normalai.getVelocity();
		assertTrue(na <= 90);
		assertTrue(na > 0);
		assertTrue(nv <= maxVelocity);
		assertTrue(nv >= -1 * maxVelocity);
		
		diffai.determineResult();
		double da = diffai.getAngle();
		double dv = diffai.getVelocity();
		assertTrue(da <= 90);
		assertTrue(da > 0);
		assertTrue(dv <= maxVelocity);
		assertTrue(dv >= -1 * maxVelocity);
		
		easyai2.determineResult();
		double ea2 = easyai.getAngle();
		double ev2 = easyai.getVelocity();
		assertTrue(ea2 <= 90);
		assertTrue(ea2 > 0);
		assertTrue(ev2 <= maxVelocity);
		assertTrue(ev2 >= -1 * maxVelocity);
		
		easyai.alterResult();
		assertTrue(easyai.getAngle() > (ea - 8));
		assertTrue(easyai.getAngle() < (ea + 8));
		assertTrue(easyai.getVelocity() > (ev - 11));
		assertTrue(easyai.getVelocity() < (ev + 11));
		
		normalai.alterResult();
		assertTrue(normalai.getAngle() > (na - 5));
		assertTrue(normalai.getAngle() < (na + 5));
		assertTrue(normalai.getVelocity() > (nv - 7));
		assertTrue(normalai.getVelocity() < (nv + 7));
		
		diffai.alterResult();
		assertTrue(diffai.getAngle() > (da - 3));
		assertTrue(diffai.getAngle() < (da + 3));
		assertTrue(diffai.getVelocity() > (dv - 4));
		assertTrue(diffai.getVelocity() < (dv + 4));
		
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
		
		assertFalse(easyai.determineNBL(easyai.getAIPos().getX(), easyai.getAIPos().getY()));
		assertFalse(easyai.determineNBR(easyai.getAIPos().getX(), easyai.getAIPos().getY()));
		assertFalse(easyai.determineObsL(easyai.getAIPos().getX(), easyai.getAIPos().getY()));
		assertFalse(easyai.determineObsR(easyai.getAIPos().getX(), easyai.getAIPos().getY()));
		
		assertTrue(easyai.determineNBD(20, 50));
		AI copy = easyai;
		copy.moveLeft();
		copy.moveRight();
		assertTrue(copy.getAIPos().getX() == easyai.getAIPos().getX());
		
		copy.moveUp();
		copy = easyai;
		copy.moveUpLeft();
		copy.moveRight();
		assertTrue(copy.getAIPos().getX() == easyai.getAIPos().getX());
		copy.moveUpRight();
		copy.moveLeft();
		assertTrue(copy.getAIPos().getX() == easyai.getAIPos().getX());
		copy.sendAttack(45.0, 5);
		assertTrue(copy.getAIPos().getX() == easyai.getAIPos().getX());
		
		easyai.aiMoveCal(100.0, 140.0);
		copy = easyai;
		assertTrue(easyai.getAIPos() == copy.getAIPos());
		
		normalai.aiMoveCal(300.0, 195.0);
		copy = normalai;
		assertTrue(normalai.getAIPos() == copy.getAIPos());
		
		easyai.aiMoveCal(50.0, 100.0);
		copy = easyai;
		assertTrue(easyai.getAIPos() == copy.getAIPos());
		
		easyai.aiMoveCal(110.0, 100.0);
		copy = easyai;
		assertTrue(easyai.getAIPos() == copy.getAIPos());
		
		normalai.aiMoveCal(290.0, 195.0);
		copy = normalai;
		assertTrue(normalai.getAIPos() == copy.getAIPos());
	}
}
