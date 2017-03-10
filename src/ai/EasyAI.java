package ai;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import GameLogic.Board;
import GameLogic.PhysObject;
import GameLogic.Square;
import GameLogic.TerrainBlock;
import Networking.Queue;

/**
 * @author JeffLeung
 *
 */
public class EasyAI extends AI{
	
	private static final double maxVelocity = 100;
	private static final double gravity = 9.81;
	private int mySquareID; // Square ID
	private int myColour;
	private Point2D.Double myPos;
	private Board board;
	private int myPlayer; // AI Player ID
	private double outAngle;
	private double outVelocity;
	private Queue q;
	private final double mistakeAngle = 7;
	private final double mistakeVelocity = 10;

	public EasyAI(int aiPlayer, int aiSquareID, int aiColour, Board board) {
		super(aiPlayer, aiSquareID, aiColour, board);
		setMistake(mistakeAngle, mistakeVelocity);
		this.mySquareID = aiSquareID;
		this.myPlayer = aiPlayer;
		this.myColour = aiColour;
		this.board = board;
	}
	
	/**
	 * Determine a target to attack and calculate the position of the target
	 * Choosing target by the shortest displacement (by pythagoras theorem)
	 * @return the position of the chosen target
	 */
	@Override
	public Point2D.Double getFinalDestination() {
		// Call functions that get enemy position from board
		ArrayList<PhysObject> squares = board.getSquares();
		int numOfPlayers = squares.size();
		int myX = (int) getAIPos().getX();
		int myY = (int) getAIPos().getY();
		int finalX = 0;
		int finalY = 0;
		PhysObject finalSquare = null;
		
		// Calculation for EasyAI
		double finalDis = 9999999999999.0;
		for (int i = 0; i < numOfPlayers; i++) {
			Square enemySquare = (Square) squares.get(i);
			if (enemySquare.getPlayerID() != myPlayer) {
//				System.out.println(enemySquare.getPlayerID());
//				System.out.println(enemySquare.getPos());
				// get position of enemies
				int enemyX = (int) enemySquare.getPos().getX();
				int enemyY = (int) enemySquare.getPos().getY();
				double xDis = myX - enemyX;
				double yDis = myY - enemyY;
				
				// calculate shortest displacement by pythagoras theorem
				double displacement = Math.sqrt((yDis * yDis) + (xDis * xDis));
//				System.out.println(displacement);
				if (displacement < finalDis) {
					finalDis = displacement;
					finalX = enemyX;
					finalY = enemyY;
					finalSquare = enemySquare;
				}
			}
		}
		System.out.println(finalSquare);
		
		// return the coordinates

		System.out.println(finalSquare.getPos());
		return finalSquare.getPos();
	}
	
}
