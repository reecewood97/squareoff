package ai;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import GameLogic.Board;
import GameLogic.Square;

// Coordinates size: 800 x 450

public class AI {
	
	private static final double maxVelocity = 0;
	private static final double gravity = 9.8;
	private int myID;
	private int myColour;
	private Point2D.Double myPos;
	private Board board;
	private int hDistance;
	private int vDistance;
	private int myPlayer;
	private double outAngle;
	private double outVelocity;
	
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
		double velocity = getVelocity();
		double angle = getAngle();
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
		
		return;
	}
	
	public void determineResult(){
		Point2D.Double target = getFinalDestination();
		double xdis = Math.abs(getAIPos().getX() - target.getX());
		double ydis = getAIPos().getY() - target.getY(); // no need to absolute
		double acc_angle = 45.0;
		double acc_velocity = maxVelocity/2;
		if (ydis < 0) {
			// angle larger than 45 degrees
			boolean hit = false;
			int state = calculation(acc_angle, acc_velocity);
			if (state == 0) {
				hit = true;
			}
			while (!hit) {
				if (state == 1) { // too close
					//hit = false;
					//while (!hit) {
						// angle increase by 3 degrees (?)
					acc_angle += 3.0;
					while (acc_velocity != maxVelocity) {
							// increase power
						acc_velocity += 10.0;
						state = calculation(acc_angle, acc_velocity);
						hit = isHit(state);
					//	}
						// power
					}
				}
				else if (state == 2) { // too far
					//hit = false;
					//while (!hit) {
						// angle increase by 3 degrees (?)
					acc_angle += 3.0;
					while (acc_velocity != 0) {
							// decrease power
						acc_velocity -= 10.0;
						state = calculation(acc_angle, acc_velocity);
						hit = isHit(state);
					//}
						// power
					}
				}
			}
			
			
			// output angle and power (force)
			setAngle(acc_angle);
			setVelocity(acc_velocity);
		}
		else {
			//try with 45 degrees and decrease it.
			boolean hit = false;
//			while (!hit) {
//				// angle decrease by 3 degrees (?)
//				acc_angle -= 3.0;
//				while (acc_velocity != maxVelocity) {
//					// increase power
//				}
//				// power
//			}
			int state = calculation(acc_angle, acc_velocity);
			if (state == 0) {
				hit = true;
			}
			while (!hit) {
				if (state == 1) { // too close
					//hit = false;
					//while (!hit) {
						// angle increase by 3 degrees (?)
					acc_angle += 3.0;
					while (acc_velocity != maxVelocity) {
							// increase power
						acc_velocity += 10.0;
						state = calculation(acc_angle, acc_velocity);
						hit = isHit(state);
					//	}
						// power
					}
				}
				if (state == 2) { // too far
					//hit = false;
					//while (!hit) {
						// angle increase by 3 degrees (?)
					acc_angle += 3.0;
					while (acc_velocity != 0) {
							// decrease power
						acc_velocity -= 10.0;
						state = calculation(acc_angle, acc_velocity);
						hit = isHit(state);
					//}
						// power
					}
				}
			}
			// output angle and power (force)
			setAngle(acc_angle);
			setVelocity(acc_velocity);
		}
	}
	
	public int calculation(double a, double v) {
		Point2D.Double target = getFinalDestination();
		double xdis = Math.abs(getAIPos().getX() - target.getX());
		double ydis = getAIPos().getY() - target.getY(); // no need to absolute
		double angle = a;
		double velocity = v;
		double r = 0;
		// R = v * cos(angle) * ((v * sin(angle)) + Math.sqrt((v * v * sin(angle) * sin(angle)) + 2 * g * ydis))
		r = (v * Math.cos(angle) * ((v * Math.sin(angle)) + Math.sqrt((v * v * Math.sin(angle) * Math.sin(angle)) + 2 * gravity * ydis))) / 2;
		
		if (r > (xdis - 5) && r < (xdis + 5)) {
			return 0;
		}
		else if (r < xdis) {
			return 1;
		}
		else {
			return 2;
		}
		
		//return 0 if accurate
		//return 1 if target not reach
		//return 2 if over target
	}
	
	public boolean isHit(int cal) {
		if (cal == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void moveLeft() {
		
	}
	
	public void moveRight() {
		
	}
	
	public void moveUp() {
		
	}
	
	public void setAngle(double angle_chosen) {
		this.outAngle = angle_chosen;
	}
	
	public void setVelocity(double velocity_chosen) {
		this.outVelocity = velocity_chosen;
	}
	
	public double getAngle() {
		return this.outAngle;
	}
	
	public double getVelocity() {
		return this.outVelocity;
	}
	
	
	public void changeAIPos() {
		ArrayList<Square> squares = board.getSquares();
		int numOfPlayers = squares.size();
		for (int i = 0; i < numOfPlayers; i++) {
			if (squares.get(i).getPlayerID() == myID) {
				setPos(squares.get(i).getPos());
			}
		}
	}
	
	public Point2D.Double getAIPos() {
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
			if (currentSquare.getPlayerID() != myID) {
				int enemyX = (int) currentSquare.getPos().getX();
				int enemyY = (int) currentSquare.getPos().getY();
				int myX = (int) getAIPos().getX();
				int myY = (int) getAIPos().getY();
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
