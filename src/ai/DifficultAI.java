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
	private Queue q;
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

		double finalX = 0;
		double finalY = 0;
		PhysObject finalBlock = null;
		
		if (thereAreObstacles()) {
			// Stage 3:
			// Decide whether go to defense (places that could be targeted by less enemy and/or blocks with higher hp) 
			// or attack when there is a clear target (a must hit enemy situation).
			for (PhysObject player:squares) {
				Square enemySquare = (Square) player;
				if (enemySquare.getPlayerID() != myPlayer) {
//							System.out.println(enemySquare.getPlayerID());
//							System.out.println(enemySquare.getPos());
					// get position of enemies 
					double enemyX = enemySquare.getPos().getX();
					double enemyY = enemySquare.getPos().getY();
					enemyposs.add(new Point2D.Double(enemyX, enemyY));
				}
			}
			
			int count = 0;
			int saveCount = 0;
			for (PhysObject block:blocks) {
				for (Point2D.Double ePos:enemyposs) {
					determineObstacle(block.getPos(), ePos);
					if (thereAreObstacles()) {
						count += 1;
					}
				}
				if (count > saveCount) {
					finalBlock = block;
					saveCount = count;
				}
				count = 0;
			}
			
			finalX = finalBlock.getPos().getX();
			finalY = finalBlock.getPos().getY();
			aiMoveCal(finalX, finalY);
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
		System.out.println(blocks);
		TerrainBlock targetBlock = null;
		int targetHealth = 999;
		for (int i = 0; i < numOfPlayers; i++) {
			Square targetSquare = (Square) squares.get(i);
			double targetX = targetSquare.getPos().getX();
			double targetY = targetSquare.getPos().getY();
			for (int j = 0; j < numOfBlocks; j++) {
				TerrainBlock oneBlock = (TerrainBlock) blocks.get(j);
				if ((oneBlock.getPos().getY() == targetY - 30.0) && (oneBlock.getPos().getX() >= targetX - 25.0) && (oneBlock.getPos().getX() <= targetX + 75.0)) {
//					&& (block.getPos().getX() <= myX + 25.0) && (block.getPos().getX() > myX)
					targetBlock = (TerrainBlock) oneBlock;
				}
			}
			System.out.println(targetBlock);
			if (targetBlock.getHealth() < targetHealth) {
				finalSquare = targetSquare;
				targetHealth = targetBlock.getHealth();
			}
			System.out.println(finalSquare);
		}
		
		
		// return the coordinates

		System.out.println(finalSquare.getPos());
		return finalSquare.getPos();
	}
	
}
