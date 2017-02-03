package ai;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import GameLogic.Board;
import GameLogic.Square;

// Coordinates size: 800 x 450

public class AI {
	
	private int myID;
	private int myColour;
	private Point2D.Double myPos;
	private Board board;
	private int hDistance;
	private int vDistance;
	private int myPlayer;
	
	public AI(int aiID, int aiColour, int aiPlayer, Board board, Point2D.Double startPos) {
		setID(aiID);
		setColour(aiColour);
		setPlayer(aiPlayer);
		this.board = board;
		setPos(startPos); // start position
	}
	
	private void setPlayer(int player) {
		this.myPlayer = player;
	}

	private void setPos(Point2D.Double pos) {
		this.myPos = pos;
	}

	private void setColour(int colour) {
		this.myColour = colour;
	}

	public void setID(int id) {
		this.myID = id;
	}

	private void determineState() {
		if(haveItems()) {
			// go get items
			// Then go attack
			aiMove();
			aiAttack();
			
			// More advance: locate item position, calculate time to reach item
			// 				 choose to get item and attack or attack directly
		}
		else {
			aiMove();
			aiAttack();
		}
	}
	
	public boolean haveItems() {
//		if (there are items){
//			return true;
//		}
//		else {
//			return false;
//		}
		return false;
	}
	
	private void aiAttack() {
		// choose the closest target at this moment (get it from server copy)
		Point2D.Double finalCoor = getFinalDestination();
		// attack the provided coordinate
		// 		by sending power, angle chosen to methods in other class.
		// More advance: choose enemy standing on a block that has less hp,
		// 				 then calculate will the grenade able to reach target (through physics engine)
		//				 if it can, attack, else, choose another target
	}
	
	private void aiMove() {
		// go to the best position to attack target
		// checks the best position through physics engine (get coordinates)
		// move to the provided coordinate
		
		// If the coordinate's block has low hp (e.g. cannot survive two hits), 
		// go to the blocks next to it which has higher hp
		
		getFinalDestination();
	}
	
	public void changeAIPos() {
		ArrayList<Square> squares = board.getSquares();
		int numOfPlayers = squares.size();
		for (int i = 0; i < numOfPlayers; i++) {
			if (squares.get(i).getID() == myID) {
				setPos(squares.get(i).getPos());
			}
		}
	}
	
	public Point2D.Double getPos() {
		return this.myPos;
	}
	
	public Point2D.Double getFinalDestination() {
		// Call functions that get enemy position from board
		ArrayList<Square> squares = board.getSquares();
		int numOfPlayers = squares.size();
		int finalX = 0;
		int finalY = 0;
		Square finalSquare = null;
		double finalDis = 0;
		for (int i = 0; i < numOfPlayers; i++) {
			Square currentSquare = squares.get(i);
			if (currentSquare.getID() != myID) {
				int enemyX = (int) currentSquare.getPos().getX();
				int enemyY = (int) currentSquare.getPos().getY();
				int myX = (int) getPos().getX();
				int myY = (int) getPos().getY();
				double xDis = myX - enemyX;
				double yDis = myY - enemyY;
				double displacement = Math.sqrt((yDis * yDis) + (xDis * xDis));
				if (displacement < finalDis) {
					finalDis = displacement;
					finalX = enemyX;
					finalY = enemyY;
					finalSquare = currentSquare;
				}
			}
		}
		return finalSquare.getPos();
		
		// Position of enemy 1
		
		// Position of enemy 2
		// Position of enemy 3
		// myPosition = getAIPosition();
		
		// calculate shortest displacement by pythagoras theorem
		// return the coordinates
	}
	
	public void setHorizontal(int distance) {
		this.hDistance = distance;
	}
	
	public void setVertical(int distance) {
		this.vDistance = distance;
	}
}
