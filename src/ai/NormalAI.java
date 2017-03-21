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
public class NormalAI extends AI {
	
	private static final double maxVelocity = 100;
	private static final double gravity = 1.0;
	private int mySquareID; // Square ID
	private int myColour;
	private Point2D.Double myPos;
	private Board board;
	private int myPlayer; // AI Player ID
	private String myName;
	private double outAngle;
	private double outVelocity;
	private Queue q;
	private final double mistakeAngle = 4;
	private final double mistakeVelocity = 6;
	
	public NormalAI(int aiPlayer, int aiSquareID, int aiColour, Board board, String name) {
		super(aiPlayer, aiSquareID, aiColour, board, name);
		setMistake(mistakeAngle, mistakeVelocity);
		this.mySquareID = aiSquareID;
		this.myPlayer = aiPlayer;
		this.myColour = aiColour;
		this.board = board;
		this.myName = name;
	}
	
	public void aiMove() {
		
		if (getEndTurn()) {
			return ;
		}
		
		// Stage 2:
		// If the angle of shooting >90 or <0 but still cannnot find a shooting path, move to elsewhere
		// Usually places that has higher hp (defense), or, there is a clear shooting path (ai and enemy on the same level(y axis))
		determineResult();
		ArrayList<PhysObject> blocks = board.getBlocks();
		ArrayList<PhysObject> squares = board.getSquares();

		int aiX = (int) getAIPos().getX();
		int aiY = (int) getAIPos().getY();
		int finalX = 0;
		int finalY = 0;
		double targetX = aiX;
		double targetY = aiY;
		PhysObject finalSquare = null;
		System.out.println("obstacles: " + thereAreObstacles());;
		double finalDis = 9999999999999.0;
		if (thereAreObstacles()) {
			for (PhysObject player:squares) {
				Square enemySquare = (Square) player;
				if (enemySquare.getPlayerID() != myPlayer) {
//							System.out.println(enemySquare.getPlayerID());
//							System.out.println(enemySquare.getPos());
					// get position of enemies
					int enemyX = (int) enemySquare.getPos().getX();
					int enemyY = (int) enemySquare.getPos().getY();
					double xDis = aiX - enemyX;
					double yDis = aiY - enemyY;
					// calculate shortest displacement by pythagoras theorem
					double displacement = Math.sqrt((yDis * yDis) + (xDis * xDis));
//							System.out.println(displacement);
					if (displacement < finalDis) {
						finalDis = displacement;
						finalX = enemyX;
						finalY = enemyY;
						finalSquare = enemySquare;
					}
				}
			}
			System.out.println("Normal AI is going to attack: " + finalSquare.getPos() );
			for (PhysObject block:blocks) {
				if (((finalSquare.getPos().getY() - 30.0 <= block.getPos().getY() + 100.0) || (finalSquare.getPos().getY() - 30.0 >= block.getPos().getY() -100.0)) && ((finalSquare.getPos().getX() <= block.getPos().getX() +400.0) || (finalSquare.getPos().getY() >= block.getPos().getX() -400.0))) {
					targetX = block.getPos().getX();
					targetY = block.getPos().getY();
				}
			}
			aiMoveCal(targetX +50, targetY);
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
				if ((oneBlock.getPos().getY() == targetY - 30.0) && (oneBlock.getPos().getX() >= targetX - 50.0) && (oneBlock.getPos().getX() <= targetX + 100.0)) {
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
