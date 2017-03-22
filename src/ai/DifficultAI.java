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
 * @author JeffLeung
 *
 */
public class DifficultAI extends AI {

	private static final double maxVelocity = 100;
	private static final double gravity = 0.5;
	private int mySquareID; // Square ID
	private int myColour;
	private Point2D.Double myPos;
	private Board board;
	private int myPlayer; // AI Player ID
	private String myName;
	private double outAngle;
	private double outVelocity;
	private final int mistakeAngle = 2;
	private final int mistakeVelocity = 3;

	public DifficultAI(int aiPlayer, int aiSquareID, int aiColour, Board board, String name) {
		super(aiPlayer, aiSquareID, aiColour, board, name);
		setMistake(mistakeAngle, mistakeVelocity);
		this.mySquareID = aiSquareID;
		this.myPlayer = aiPlayer;
		this.myColour = aiColour;
		this.board = board;
		this.myName = name;
	}
	
	/**
	 * Movement of the Square of the AI player
	 * Current Stage: if the current block standing has less than 2 health, move to other position
	 */
	public void aiMove() {
		
		if (getEndTurn()) {
			return ;
		}
		
		determineResult();
		ArrayList<PhysObject> blocks = board.getBlocks();
		ArrayList<PhysObject> squares = board.getSquares();
		ArrayList<Point2D.Double> enemyposs = new ArrayList<Point2D.Double>();
		
		int aiX = (int) getAIPos().getX();
		int aiY = (int) getAIPos().getY();

		PhysObject finalSquare = null;
		
		double finalX = 0;
		double finalY = 0;
		PhysObject finalBlock = null;
		
		if (thereAreObstacles()) {
			System.out.println(myName + " no clear path");
			// Stage 3:
			// Decide whether go to defense (places that could be targeted by less enemy and/or blocks with higher hp) 
			// or attack when there is a clear target (a must hit enemy situation).
//			for (PhysObject player:squares) {
//				Square enemySquare = (Square) player;
//				if (enemySquare.getPlayerID() != myPlayer) {
////							System.out.println(enemySquare.getPlayerID());
////							System.out.println(enemySquare.getPos());
//					// get position of enemies 
//					double enemyX = enemySquare.getPos().getX();
//					double enemyY = enemySquare.getPos().getY();
//					enemyposs.add(new Point2D.Double(enemyX, enemyY));
//				}
//			}
//			
//			int count = 0;
//			int saveCount = 0;
//			for (PhysObject block:blocks) {
//				for (Point2D.Double ePos:enemyposs) {
//					determineResult();
//					determineObstacle(block.getPos(), ePos);
//					if (thereAreObstacles()) {
//						count += 1;
//					}
//				}
//				if (count > saveCount) {
//					finalBlock = block;
//					saveCount = count;
//				}
//				count = 0;
//			}
//			System.out.println(myName + " go to defend: " + finalBlock.getPos());
//			finalX = finalBlock.getPos().getX();
//			finalY = finalBlock.getPos().getY();
//			aiMoveCal(finalX, finalY);

			double finalDis = 0;
			for (PhysObject block:blocks) {
				double blockX = block.getPos().getX();
				double blockY = block.getPos().getY();
				double totaldis = 0;
				int numP = 0;
				for (PhysObject square:squares) {
					Square enemySquare = (Square) square;
					if (enemySquare.getPlayerID() != myPlayer && enemySquare.getInUse()) {
						numP += 1;
						double enemyX = enemySquare.getPos().getX();
						double enemyY = enemySquare.getPos().getY();
						double xDis = blockX- enemyX;
						double yDis = blockY - enemyY;
						// calculate shortest displacement by pythagoras theorem
						double displacement = Math.sqrt((yDis * yDis) + (xDis * xDis));
//							System.out.println(displacement);
//						if (displacement < finalDis) {
//							finalDis = displacement;
//							finalX = blockX;
//							finalY = blockY;
//							finalBlock = block;
//						}
						totaldis += displacement;
					}
				}
				double averagedis = totaldis / numP;
				if (averagedis > finalDis) {
					finalBlock = block;
					finalDis = averagedis;
				}
			}
			System.out.println(myName + " go to defend: " + finalBlock.getPos());
			finalX = finalBlock.getPos().getX();
			finalY = finalBlock.getPos().getY();
			aiMoveCal(finalX, finalY);
		}
		else {
			double aix = getAIPos().getX();
			double aiy = getAIPos().getY();
			System.out.println(myName + " at " + aix + ", " + aiy);
			System.out.println(myName + " ATTACCCCCKKKKK!");
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
		ArrayList<PhysObject> squares = board.getSquares();
		int numOfPlayers = squares.size();
		int myX = (int) getAIPos().getX();
		int myY = (int) getAIPos().getY();
		int finalX = 0;
		int finalY = 0;
		PhysObject finalSquare = null;
		
		// Calculation for NormalAI & DifficultAI
		ArrayList<PhysObject> blocks = board.getBlocks();
		int numOfBlocks = blocks.size();
		TerrainBlock targetBlock = null;
		int targetHealth = 999;
		double finalDis = 99999999.0;
		for (int i = 0; i < numOfPlayers; i++) {
			Square targetSquare = (Square) squares.get(i);
			if (targetSquare.getPlayerID() == myPlayer || !targetSquare.getInUse()) {
				continue;
			}
			double targetX = targetSquare.getPos().getX();
			double targetY = targetSquare.getPos().getY();
			for (int j = 0; j < numOfBlocks; j++) {
				TerrainBlock oneBlock = (TerrainBlock) blocks.get(j);
				if ((oneBlock.getPos().getY() == targetY - 30.0) && (oneBlock.getPos().getX() >= targetX - 20.0) && (oneBlock.getPos().getX() <= targetX + 60.0)) {
//							&& (block.getPos().getX() <= myX + 25.0) && (block.getPos().getX() > myX)
					targetBlock = (TerrainBlock) oneBlock;
					break;
				}
			}
			
			double xDis = myX - targetX;
			double yDis = myY - targetY;
			
			double displacement = Math.sqrt((yDis * yDis) + (xDis * xDis));
			
			if ((targetBlock.getHealth() <= targetHealth) && displacement < finalDis) {
				finalSquare = targetSquare;
				targetHealth = targetBlock.getHealth();
				finalDis = displacement;
			}
		}
		
		System.out.println("target block: " + targetBlock);
		
		// return the coordinates
		System.out.println("closest target with low hp: " + finalSquare.getPos());
		return finalSquare.getPos();
	}
	
}
