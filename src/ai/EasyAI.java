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
	
	public void aiMove() {
		// go to the best position to attack target
		// checks the best position through physics engine (get coordinates)
		// move to the provided coordinate
		
		ArrayList<PhysObject> blocks = board.getBlocks();
		double myX = getAIPos().getX();
		double myY = getAIPos().getY();
		double targetX = myX;
		double targetY = myY - 30.0;
		TerrainBlock currentBlock = (TerrainBlock) blocks.get(0);
//				System.out.println("block 0: " + currentBlock.getPos());
		for (PhysObject block:blocks) {
			if ((block.getPos().getY() == myY - 30.0) && (block.getPos().getX() > myX) && (block.getPos().getX() <= myX + 50.0)) {
//						&& (block.getPos().getX() <= myX + 25.0) && (block.getPos().getX() > myX)
				currentBlock = (TerrainBlock) block;
			}
		}
//				System.out.println("block standing: " + currentBlock.getPos());
		int currentBlockHealth = currentBlock.getHealth();
		double distance = 99999999999.0;
		
		// fixed how to get the block an AI is standing on!!!!
		
		
		// Stage 1:
		// Only move if the blocks that it's standing on has low hp.
		// If the coordinate's block has low hp (e.g. cannot survive two hits), 
		// go to the blocks next to it which has higher hp.
		
		if (currentBlockHealth <= 2) {
			int largerHealth = currentBlockHealth;
			double xPos = currentBlock.getPos().getX();
			double yPos = currentBlock.getPos().getY();
			for (PhysObject block:blocks) {
				TerrainBlock searchBlock = (TerrainBlock) block;
				if (searchBlock.getHealth() >= largerHealth) {
					double sBlockX = searchBlock.getPos().getX();
					double sBlockY = searchBlock.getPos().getY();
					double xDis = xPos - sBlockX;
					double yDis = yPos - sBlockY;
					// calculate shortest displacement by pythagoras theorem
					double displacement = Math.sqrt((yDis * yDis) + (xDis * xDis));
					if (displacement > distance && displacement > 25.0) {
						distance = displacement;
						targetX = sBlockX;
						targetY = sBlockY;
					}
				}
			}
		}
		aiMoveCal(targetX, targetY);
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
