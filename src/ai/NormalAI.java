package ai;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import GameLogic.Board;
import GameLogic.PhysObject;
import GameLogic.Square;
import GameLogic.TerrainBlock;
import Networking.Queue;

/**
 * The class of an AI player with normal difficulty. 
 * It does all the calculation that a normal AI needs.
 * @author JeffLeung
 *
 */
public class NormalAI extends AI {
	
	private static final double maxVelocity = 100;
	private static final double gravity = 1.0;
	private int mySquareID; // Square ID
	private int myColour;
	private Board board;
	private int myPlayer; // AI Player ID
	private String myName;
	private final double mistakeAngle = 4;
	private final double mistakeVelocity = 6;
	private boolean targetLocked = false;
	private Square target;
	
	/**
	 * Constructor that sets up AI with normal difficulty
	 * @param aiPlayer Player ID
	 * @param aiSquareID Square ID
	 * @param aiColour colour for this AI player
	 * @param board Board of the current game
	 * @param name name for this AI player
	 */
	public NormalAI(int aiPlayer, int aiSquareID, int aiColour, Board board, String name) {
		super(aiPlayer, aiSquareID, aiColour, board, name);
		setMistake(mistakeAngle, mistakeVelocity);
		this.mySquareID = aiSquareID;
		this.myPlayer = aiPlayer;
		this.myColour = aiColour;
		this.board = board;
		this.myName = name;
	}
	
	/**
	 * Determine the destination of the Square of the AI player and move the AI player
	 * Normal AI move when no shooting path is found, 
	 * it will move to position which is near target
	 * If clear shooting path is found, 
	 * stay and attack
	 */
	public void aiMove() {
		determineResult();
		aiMoveHelper();
	}
	
	/**
	 * Determine whether normal AI should move or not. 
	 * Helper method of aiMove().
	 */
	public void aiMoveHelper() {
		ArrayList<PhysObject> blocks = board.getBlocks();
		ArrayList<PhysObject> squares = board.getSquares();

		double aiX = getAIPos().getX();
		double aiY = getAIPos().getY();
		double finalX = 0;
		double finalY = 0;
		double targetX = aiX;
		double targetY = aiY - 30.0;
		PhysObject finalSquare = null;
//		System.out.println("obstacles: " + thereAreObstacles());
		double finalDis = 9999999999999.0;
		if (thereAreObstacles()) {
			for (PhysObject player:squares) {
				Square enemySquare = (Square) player;
				if (enemySquare.getPlayerID() != myPlayer) {
					// get position of enemies
					double enemyX = enemySquare.getPos().getX();
					double enemyY = enemySquare.getPos().getY();
					double xDis = aiX - enemyX;
					double yDis = aiY - enemyY;
					// calculate shortest displacement by pythagoras theorem
					double displacement = Math.sqrt((yDis * yDis) + (xDis * xDis));
					if (displacement < finalDis) {
						finalDis = displacement;
						finalX = enemyX;
						finalY = enemyY;
						finalSquare = enemySquare;
					}
				}
			}
//			System.out.println("Normal AI is going to attack: " + finalSquare.getPos() );
			if (((finalSquare.getPos().getY() + 50.0 <= aiY) && (finalSquare.getPos().getY() - 110.0 >= aiY)) && ((finalSquare.getPos().getX() + 160.0 <= aiX) && (finalSquare.getPos().getX() -120.0 >= aiX))) {
				targetX = aiX;
				targetY = aiY - 30.0;
			}
			else {
				for (PhysObject block:blocks) {
					if ((finalSquare.getPos().getY() - 30.0 == block.getPos().getY()) && ((finalSquare.getPos().getX() + 160.0 <= block.getPos().getX()) && (finalSquare.getPos().getX() -120.0 >= block.getPos().getX()))) {
						targetX = block.getPos().getX();
						targetY = block.getPos().getY();

						break;
					}
				}
			}
			setTargetLocked(true);
			setTarget((Square)finalSquare);
			aiMoveCal(targetX, targetY);
		}
		else {
			
			aiMoveCal(targetX, targetY);
		}
	}

	/**
	 * Determine a target to attack and calculate the position of the target
	 * Choosing target by the shortest displacement (by pythagoras theorem)
	 * @return the position of the chosen target
	 */
	@Override
	public Point2D.Double getFinalDestination() {
		// Call functions that get enemy position from board
		if (isTargetLocked()) {
			setTargetLocked(false);
			return getTarget().getPos();
		}
		// Call functions that get enemy position from board
		ArrayList<PhysObject> squares = board.getSquares();
		int numOfPlayers = squares.size();
		int myX = (int) getAIPos().getX();
		int myY = (int) getAIPos().getY();
		int finalX = 0;
		int finalY = 0;
		PhysObject finalSquare = null;
		
		// Calculation for NormalAI
		double finalDis = 9999999999999.0;
		for (int i = 0; i < numOfPlayers; i++) {
			Square enemySquare = (Square) squares.get(i);
			if (enemySquare.getPlayerID() != myPlayer && enemySquare.getAlive()) {
				// get position of enemies
				int enemyX = (int) enemySquare.getPos().getX();
				int enemyY = (int) enemySquare.getPos().getY();
				double xDis = myX - enemyX;
				double yDis = myY - enemyY;
				
				// calculate shortest displacement by pythagoras theorem
				double displacement = Math.sqrt((yDis * yDis) + (xDis * xDis));
				if (displacement < finalDis && !dontKillMyself(myX, myY, enemyX, enemyY)) {
					finalDis = displacement;
					finalX = enemyX;
					finalY = enemyY;
					finalSquare = enemySquare;
				}
			}
		}
		try {
			if (finalSquare.getPos() == null) {
				setObstacles(true);
				aiMoveHelper();
			}
			setObstacles(false);
			return finalSquare.getPos();
		}
		catch (NullPointerException e){
			setObstacles(true);
			aiMoveHelper();
		}
		
		// return the coordinates
		return finalSquare.getPos();
	}
	
	/**
	 * Set true if there was a target already
	 * @param lock true if there is a target already, false if there are no targets
	 */
	public void setTargetLocked(boolean lock) {
		this.targetLocked = lock;
	}
	
	/**
	 * Returns whether a target is already chosen
	 * @return true if target is already chosen, false if not
	 */
	public boolean isTargetLocked() {
		return this.targetLocked;
	}
	
	/**
	 * Set chosen target
	 * @param newTarget target
	 */
	public void setTarget(Square newTarget) {
		this.target = newTarget;
	}
	
	/**
	 * Get chosen target
	 * @return target
	 */
	public Square getTarget() {
		return this.target;
	}

}
